package org.example.taskflow.domain.activity.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.activity.entity.ActivityType;
import org.example.taskflow.domain.activity.enums.ActivityTypeCode;
import org.example.taskflow.domain.activity.repository.ActivityRepository;
import org.example.taskflow.domain.activity.repository.ActivityTypeRepository;
import org.example.taskflow.domain.task.dto.TaskResponse;
import org.example.taskflow.domain.task.dto.TaskUpdateStatusRequest;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.repository.TaskRepository;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ActivityLogAspect {

    private final ActivityRepository activityRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @AfterReturning(pointcut = "execution(* org.example.taskflow.domain.task.service.TaskService.createTask(..))", returning = "result")
    public void logTaskCreation(JoinPoint joinPoint, Object result) {
        TaskResponse createdTaskDto = (TaskResponse) result;
        taskRepository.findById(createdTaskDto.id()).ifPresent(task -> {
            String description = createTaskDescription(task);
            recordActivityLog(ActivityTypeCode.TASK_CREATED, description, task);
        });
    }

    @AfterReturning(pointcut = "execution(* org.example.taskflow.domain.task.service.TaskService.updateTask(..))", returning = "result")
    public void logTaskUpdate(JoinPoint joinPoint, Object result) {
        TaskResponse updatedTaskDto = (TaskResponse) result;
        taskRepository.findById(updatedTaskDto.id()).ifPresent(task -> {
            String description = createTaskDescriptionForUpdate(task);
            recordActivityLog(ActivityTypeCode.TASK_UPDATED, description, task);
        });
    }

    @AfterReturning(pointcut = "execution(* org.example.taskflow.domain.task.service.TaskService.updateStatus(..))", returning = "result")
    public void logTaskStatusUpdate(JoinPoint joinPoint, Object result) {
        TaskResponse updatedTaskDto = (TaskResponse) result;
        taskRepository.findById(updatedTaskDto.id()).ifPresent(task -> {
            String status = Arrays.stream(joinPoint.getArgs())
                    .filter(TaskUpdateStatusRequest.class::isInstance)
                    .map(arg -> ((TaskUpdateStatusRequest) arg).status().name())
                    .findFirst()
                    .orElse("[알 수 없음]");
            String description = createTaskDescriptionForStatusChange(task, status);
            recordActivityLog(ActivityTypeCode.TASK_STATUS_CHANGED, description, task);
        });
    }

    @AfterReturning(pointcut = "execution(* org.example.taskflow.domain.task.service.TaskService.deleteTask(..))")
    public void logTaskDeletion(JoinPoint joinPoint) {
        Long taskId = (Long) joinPoint.getArgs()[0];
        taskRepository.findById(taskId).ifPresent(task -> {
            String description = createTaskDescriptionForDeletion(task);
            recordActivityLog(ActivityTypeCode.TASK_DELETED, description, task);
        });
    }

//    /**
//     * CommentService의 createComment 메소드가 성공적으로 실행된 후 로그를 기록합니다.
//     */
//    @AfterReturning(
//            pointcut = "execution(* org.example.taskflow.domain.comment.service.CommentService.createComment(..))",
//            returning = "result"
//    )
//    public void logCommentCreation(JoinPoint joinPoint, Object result) {
//        Comment createdComment = (Comment) result;
//        String description = String.format("작업 '%s'에 새로운 댓글이 작성되었습니다.", createdComment.getTask().getTitle());
//        recordActivityLog(ActivityTypeCode.COMMENT_CREATED, description);
//    }
//
//    /**
//     * CommentService의 updateComment 메소드가 성공적으로 실행된 후 로그를 기록합니다.
//     */
//    @AfterReturning(
//            pointcut = "execution(* org.example.taskflow.domain.comment.service.CommentService.updateComment(..))",
//            returning = "result"
//    )
//    public void logCommentUpdate(JoinPoint joinPoint, Object result) {
//        Comment updatedComment = (Comment) result;
//        String description = String.format("작업 '%s'의 댓글이 수정되었습니다.", updatedComment.getTask().getTitle());
//        recordActivityLog(ActivityTypeCode.COMMENT_UPDATED, description);
//    }
//
//    /**
//     * CommentService의 deleteComment 메소드가 성공적으로 실행된 후 로그를 기록합니다.
//     */
//    @AfterReturning(
//            pointcut = "execution(* org.example.taskflow.domain.comment.service.CommentService.deleteComment(..))",
//            returning = "result"
//    )
//    public void logCommentDelete(JoinPoint joinPoint, Object result) {
//        Comment deleteComment = (Comment) result;
//        String description = String.format("작업 '%s'의 댓글이 삭제되었습니다.", deleteComment.getTask().getTitle());
//        recordActivityLog(ActivityTypeCode.COMMENT_DELETED, description);
//    }

    // ------------------- 설명 문자열 생성 헬퍼 메소드 -------------------

    private String createTaskDescription(Task task) {
        if (task.getCategory() != null) {
            return String.format("새로운 '%s' 유형의 작업 '%s'을(를) 생성했습니다.", task.getCategory().name(), task.getTitle());
        }
        return String.format("새로운 작업 '%s'을(를) 생성했습니다.", task.getTitle());
    }

    private String createTaskDescriptionForUpdate(Task task) {
        if (task.getCategory() != null) {
            return String.format("'%s' 유형의 작업 '%s'의 정보가 수정되었습니다.", task.getCategory().name(), task.getTitle());
        }
        return String.format("작업 '%s'의 정보가 수정되었습니다.", task.getTitle());
    }

    private String createTaskDescriptionForStatusChange(Task task, String status) {
        if (task.getCategory() != null) {
            return String.format("'%s' 유형의 작업 '%s'의 상태가 %s (으)로 변경되었습니다.", task.getCategory().name(), task.getTitle(), status);
        }
        return String.format("작업 '%s'의 상태가 %s (으)로 변경되었습니다.", task.getTitle(), status);
    }

    private String createTaskDescriptionForDeletion(Task task) {
        if (task.getCategory() != null) {
            return String.format("'%s' 유형의 작업 '%s'이(가) 삭제되었습니다.", task.getCategory().name(), task.getTitle());
        }
        return String.format("작업 '%s'이(가) 삭제되었습니다.", task.getTitle());
    }

    // ------------------- 공통 로직 -------------------

    /**
     * 실제 활동 로그를 생성하고 저장하는 공통 메소드
     */
    private void recordActivityLog(ActivityTypeCode type, String description, Task task) {
        User currentUser = getCurrentUser().orElse(null);
        if (currentUser == null) {
            log.warn("로그인한 사용자가 없어 활동 로그를 기록할 수 없습니다.");
            return;
        }

        ActivityType activityType = activityTypeRepository.findByTypeCode(type.getCode())
                .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 활동 유형 코드입니다: " + type.getCode()));

        Activity activity = Activity.builder()
                .user(currentUser)
                .activityType(activityType)
                .description(description)
                .task(task)
                .build();

        activityRepository.save(activity);
    }

    private Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return Optional.empty();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsernameAndDeletedAtIsNull(userDetails.getUsername());
    }
}

