package com.example.universityscheduler.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gcp.firebase",
        ignoreUnknownFields = false)
public class FirebaseProperties {

    private String serviceAccount;
}
