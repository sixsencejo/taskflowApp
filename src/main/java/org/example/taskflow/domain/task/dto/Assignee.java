package org.example.taskflow.domain.task.dto;

public record Assignee(

        Long id,
        String username,
        String name,
        String email
) {
}
