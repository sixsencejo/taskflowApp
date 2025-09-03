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
        /*
        TODO: 추가할 것 UserDto
            "assignee": {
                "id": 1,
                "username": "johndoe",
                "name": "John Doe",
                "email": "john@example.com"
            }
         */
        AssigneeResponse assignee,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
