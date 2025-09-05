package org.example.taskflow.domain.user.dto;

import org.example.taskflow.domain.user.enums.UserRole;

public record UserInfoForTaskResponse(

        Long id,
        String email,
        String name,
        UserRole role
) {
}
