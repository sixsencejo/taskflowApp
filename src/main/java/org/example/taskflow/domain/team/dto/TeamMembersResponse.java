package org.example.taskflow.domain.team.dto;

import lombok.Getter;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.enums.UserRole;

import java.time.LocalDateTime;

@Getter
public class TeamMembersResponse {

    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final UserRole role;
    private final LocalDateTime createdAt;

    public TeamMembersResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }
}
