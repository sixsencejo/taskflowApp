package org.example.taskflow.domain.task.dto;

import org.example.taskflow.domain.user.entity.User;

public record Assignee(

        Long id,
        String username,
        String name,
        String email
) {
    public static Assignee from(User user) { // 정적 팩토리 메서드 2025-09-09 작성 이동재
        return new Assignee(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail()
        );
    }
}
