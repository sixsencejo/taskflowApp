package org.example.taskflow.domain.task.dto;

import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Category;
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
        Category category,
        Long assigneeId,
        Assignee assignee,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
)
{
    public static TaskResponse from(Task task) { // 정적 팩토리 메서드 2025-09-09 작성 이동재
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus(),
                task.getCategory(),
                task.getAssignee().getId(),
                Assignee.from(task.getAssignee()),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
