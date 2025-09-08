package org.example.taskflow.domain.team.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.team.dto.request.TeamRequest;
import org.example.taskflow.domain.team.dto.response.TeamResponse;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.team.repository.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TeamRepository teamRepository;

    @Transactional
    public TeamResponse createMember(Long teamId, TeamRequest request) {
        Team team = teamRepository.findTeamByTeamIdAndNameAndDescription(teamId, request.getName(), request.getDescription()).orElseThrow(
                () -> new IllegalArgumentException("회원이 구성되지 않았습니다."));

        Team newTeam = new Team(team);
        teamRepository.save(newTeam);

        return new TeamResponse(
                newTeam.getId(),
                newTeam.getName(),
                newTeam.getDescription(),
                newTeam.getCreatedAt(),
                newTeam.getUpdatedAt()
        );
    }


    @Transactional
    public void deleteById(Long teamId, Long userId) {
        Team team = teamRepository.findTeamByUserIdAndTeamId(teamId, userId).orElseThrow(
                ()->  new IllegalArgumentException("멤버가 없습니다."));

        if(team.getId().equals(userId)) {
            throw new IllegalArgumentException("해당팀의 아이디가 일치하지 않습니다");
        }

        teamRepository.delete(team);

    }
}
