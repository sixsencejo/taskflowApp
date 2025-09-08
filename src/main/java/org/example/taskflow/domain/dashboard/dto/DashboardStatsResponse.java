package org.example.taskflow.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.task.vo.TaskCounts;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private int totalTasks;
    private int completedTasks;
    private int inProgressTasks;
    private int todoTasks;
    private int overdueTasks;
    private int teamProgress;
    private int myTasksToday;
    private int completionRate;

    public static DashboardStatsResponse of(TaskCounts taskCounts, int teamProgress, int myTasksToday) {
        int completionRate = taskCounts.calculateCompletionRate();

        return DashboardStatsResponse.builder()
                .totalTasks(taskCounts.getTotal())
                .completedTasks(taskCounts.getCompleted())
                .inProgressTasks(taskCounts.getInProgress())
                .todoTasks(taskCounts.getTodo())
                .overdueTasks(taskCounts.getOverdue())
                .teamProgress(teamProgress)
                .myTasksToday(myTasksToday)
                .completionRate(completionRate)
                .build();
    }

}
