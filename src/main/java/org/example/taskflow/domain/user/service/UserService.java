package org.example.taskflow.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.common.utils.SecurityUtil;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        String username = SecurityUtil.getCurrentUsername();

        // 사용자 이름으로 데이터베이스에서 사용자 정보를 조회
        User user = userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // User 엔티티를 UserResponse DTO로 변환하여 반환합니다.
        return UserResponse.from(user);
    }
}
