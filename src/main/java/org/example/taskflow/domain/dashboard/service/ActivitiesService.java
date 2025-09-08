package org.example.taskflow.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.activity.entity.Activity;
import org.example.taskflow.domain.dashboard.repository.ActivitiesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivitiesService {

    private final ActivitiesRepository activitiesRepository;


    public Page<Activity> findUserActivities(Long userId, Pageable pageable) {
        // 사용자별 활동 조회
        return activitiesRepository.findUserActivities(userId, pageable);
    }
}
