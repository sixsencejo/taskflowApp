package org.example.taskflow.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.dashboard.conveter.ActivityDtoConverter;
import org.example.taskflow.domain.dashboard.dto.*;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService implements DashboardServiceImpl {

    private final TasksService tasksService;
    private final UserService userService;
    private final TeamsService teamsService;
    private final ActivitiesService activitiesService;
    private final ActivityDtoConverter activityDtoConverter;
    private final TaskHistoryService taskHistoryService;

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

        List<Task> teamTasks = tasksService.findTasksByTeamId(userId);
        int teamProgress = calculateTeamProgress(teamTasks);

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
    public Map<String, Integer> getTeamProgress() {

        Map<String, Integer> teamProgressMap = new HashMap<>();
        List<Team> allTeams = teamsService.findAll();

        for (Team team : allTeams) {
            List<Task> teamTasks = tasksService.findTasksByTeamId(team.getId());
            int progress = calculateTeamProgress(teamTasks);
            teamProgressMap.put(team.getName(), progress);
        }

        return teamProgressMap;
    }

    @Override
    public PageResponse<ActivityDto> getActivities(Pageable pageable) {

        Long userId = userService.getUserId();
        Page<Activity> activities = activitiesService.findUserActivities(userId, pageable);

        Page<ActivityDto> activityDtos = activities.map(activityDtoConverter::convertToActivityDto);

        return PageResponse.of(activityDtos);
    }

    public List<WeeklyDto> getWeeklyDto(){

        Long userId = userService.getUserId();
        List<WeeklyDto> weeklyTrend = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6);

        for (int i = 0; i < 7; i++) {
            LocalDate targetDate = startDate.plusDays(i);

            String dayName = targetDate.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, Locale.KOREAN);

            int totalTasks = taskHistoryService.countNewTasksByAssigneeIdAndDate(userId, targetDate);
            int completedTasks = taskHistoryService.countDoneTasksByAssigneeIdAndDate(userId, targetDate);

            WeeklyDto trendDto = WeeklyDto.builder()
                    .name(dayName)
                    .tasks(totalTasks)
                    .completed(completedTasks)
                    .date(targetDate)
                    .build();

            weeklyTrend.add(trendDto);
        }

        return weeklyTrend;
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

    private int calculateTeamProgress(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return 0;
        }
        long completedCount = tasks.stream()
                .filter(task -> task.getStatus().equals(Status.DONE))
                .count();
        return (int) ((completedCount * 100) / tasks.size());
    }
}
