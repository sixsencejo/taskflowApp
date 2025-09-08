package org.example.taskflow.domain.dashboard.service;

import org.example.taskflow.domain.task.enums.Status;

import java.time.LocalDate;

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

}
