package org.example.taskflow.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.auth.dto.*;
import org.example.taskflow.domain.auth.enums.ResponseCode;
import org.example.taskflow.domain.auth.service.AuthService;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserResponse>> registerUser(@Valid @RequestBody AuthRegisterRequest request) {
        UserResponse userResponse = authService.register(request);
        return ResponseEntity.ok(ResponseUtil.success(userResponse, ResponseCode.AUTH_SIGNUP_RESPONSE.getMessage()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<AuthLoginResponse>> login(@Valid @RequestBody AuthLoginRequest request) {
        AuthLoginResponse token = authService.login(request);
        return ResponseEntity.ok(ResponseUtil.success(token, ResponseCode.AUTH_LOGIN_RESPONSE.getMessage()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<CommonResponse<Void>> withdraw(@Valid @RequestBody AuthWithdrawRequest request) {
        authService.withdraw(request);
        return ResponseEntity.ok(ResponseUtil.success(null, ResponseCode.AUTH_WITHDRAW_RESPONSE.getMessage()));
    }

    @PostMapping("/recover")
    public ResponseEntity<CommonResponse<UserResponse>> recoverAccount(@RequestBody @Valid AuthRecoverRequest request) {
        UserResponse userResponse = authService.recoverAccount(request);
        return ResponseEntity.ok(ResponseUtil.success(userResponse, ResponseCode.AUTH_ACCOUNT_RECOVER_RESPONSE.getMessage()));
    }
}
