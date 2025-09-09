package org.example.taskflow.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.domain.comment.enums.ResponseCode;
import org.example.taskflow.domain.team.dto.TeamDeleteResponse;
import org.example.taskflow.domain.team.dto.TeamMemberInsertRequest;
import org.example.taskflow.domain.team.dto.TeamRequest;
import org.example.taskflow.domain.team.dto.TeamResponse;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.team.repository.TeamRepository;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    // 4.1 팀 목록 조회
    @Transactional(readOnly = true)
    public List<TeamResponse> getTeams() {
        List<Team> teams = teamRepository.findAll();

        return teams.stream().map(team -> {
            List<User> members = userRepository.findByTeam(team);

            return new TeamResponse(team, members);
        }).collect(Collectors.toList());
    }

    // 4.2 특정 팀 조회
    @Transactional(readOnly = true)
    public TeamResponse getTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND, ErrorCode.TASK_NOT_FOUND.getMessage())
        );
        List<User> members = userRepository.findByTeam(team);

        return new TeamResponse(team, members);
    }

    // 4.3 팀 멤버 목록 조회
    @Transactional(readOnly = true)
    public List<User> getMembersOfTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND, ErrorCode.TASK_NOT_FOUND.getMessage())
        );

        return userRepository.findByTeam(team);
    }

    // 4.4 팀 생성
    public TeamResponse createTeam(TeamRequest teamRequest) {
        if (teamRepository.existsByName(teamRequest.name())) {
            throw new CustomException(ErrorCode.TEAM_NAME_ALREADY_EXISTS, ErrorCode.TEAM_NAME_ALREADY_EXISTS.getMessage());
        }

        Team team = teamRepository.save(
                Team.builder()
                        .name(teamRequest.name())
                        .description(teamRequest.description())
                        .build()
        );

        List<User> members = userRepository.findByTeam(team);
        return new TeamResponse(team, members);
    }

    // 4.5 팀 정보 수정
    public TeamResponse updateTeam(Long teamId, TeamRequest teamRequest) {
        if (teamRepository.existsByName(teamRequest.name())) {
            throw new CustomException(ErrorCode.TEAM_NAME_ALREADY_EXISTS, ErrorCode.TEAM_NAME_ALREADY_EXISTS.getMessage());
        }
        
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND, ErrorCode.TASK_NOT_FOUND.getMessage())
        );

        team.updateTeamInfo(teamRequest);

        List<User> members = userRepository.findByTeam(team);

        return new TeamResponse(team, members);
    }

    // 4.6 팀 삭제
    public TeamDeleteResponse deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND, ErrorCode.TASK_NOT_FOUND.getMessage())
        );
        List<User> users = userRepository.findByTeam(team);

        users.forEach(User::deleteTeam);

        teamRepository.deleteById(teamId);

        return new TeamDeleteResponse(ResponseCode.COMMENT_DELETED_RESPONSE.getMessage());
    }

    // 4.7 팀 멤버 추가
    public TeamResponse insertMember(Long teamId, TeamMemberInsertRequest teamMemberInsertRequest) {
        User member = userRepository.findById(teamMemberInsertRequest.userId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage())
        );

        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND, ErrorCode.TASK_NOT_FOUND.getMessage())
        );

        member.updateTeam(team);

        List<User> members = userRepository.findByTeam(team);

        return new TeamResponse(team, members);
    }

    // 4.8 팀 멤버 제거
    public TeamResponse deleteMember(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ErrorCode.TASK_NOT_FOUND, ErrorCode.TASK_NOT_FOUND.getMessage())
        );

        User member = userRepository.findByIdAndTeam(userId, team).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage())
        );

        member.deleteTeam();

        List<User> members = userRepository.findByTeam(team);

        return new TeamResponse(team, members);
    }
}
