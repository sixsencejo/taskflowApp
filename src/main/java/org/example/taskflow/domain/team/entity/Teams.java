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
@Table(name = "teams")
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    private String explain;

    public void Teams(Long id) {
        this.id = id;
    }

}
