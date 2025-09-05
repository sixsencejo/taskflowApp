package org.example.taskflow.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 사용자 관련 에러
    USERNAME_ALREADY_EXISTS(400, "이미 존재하는 사용자명입니다"),
    EMAIL_ALREADY_EXISTS(400, "이미 존재하는 이메일입니다"),
    INVALID_CREDENTIALS(401, "잘못된 사용자명 또는 비밀번호입니다"),
    AUTHENTICATION_REQUIRED(401, "인증이 필요합니다"),
    PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다"),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다"),
    USER_ALREADY_DELETED(400, "이미 탈퇴한 사용자입니다. 계정을 복구해 주세요."),
    EMAIL_ALREADY_DELETED(400, "이미 탈퇴된 이메일입니다. 계정을 복구해 주세요."),

    // 팀 관련 에러
    TEAM_NAME_ALREADY_EXISTS(400, "팀 이름이 이미 존재합니다"),
    TEAM_NOT_FOUND(404, "팀을 찾을 수 없습니다"),
    USER_NOT_TEAM_MEMBER(400, "사용자가 팀 멤버가 아닙니다"),
    USER_ALREADY_TEAM_MEMBER(400, "사용자가 이미 팀 멤버입니다"),

    // 태스크 관련 에러
    TASK_NOT_FOUND(404, "해당 ID의 작업을 찾을 수 없습니다"),
    INVALID_STATUS_VALUE(400, "유효하지 않은 상태값입니다"),

    // 일반적인 에러
    INVALID_REQUEST_PARAMETER(400, "잘못된 요청 파라미터입니다"),
    PROCESSING_ERROR(500, "처리 중 오류가 발생했습니다"),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다");

    private final int status;
    private final String message;
}
