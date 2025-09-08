package org.example.taskflow.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.domain.comment.dto.*;
import org.example.taskflow.domain.comment.entity.Comment;
import org.example.taskflow.domain.comment.repository.CommentRepository;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.repository.TaskRepository;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    public CommentResponse createComment(Long taskId, Long userId, CommentCreateRequest commentCreateRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );
        User user = userRepository.getReferenceById(userId);

        // 부모 댓글이 없을 시
        Comment parent = null;
        if (commentCreateRequest.parentId() != null) {
            parent = commentRepository.findById(commentCreateRequest.parentId()).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER, "존재하지 않는 댓글입니다.")
            );

            // 부모 댓글의 부모값이 null이 아닐 시
            if (parent.getParent() != null) {
                throw new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER, "상위 댓글의 설정이 잘못 되어있습니다.");
            }
        }

        Comment comment = commentRepository.save(
                Comment.builder()
                        .content(commentCreateRequest.content())
                        .user(user)
                        .task(task)
                        .parent(parent)
                        .build()
        );

        CommentUserResponse commentUserResponse = getCommentUserResponse(comment.getUser());

        return getCommentResponse(comment, commentUserResponse);
    }

    // 댓글 목록 조회
    @Transactional(readOnly = true)
    public CommentPageResponse<CommentGetAllResponse> getComments(Long taskId, String sort, int page, int size) {
        taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        List<Comment> comments = commentRepository.findByTaskIdAndDeletedAtIsNull(taskId);

        // createdAt 기준 내림차순 sort
        Comparator<Comment> comparator = Comparator.comparing(Comment::getCreatedAt).reversed();
        // createdAt 기준 오름차순 sort
        if ("oldest".equalsIgnoreCase(sort)) {
            comparator = comparator.reversed(); // oldest 요청 시 오래된 순
        }

        List<Comment> parents = comments.stream()
                .filter(c -> c.getParent() == null)
                .sorted(comparator) // 부모 정렬
                .toList();

        Map<Long, List<Comment>> childMap = comments.stream()
                .filter(c -> c.getParent() != null)
                .collect(Collectors.groupingBy(c -> c.getParent().getId()));

        List<CommentGetAllResponse> flatList = new ArrayList<>();
        for (Comment parent : parents) {
            flatList.add(
                    new CommentParentResponse(
                            parent.getId(),
                            parent.getContent(),
                            parent.getTask().getId(),
                            parent.getUser().getId(),
                            getCommentUserResponse(parent.getUser()),
                            parent.getCreatedAt(),
                            parent.getUpdatedAt()
                    )
            );

            List<Comment> children = childMap.getOrDefault(parent.getId(), List.of()).stream()
                    .sorted(comparator) // 자식 정렬
                    .toList();

            for (Comment child : children) {
                flatList.add(
                        new CommentResponse(
                                child.getId(),
                                child.getContent(),
                                child.getTask().getId(),
                                child.getUser().getId(),
                                getCommentUserResponse(child.getUser()),
                                child.getParent().getId(),
                                child.getCreatedAt(),
                                child.getUpdatedAt()
                        )
                );
            }
        }

        int start = page * size;
        int end = Math.min(start + size, flatList.size());
        List<CommentGetAllResponse> pageContent = start > end ? List.of() : flatList.subList(start, end);

        return new CommentPageResponse<>(
                pageContent,
                (long) flatList.size(),
                (int) Math.ceil((double) flatList.size() / size),
                size,
                page
        );
    }

    // 댓글 수정
    public CommentResponse updateComment(Long taskId, Long commentId, CommentUpdateRequest commentUpdateRequest) {
        taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        Comment comment = commentRepository.findByIdAndDeletedAtIsNull(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER, "존재하지 않는 댓글입니다.")
        );

        comment.updateContent(commentUpdateRequest.content());

        CommentUserResponse commentUserResponse = getCommentUserResponse(comment.getUser());

        return getCommentResponse(comment, commentUserResponse);
    }

    // 댓글 삭제
    public boolean deleteComment(Long taskId, Long commentId) {
        taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        Comment comment = commentRepository.findByIdAndDeletedAtIsNull(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER, "존재하지 않는 댓글입니다.")
        );

        comment.softDelete();

        // 부모 댓글일 경우 자식 댓글도 삭제 (재귀적 삭제)
        if (comment.getParent() == null) {
            List<Comment> childComments = commentRepository.findByParent(comment);

            childComments.forEach(Comment::softDelete);
            return false;
        }

        return true;
    }

    // 헬퍼 메서드
    public static CommentUserResponse getCommentUserResponse(User user) {
        return new CommentUserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    // 헬퍼 메서드
    public static CommentResponse getCommentResponse(Comment comment, CommentUserResponse commentUserResponse) {
        if (comment.getParent() == null) {
            return CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .taskId(comment.getTask().getId())
                    .userId(comment.getUser().getId())
                    .user(commentUserResponse)
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .build();
        }
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .taskId(comment.getTask().getId())
                .userId(comment.getUser().getId())
                .user(commentUserResponse)
                .parentId(comment.getParent().getId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    // 헬퍼 메서드
    public static List<CommentResponse> getCommentsResponse(Page<Comment> comments) {
        List<Comment> commentList = comments.getContent();

        return commentList.stream()
                .map(comment -> {
                    CommentUserResponse user = getCommentUserResponse(comment.getUser());

                    return getCommentResponse(comment, user);
                })
                .toList();
    }
}
