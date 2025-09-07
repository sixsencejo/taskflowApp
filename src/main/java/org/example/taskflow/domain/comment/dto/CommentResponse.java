package org.example.taskflow.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse implements CommentGetAllResponse {
    private final Long id;
    private final String content;
    private final Long taskId;
    private final Long userId;
    private final CommentUserResponse user;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public CommentResponse(
            Long id,
            String content,
            Long taskId,
            Long userId,
            CommentUserResponse user,
            Long parentId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.content = content;
        this.taskId = taskId;
        this.userId = userId;
        this.user = user;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
