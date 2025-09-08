package org.example.taskflow.domain.dashboard.conveter;

import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.dashboard.dto.ActivityDto;
import org.example.taskflow.domain.dashboard.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class ActivityDtoConverter {

    public ActivityDto convertToActivityDto(Activity activity) {
        String typeCode = activity.getActivityType().getTypeCode();

        // ActivityType을 통해 action을 결정
        String action = convertActivityTypeToAction(typeCode);

        // ActivityType에 따라 targetType과 targetId를 설정
        String targetType = determineTargetType(typeCode);
        Long targetId = determineTargetId(activity, typeCode);

        return ActivityDto.builder()
                .id(activity.getId())
                .userId(activity.getUser().getId())
                .user(UserDto.builder()
                        .id(activity.getUser().getId())
                        .name(activity.getUser().getName())
                        .build())
                .action(action)
                .targetType(targetType)
                .targetId(targetId)
                .description(activity.getDescription())
                .createdAt(activity.getCreatedAt())
                .build();
    }

    private String determineTargetType(String typeCode) {
        if (typeCode.startsWith("TASK_")) {
            return "task";
        } else if (typeCode.startsWith("USER_") || typeCode.equals("PROFILE_UPDATED")) {
            return "user";
        } else if (typeCode.startsWith("COMMENT_")) {
            return "comment";
        }
        return null;
    }

    private Long determineTargetId(Activity activity, String typeCode) {
        if (typeCode.startsWith("TASK_") && activity.getTask() != null) {
            return activity.getTask().getId();
        } else if (typeCode.startsWith("USER_") || typeCode.equals("PROFILE_UPDATED")) {
            return activity.getUser().getId();
        } else if (typeCode.startsWith("COMMENT_")) {
            // Comment 엔티티가 있다면 해당 ID를 반환
            // 현재는 Comment 관계가 없으므로 null 반환
            return null;
        }
        return null;
    }

    private String convertActivityTypeToAction(String typeCode) {
        switch (typeCode) {
            case "TASK_CREATED":
                return "created_task";
            case "TASK_UPDATED":
                return "updated_task";
            case "TASK_STATUS_CHANGED":
                return "status_changed_task";
            case "TASK_DELETED":
                return "deleted_task";
            case "USER_CREATED":
                return "created_user";
            case "PROFILE_UPDATED":
                return "updated_profile";
            case "COMMENT_CREATED":
                return "created_comment";
            case "COMMENT_UPDATED":
                return "updated_comment";
            case "COMMENT_DELETED":
                return "deleted_comment";
            default:
                return typeCode.toLowerCase().replace("_", "_");
        }
    }
}
