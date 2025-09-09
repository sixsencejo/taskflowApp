package org.example.taskflow.domain.dashboard.conveter;

import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.dashboard.dto.ActivityDto;
import org.example.taskflow.domain.dashboard.dto.UserDto;
import org.example.taskflow.domain.dashboard.utility.ActivityTypeMapper;
import org.example.taskflow.domain.task.entity.Task;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActivityDtoConverter {

    public ActivityDto convertToActivityDto(Activity activity) {
        String typeCode = activity.getActivityType().getTypeCode();

        return ActivityDto.builder()
                .id(activity.getId())
                .userId(activity.getUser().getId())
                .user(UserDto.from(activity.getUser()))
                .action(ActivityTypeMapper.toAction(typeCode))
                .targetType(ActivityTypeMapper.toTargetType(typeCode))
                .targetId(determineTargetId(activity, typeCode))
                .description(activity.getDescription())
                .createdAt(activity.getCreatedAt())
                .build();
    }

    private Long determineTargetId(Activity activity, String typeCode) {
        if (typeCode.startsWith("TASK_")) {
            return Optional.ofNullable(activity.getTask())
                    .map(Task::getId)
                    .orElse(null);
        } else if (typeCode.startsWith("USER_") || typeCode.equals("PROFILE_UPDATED")) {
            return activity.getUser().getId();
        }
        //현재 activity_entity에거 comment_id가 정의 되어 있지 않음
        return null;
    }
}
