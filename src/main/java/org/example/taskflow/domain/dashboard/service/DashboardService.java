package org.example.taskflow.domain.dashboard.service;

import org.example.taskflow.domain.dashboard.dto.*;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public class DashboardService implements DashboardServiceImpl {

    private TasksService tasksService;
    private UserService userService;

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
        int completionRate = totalTasks > 0 ? (completedTasks * 100) / inProgressTasks : 0;

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
    public MyTasksResponse getMyTasks(String username) {
        return null;
    }

    @Override
    public TeamProgressResponse getTeamProgress(String username) {
        return null;
    }

    @Override
    public PageResponse<ActivityDto> getActivityDto(String username, Pageable pageable) {
        return null;
    }
}
