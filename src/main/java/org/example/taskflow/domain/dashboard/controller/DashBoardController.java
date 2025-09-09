package org.example.taskflow.domain.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.Response;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.dashboard.dto.DashboardStatsResponse;
import org.example.taskflow.domain.dashboard.dto.MyTasksResponse;
import org.example.taskflow.domain.dashboard.dto.WeeklyTrendResponse;
import org.example.taskflow.domain.dashboard.enums.ResponseMessage;
import org.example.taskflow.domain.dashboard.service.DashboardServiceImpl;
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

    private final DashboardServiceImpl dashboardServiceImpl;

    @GetMapping("/stats")
    public ResponseEntity<Response<DashboardStatsResponse>> getDashboardStats() {
        DashboardStatsResponse statsResponse = dashboardServiceImpl.getDashboardStats();
        return ResponseEntity.ok(
                ResponseUtil.success(statsResponse, ResponseMessage.STATS_SUCCESS.getMessage())
        );
    }

    /**
     * 내 작업 요약 조회
     */
    @GetMapping("/my-tasks")
    public ResponseEntity<Response<MyTasksResponse>> getMyTasks() {
        MyTasksResponse myTasksResponse = dashboardServiceImpl.getMyTasks();
        return ResponseEntity.ok(
                ResponseUtil.success(myTasksResponse, ResponseMessage.MY_TASKS_SUCCESS.getMessage())
        );
    }

    @GetMapping("team-progress")
    public ResponseEntity<Response<Map<String, Integer>>> getTeamProgress() {
        Map<String, Integer> teamProgressMap = dashboardServiceImpl.getTeamProgress();

        return ResponseEntity.ok(
                ResponseUtil.success(teamProgressMap, ResponseMessage.TEAM_PROGRESS_SUCCESS.getMessage())
        );
    }

    @GetMapping("/weekly-trend")
    public ResponseEntity<Response<List<WeeklyTrendResponse>>> getWeeklyTrend() {
        List<WeeklyTrendResponse> weeklyTrend = dashboardServiceImpl.getWeeklyTrendResponse();

        return ResponseEntity.ok(
                ResponseUtil.success(weeklyTrend, ResponseMessage.WEEKLY_TREND_SUCCESS.getMessage())
        );
    }
}

