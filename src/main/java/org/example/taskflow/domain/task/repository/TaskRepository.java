package org.example.taskflow.domain.task.repository;

import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatusAndDeletedAtIsNull(Status status, Pageable pageable);

    Page<Task> findByTitleContainingIgnoreCaseAndDeletedAtIsNull(String search, Pageable pageable);

    Page<Task> findByAssigneeAndDeletedAtIsNull(User assignee, Pageable pageable);

    Page<Task> findByDeletedAtIsNull(Pageable pageable);

    Optional<Task> findByIdAndDeletedAtIsNull(Long taskId);
}
