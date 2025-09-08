package org.example.taskflow.domain.task.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    TODO("할 일"),
    IN_PROGRESS("잔행 중"),
    DONE("완료");

    private final String description;

}
