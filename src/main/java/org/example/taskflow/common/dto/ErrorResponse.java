package org.example.taskflow.common.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponse (
        int status,
        String code,
        String message,
        String path,
        LocalDateTime timestamp,
        Map<String, String> data
) {

    public static ErrorResponse of(int status, String code, String message, String path) {

        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .data(null)
                .build();
    }

    public static ErrorResponse of(int status, String code, String message, String path, Map<String, String> data) {

        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
}
