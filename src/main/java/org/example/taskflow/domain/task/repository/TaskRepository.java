package org.example.taskflow.domain.task.repository;

import org.example.taskflow.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
