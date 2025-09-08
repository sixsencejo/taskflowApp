package org.example.taskflow.domain.team.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.team.dto.request.TeamRequest;
import org.example.taskflow.domain.team.dto.response.TeamResponse;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public TeamResponse createTeam(TeamRequest request) {
        Team team = teamRepository.findTeamByName(request.getName()).orElseThrow(
                () -> new IllegalArgumentException("팀 이름이 존재하지 않습니다."));

        Team savedTeam = teamRepository.save(team);

        return new TeamResponse(
                savedTeam.getId(),
                savedTeam.getName(),
                savedTeam.getDescription(),
                savedTeam.getCreatedAt(),
                savedTeam.getUpdatedAt()
        );
    }

    @Transactional
    public List<TeamResponse> AllTeams() {
        return teamRepository.findAll().stream()
                .map(team -> new TeamResponse(
                        team.getId(),
                        team.getName(),
                        team.getDescription(),
                        team.getCreatedAt(),
                        team.getUpdatedAt()
                )).collect(Collectors.toList());
    }

    @Transactional
    public TeamResponse OneTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new IllegalArgumentException("팀 아이디가 존재하지 않습니다."));

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                team.getUpdatedAt()
        );

    }

    @Transactional
    public TeamResponse updateTeam(Long teamId, TeamRequest request) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new IllegalArgumentException("팀 아이디가 존재하지 않습니다."));

        if(!StringUtils.hasText(request.getName())) {
            throw new IllegalArgumentException("팀이름을 입력해주세요.");
        }

        if(!StringUtils.hasText(request.getDescription())) {
            throw new IllegalArgumentException("팀설명을 입력해주세요");
        }

        if(team.getId() == null) {
            throw new IllegalArgumentException("팀아이디가 없습니다.");
        }

        if(team.getName().equals(request.getName())) {
            throw new IllegalArgumentException("팀이름이 같습니다");
        }

        if(team.getDescription().equals(request.getDescription())) {
            throw new IllegalArgumentException("팀 설명이 같습니다");
        }

        team.update(request.getName(), request.getDescription());

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                team.getUpdatedAt()
        );
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        teamRepository.DeleteByTeamId(teamId);
    }


}
