package org.example.taskflow.domain.comment.repository;

import org.example.taskflow.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndDeletedAtIsNull(Long commentId);

    List<Comment> findByParent(Comment parent);

    List<Comment> findByTaskIdAndDeletedAtIsNull(Long taskId);
}
