package org.example.taskflow.domain.activity.repository;

import jakarta.persistence.criteria.Join;
import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.task.entity.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ActivitySpecification {

    /**
     * 특정 활동 유형 코드로 필터링하는 조건을 생성합니다.
     *
     * @param typeCode 필터링할 활동 유형 코드 (예: "TASK_CREATED")
     * @return Specification 객체
     */
    public static Specification<Activity> hasTypeCode(String typeCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("activityType").get("typeCode"), typeCode);
    }

    /**
     * 특정 사용자 ID로 필터링하는 조건을 생성합니다.
     *
     * @param userId 필터링할 사용자의 ID
     * @return Specification 객체
     */
    public static Specification<Activity> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("user").get("id"), userId);
    }

    /**
     * ✅ [추가] 특정 작업 ID로 필터링하는 조건을 생성합니다.
     * 이 메소드가 없어서 ActivityService에서 오류가 발생했습니다.
     *
     * @param taskId 필터링할 작업의 ID
     * @return Specification 객체
     */
    public static Specification<Activity> hasTaskId(Long taskId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("task").get("id"), taskId);
    }

    /**
     * 특정 날짜 범위로 필터링하는 조건을 생성합니다.
     *
     * @param startDate 시작 날짜
     * @param endDate   종료 날짜
     * @return Specification 객체
     */
    public static Specification<Activity> isBetweenDates(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            // Activity 엔티티와 Task 엔티티를 조인(연결)합니다.
            Join<Activity, Task> taskJoin = root.join("task");

            // 조인된 Task의 dueDate를 기준으로 날짜 범위를 검색합니다.
            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(taskJoin.get("dueDate"), startDate.atStartOfDay()),
                    criteriaBuilder.lessThan(taskJoin.get("dueDate"), endDate.plusDays(1).atStartOfDay())
            );
        };
    }
}

