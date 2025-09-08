INSERT IGNORE INTO taskflow.activity_types (id, type_code, description)
VALUES
  (1, 'TASK_CREATED', '작업 생성'),
  (2, 'TASK_UPDATED', '작업 수정'),
  (3, 'TASK_DELETED', '작업 삭제'),
  (4, 'TASK_STATUS_CHANGED', '작업 상태 변경'),
  (5, 'COMMENT_CREATED', '댓글 작성'),
  (6, 'COMMENT_UPDATED', '댓글 수정'),
  (7, 'COMMENT_DELETED', '댓글 삭제'),
  (8, 'USER_CREATED', '사용자 생성'),
  (9, 'PROFILE_UPDATED', '프로필 수정');