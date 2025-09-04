package org.example.taskflow.domain.task.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.SoftDeletableEntity;
import org.example.taskflow.domain.task.enums.Category;
import org.example.taskflow.domain.task.enums.Priority;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends SoftDeletableEntity {

    private String title;
    private String description;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private Priority priority;      // 우선 순위
    @Enumerated(EnumType.STRING)
    private Status status;          // 작업 상태
    @Enumerated(EnumType.STRING)
    private Category category;      // 활동 유형

    // 담당자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @Builder
    public Task(
            String title, String description, LocalDateTime dueDate, Priority priority,
            Category category, User assignee
    ) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = Status.TODO;
        this.category = category;
        this.assignee = assignee;
    }

    // Task 수정
    public void updateAll(
            String title, String description, LocalDateTime dueDate, Priority priority,
            Status status, User assignee
    ) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.assignee = assignee;
    }

    // Status 수정
    public void updateStatus(Status status) {
        this.status = status;
    }
}
