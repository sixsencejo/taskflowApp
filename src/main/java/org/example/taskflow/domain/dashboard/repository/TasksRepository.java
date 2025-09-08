package org.example.taskflow.domain.dashboard.repository;

import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    // count 관련
    /**
     * 특정 사용자에게 할당된 전체 작업 수 조회
     */
    int countByAssigneeId(Long assigneeId);

    /**
     * 특정 사용자의 특정 상태 작업 수 조회
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignee.id = :assigneeId AND t.status = :status")
    int countByAssigneeIdAndStatus(@Param("assigneeId") Long assigneeId, @Param("status") Status status);

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

    //find 관련
    /**
     * 특정 팀의 모든 작업 조회
     */
    @Query("SELECT t FROM Task t JOIN FETCH t.assignee u WHERE u.team.id = :teamId")
    List<Task> findTasksByAssigneeTeamId(@Param("teamId") Long teamId);

    /**
     * 특정 담당자의 오늘 할 작업 목록 조회
     */
    @Query("SELECT t FROM Task t JOIN FETCH t.assignee WHERE t.assignee.id = :assigneeId " +
            "AND t.dueDate >= :startOfDay AND t.dueDate <= :endOfDay " +
            "ORDER BY t.priority DESC, t.dueDate ASC")
    List<Task> findTodayTasksByAssigneeId(@Param("assigneeId") Long assigneeId,
                                          @Param("startOfDay") LocalDateTime startOfDay,
                                          @Param("endOfDay") LocalDateTime endOfDay);
    /**
     * 특정 담당자의 향후 작업 목록 조회
     */
    @Query("SELECT t FROM Task t JOIN FETCH t.assignee WHERE t.assignee.id = :assigneeId " +
            "AND t.dueDate >= :startTime " +
            "ORDER BY t.dueDate ASC, t.priority DESC")
    List<Task> findUpcomingTasksByAssigneeId(@Param("assigneeId") Long assigneeId,
                                             @Param("startTime") LocalDateTime startTime);

    /**
     * 특정 담당자의 기한이 지난 작업 목록 조회
     */
    @Query("SELECT t FROM Task t JOIN FETCH t.assignee WHERE t.assignee.id = :assigneeId " +
            "AND t.dueDate < :currentTime AND t.status != 'COMPLETED' " +
            "ORDER BY t.dueDate ASC")
    List<Task> findOverdueTasksByAssigneeId(@Param("assigneeId") Long assigneeId,
                                            @Param("currentTime") LocalDateTime currentTime);
}
