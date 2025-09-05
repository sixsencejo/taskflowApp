package org.example.taskflow.domain.auth.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.common.utils.SecurityUtil;
import org.example.taskflow.domain.auth.dto.*;
import org.example.taskflow.domain.auth.jwt.JwtTokenProvider;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.enums.UserRole;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Transactional
    public UserResponse register(AuthRegisterRequest request) {
        Optional<User> existingUserOpt = userRepository.findByUsername(request.getUsername());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (existingUser.getDeletedAt() != null) {
                // 탈퇴된 회원이면 계정 복구 안내
                throw new CustomException(ErrorCode.USER_ALREADY_DELETED);
            } else {
                throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
            }
        }

        Optional<User> existingUserByEmailOpt = userRepository.findByEmail(request.getEmail());

        if (existingUserByEmailOpt.isPresent()) {
            User existingUser = existingUserByEmailOpt.get();
            if (existingUser.getDeletedAt() != null) {
                throw new CustomException(ErrorCode.EMAIL_ALREADY_DELETED);
            } else {
                throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
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

    @Transactional
    public AuthLoginResponse login(AuthLoginRequest request) {
        User user = userRepository.getByUsernameWithoutDeletedAtOrThrow(request.getUsername());


        if (user.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.USER_ALREADY_DELETED);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtTokenProvider.generateAccessToken(user.getUsername(), user.getRole().name());

        return AuthLoginResponse.builder()
                .token(token)
                .build();
    }

    @Transactional
    public void withdraw(@Valid AuthWithdrawRequest request) {
        String username = SecurityUtil.getCurrentUsername();

        User user = userRepository.getByUsernameOrThrow(username);

        if (user.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.USER_ALREADY_DELETED);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        user.softDelete();
    }


    /* 이메일로 인증번호 보내려 했는데 redis 사용해야 하고
       회원가입 할 때도 메일 인증 받아야할 것 같은데 일단
       이번 플젝에서 요구사항에 없어서 Pass
       회원 복구도 요구사항에 없지만 deletedAt을 사용하는
       soft Delete를 사용하기 때문에 회원가입시 중복 이메일 발생하면 처치 곤란
       회원 복구 간단하게 로그인처럼 만들어 둠
    * */
    @Transactional
    public UserResponse recoverAccount(@Valid AuthRecoverRequest request) {
        User user = userRepository.getByUsernameOrThrow(request.getUsername());

        if (user.getDeletedAt() == null) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        user.recover();

        return UserResponse.from(user);
    }
}
