package org.example.taskflow.domain.dashboard.dto;

import java.time.LocalDateTime;

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
