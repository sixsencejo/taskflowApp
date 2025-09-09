package org.example.taskflow.domain.task.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.task.service.TaskService;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskCounts { // 대시보드 통계 조회시 필요한 카운트 값을 담는 객체 2025-09-09 작성 이동재
    private final int total;
    private final int completed;
    private final int inProgress;
    private final int todo;
    private final int overdue;

    public static TaskCounts of(TaskService taskService, Long userId, LocalDate today) {
        return new TaskCounts(
                taskService.countByAssigneeId(userId),
                taskService.countByAssigneeIdAndStatus(userId, Status.DONE),
                taskService.countByAssigneeIdAndStatus(userId, Status.IN_PROGRESS),
                taskService.countByAssigneeIdAndStatus(userId, Status.TODO),
                taskService.countOverdueTasksByAssigneeId(userId, today)
        );
    }

    public int calculateCompletionRate() {
        return 0 < total ? (completed * 100) / total : 0;
    }
}

