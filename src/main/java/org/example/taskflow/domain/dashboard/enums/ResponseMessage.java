package org.example.taskflow.domain.dashboard.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {
    STATS_SUCCESS("대시보드 통계 조회 완료"),
    MY_TASKS_SUCCESS("내 작업 요약 조회 완료"),
    TEAM_PROGRESS_SUCCESS("팀 진행률 조회 완료"),
    WEEKLY_TREND_SUCCESS("주간 작업 추세 조회 완료"),
    ACTIVITIES_SUCCESS("활동 내역 조회 완료");

    private final String message;
}
