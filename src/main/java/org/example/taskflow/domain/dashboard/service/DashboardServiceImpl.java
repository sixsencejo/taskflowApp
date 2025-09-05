package org.example.taskflow.domain.dashboard.service;

import org.example.taskflow.domain.dashboard.dto.*;
import org.springframework.data.domain.Pageable;

public interface DashboardServiceImpl {
    /**
     * 대시보드 통계 조회
     *
     * @param username
     * @return 대시보드 통계
     */
    DashboardStatsResponse getDashboardStats(String username);

    /**
     * 사용자 작업 목록 조화
     *
     * @param username
     * @return 사용자 작업 정보
     */
    MyTasksResponse getMyTasks(String username);

    /**
     * 팀별 진행률 조회
     *
     * @param username
     * @return 팀별 진행률
     */
    TeamProgressResponse getTeamProgress(String username);

    /**
     * 탐 활동 내역을 페이징으로 조회
     *
     * @param username
     * return 활동내역 페이지 응답
     */
    PageResponse<ActivityDto> getActivityDto(String username, Pageable pageable);
}
