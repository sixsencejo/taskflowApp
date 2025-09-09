package org.example.taskflow.domain.team.repository;

import org.example.taskflow.domain.team.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);

    @Query("SELECT t FROM Team t WHERE " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')) ")
    List<Team> findByNameContainingIgnoreCase(
            @Param("name") String name,
            Pageable pageable);
}
