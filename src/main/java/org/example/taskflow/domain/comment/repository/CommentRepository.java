package org.example.taskflow.domain.comment.repository;

import org.example.taskflow.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
            "select c from Comment c where c.deletedAt is null and c.task.id = :taskId order by coalesce(c.parent.id, c.id), c.createdAt desc "
    )
    Page<Comment> findAllWithParentOrderByDesc(Long taskId, Pageable pageable);

    @Query(
            "select c from Comment c where c.deletedAt is null and c.task.id = :taskId order by coalesce(c.parent.id, c.id), c.createdAt asc"
    )
    Page<Comment> findAllWithParentOrderByAsc(Long taskId, Pageable pageable);

    Optional<Comment> findByIdAndDeletedAtIsNull(Long commentId);

    List<Comment> findByParent(Comment parent);

    List<Comment> findByParentIsNull();

    List<Comment> findByParentIsNotNull();

    List<Comment> findByTaskIdAndDeletedAtIsNull(Long taskId);
}
