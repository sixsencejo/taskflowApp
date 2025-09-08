package org.example.taskflow.domain.team.repository;

import org.example.taskflow.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findTeamByName(String name);
    @Query("select t from Team t where t.id = :teamId")
    Optional<Team> DeleteByTeamId(@Param("teamId") Long teamId);
    boolean existsDescription(String description);

    Optional<Team> findTeamByTeamIdAndNameAndDescription(Long teamId, String name, String description);
    Optional<Team> findTeamByUserIdAndTeamId(Long userId, Long teamId);
}
