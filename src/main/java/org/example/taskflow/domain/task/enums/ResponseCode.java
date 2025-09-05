package org.example.taskflow.domain.task.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    TASK_CREATED_RESPONSE("Task가 생성되었습니다."),
    TASK_FINDS_RESPONSE("Task 목록을 조회했습니다."),
    TASK_FIND_RESPONSE("Task를 조회했습니다."),
    TASK_UPDATED_RESPONSE("Task가 수정되었습니다."),
    TASK_STATUS_UPDATED_RESPONSE("작업 상태가 업데이트되었습니다."),
    TASK_DELETED_RESPONSE("Task가 삭제되었습니다."),
    TASK_FOR_USER_RESPONSE("요청이 성공적으로 처리되었습니다.");

    private final String message;
}
