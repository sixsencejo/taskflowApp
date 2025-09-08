package org.example.taskflow.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.team.entity.Team;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TeamSearchDto {
    private Long id;
    private String name;
    private String description;

    public static TeamSearchDto from(Team team) {
        return TeamSearchDto.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .build();
    }
}
