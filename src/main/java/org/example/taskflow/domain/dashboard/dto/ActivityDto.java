package org.example.taskflow.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ActivityDto {
    private Long id;
    private Long userId;
    private UserDto user;
    private String action;
    private String targetType;
    private Long targetId;
    private String description;
    private LocalDateTime createdAt;
}
