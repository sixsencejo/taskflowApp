package org.example.taskflow.domain.dashboard.service;

import org.example.taskflow.domain.dashboard.dto.ActivityDto;
import org.example.taskflow.domain.dashboard.dto.DashboardStatsResponse;
import org.example.taskflow.domain.dashboard.dto.MyTasksResponse;
import org.example.taskflow.domain.dashboard.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface DashboardServiceImpl {
    /**
     * 대시보드 통계 조회
     *
     * @return 대시보드 통계
     */
    DashboardStatsResponse getDashboardStats();

    /**
     * 사용자 작업 목록 조화
     *
     * @return 사용자 작업 정보
     */
    MyTasksResponse getMyTasks();

    /**
     * 팀별 진행률 조회
     *
     * @return 팀별 진행률
     */
    Map getTeamProgress();

    /**
     * 탐 활동 내역을 페이징으로 조회
     *
     * @param username
     * return 활동내역 페이지 응답
     */
    PageResponse<ActivityDto> getActivityDto(String username, Pageable pageable);
}
