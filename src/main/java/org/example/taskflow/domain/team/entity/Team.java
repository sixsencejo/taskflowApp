package org.example.taskflow.domain.team.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.taskflow.common.entity.BaseEntity;
import org.example.taskflow.domain.user.entity.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "team")
public class Team extends BaseEntity {

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

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
