package org.example.taskflow.domain.team.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.BaseEntity;
import org.example.taskflow.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
public class Team extends BaseEntity {

    @Column(length = 10)
    private String name;

    @Id
    @Column(nullable = false, length = 50)
    private Long teamId;

    @Column(length = 100)
    private String explain;

    @Builder
    public Team(String name, String explain) {
        this.name = name;
        this.explain = explain;
    }

}
