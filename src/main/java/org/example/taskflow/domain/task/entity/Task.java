package org.example.taskflow.domain.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.taskflow.common.entity.SoftDeletableEntity;
import org.example.taskflow.domain.task.enums.Category;
import org.example.taskflow.domain.task.enums.Priority;
import org.example.taskflow.domain.task.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends SoftDeletableEntity {

    private String title;
    private String description;
    private Priority priority;
    private Status status;
    // 활동 유형
    private Category category;
    private LocalDateTime deadline;
}
