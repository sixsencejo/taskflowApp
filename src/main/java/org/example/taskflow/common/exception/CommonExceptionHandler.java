package org.example.taskflow.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.taskflow.common.dto.CommonResponse;
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
    public ResponseEntity<CommonResponse<Object>> handleCustomException(CustomException e) {
        CommonResponse<Object> commonResponse = ResponseUtil.fail(e.getMessage());
        log.error("handleCustomException={}", commonResponse);
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(commonResponse);
    }

    // Valid 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        CustomException customException = new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER, message);
        log.warn("handleMethodArgumentNotValidException={}", customException);
        return handleCustomException(customException);
    }

    // 일반 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleException(Exception e) {

        log.error("Unhandled server exception occurred", e);

        CustomException customException = new CustomException(ErrorCode.PROCESSING_ERROR);
        return handleCustomException(customException);
    }

}
