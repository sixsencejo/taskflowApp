package org.example.taskflow.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthWithdrawRequest {
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
