package com.example.universityscheduler.config.property;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "notification.time",
        ignoreUnknownFields = false)
public class NotificationTimeProperties {

    private Long notifyTimeInMinutes;
    private Long timeSpreadInMinutes;
}
