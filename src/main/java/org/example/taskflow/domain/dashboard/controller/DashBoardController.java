package org.example.taskflow.domain.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.dashboard.dto.DashboardStatsResponse;
import org.example.taskflow.domain.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<CommonResponse<DashboardStatsResponse>> getDashboardStats() {
        DashboardStatsResponse statsResponse = dashboardService.getDashboardStats();
        return ResponseEntity.ok(
                ResponseUtil.success(statsResponse,"대시보드 통계 조회 완료")
        );
    }
}
