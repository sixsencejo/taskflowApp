package org.example.taskflow.domain.team.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.team.dto.request.TeamRequest;
import org.example.taskflow.domain.team.dto.response.TeamResponse;
import org.example.taskflow.domain.team.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamServcie;

    @PostMapping("/teams")
    public ResponseEntity<TeamResponse> createTeam(
            @Valid @RequestBody TeamRequest request
    ) {
        return ResponseEntity.ok(teamServcie.createTeam(request));
    }

    @GetMapping("/teams")
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        return ResponseEntity.ok(teamServcie.AllTeams());
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<TeamResponse> getTeams(
            @PathVariable Long teamId
    ) {
        return ResponseEntity.ok(teamServcie.OneTeam(teamId));
    }

    @PutMapping("/teams/{teamId}")
    public ResponseEntity<TeamResponse> updateTeam(
            @PathVariable Long teamId,
            @Valid @RequestBody TeamRequest request
    ) {
        return ResponseEntity.ok(teamServcie.updateTeam(teamId, request));
    }

    @DeleteMapping("/teams/{teamId}")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable Long teamId
    ) {
        teamServcie.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }

}
