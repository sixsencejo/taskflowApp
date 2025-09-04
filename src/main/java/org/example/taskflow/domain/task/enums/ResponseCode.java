package org.example.taskflow.domain.task.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    TASK_CREATED("Task가 생성되었습니다."),
    TASK_FINDS("Task 목록을 조회했습니다."),
    TASK_FIND("Task를 조회했습니다."),
    TASK_UPDATED("Task가 수정되었습니다."),
    TASK_STATUS_UPDATED("작업 상태가 업데이트되었습니다."),
    TASK_DELETED("Task가 삭제되었습니다.");

    private final String message;
}
