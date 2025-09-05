package org.example.taskflow.domain.comment.dto;

import java.time.LocalDateTime;

public record CommentResponse(

        Long id,
        String content,
        Long taskId,
        CommentUserResponse user,
        Long parentId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
