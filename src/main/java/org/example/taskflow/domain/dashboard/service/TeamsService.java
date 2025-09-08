package org.example.taskflow.domain.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.dashboard.repository.TeamsRepository;
import org.example.taskflow.domain.team.entity.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamsService {
    private final TeamsRepository teamRepository;

    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}
