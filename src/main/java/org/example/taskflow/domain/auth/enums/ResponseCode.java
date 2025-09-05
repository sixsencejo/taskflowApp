package org.example.taskflow.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    AUTH_SIGNUP_RESPONSE("회원가입이 완료되었습니다."),
    AUTH_LOGIN_RESPONSE("로그인이 완료되었습니다."),
    AUTH_WITHDRAW_RESPONSE("회원탈퇴가 완료되었습니다."),
    AUTH_ACCOUNT_RECOVER_RESPONSE("계정 복구가 완료되었습니다.");

    private final String message;
}
