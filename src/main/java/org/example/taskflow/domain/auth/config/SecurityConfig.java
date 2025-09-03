package org.example.taskflow.domain.auth.config;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.domain.auth.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt 해시 함수를 이용해 비밀번호를 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF(Cross-Site Request Forgery) 공격 방지 기능 비활성화 (JWT 사용 시 필요 없음)
                .csrf(AbstractHttpConfigurer::disable)
                // HTTP Basic 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션 관리 방식 설정: JWT 기반이므로 세션을 사용하지 않음
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인가(Authorization) 규칙 설정
                .authorizeHttpRequests(authorize -> authorize
                        // "/api/auth/**" 경로는 모두 접근 허용
                        .requestMatchers("/api/auth/**").permitAll()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated())
                // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
