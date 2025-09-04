package org.example.taskflow.domain.task.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.domain.task.dto.*;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Category;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.task.repository.TaskRepository;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // Task 생성
    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        User assignee = userRepository.findById(taskCreateRequest.assigneeId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Task task = taskRepository.save(
                Task.builder()
                        .title(taskCreateRequest.title())
                        .description(taskCreateRequest.description())
                        .dueDate(taskCreateRequest.dueDate())
                        .priority(taskCreateRequest.priority())
                        .category(Category.TASK_CREATED)
                        .assignee(assignee)
                        .build()
        );

        Assignee assigneeResponse = getAssigneeResponse(task);

        return getTaskResponse(task, assigneeResponse);
    }

    // default - page = 0, size = 10
    // 자신의 Task 목록 조회
    // status 시
    @Transactional(readOnly = true)
    public TaskPageResponse<TaskResponse> getTaskAllByStatus(Status status, int page, int size) {
        Page<Task> tasks = taskRepository.findByStatus(
                status,
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.DESC, "dueDate")
                )
        );

        List<TaskResponse> taskResponseList = getTaskResponse(tasks);

        return new TaskPageResponse<>(
                taskResponseList,
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.getSize(),
                tasks.getNumber()
        );
    }

    // 자신의 Task 목록 조회
    // search 시
    @Transactional(readOnly = true)
    public TaskPageResponse<TaskResponse> getTaskAlBySearch(String search, int page, int size) {
        Page<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(
                search,
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.DESC, "dueDate")
                )
        );

        List<TaskResponse> taskResponseList = getTaskResponse(tasks);

        return new TaskPageResponse<>(
                taskResponseList,
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.getSize(),
                tasks.getNumber()
        );
    }

    // 자신의 Task 목록 조회
    // assigneeId 시(전체 조회)
    @Transactional(readOnly = true)
    public TaskPageResponse<TaskResponse> getTaskAllByAssigneeId(Long assigneeId, int page, int size) {
        User assignee = userRepository.findById(assigneeId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Page<Task> tasks = taskRepository.findByAssignee(
                assignee,
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.DESC, "dueDate")
                )
        );

        List<TaskResponse> taskResponseList = getTaskResponse(tasks);

        return new TaskPageResponse<>(
                taskResponseList,
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.getSize(),
                tasks.getNumber()
        );
    }

    // 자신의 Task 목록 전체 조회
    @Transactional(readOnly = true)
    public TaskPageResponse<TaskResponse> getTaskAll(Long userId, int page, int size) {
        User assignee = userRepository.getReferenceById(userId);

        Page<Task> tasks = taskRepository.findByAssignee(
                assignee,
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.DESC, "dueDate")
                )
        );

        List<TaskResponse> taskResponseList = getTaskResponse(tasks);

        return new TaskPageResponse<>(
                taskResponseList,
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.getSize(),
                tasks.getNumber()
        );
    }

    // Task 단건 조회
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        Assignee assigneeResponse = getAssigneeResponse(task);

        return getTaskResponse(task, assigneeResponse);
    }

    // Task 전체 수정
    public TaskResponse updateTask(Long taskId, TaskUpdateAllRequest taskUpdateAllRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );
        User assignee = userRepository.findById(task.getAssignee().getId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        task.updateAll(
                taskUpdateAllRequest.title(),
                taskUpdateAllRequest.description(),
                taskUpdateAllRequest.dueDate(),
                taskUpdateAllRequest.priority(),
                taskUpdateAllRequest.status(),
                assignee
        );

        Assignee assigneeResponse = getAssigneeResponse(task);

        return getTaskResponse(task, assigneeResponse);
    }

    // Status 수정
    public TaskResponse updateStatus(Long taskId, TaskUpdateStatusRequest taskUpdateStatusRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        task.updateStatus(taskUpdateStatusRequest.status());

        Assignee assigneeResponse = getAssigneeResponse(task);

        return getTaskResponse(task, assigneeResponse);
    }

    // Task 삭제
    public Void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );
        // 이거 소프트 딜리트 이렇게 사용하는 것인지 내일 물어보자
        // 이거 삭제할 때 로그인된 사용자와 작업을 생성한 사용자와 비교한 이후에 삭제해야 하는지 물어보자
        task.softDelete();
        return null;
    }

    // 헬퍼 메서드
    public static Assignee getAssigneeResponse(Task task) {
        return new Assignee(
                task.getAssignee().getId(),
                task.getAssignee().getUsername(),
                task.getAssignee().getName(),
                task.getAssignee().getEmail()
        );
    }

    // 헬퍼 메서드
    public static TaskResponse getTaskResponse(Task task, Assignee assigneeResponse) {
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

    // 헬퍼 메서드
    public static List<TaskResponse> getTaskResponse(Page<Task> tasks) {
        List<Task> taskList = tasks.getContent();
        return taskList.stream()
                .map(task -> {
                    Assignee assigneeResponse = getAssigneeResponse(task);

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
                })
                .toList();
    }
}
