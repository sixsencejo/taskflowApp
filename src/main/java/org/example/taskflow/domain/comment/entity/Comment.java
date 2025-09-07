package org.example.taskflow.domain.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.SoftDeletableEntity;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.user.entity.User;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends SoftDeletableEntity {

    @NotEmpty
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder
    public Comment(String content, User user, Task task, Comment parent) {
        this.content = content;
        this.user = user;
        this.task = task;
        this.parent = parent;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}

