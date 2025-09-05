package org.example.taskflow.domain.task.dto;

import org.example.taskflow.domain.task.enums.Status;

public record TaskUpdateStatusRequest(
        Status status
) {
}
