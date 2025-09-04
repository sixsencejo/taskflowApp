package org.example.taskflow.domain.team.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    private String name;

    @Column(nullable = false, unique = true)
    private Long teamId;

    private String explain;

}
