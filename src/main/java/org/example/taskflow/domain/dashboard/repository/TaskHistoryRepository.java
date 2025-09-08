package org.example.taskflow.domain.dashboard.repository;

import org.example.taskflow.domain.dashboard.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory,Long> {

    /**
     * 특정 날짜에 완료된 사용자의 작업 수 조회
     * (해당 날짜에 DONE 상태로 변경된 작업들)
     */
    @Query("SELECT COUNT(DISTINCT th.task.id) FROM TaskHistory th " +
            "WHERE th.user.id = :userId " +
            "AND th.status = 'DONE' " +
            "AND DATE(th.createdAt) = :date")
    int countDoneTasksByAssigneeIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    /**
     * 특정 날짜에 새로 할당된 작업 수 조회
     * (해당 날짜에 TODO 상태로 처음 기록된 작업들)
     */
    @Query("SELECT COUNT(DISTINCT th.task.id) FROM TaskHistory th " +
            "WHERE th.user.id = :userId " +
            "AND th.status = 'TODO' " +
            "AND DATE(th.createdAt) = :date " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM TaskHistory th2 " +
            "    WHERE th2.task.id = th.task.id " +
            "    AND th2.user.id = th.user.id " +
            "    AND th2.createdAt < th.createdAt" +
            ")")
    int countNewTasksByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    /**
     * 특정 날짜에 작업이 할당되거나 상태가 변경된 작업 수 조회
     * (해당 날짜에 활동이 있었던 고유한 작업들)
     */
    @Query("SELECT COUNT(DISTINCT th.task.id) FROM TaskHistory th " +
            "WHERE th.user.id = :userId " +
            "AND DATE(th.createdAt) = :date")
    int countTaskActivitiesByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}

