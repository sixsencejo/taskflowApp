package org.example.taskflow.domain.task.dto;

import java.util.List;

public record TaskPageResponse<T>(
        List<T> content,
        Long totalElement,
        int totalPages,
        int size,
        int number
) {
}
