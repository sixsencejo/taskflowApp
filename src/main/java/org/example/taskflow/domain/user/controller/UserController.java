package org.example.taskflow.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<UserResponse>> getCurrentUser() {
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok(ResponseUtil.success(userResponse, "사용자 정보를 조회했습니다."));
    }
}
