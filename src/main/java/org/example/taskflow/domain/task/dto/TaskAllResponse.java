package org.example.taskflow.domain.task.dto;

import java.util.List;

public record TaskAllResponse(
        List<TaskResponse> content,
        Long totalElement,
        Long totalPages,
        Long size,
        Long number
) {
}
