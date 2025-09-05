package org.example.taskflow.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.domain.comment.dto.CommentCreateRequest;
import org.example.taskflow.domain.comment.dto.CommentPageResponse;
import org.example.taskflow.domain.comment.dto.CommentResponse;
import org.example.taskflow.domain.comment.dto.CommentUserResponse;
import org.example.taskflow.domain.comment.entity.Comment;
import org.example.taskflow.domain.comment.repository.CommentRepository;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.repository.TaskRepository;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            if (parent.getId() != null) {
                throw new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER, "잘못된 요청입니다.");
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
    public CommentPageResponse<CommentResponse> getComments(Long taskId, String sort, int page, int size) {
        taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        Page<Comment> comments;
        if ("oldest".equals(sort)) {
            comments = commentRepository.findAllWithParentOrderByDescAndDeletedAtIsNull(
                    PageRequest.of(
                            page,
                            size
                    )
            );
        } else {
            comments = commentRepository.findAllWithParentOrderByAsc(
                    PageRequest.of(
                            page,
                            size
                    )
            );
        }

        List<CommentResponse> commentResponses = getCommentResponse(comments);

        return new CommentPageResponse<>(
                commentResponses,
                comments.getTotalElements(),
                comments.getTotalPages(),
                comments.getSize(),
                comments.getNumber()
        );
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
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getTask().getId(),
                commentUserResponse,
                comment.getParent().getId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    // 헬퍼 메서드
    public static List<CommentResponse> getCommentResponse(Page<Comment> comments) {
        List<Comment> commentList = comments.getContent();
        return commentList.stream()
                .map(comment -> {
                    CommentUserResponse user = getCommentUserResponse(comment.getUser());

                    return new CommentResponse(
                            comment.getId(),
                            comment.getContent(),
                            comment.getTask().getId(),
                            user,
                            comment.getParent().getId(),
                            comment.getCreatedAt(),
                            comment.getUpdatedAt()
                    );
                })
                .toList();
    }
}
