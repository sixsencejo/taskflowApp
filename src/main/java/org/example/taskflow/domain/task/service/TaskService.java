package org.example.taskflow.domain.task.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.task.dto.AssigneeResponse;
import org.example.taskflow.domain.task.dto.TaskAllResponse;
import org.example.taskflow.domain.task.dto.TaskCreateRequest;
import org.example.taskflow.domain.task.dto.TaskResponse;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Category;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.task.repository.TaskRepository;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // Task 생성
    @Transactional
    public TaskResponse createTask(Long userId, TaskCreateRequest taskCreateRequest) {
        Task task = taskRepository.save(
                Task.builder()
                        .title(taskCreateRequest.title())
                        .description(taskCreateRequest.description())
                        .dueDate(taskCreateRequest.dueDate())
                        .priority(taskCreateRequest.priority())
                        .status(Status.TODO)
                        .category(Category.TASK_CREATED)
                        .build()
        );

        User user = userRepository.getReferenceById(userId);
        AssigneeResponse assigneeResponse = new AssigneeResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail()
        );

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus(),
                assigneeResponse,
                task.getCreatedAt(),
                task.getUpdatedAt()

        );
    }

    // Task 목록 조회
    @Transactional(readOnly = true)
    public TaskAllResponse getTaskAll(Long userId) {
        
    }
}
