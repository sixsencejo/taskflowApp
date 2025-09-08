package org.example.taskflow.domain.activity.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.Response;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.activity.dto.ActivityPageResponse;
import org.example.taskflow.domain.activity.dto.ActivityResponse;
import org.example.taskflow.domain.activity.service.ActivityService;
import org.example.taskflow.domain.dashboard.dto.ActivityDto;
import org.example.taskflow.domain.dashboard.dto.PageResponse;
import org.example.taskflow.domain.dashboard.service.DashboardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final DashboardService dashboardService;

    @GetMapping
    public Response<ActivityPageResponse<ActivityResponse>> getActivities(
            @RequestParam(name = "type", required = false) String typeCode,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ActivityResponse> activityPage = activityService.getActivities(
                typeCode, userId, taskId, startDate, endDate, pageable);
        return ResponseUtil.success(new ActivityPageResponse<>(activityPage), "활동 로그를 조회했습니다.");
    }

    @GetMapping("/my")
    public ResponseEntity<Response<PageResponse<ActivityDto>>> getActivities(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        PageResponse<ActivityDto> activityDtoPageResponse = dashboardService.getActivities(pageable);

        return ResponseEntity.ok(
                ResponseUtil.success(activityDtoPageResponse, "활동 내역 조회 완료")
        );
    }

}
