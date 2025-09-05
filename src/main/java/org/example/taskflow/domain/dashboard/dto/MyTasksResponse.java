package org.example.taskflow.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyTasksResponse {
    private List<TaskSummaryDto> todayTasks;
    private List<TaskSummaryDto> upcomingTasks;
    private List<TaskSummaryDto> overdueTasks;
}