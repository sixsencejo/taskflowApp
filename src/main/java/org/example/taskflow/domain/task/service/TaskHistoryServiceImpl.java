package org.example.taskflow.domain.task.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.entity.TaskHistory;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.task.repository.TaskHistoryRepository;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskHistoryServiceImpl implements TaskHistoryService { // 작업 기록 조회 서비스 구현 2025-09-08 작성 이동재

    private final TaskHistoryRepository taskHistoryRepository;
    private final UserService userService;

    @Override
    public int countNewTasksByAssigneeIdAndDate(Long assigneeId, LocalDate date) {
        return taskHistoryRepository.countNewTasksByAssigneeIdAndDate(assigneeId, date);
    }

    @Override
    public int countDoneTasksByAssigneeIdAndDate(Long assigneeId, LocalDate date) {
        return taskHistoryRepository.countDoneTasksByAssigneeIdAndDate(assigneeId, date);
    }


    public void recordTaskHistory(Task task, Status status){
        User user = userService.getCurrentUserEntity();

        TaskHistory taskHistory = TaskHistory.builder()
                .user(user)
                .task(task)
                .status(status)
                .build();

        taskHistoryRepository.save(taskHistory);
    }
}
