package org.example.taskflow.domain.activity.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogActivity {

    /**
     * 기록될 활동의 유형 (예: "TASK_CREATED", "TASK_STATUS_CHANGED")
     */
    String activityType();

    /**
     * 로그에 기록될 설명의 형식입니다.
     * 예: "새로운 작업 '%s'을(를) 생성했습니다."
     * 여기서 '%s'는 메소드의 파라미터나 반환값에서 동적으로 채워집니다.
     */
    String descriptionFormat();
}
