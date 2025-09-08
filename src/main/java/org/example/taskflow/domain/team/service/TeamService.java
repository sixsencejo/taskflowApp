package org.example.taskflow.domain.team.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.domain.team.dto.request.TeamRequest;
import org.example.taskflow.domain.team.dto.response.TeamResponse;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public CommonResponse<TeamResponse> createTeam(TeamRequest request) {
//        Team team = teamRepository.findTeamByName(request.getName()).orElseThrow(
//                () -> new IllegalArgumentException("팀 이름이 존재하지 않습니다."));

        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Team savedTeam = teamRepository.save(team);

        return new CommonResponse<>(
                true,
                "팀 생성 성공",
                TeamResponse.from(savedTeam),
                LocalDateTime.now()
        );
    }

    @Transactional
    public CommonResponse<List<TeamResponse>> AllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamResponse> responses = teams.stream().map(TeamResponse::from).toList();
        return new CommonResponse (
                true,
                "전체 팀 조회 성공",
                responses,
                LocalDateTime.now()
        );
    }

    @Transactional
    public CommonResponse<TeamResponse> OneTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new IllegalArgumentException("팀 아이디가 존재하지 않습니다."));

        return new CommonResponse<>(
                true,
                "팀 조회 성공",
                TeamResponse.from(team),
                LocalDateTime.now()
        );

    }

    @Transactional
    public CommonResponse<TeamResponse> updateTeam(Long teamId, TeamRequest request) {
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

        team.setName(request.getName());
        team.setDescription(request.getDescription());

        Team updatedTeam = teamRepository.save(team);

        return new CommonResponse<>(
                true,
                "팀 수정 성공",
                TeamResponse.from(updatedTeam),
                LocalDateTime.now()
        );
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        teamRepository.deleteByTeamId(teamId);
    }

}