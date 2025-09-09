package org.example.taskflow.domain.team.repository;

import org.example.taskflow.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);
}
