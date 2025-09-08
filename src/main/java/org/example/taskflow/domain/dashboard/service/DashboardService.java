package org.example.taskflow.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.dashboard.dto.*;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService implements DashboardServiceImpl {

    private final TasksService tasksService;
    private final UserService userService;

    @Override
    public DashboardStatsResponse getDashboardStats() {

        Long userId = userService.getUserId();
        LocalDate today = LocalDate.now();

        int totalTasks = tasksService.countByAssigneeId(userId);
        int completedTasks = tasksService.countByAssigneeIdAndStatus(userId, Status.DONE);
        int inProgressTasks = tasksService.countByAssigneeIdAndStatus(userId, Status.IN_PROGRESS);
        int todoTasks = tasksService.countByAssigneeIdAndStatus(userId,Status.TODO);
        int overdueTasks = tasksService.countOverdueTasksByAssigneeId(userId,today);
        int myTasksToday = tasksService.countTodayTasksByAssigneeId(userId,today);

        // 팀 진행률 관련 메서드 개발 예정
        int teamProgress = 0;

        // 완료율
        int completionRate = totalTasks > 0 ? (completedTasks * 100) / totalTasks : 0;

        return DashboardStatsResponse.builder()
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .inProgressTasks(inProgressTasks)
                .todoTasks(todoTasks)
                .overdueTasks(overdueTasks)
                .teamProgress(teamProgress)
                .myTasksToday(myTasksToday)
                .completionRate(completionRate)
                .build();
    }

    @Override
    public MyTasksResponse getMyTasks() {
        Long userId = userService.getUserId();
        LocalDate today = LocalDate.now();

        List<Task> todayTasks = tasksService.findTodayTasksByAssigneeId(userId, today);
        List<Task> upcomingTasks = tasksService.findUpcomingTasksByAssigneeId(userId, today);
        List<Task> overdueTasks = tasksService.findOverdueTasksByAssigneeId(userId, today);

        return MyTasksResponse.builder()
                .todayTasks(convertToTaskSummaryList(todayTasks))
                .upcomingTasks(convertToTaskSummaryList(upcomingTasks))
                .overdueTasks(convertToTaskSummaryList(overdueTasks))
                .build();
    }

    @Override
    public TeamProgressResponse getTeamProgress(String username) {
        return null;
    }

    @Override
    public PageResponse<ActivityDto> getActivityDto(String username, Pageable pageable) {
        return null;
    }


    private List<TaskSummaryDto> convertToTaskSummaryList(List<Task> tasks) {
        return tasks.stream()
                .map(this::convertToTaskSummary)
                .collect(Collectors.toList());
    }

    private TaskSummaryDto convertToTaskSummary(Task task) {
        return TaskSummaryDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus().name())
                .dueDate(task.getDueDate())
                .build();
    }
}
