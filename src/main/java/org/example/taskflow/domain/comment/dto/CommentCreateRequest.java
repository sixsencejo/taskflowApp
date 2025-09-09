package org.example.taskflow.domain.comment.dto;

public record CommentCreateRequest(
        
        String content,
        Long parentId
) {
}
