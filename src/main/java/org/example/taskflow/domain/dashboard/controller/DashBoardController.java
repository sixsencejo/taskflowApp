package org.example.taskflow.domain.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.Response;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.dashboard.dto.DashboardStatsResponse;
import org.example.taskflow.domain.dashboard.dto.MyTasksResponse;
import org.example.taskflow.domain.dashboard.dto.WeeklyDto;
import org.example.taskflow.domain.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<Response<DashboardStatsResponse>> getDashboardStats() {
        DashboardStatsResponse statsResponse = dashboardService.getDashboardStats();
        return ResponseEntity.ok(
                ResponseUtil.success(statsResponse, "대시보드 통계 조회 완료")
        );
    }

    /**
     * 내 작업 요약 조회
     */
    @GetMapping("/my-tasks")
    public ResponseEntity<Response<MyTasksResponse>> getMyTasks() {
        MyTasksResponse myTasksResponse = dashboardService.getMyTasks();
        return ResponseEntity.ok(
                ResponseUtil.success(myTasksResponse, "내 작업 요약 조회 완료")
        );
    }

    @GetMapping("team-progress")
    public ResponseEntity<Response<Map<String, Integer>>> getTeamProgress() {
        Map<String, Integer> teamProgressMap = dashboardService.getTeamProgress();

        return ResponseEntity.ok(
                ResponseUtil.success(teamProgressMap, "팀 진행률 조회")
        );
    }

    @GetMapping("/weekly-trend")
    public ResponseEntity<Response<List<WeeklyDto>>> getWeeklyTrend() {
        List<WeeklyDto> weeklyTrend = dashboardService.getWeeklyDto();

        return ResponseEntity.ok(
                ResponseUtil.success(weeklyTrend, "주간 작업 추세 조회 완료")
        );
    }
}

