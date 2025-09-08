package org.example.taskflow.domain.team.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.team.dto.request.TeamRequest;
import org.example.taskflow.domain.team.dto.response.TeamResponse;
import org.example.taskflow.domain.team.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class MemberController {

    private final MemberService memberService;

    //회원 추가
    @PostMapping("/{teamId}/members")
    public ResponseEntity<TeamResponse> createMember(
            @PathVariable Long teamId,
            @RequestBody TeamRequest request
    ) {
        return ResponseEntity.ok(memberService.createMember(teamId, request));
    }

    //회원삭제
    //teamId -> 팀식별
    //userId -> 유저인지 식별
    @DeleteMapping("/{teamId}/members/{userId}")
    private ResponseEntity<Void> deleteById(
            @PathVariable Long teamId,
            @PathVariable Long userId
    ) {
        memberService.deleteById(teamId, userId);
        return ResponseEntity.noContent().build();
    }

}
