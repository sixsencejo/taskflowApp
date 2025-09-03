package org.example.taskflow.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.auth.dto.AuthRegisterRequest;
import org.example.taskflow.domain.auth.service.AuthService;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserResponse>> registerUser(@Valid @RequestBody AuthRegisterRequest request) {
        UserResponse userResponse = authService.register(request);
        return ResponseEntity.ok(ResponseUtil.success(userResponse, "회원가입이 완료되었습니다."));
    }
}
