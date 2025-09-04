package org.example.taskflow.domain.task.dto;

import org.example.taskflow.domain.task.enums.Priority;
import org.example.taskflow.domain.task.enums.Status;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDateTime dueDate,
        Priority priority,
        Status status,
        Assignee assignee,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
