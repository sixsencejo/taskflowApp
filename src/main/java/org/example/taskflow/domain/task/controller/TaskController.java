package org.example.taskflow.domain.task.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.task.dto.*;
import org.example.taskflow.domain.task.enums.ResponseCode;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public CommonResponse<TaskResponse> createTask(
            @Valid @RequestBody TaskCreateRequest taskCreateRequest) {
        TaskResponse taskResponse = taskService.createTask(taskCreateRequest);

        return ResponseUtil.success(taskResponse, ResponseCode.TASK_CREATED_RESPONSE.getMessage());
    }

    @GetMapping
    public CommonResponse<TaskPageResponse<TaskResponse>> findAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long assigneeId
    ) {
        TaskPageResponse<TaskResponse> taskResponses;

        if (status != null) {
            taskResponses = taskService.getTaskAllByStatus(status, page, size);
            return ResponseUtil.success(taskResponses, ResponseCode.TASK_FINDS_RESPONSE.getMessage());
        }
        if (search != null) {
            taskResponses = taskService.getTaskAlBySearch(search, page, size);
            return ResponseUtil.success(taskResponses, ResponseCode.TASK_FINDS_RESPONSE.getMessage());
        }
        if (assigneeId != null) {
            taskResponses = taskService.getTaskAllByAssigneeId(assigneeId, page, size);
            return ResponseUtil.success(taskResponses, ResponseCode.TASK_FINDS_RESPONSE.getMessage());
        }
        taskResponses = taskService.getTaskAll(page, size);
        return ResponseUtil.success(taskResponses, ResponseCode.TASK_FINDS_RESPONSE.getMessage());
    }

    @GetMapping("/{taskId}")
    public CommonResponse<TaskResponse> getTask(
            @PathVariable Long taskId
    ) {
        TaskResponse taskResponse = taskService.getTask(taskId);
        return ResponseUtil.success(taskResponse, ResponseCode.TASK_FIND_RESPONSE.getMessage());
    }

    @PutMapping("/{taskId}")
    public CommonResponse<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskUpdateAllRequest taskUpdateAllRequest
    ) {
        TaskResponse taskResponse = taskService.updateTask(taskId, taskUpdateAllRequest);
        return ResponseUtil.success(taskResponse, ResponseCode.TASK_UPDATED_RESPONSE.getMessage());
    }

    @PatchMapping("/{taskId}/status")
    public CommonResponse<TaskResponse> updateStatus(
            @PathVariable Long taskId,
            @RequestBody TaskUpdateStatusRequest taskUpdateStatusRequest
    ) {

        TaskResponse taskResponse = taskService.updateStatus(taskId, taskUpdateStatusRequest);
        return ResponseUtil.success(taskResponse, ResponseCode.TASK_STATUS_UPDATED_RESPONSE.getMessage());
    }

    @DeleteMapping("/{taskId}")
    public CommonResponse<Void> deleteTask(@PathVariable Long taskId) {
        return ResponseUtil.success(taskService.deleteTask(taskId), ResponseCode.TASK_DELETED_RESPONSE.getMessage());
    }
}
