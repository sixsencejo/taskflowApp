package org.example.taskflow.domain.dashboard.repository;

import org.example.taskflow.domain.team.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamsRepository extends JpaRepository<Team,Long> {
    @Query("SELECT t FROM Team t WHERE " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "AND t.deletedAt IS NULL")
    List<Team> findByNameContainingIgnoreCaseAndDeletedAtIsNull(
            @Param("name") String name,
            Pageable pageable);
}
