package com.example.universityscheduler.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt",
        ignoreUnknownFields = false)
public class JwtProperties {

    private String secret;
    private String base64Secret;
    private long tokenValidityInSeconds;
    private String contentSecurityPolicy;
}
