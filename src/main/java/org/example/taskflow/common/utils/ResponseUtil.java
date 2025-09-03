package org.example.taskflow.common.utils;

import org.example.taskflow.common.dto.CommonResponse;

public class ResponseUtil {

    public static <T> CommonResponse<T> success(T data, String message) {
        return CommonResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> fail(String message) {
        return CommonResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
