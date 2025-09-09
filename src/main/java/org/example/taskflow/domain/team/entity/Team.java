package org.example.taskflow.domain.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.BaseEntity;
import org.example.taskflow.domain.team.dto.TeamRequest;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "teams")
public class Team extends BaseEntity {

    @Column(length = 50)
    private String name;

    @Column(length = 100)
    private String description;

    @Builder
    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateTeamInfo(TeamRequest teamRequest) {
        this.name = teamRequest.name();
        this.description = teamRequest.description();
    }

    public void updateTeamName(String name) {
        this.name = name;
    }

    public void updateTeamDescription(String description) {
        this.description = description;
    }
}
