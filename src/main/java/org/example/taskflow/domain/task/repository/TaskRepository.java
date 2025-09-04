package org.example.taskflow.domain.task.repository;

import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatus(Status status, Pageable pageable);

    Page<Task> findByTitleContainingIgnoreCase(String search, Pageable pageable);

    Page<Task> findByAssignee(User assignee, Pageable pageable);
}
