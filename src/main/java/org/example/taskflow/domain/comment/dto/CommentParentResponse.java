package org.example.taskflow.domain.comment.dto;

import java.time.LocalDateTime;

public record CommentParentResponse(
        
        Long id,
        String content,
        Long taskId,
        Long userId,
        CommentUserResponse user,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) implements CommentGetAllResponse {
}
