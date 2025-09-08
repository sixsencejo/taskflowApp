package org.example.taskflow.domain.team.repository;

import org.example.taskflow.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findTeamByName(String name);
    @Query("select t from Team t where t.id = :teamId")
    Optional<Team> deleteByTeamId(@Param("teamId") Long teamId);
    boolean existsByDescription(String description);

    Optional<Team> findByIdAndNameAndDescription(Long teamId, String name, String description);
    //Optional<Team> findByUserIdAndId(Long userId, Long teamId);
}
