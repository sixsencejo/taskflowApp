package org.example.taskflow.domain.task.service;

import java.time.LocalDate;

public interface TaskHistoryService { // 작업 기록 조회 2025-09-08 작성 이동재
    /**
     *
     * @param assigneeId
     * @param date
     * @return 해당 날짜에 생성된 작업 수 조회
     */
    int countNewTasksByAssigneeIdAndDate(Long assigneeId, LocalDate date);

    /**
     *
     * @param assigneeId
     * @param date
     * @return 해당 날짜에 완료된 사용자 작업 수 조회
     */
    int countDoneTasksByAssigneeIdAndDate(Long assigneeId, LocalDate date);

}
