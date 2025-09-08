package org.example.taskflow.domain.team.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.domain.team.dto.request.TeamRequest;
import org.example.taskflow.domain.team.dto.response.TeamResponse;
import org.example.taskflow.domain.team.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamServcie;

    @PostMapping
    public ResponseEntity<CommonResponse<TeamResponse>> createTeam(
            @Valid @RequestBody TeamRequest request
    ) {
        return ResponseEntity.ok(teamServcie.createTeam(request));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamResponse>>> getAllTeams() {
        return ResponseEntity.ok(teamServcie.AllTeams());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<CommonResponse<TeamResponse>> getTeams(
            @PathVariable Long teamId
    ) {
        return ResponseEntity.ok(teamServcie.OneTeam(teamId));
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<CommonResponse<TeamResponse>> updateTeam(
            @PathVariable Long teamId,
            @Valid @RequestBody TeamRequest request
    ) {
        return ResponseEntity.ok(teamServcie.updateTeam(teamId, request));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable Long teamId
    ) {
        teamServcie.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }

}
