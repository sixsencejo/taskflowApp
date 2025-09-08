package org.example.taskflow.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.task.service.TaskHistoryService;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyTrendResponse {
    private String name;
    private int tasks;
    private int completed;
    private LocalDate date;

    public static WeeklyTrendResponse of(LocalDate targetDate, int totalTasks, int completedTasks) {
        String dayName = targetDate.getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        return WeeklyTrendResponse.builder()
                .name(dayName)
                .tasks(totalTasks)
                .completed(completedTasks)
                .date(targetDate)
                .build();
    }

    public static List<WeeklyTrendResponse> createWeeklyTrend(
            TaskHistoryService taskHistoryService, Long userId) {

        return IntStream.rangeClosed(-6, 0)  // 오늘부터 6일 전까지
                .mapToObj(LocalDate.now()::plusDays)
                .map(date -> WeeklyTrendResponse.of(
                        date,
                        taskHistoryService.countNewTasksByAssigneeIdAndDate(userId, date),
                        taskHistoryService.countDoneTasksByAssigneeIdAndDate(userId, date)
                ))
                .collect(Collectors.toList());
    }
}