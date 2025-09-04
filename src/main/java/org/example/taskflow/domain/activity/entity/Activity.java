package org.example.taskflow.domain.activity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "task_id", nullable = false)
//    private Task task;

    @Column(name = "activity_type", length = 50, nullable = false)
    private String activityType;

    @Column(name = "category", length = 20, nullable = false)
    private String category;

    @CreatedDate
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

//    @Builder
//    public Activity(User user, Task task, String activityType, String category, LocalDateTime createdAt) {
//        this.user = user;
//        this.task = task;
//        this.activityType = activityType;
//        this.category = category;
//    }
}