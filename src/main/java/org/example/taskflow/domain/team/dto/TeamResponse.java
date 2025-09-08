package org.example.taskflow.domain.team.dto;

import lombok.Getter;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TeamResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final List<User> members;

    public TeamResponse(Team team, List<User> members) {
        this.id = team.getId();
        this.name = team.getName();
        this.description = team.getDescription();
        this.createdAt = team.getCreatedAt();
        this.members = members;
    }
}
