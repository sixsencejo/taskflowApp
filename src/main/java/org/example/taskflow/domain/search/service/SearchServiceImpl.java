package org.example.taskflow.domain.search.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.search.dto.SearchResponse;
import org.example.taskflow.domain.search.dto.TaskSearchDto;
import org.example.taskflow.domain.search.dto.TeamSearchDto;
import org.example.taskflow.domain.search.dto.UserSearchDto;
import org.example.taskflow.domain.task.dto.TaskPageResponse;
import org.example.taskflow.domain.task.dto.TaskResponse;
import org.example.taskflow.domain.task.service.TaskService;
import org.example.taskflow.domain.team.service.TeamService;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {
    private final TaskService taskService;
    private final UserService userService;
    private final TeamService teamService;

    @Override
    public SearchResponse search(String query) {
        // 각 도메인 서비스를 통해 검색
        List<TaskSearchDto> taskResults = taskService.searchTasksForIntegratedSearch(query, 5);
        List<UserSearchDto> userResults = userService.searchUsersForIntegratedSearch(query, 5);
        List<TeamSearchDto> teamResults = teamService.searchTeamsForIntegratedSearch(query, 5);

        return SearchResponse.builder()
                .tasks(taskResults)
                .users(userResults)
                .teams(teamResults)
                .build();
    }

    @Override
    public TaskPageResponse<TaskResponse> searchTasks(String query, int page, int size) {
        return taskService.searchTasks(query, page, size);
    }
}