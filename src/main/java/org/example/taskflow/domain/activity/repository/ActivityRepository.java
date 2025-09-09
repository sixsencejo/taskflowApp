package org.example.taskflow.domain.activity.repository;

import org.example.taskflow.domain.activity.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {
    // 개별 사용자 활동 조회
    @Query("SELECT a FROM Activity a " +
            "JOIN FETCH a.user " +
            "JOIN FETCH a.activityType " +
            "LEFT JOIN FETCH a.task " +
            "WHERE a.user.id = :userId " +
            "ORDER BY a.createdAt DESC")
    Page<Activity> findUserActivities(@Param("userId") Long userId, Pageable pageable);

}
