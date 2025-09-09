package org.example.taskflow.domain.dashboard.utility;

import lombok.experimental.UtilityClass;
import org.example.taskflow.domain.task.entity.Task;
import org.example.taskflow.domain.task.enums.Status;
import org.example.taskflow.domain.task.service.TaskService;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.team.service.TeamService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class TeamProgressCalculator {

    public static int calculate(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return 0;
        }

        long completedCount = tasks.stream()
                .filter(task -> task.getStatus().equals(Status.DONE))
                .count();

        return (int) ((completedCount * 100) / tasks.size());
    }

    public static Map<String, Integer> calculateForAllTeams(TeamService teamsService, TaskService taskService) {
        return teamsService.findAll().stream()
                .collect(Collectors.toMap(
                        Team::getName,
                        team -> {
                            List<Task> teamTasks = taskService.findTasksByTeamId(team.getId());
                            return calculate(teamTasks);
                        }
                ));
    }
}
