package org.example.taskflow.domain.comment.dto;

import org.example.taskflow.domain.user.enums.UserRole;

public record CommentUserResponse(
        
        Long id,
        String username,
        String name,
        String email,
        UserRole role
) {
}
