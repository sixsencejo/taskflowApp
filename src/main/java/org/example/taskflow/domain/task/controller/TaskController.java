package org.example.taskflow.domain.task.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.task.dto.*;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public CommonResponse<TaskResponse> createTask(TaskCreateRequest taskCreateRequest) {
        TaskResponse taskResponse = taskService.createTask(taskCreateRequest);

        return ResponseUtil.success(taskResponse, "Task가 생성되었습니다.");
    }

    @GetMapping
    public CommonResponse<TaskPageResponse<TaskResponse>> findAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long assigneeId
    ) {
        Long userId = 1L;
        TaskPageResponse<TaskResponse> taskResponses;

        if (status != null) {
            taskResponses = taskService.getTaskAllByStatus(status, page, size);
            return ResponseUtil.success(taskResponses, "Task 목록을 조회했습니다.");
        }
        if (search != null) {
            taskResponses = taskService.getTaskAlBySearch(search, page, size);
            return ResponseUtil.success(taskResponses, "Task 목록을 조회했습니다.");
        }
        if (assigneeId != null) {
            taskResponses = taskService.getTaskAllByAssigneeId(assigneeId, page, size);
            return ResponseUtil.success(taskResponses, "Task 목록을 조회했습니다.");
        }
        taskResponses = taskService.getTaskAll(userId, page, size);
        return ResponseUtil.success(taskResponses, "Task 목록을 조회했습니다.");
    }

    @GetMapping("/{taskId}")
    public CommonResponse<TaskResponse> getTask(@PathVariable Long taskId) {
        TaskResponse taskResponse = taskService.getTask(taskId);
        return ResponseUtil.success(taskResponse, "Task를 조회했습니다.");
    }

    @PutMapping("/{taskId}")
    public CommonResponse<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskUpdateAllRequest taskUpdateAllRequest
    ) {
        TaskResponse taskResponse = taskService.updateTask(taskId, taskUpdateAllRequest);
        return ResponseUtil.success(taskResponse, "Task가 수정되었습니다.");
    }

    @PatchMapping("/{taskId}/status")
    public CommonResponse<TaskResponse> updateStatus(
            @PathVariable Long taskId,
            @RequestBody TaskUpdateStatusRequest taskUpdateStatusRequest
    ) {

        TaskResponse taskResponse = taskService.updateStatus(taskId, taskUpdateStatusRequest);
        return ResponseUtil.success(taskResponse, "작업 상태가 업데이트되었습니다.");
    }

    @DeleteMapping("/{taskId}")
    public CommonResponse<Void> deleteTask(@PathVariable Long taskId) {
        return ResponseUtil.success(taskService.deleteTask(taskId), "Task가 삭제되었습니다.");
    }
}
