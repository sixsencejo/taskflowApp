package org.example.taskflow.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.dashboard.dto.UserDto;
import org.example.taskflow.domain.task.entity.Task;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskSearchDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private UserDto user;

    public static TaskSearchDto from(Task task) {
        return TaskSearchDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().toString())
                .user(UserDto.from(task.getAssignee()))
                .build();
    }
}
