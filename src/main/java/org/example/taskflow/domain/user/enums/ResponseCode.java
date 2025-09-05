package org.example.taskflow.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    USER_FETCH_RESPONSE("사용자 정보를 조회했습니다.");

    private final String message;
}

