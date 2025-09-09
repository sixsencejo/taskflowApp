package org.example.taskflow.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.Response;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.auth.dto.*;
import org.example.taskflow.domain.auth.enums.ResponseCode;
import org.example.taskflow.domain.auth.service.AuthService;
import org.example.taskflow.domain.user.dto.UserResponse;
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
    public Response<UserResponse> registerUser(@Valid @RequestBody AuthRegisterRequest request) {
        UserResponse userResponse = authService.register(request);
        return ResponseUtil.success(userResponse, ResponseCode.AUTH_SIGNUP_RESPONSE.getMessage());
    }

    @PostMapping("/login")
    public Response<AuthLoginResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        AuthLoginResponse token = authService.login(request);
        return ResponseUtil.success(token, ResponseCode.AUTH_LOGIN_RESPONSE.getMessage());
    }

    @PostMapping("/withdraw")
    public Response<Void> withdraw(@Valid @RequestBody AuthWithdrawRequest request) {
        authService.withdraw(request);
        return ResponseUtil.success(null, ResponseCode.AUTH_WITHDRAW_RESPONSE.getMessage());
    }

    @PostMapping("/recover")
    public Response<UserResponse> recoverAccount(@RequestBody @Valid AuthRecoverRequest request) {
        UserResponse userResponse = authService.recoverAccount(request);
        return ResponseUtil.success(userResponse, ResponseCode.AUTH_ACCOUNT_RECOVER_RESPONSE.getMessage());
    }
}
