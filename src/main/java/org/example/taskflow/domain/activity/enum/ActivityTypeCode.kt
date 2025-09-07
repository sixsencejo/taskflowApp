package org.example.taskflow.domain.activity.enums

/**
 * activity_types 테이블의 type_code와 description을 상수로 관리하는 Enum입니다.
 * 애플리케이션 내에서 활동 유형을 참조할 때 이 Enum을 사용합니다.
 */
enum class ActivityTypeCode(
    val code: String,          // DB의 type_code 컬럼과 일치하는 값 (예: "TASK_CREATED")
    val description: String    // DB의 description 컬럼과 일치하는 값 (예: "작업 생성")
) {

    // Task 관련
    TASK_CREATED("TASK_CREATED", "작업 생성"),
    TASK_UPDATED("TASK_UPDATED", "작업 수정"),
    TASK_STATUS_CHANGED("TASK_STATUS_CHANGED", "작업 상태 변경"),
    TASK_DELETED("TASK_DELETED", "작업 삭제"),

    // User 관련
    USER_CREATED("USER_CREATED", "사용자 생성"),
    PROFILE_UPDATED("PROFILE_UPDATED", "프로필 수정"),

    // Comment 관련 (나중에 사용)
    COMMENT_CREATED("COMMENT_CREATED", "댓글 작성"),
    COMMENT_UPDATED("COMMENT_UPDATED", "댓글 수정"),
    COMMENT_DELETED("COMMENT_DELETED", "댓글 삭제")
}

