package org.example.taskflow.domain.team.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.BaseEntity;
import org.example.taskflow.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "team")
public class Team extends BaseEntity {

    @NotBlank
    @Column(length = 10)
    private String name;

    @NotBlank
    @Column(length = 100)
    private String description;

    @Builder
    public Team(Team team) {
        this.name = team.getName();
        this.description = team.getDescription();
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
