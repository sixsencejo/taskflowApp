package org.example.taskflow.domain.team.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "team")
public class Team extends BaseEntity {

    @Column(length = 10)
    private String name;

    @Column(length = 100)
    private String description;

    @Builder
    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public Team(Team team) {
        this.name = team.getName();
        team.description = team.getDescription();
    }

    public static Team of(Team team) {
       return Team.builder()
               .name(team.getName())
               .description(team.getDescription())
               .build();
   }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
