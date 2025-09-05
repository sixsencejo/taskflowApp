package org.example.taskflow.domain.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TeamRequest {

    @NotBlank(message = "이름을 입력해야합니다")
    private String name;

    @NotBlank(message = "팀 설명을 적어주세요.")
    private String description;
}
