package org.example.taskflow.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.dashboard.repository.TeamsRepository;
import org.example.taskflow.domain.search.dto.TeamSearchDto;
import org.example.taskflow.domain.team.entity.Team;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamsService {
    private final TeamsRepository teamRepository;

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public List<TeamSearchDto> searchTeamsForIntegratedSearch(String query, int limit) {
        List<Team> teams = teamRepository.findByNameContainingIgnoreCaseAndDeletedAtIsNull(
                query, PageRequest.of(0, limit)
        );

        return teams.stream()
                .map(TeamSearchDto::from)
                .collect(Collectors.toList());
    }
}
