package org.example.taskflow.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.common.utils.SecurityUtil;
import org.example.taskflow.domain.search.dto.UserSearchDto;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.team.repository.TeamRepository;
import org.example.taskflow.domain.user.dto.UserInfoForTaskResponse;
import org.example.taskflow.domain.user.dto.UserResponse;
import org.example.taskflow.domain.user.entity.User;
import org.example.taskflow.domain.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

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

    // 현재 로그인 된 유저 엔티티 반환 2025-09-08 작성 이동재
    @Transactional(readOnly = true)
    public User getCurrentUserEntity() {
        String username = SecurityUtil.getCurrentUsername();

        return userRepository.getByUsernameWithoutDeletedAtOrThrow(username);
    }

    // 4.9 추가 가능한 사용자 목록 조회
    public List<UserResponse> getInsertTeamUsers(Long teamId) {
        List<User> users = userRepository.findAllByDeletedAtIsNull();
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        users = users.stream()
                .filter(user -> !Objects.equals(user.getTeam(), team))
                .toList();

        return users.stream().map(UserResponse::from).toList();
    }

    // 4.9 추가 가능한 사용자 목록 조회
    public List<UserResponse> getInsertTeamUsers() {
        List<User> users = userRepository.findAllByDeletedAtIsNull();

        users = users.stream().filter(user -> user.getTeam() == null).toList();

        return users.stream().map(UserResponse::from).toList();
    }

    //유저 통합 검색 서비스 기능 2025-09-09 작성 이동재
    @Transactional(readOnly = true)
    public List<UserSearchDto> searchUsersForIntegratedSearch(String query, int limit) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseAndDeletedAtIsNull(
                query, query, PageRequest.of(0, limit)
        );

        return users.stream()
                .map(UserSearchDto::from)
                .collect(Collectors.toList());
    }

}
