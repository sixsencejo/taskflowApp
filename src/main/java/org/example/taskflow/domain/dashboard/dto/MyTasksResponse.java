package org.example.taskflow.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyTasksResponse {
    private List<TaskSummaryDto> todayTasks;
    private List<TaskSummaryDto> upcomingTasks;
    private List<TaskSummaryDto> overdueTasks;

    public static MyTasksResponse from(TaskService taskService, Long userId, LocalDate today) {
        List<Task> todayTasks = taskService.findTodayTasksByAssigneeId(userId, today);
        List<Task> upcomingTasks = taskService.findUpcomingTasksByAssigneeId(userId, today);
        List<Task> overdueTasks = taskService.findOverdueTasksByAssigneeId(userId, today);

        return MyTasksResponse.builder()
                .todayTasks(TaskSummaryDto.fromTasks(todayTasks))
                .upcomingTasks(TaskSummaryDto.fromTasks(upcomingTasks))
                .overdueTasks(TaskSummaryDto.fromTasks(overdueTasks))
                .build();
    }
}