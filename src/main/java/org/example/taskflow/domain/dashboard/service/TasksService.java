package org.example.taskflow.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.dashboard.repository.TasksRepository;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    @Override
    public List<Task> findTasksByTeamId(Long teamId) {
        return tasksRepository.findTasksByAssigneeTeamId(teamId);
    }

    @Override
    public List<Task> findTodayTasksByAssigneeId(Long assigneeId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return tasksRepository.findTodayTasksByAssigneeId(assigneeId, startOfDay, endOfDay);
    }

    @Override
    public List<Task> findUpcomingTasksByAssigneeId(Long assigneeId, LocalDate date) {

        LocalDateTime currentTime = LocalDateTime.now();
        return tasksRepository.findUpcomingTasksByAssigneeId(assigneeId, currentTime);
    }

    @Override
    public List<Task> findOverdueTasksByAssigneeId(Long assigneeId, LocalDate date) {
        LocalDateTime currentTime = LocalDateTime.now();
        return tasksRepository.findOverdueTasksByAssigneeId(assigneeId, currentTime);
    }
}
