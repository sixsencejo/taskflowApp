package org.example.taskflow.domain.dashboard.repository;

import org.example.taskflow.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    /**
     * 특정 사용자에게 할당된 전체 작업 수 조회
     */
    int countByAssigneeId(Long assigneeId);

    /**
     * 특정 사용자의 특정 상태 작업 수 조회
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignee.id = :assigneeId AND t.status = :status")
    int countByAssigneeIdAndStatus(@Param("assigneeId") Long assigneeId, @Param("status") String status);

    /**
     * 특정 사용자의 기한이 자난 작업 수 조회
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignee.id = :assigneeId " + "AND t.dueDate < :currentDateTime AND t.status != 'COMPLETED'")
    int countOverdueTasksByAssigneeId(@Param("assigneeId") Long assigneeId,
                                      @Param("currentDateTime") LocalDateTime currentDateTime);

    /**
     * 특정 사용자의 오늘 할 작업 수 조회
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignee.id = :assigneeId " + "AND t.dueDate >= :startOfDay AND t.dueDate <= :endOfDay")
    int countTodayTasksByAssigneeId(@Param("assigneeId") Long assigneeId,
                                    @Param("startOfDay") LocalDateTime startOfDay,
                                    @Param("endOfDay") LocalDateTime endOfDay);
}
