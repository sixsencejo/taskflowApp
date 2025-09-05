package org.example.taskflow.domain.team.dto.reponse;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TeamResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedat;

    public TeamResponse(Long id, String name, String description, LocalDateTime createdAt, LocalDateTime updatedat) {
           this.id = id;
           this.name = name;
           this.description = description;
           this.createdAt = createdAt;
           this.updatedat = updatedat;
    }

}
