package org.example.taskflow.domain.dashboard.service;

import org.example.taskflow.domain.search.dto.TaskSearchDto;
import org.example.taskflow.domain.task.dto.TaskPageResponse;
import org.example.taskflow.domain.task.dto.TaskResponse;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface TaskServiceImpl {

    /**
     * 특정 사용자에게 할당된 전체 작업 수 조회
     *
     * @param assigneeId 담당자 ID
     * @return 전체 작업 개수
     */
    int countByAssigneeId(Long assigneeId);

    /**
     * 특정 사용자의 특정 상태 작업 수 조회
     *
     * @param assigneeId 담당자 ID
     * @param status 작업 상태
     * @return 해당 상태의 작업 개수
     */
    int countByAssigneeIdAndStatus(Long assigneeId, Status status);

    /**
     * 특정 사용자의 기한이 자난 작업 수 조회
     *
     * @param assigneeId 담당자 ID
     * @param date 오늘 일자
     * @return 기한이 지난 작업 수 조회
     */
    int countOverdueTasksByAssigneeId(Long assigneeId, LocalDate date);

    /**
     * 특정 사용자의 오늘 할 작업 수 조회
     *
     * @param assigneeId 담당자 ID
     * @param date 오늘 일자
     * @return
     */
    int countTodayTasksByAssigneeId(Long assigneeId, LocalDate date);

    /**
     * 특정 팀에 속한 모든 작업을 조회
     *
     * @param teamId 팀 ID
     * @return 팀의 모든 작업 리스트
     */
    List<Task> findTasksByTeamId(Long teamId);

    /**
     * 특정 사용자의 오늘 해야 할 작업 목록을 조회
     *
     * @param assigneeId 담당자 ID
     * @param date 오늘 날짜
     * @return 오늘 작업 목록
     */
    List<Task> findTodayTasksByAssigneeId(Long assigneeId, LocalDate date);

    /**
     * 특정 사용자의 다가오는 작업 목록을 조회
     *
     * @param assigneeId 담당자 ID
     * @param date 기준 날짜
     * @return 향후 작업 목록
     */
    List<Task> findUpcomingTasksByAssigneeId(Long assigneeId, LocalDate date);

    /**
     * 특정 사용자의 기한이 지난 작업 목록을 조회
     *
     * @param assigneeId 담당자 ID
     * @param date 기준 날짜
     * @return 지연된 작업 목록
     */
    List<Task> findOverdueTasksByAssigneeId(Long assigneeId, LocalDate date);

    /**
     * 통합 검색용 작업 검색
     *
     * @param query 검색어
     * @param limit 결과 개수 제한
     * @return 작업 검색 결과 목록
     */
    List<TaskSearchDto> searchTasksForIntegratedSearch(String query, int limit);

    /**
     * 작업 검색 (페이징)
     *
     * @param query 검색어
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 작업 검색 결과 (페이징)
     */
    TaskPageResponse<TaskResponse> searchTasks(String query, int page, int size);

}
