package org.example.taskflow.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.taskflow.common.dto.Response;
import org.example.taskflow.common.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response<Object>> handleCustomException(CustomException e) {
        Response<Object> response = ResponseUtil.fail(e.getMessage());
        log.error("handleCustomException={}", response);
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }

    // Valid 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        CustomException customException = new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER, message);
        log.warn("handleMethodArgumentNotValidException={}", customException);
        return handleCustomException(customException);
    }

    // 일반 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleException(Exception e) {

        log.error("Unhandled server exception occurred", e);

        CustomException customException = new CustomException(ErrorCode.PROCESSING_ERROR);
        return handleCustomException(customException);
    }

}
