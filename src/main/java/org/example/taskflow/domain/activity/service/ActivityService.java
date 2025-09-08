package org.example.taskflow.domain.activity.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.activity.dto.ActivityResponse;
import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.activity.repository.ActivityRepository;
import org.example.taskflow.domain.activity.repository.ActivitySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityRepository activityRepository;

    public Page<ActivityResponse> getActivities(
            String typeCode, Long userId, Long taskId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Specification<Activity> spec = buildSpecification(typeCode, userId, taskId, startDate, endDate);

        Page<Activity> activities = activityRepository.findAll(spec, pageable);

        return activities.map(ActivityResponse::from);
    }

    /**
     * API 명세서에 따라 다양한 필터 조건으로 Specification을 동적으로 생성합니다.
     */
    private Specification<Activity> buildSpecification(
            String typeCode, Long userId, Long taskId, LocalDate startDate, LocalDate endDate) {

        List<Specification<Activity>> spec = new ArrayList<>();

        if (StringUtils.hasText(typeCode)) {
            spec.add(ActivitySpecification.hasTypeCode(typeCode));
        }
        if (userId != null) {
            spec.add(ActivitySpecification.hasUserId(userId));
        }
        if (taskId != null) {
            spec.add(ActivitySpecification.hasTaskId(taskId));
        }
        if (startDate != null && endDate != null) {
            spec.add(ActivitySpecification.isBetweenDates(startDate, endDate));
        }
        return spec.stream().reduce(Specification::and).orElse(null);
    }
}

