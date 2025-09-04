package org.example.taskflow.domain.dashboard.service;

import org.example.taskflow.domain.dashboard.dto.*;
import org.springframework.data.domain.Pageable;

public interface DashboardServiceImpl {
    /**
     * 대시보드 통계 조회
     *
     * @param userId
     * @return 대시보드 통계
     */
    DashboardStatsResponse getDashboardStats(Long userId);

    /**
     * 사용자 작업 목록 조화
     *
     * @param userId
     * @return 사용자 작업 정보
     */
    MyTasksResponse getMyTasks(Long userId);

    /**
     * 팀별 진행률 조회
     *
     * @param userId
     * @return 팀별 진행률
     */
    TeamProgressResponse getTeamProgress(Long userId);

    /**
     * 탐 활동 내역을 페이징으로 조회
     *
     * @param userId
     * return 활동내역 페이지 응답
     */
    PageResponse<ActivityDto> getActivityDtos(Long userId, Pageable pageable);
}
