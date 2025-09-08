package org.example.taskflow.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.common.utils.SecurityUtil;
import org.example.taskflow.domain.user.dto.UserInfoForTaskResponse;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        String username = SecurityUtil.getCurrentUsername();

        User user = userRepository.getByUsernameWithoutDeletedAtOrThrow(username);

        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public Long getUserId() {
        String username = SecurityUtil.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user.getId();
    }

    // Task - 사용자 정보 조회
    @Transactional(readOnly = true)
    public List<UserInfoForTaskResponse> getUsers() {
        List<User> users = userRepository.findAllByDeletedAtIsNull();

        return users.stream()
                .map(user -> new UserInfoForTaskResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public User getCurrentUserEntity() {
        String username = SecurityUtil.getCurrentUsername();

        return userRepository.getByUsernameWithoutDeletedAtOrThrow(username);
    }
}
