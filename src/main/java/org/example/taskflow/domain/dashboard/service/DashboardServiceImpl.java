package org.example.taskflow.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.activity.service.ActivityService;
import org.example.taskflow.domain.dashboard.conveter.ActivityDtoConverter;
import org.example.taskflow.domain.dashboard.dto.*;
import org.example.taskflow.domain.dashboard.utility.TeamProgressCalculator;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.service.TaskHistoryService;
import org.example.taskflow.domain.task.service.TaskService;
import org.example.taskflow.domain.task.vo.TaskCounts;
import org.example.taskflow.domain.team.service.TeamService;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements org.example.taskflow.domain.dashboard.service.DashboardService {

    private final TaskService taskService;
    private final UserService userService;
    private final TeamService teamService;
    private final ActivityService activityService;
    private final ActivityDtoConverter activityDtoConverter;
    private final TaskHistoryService taskHistoryService;

    @Override
    public DashboardStatsResponse getDashboardStats() {
        Long userId = userService.getUserId();
        LocalDate today = LocalDate.now();

        TaskCounts taskCounts = TaskCounts.of(taskService, userId, today);
        List<Task> teamTasks = taskService.findTasksByTeamId(userId);
        int teamProgress = TeamProgressCalculator.calculate(teamTasks);
        int myTasksToday = taskService.countTodayTasksByAssigneeId(userId, today);

        return DashboardStatsResponse.of(taskCounts, teamProgress, myTasksToday);
    }

    @Override
    public MyTasksResponse getMyTasks() {
        Long userId = userService.getUserId();
        LocalDate today = LocalDate.now();

        return MyTasksResponse.from(taskService, userId, today);
    }

    @Override
    public Map<String, Integer> getTeamProgress() {
        return TeamProgressCalculator.calculateForAllTeams(teamService, taskService);
    }

    @Override
    public PageResponse<ActivityDto> getActivities(Pageable pageable) {
        Long userId = userService.getUserId();
        Page<Activity> activities = activityService.findUserActivities(userId, pageable);
        Page<ActivityDto> activityDtos = activities.map(activityDtoConverter::convertToActivityDto);

        return PageResponse.of(activityDtos);
    }

    public List<WeeklyTrendResponse> getWeeklyTrendResponse() {
        Long userId = userService.getUserId();
        return WeeklyTrendResponse.createWeeklyTrend(taskHistoryService, userId);
    }
}
