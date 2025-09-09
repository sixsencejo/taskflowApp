package org.example.taskflow.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    USER_FETCH_RESPONSE("사용자 정보를 조회했습니다."),
    USER_FOR_TASK_RESPONSE("요청이 성공적으로 처리되었습니다."),
    USER_FOR_TEAM_RESPONSE("사용 가능한 사용자 목록을 조회했습니다.");

    private final String message;
}

