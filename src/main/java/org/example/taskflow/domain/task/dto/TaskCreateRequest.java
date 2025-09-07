package org.example.taskflow.domain.task.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.example.taskflow.domain.task.enums.Category;
import org.example.taskflow.domain.task.enums.Priority;

import java.time.LocalDateTime;

public record TaskCreateRequest(

        @NotEmpty(message = "제목을 비워둘 수 없습니다.")
        @Size(max = 100, message = "제목은 최대 100자까지 입력할 수 있습니다.")
        String title,
        @NotEmpty(message = "설명을 비워둘 수 없습니다.")
        String description,
        @FutureOrPresent(message = "기한을 오늘 또는 오늘 이후로 설정해야합니다.")
        LocalDateTime dueDate,
        Category category,
        Priority priority,
        Long assigneeId
) {
}
