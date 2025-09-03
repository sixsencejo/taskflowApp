package org.example.taskflow.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.domain.auth.dto.AuthRegisterRequest;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.enums.UserRole;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(AuthRegisterRequest request) {
        if (userRepository.existsByUsernameAndDeletedAtIsNull(request.getUsername())) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .role(UserRole.USER)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }
}
