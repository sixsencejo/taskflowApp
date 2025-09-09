package org.example.taskflow.domain.team.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.Response;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.team.dto.TeamDeleteResponse;
import org.example.taskflow.domain.team.dto.TeamMemberInsertRequest;
import org.example.taskflow.domain.team.dto.TeamRequest;
import org.example.taskflow.domain.team.dto.TeamResponse;
import org.example.taskflow.domain.team.enums.ResponseCode;
import org.example.taskflow.domain.team.service.TeamService;
import org.example.taskflow.domain.user.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public Response<List<TeamResponse>> getTeams() {
        List<TeamResponse> teamResponse = teamService.getTeams();

        return ResponseUtil.success(teamResponse, ResponseCode.TEAMS_SEARCH_SUSSES.getMessage());
    }

    @GetMapping("/{teamId}")
    public Response<TeamResponse> getTeam(
            @PathVariable Long teamId
    ) {
        TeamResponse teamResponse = teamService.getTeam(teamId);
        return ResponseUtil.success(teamResponse, ResponseCode.TEAM_SEARCH_SUSSES.getMessage());
    }

    @GetMapping("/{teamId}/members")
    public Response<List<User>> getTeamMembers(
            @PathVariable Long teamId
    ) {
        List<User> users = teamService.getMembersOfTeam(teamId);

        return ResponseUtil.success(users, ResponseCode.TEAM_SEARCH_MEMBER_SUSSES.getMessage());
    }

    @PostMapping
    public Response<TeamResponse> createTeam(
            @RequestBody TeamRequest teamRequest
    ) {
        TeamResponse teamResponse = teamService.createTeam(teamRequest);

        return ResponseUtil.success(teamResponse, ResponseCode.TEAM_CREATE_SUSSES.getMessage());
    }

    @PutMapping("/{teamId}")
    public Response<TeamResponse> updateTeam(
            @PathVariable Long teamId,
            @RequestBody TeamRequest teamRequest
    ) {
        TeamResponse teamResponse = teamService.updateTeam(teamId, teamRequest);

        return ResponseUtil.success(teamResponse, ResponseCode.TEAM_UPDATE_SUSSES.getMessage());
    }

    @DeleteMapping("/{teamId}")
    public Response<TeamDeleteResponse> deleteTeam(
            @PathVariable Long teamId
    ) {
        TeamDeleteResponse teamDeleteResponse = teamService.deleteTeam(teamId);

        return ResponseUtil.success(teamDeleteResponse, ResponseCode.TEAM_DELETE_SUSSES.getMessage());
    }

    @PostMapping("/{teamId}/members")
    public Response<TeamResponse> insertMember(
            @PathVariable Long teamId,
            @RequestBody TeamMemberInsertRequest teamMemberInsertRequest
    ) {
        TeamResponse teamResponse = teamService.insertMember(teamId, teamMemberInsertRequest);

        return ResponseUtil.success(teamResponse, ResponseCode.TEAM_SEARCH_MEMBER_SUSSES.getMessage());
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    public Response<TeamResponse> deleteMember(
            @PathVariable Long teamId,
            @PathVariable Long userId
    ) {
        TeamResponse teamResponse = teamService.deleteMember(teamId, userId);

        return ResponseUtil.success(teamResponse, ResponseCode.TEAM_MEMBER_DELETE_SUSSES.getMessage());
    }
}
