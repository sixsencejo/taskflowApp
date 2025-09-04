package org.example.taskflow.domain.activity.repository;

import org.example.taskflow.domain.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
