package org.example.taskflow.common.utils;

import org.example.taskflow.common.dto.Response;

public class ResponseUtil {

    public static <T> Response<T> success(T data, String message) {
        return Response.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> Response<T> fail(String message) {
        return Response.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
