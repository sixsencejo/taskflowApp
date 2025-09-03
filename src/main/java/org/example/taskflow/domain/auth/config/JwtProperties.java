package org.example.taskflow.domain.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.jwt")
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private Token token;

    @Getter
    @Setter
    public static class Token {
        private long accessExpiration;
    }
}
