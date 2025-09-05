package org.example.taskflow.domain.comment.dto;

import java.util.List;

public record CommentPageResponse<T>(

        List<T> content,
        Long totalElement,
        int totalPages,
        int size,
        int number
) {
}
