package org.example.taskflow.domain.user.repository;

import org.example.taskflow.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
