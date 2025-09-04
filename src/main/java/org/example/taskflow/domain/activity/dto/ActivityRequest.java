package org.example.taskflow.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRequest {

    private Long userId;
    private Long taskId;
    private String activityType;
    private String category;

}
