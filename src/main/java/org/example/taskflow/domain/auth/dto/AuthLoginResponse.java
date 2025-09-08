package org.example.taskflow.domain.auth.dto;

import lombok.Builder;

@Builder
public record AuthLoginResponse(String token) {
}
