package org.example.taskflow.domain.activity.repository;

import org.example.taskflow.domain.activity.entity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {
    /**
     * typeCode로 ActivityType 엔티티 조회
     */
    Optional<ActivityType> findByTypeCode(String typeCode);
}
