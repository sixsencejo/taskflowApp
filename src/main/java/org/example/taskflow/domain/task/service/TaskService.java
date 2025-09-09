package org.example.taskflow.domain.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.domain.search.dto.TaskSearchDto;
import org.example.taskflow.domain.task.dto.*;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.task.repository.TaskRepository;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskService implements TaskServiceImpl{

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskHistoryServiceImpl taskHistoryService;

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
                task.getCategory(),
                assigneeResponse.id(),
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
                            task.getCategory(),
                            assigneeResponse.id(),
                            assigneeResponse,
                            task.getCreatedAt(),
                            task.getUpdatedAt()
                    );
                })
                .toList();
    }

    // Task 생성
    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        User assignee = userRepository.findByIdAndDeletedAtIsNull(taskCreateRequest.assigneeId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        log.info("assignee={}", assignee);
        Task task = taskRepository.save(
                Task.builder()
                        .title(taskCreateRequest.title())
                        .description(taskCreateRequest.description())
                        .dueDate(taskCreateRequest.dueDate())
                        .priority(taskCreateRequest.priority())
                        .category(taskCreateRequest.category())
                        .assignee(assignee)
                        .build()
        );

        taskHistoryService.recordTaskHistory(task,task.getStatus());
        Assignee assigneeResponse = getAssigneeResponse(task);

        return getTaskResponse(task, assigneeResponse);
    }

    // Task 목록 조회
    // status 시
    @Transactional(readOnly = true)
    public TaskPageResponse<TaskResponse> getTaskAllByStatus(Status status, int page, int size) {
        Page<Task> tasks = taskRepository.findByStatusAndDeletedAtIsNull(
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

    // Task 목록 조회
    // search 시
    @Transactional(readOnly = true)
    public TaskPageResponse<TaskResponse> getTaskAlBySearch(String search, int page, int size) {
        Page<Task> tasks = taskRepository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(
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

    // Task 목록 조회
    // assigneeId 시(전체 조회)
    @Transactional(readOnly = true)
    public TaskPageResponse<TaskResponse> getTaskAllByAssigneeId(Long assigneeId, int page, int size) {
        User assignee = userRepository.findById(assigneeId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Page<Task> tasks = taskRepository.findByAssigneeAndDeletedAtIsNull(
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

    // Task 목록 전체 조회
    @Transactional(readOnly = true)
    public TaskPageResponse<TaskResponse> getTaskAll(int page, int size) {
        Page<Task> tasks = taskRepository.findByDeletedAtIsNull(
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
        Task task = taskRepository.findByIdAndDeletedAtIsNull(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        Assignee assigneeResponse = getAssigneeResponse(task);

        return getTaskResponse(task, assigneeResponse);
    }

    // Task 전체 수정
    public TaskResponse updateTask(Long taskId, TaskUpdateAllRequest taskUpdateAllRequest) {
        Task task = taskRepository.findByIdAndDeletedAtIsNull(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );
        User assignee = userRepository.findById(taskUpdateAllRequest.assigneeId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Status oldStatus = task.getStatus();

        task.updateAll(
                taskUpdateAllRequest.title(),
                taskUpdateAllRequest.description(),
                taskUpdateAllRequest.dueDate(),
                taskUpdateAllRequest.priority(),
                taskUpdateAllRequest.status(),
                assignee
        );

        Assignee assigneeResponse = getAssigneeResponse(task);

        // 상태가 변경된 경우 히스토리 기록
        if (!oldStatus.equals(task.getStatus())) {
            taskHistoryService.recordTaskHistory(task, task.getStatus());
        }

        return getTaskResponse(task, assigneeResponse);
    }

    // Status 수정
    public TaskResponse updateStatus(Long taskId, TaskUpdateStatusRequest taskUpdateStatusRequest) {
        Task task = taskRepository.findByIdAndDeletedAtIsNull(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        Status oldStatus = task.getStatus();
        task.updateStatus(taskUpdateStatusRequest.status());

        // 상태가 변경된 경우 히스토리 기록
        if (!oldStatus.equals(task.getStatus())) {
            taskHistoryService.recordTaskHistory(task, task.getStatus());
        }

        Assignee assigneeResponse = getAssigneeResponse(task);

        return getTaskResponse(task, assigneeResponse);
    }

    // Task 삭제
    public Void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndDeletedAtIsNull(taskId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND)
        );

        task.softDelete();
        return null;
    }

    //----------------------------------//
    // 대시보드 기능에 필요한 작업 조회 기능 및 검색 기능 구현 2025-09-09 수정 이동재
    @Override
    public int countByAssigneeId(Long assigneeId) {
        return taskRepository.countByAssigneeId(assigneeId);
    }

    @Override
    public int countByAssigneeIdAndStatus(Long assigneeId, Status status) {
        return taskRepository.countByAssigneeIdAndStatus(assigneeId, status);
    }

    @Override
    public int countOverdueTasksByAssigneeId(Long assigneeId, LocalDate date) {

        //현재 시간 기준으로 기한이 지난 작업 조회
        LocalDateTime currentDateTime = LocalDateTime.now();
        return taskRepository.countOverdueTasksByAssigneeId(assigneeId,currentDateTime);
    }

    @Override
    public int countTodayTasksByAssigneeId(Long assigneeId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return taskRepository.countTodayTasksByAssigneeId(assigneeId,startOfDay,endOfDay);
    }

    @Override
    public List<Task> findTasksByTeamId(Long teamId) {
        return taskRepository.findTasksByAssigneeTeamId(teamId);
    }

    @Override
    public List<Task> findTodayTasksByAssigneeId(Long assigneeId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return taskRepository.findTodayTasksByAssigneeId(assigneeId, startOfDay, endOfDay);
    }

    @Override
    public List<Task> findUpcomingTasksByAssigneeId(Long assigneeId, LocalDate date) {

        LocalDateTime currentTime = LocalDateTime.now();
        return taskRepository.findUpcomingTasksByAssigneeId(assigneeId, currentTime);
    }

    @Override
    public List<Task> findOverdueTasksByAssigneeId(Long assigneeId, LocalDate date) {
        LocalDateTime currentTime = LocalDateTime.now();
        return taskRepository.findOverdueTasksByAssigneeId(assigneeId, currentTime);
    }

    @Override
    public List<TaskSearchDto> searchTasksForIntegratedSearch(String query, int limit) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(
                query, PageRequest.of(0, limit)
        ).getContent();

        return tasks.stream()
                .map(TaskSearchDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public TaskPageResponse<TaskResponse> searchTasks(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Task> tasksPage = taskRepository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(
                query, pageable
        );

        List<TaskResponse> taskResponses = tasksPage.getContent().stream()
                .map(TaskResponse::from)
                .collect(Collectors.toList());

        return new TaskPageResponse<>(
                taskResponses,
                tasksPage.getTotalElements(),
                tasksPage.getTotalPages(),
                tasksPage.getSize(),
                tasksPage.getNumber()
        );
    }
}
