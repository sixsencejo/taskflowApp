package org.example.taskflow.domain.task.repository;

import org.example.taskflow.domain.task.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory,Long> {

    // 2025-09-09 수정 이동재
    /**
     * 특정 날짜에 완료된 담당자의 작업 수 조회
     * (해당 날짜에 DONE 상태로 변경된 작업들 - 담당자 기준)
     */
    @Query("SELECT COUNT(DISTINCT th.task.id) FROM TaskHistory th " +
            "WHERE th.task.assignee.id = :assigneeId " +  // user.id -> task.assignee.id 변경
            "AND th.status = 'DONE' " +
            "AND DATE(th.createdAt) = :date")
    int countDoneTasksByAssigneeIdAndDate(@Param("assigneeId") Long assigneeId, @Param("date") LocalDate date);

    /**
     * 특정 날짜에 새로 할당된 작업 수 조회
     * (해당 날짜에 TODO 상태로 처음 기록된 작업들 - 담당자 기준)
     */
    @Query("SELECT COUNT(DISTINCT th.task.id) FROM TaskHistory th " +
            "WHERE th.task.assignee.id = :assigneeId " +  // user.id -> task.assignee.id 변경
            "AND th.status = 'TODO' " +
            "AND DATE(th.createdAt) = :date " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM TaskHistory th2 " +
            "    WHERE th2.task.id = th.task.id " +
            "    AND th2.task.assignee.id = th.task.assignee.id " +
            "    AND th2.createdAt < th.createdAt" +
            ")")
    int countNewTasksByAssigneeIdAndDate(@Param("assigneeId") Long assigneeId, @Param("date") LocalDate date);
}

