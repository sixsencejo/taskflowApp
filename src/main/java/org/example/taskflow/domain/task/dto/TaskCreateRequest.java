package org.example.taskflow.domain.task.dto;

import org.example.taskflow.domain.task.enums.Priority;

import java.time.LocalDateTime;

public record TaskCreateRequest(
        String title,
        String description,
        LocalDateTime dueDate,
        Priority priority
) {
}
