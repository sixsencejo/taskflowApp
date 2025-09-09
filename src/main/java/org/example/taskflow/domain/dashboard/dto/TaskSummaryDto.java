package org.example.taskflow.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.task.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskSummaryDto {
    private Long id;
    private String title;
    private String status;
    private LocalDateTime dueDate;

    public static TaskSummaryDto from(Task task) {
        return TaskSummaryDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus().name())
                .dueDate(task.getDueDate())
                .build();
    }

    public static List<TaskSummaryDto> fromTasks(List<Task> tasks) {
        return tasks.stream()
                .map(TaskSummaryDto::from)
                .collect(Collectors.toList());
    }
}
