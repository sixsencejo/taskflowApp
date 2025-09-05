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
            "select c  from Comment c " +
                    "where c.deletedAt = null order by c.task.id, coalesce(c.parent.id, c.id), " +
                    "c.createdAt desc"
    )
    Page<Comment> findAllWithParentOrderByDesc(Pageable pageable);

    @Query(
            "select c  from Comment c " +
                    "where c.deletedAt = null order by c.task.id, coalesce(c.parent.id, c.id), " +
                    "c.createdAt asc"
    )
    Page<Comment> findAllWithParentOrderByAsc(Pageable pageable);

    Optional<Comment> findByIdAndDeletedAtIsNull(Long commentId);

    List<Comment> findByParent(Comment parent);
}
