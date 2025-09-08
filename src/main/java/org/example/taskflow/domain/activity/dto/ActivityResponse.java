package org.example.taskflow.domain.activity.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.user.dto.UserResponse;

import java.time.LocalDateTime;

@Getter
@Builder
public class ActivityResponse {

    // API 명세서와 일치하는 필드들
    private Long id;
    private String type;
    private Long userId;
    private UserResponse user;
    private Long taskId; // taskId 필드 추가
    private LocalDateTime timestamp;
    private String description;

    /**
     * Activity 엔티티를 API 명세서에 맞는 ActivityResponse DTO로 변환합니다.
     */
    public static ActivityResponse from(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .type(activity.getActivityType().getTypeCode())
                .userId(activity.getUser().getId())
                .user(UserResponse.from(activity.getUser()))
                // 활동 로그에 task가 없는 경우(예: 사용자 관련 로그)를 대비해 Null-safe 처리
                .taskId(activity.getTask() != null ? activity.getTask().getId() : null)
                .timestamp(activity.getCreatedAt())
                .description(activity.getDescription())
                .build();
    }
}
