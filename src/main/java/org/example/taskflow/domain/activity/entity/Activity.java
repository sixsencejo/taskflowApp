package org.example.taskflow.domain.activity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.SoftDeletableEntity;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.user.entity.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * 사용자의 활동 기록을 저장하는 엔티티입니다. (activities 테이블)
 * 한국어 이름: 활동 로그
 */
@Getter
@Entity
@Table(name = "activities")
@SQLDelete(sql = "UPDATE activities SET deleted_at = NOW() WHERE id = ?") // ✅ 소프트 삭제 기능 추가
@Where(clause = "deleted_at IS NULL") // ✅ 삭제되지 않은 데이터만 조회
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends SoftDeletableEntity { // ✅ BaseTime 상속으로 변경

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 활동을 수행한 사용자 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 활동의 유형 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_type_id", nullable = false)
    private ActivityType activityType;

    /**
     * ✅ [핵심] 이 활동과 관련된 작업 (FK)
     * ActivitySpecification.hasTaskId()가 동작하기 위해 반드시 필요합니다.
     * 모든 활동이 Task에 연결되는 것은 아닐 수 있으므로 nullable = true 설정이 적절합니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    /**
     * 활동에 대한 상세 설명
     * 예: "사용자 'admin'이 로그인했습니다."
     */
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Builder
    public Activity(User user, ActivityType activityType, Task task, String description) {
        this.user = user;
        this.activityType = activityType;
        this.task = task; // ✅ Builder에 task 추가
        this.description = description;
    }
}

