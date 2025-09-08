package org.example.taskflow.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.dashboard.repository.TasksRepository;
import org.example.taskflow.domain.task.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TasksService implements TaskServiceImpl {

    private final TasksRepository tasksRepository;


    @Override
    public int countByAssigneeId(Long assigneeId) {
        return tasksRepository.countByAssigneeId(assigneeId);
    }

    @Override
    public int countByAssigneeIdAndStatus(Long assigneeId, Status status) {
        return tasksRepository.countByAssigneeIdAndStatus(assigneeId, status);
    }

    @Override
    public int countOverdueTasksByAssigneeId(Long assigneeId, LocalDate date) {

        //현재 시간 기준으로 기한이 지난 작업 조회
        LocalDateTime currentDateTime = LocalDateTime.now();
        return tasksRepository.countOverdueTasksByAssigneeId(assigneeId,currentDateTime);
    }

    @Override
    public int countTodayTasksByAssigneeId(Long assigneeId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return tasksRepository.countTodayTasksByAssigneeId(assigneeId,startOfDay,endOfDay);
    }
}
