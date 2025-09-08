package org.example.taskflow.domain.dashboard.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_historys")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private Status status;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public TaskHistory(User user, Task task, Status status) {
        this.user = user;
        this.task = task;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}