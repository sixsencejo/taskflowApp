package org.example.taskflow.domain.comment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    COMMENT_CREATED_RESPONSE("댓글이 생성되었습니다."),
    COMMENT_FINDS_RESPONSE("댓글 목록을 조회했습니다."),
    COMMENT_UPDATED_RESPONSE("댓글이 수정되었습니다."),
    COMMENT_DELETED_RESPONSE("댓글이 삭제되었습니다."),
    COMMENT_DELETED_WITH_CHILD_RESPONSE("댓글과 대댓글들이 삭제되었습니다.");

    private final String message;
}
