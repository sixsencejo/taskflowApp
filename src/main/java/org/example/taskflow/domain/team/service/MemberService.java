package org.example.taskflow.domain.team.service;

import ch.qos.logback.core.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.team.dto.reponse.TeamResponse;
import org.example.taskflow.domain.team.dto.request.TeamRequest;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.team.repository.TeamRepository;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MemberService {

     private final TeamRepository teamRepository;

     @Transactional
     public TeamResponse createMember(Long teamId,TeamRequest request) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new IllegalArgumentException("팀이 없습니다."));

        Team team2 = teamRepository.existsEmailAndDescription(request.getName(), request.getDescription()).orElseThrow(
                () -> new IllegalArgumentException("설명하고 이름이 없습니다."));

        Team newTeam = new Team(Team.of(team, team2));
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
