package org.example.taskflow.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getCurrentUser() {
        // SecurityContextHolder에서 현재 로그인된 사용자의 이름을 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 사용자 이름으로 데이터베이스에서 사용자 정보를 조회
        User user = userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // User 엔티티를 UserResponse DTO로 변환하여 반환합니다.
        return UserResponse.from(user);
    }
}
