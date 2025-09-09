package org.example.taskflow.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private List<TaskSearchDto> tasks;
    private List<UserSearchDto> users;
    private List<TeamSearchDto> teams;
}
