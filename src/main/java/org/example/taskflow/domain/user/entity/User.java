package org.example.taskflow.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.SoftDeletableEntity;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.user.enums.UserRole;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends SoftDeletableEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 150)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UserRole role = UserRole.USER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


    @Builder
    public User(String username, String email, String password, String name, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
