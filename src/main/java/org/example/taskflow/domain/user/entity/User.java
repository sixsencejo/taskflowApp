package org.example.taskflow.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.SoftDeletableEntity;
import org.example.taskflow.domain.user.enums.UserRole;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends SoftDeletableEntity {
    private String username;
    private String email;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String username, String email, String password, String name, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
