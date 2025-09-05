package org.example.taskflow.domain.task.dto;

import org.example.taskflow.domain.user.enums.UserRole;

public record UserInfoResponse(

        Long id,
        String email,
        String name,
        UserRole role
) {
}
