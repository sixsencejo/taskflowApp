package org.example.taskflow.domain.activity.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.activity.dto.ActivityPageResponse;
import org.example.taskflow.domain.activity.dto.ActivityResponse;
import org.example.taskflow.domain.activity.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/activities")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping
    public CommonResponse<ActivityPageResponse<ActivityResponse>> getActivities(
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

}
