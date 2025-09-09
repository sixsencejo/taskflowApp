package org.example.taskflow.domain.user.repository;

import org.example.taskflow.common.exception.CustomException;
import org.example.taskflow.common.exception.ErrorCode;
import org.example.taskflow.domain.team.entity.Team;
import org.example.taskflow.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    default User getByUsernameWithoutDeletedAtOrThrow(String username) {
        return findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    default User getByUsernameOrThrow(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    List<User> findAllByDeletedAtIsNull();

    List<User> findByTeam(Team team);

    Optional<User> findByIdAndTeam(Long id, Team team);


    // 유저 통합 검색을 위해 대소문자 구분 없이 추출 2025-09-09 수정 이동재
    @Query("SELECT u FROM User u WHERE " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%')) OR " +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND u.deletedAt IS NULL")
    List<User> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseAndDeletedAtIsNull(
            @Param("username") String username,
            @Param("name") String name,
            Pageable pageable);
}
