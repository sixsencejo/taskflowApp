package org.example.taskflow.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.user.dto.UserInfoForTaskResponse;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.example.taskflow.domain.user.enums.ResponseCode;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<UserResponse>> getCurrentUser() {
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok(ResponseUtil.success(userResponse, ResponseCode.USER_FETCH_RESPONSE.getMessage()));
    }

    @GetMapping
    public CommonResponse<List<UserInfoForTaskResponse>> getUsers() {
        return ResponseUtil.success(userService.getUsers(), ResponseCode.USER_FOR_TASK_RESPONSE.getMessage());
    }
}
