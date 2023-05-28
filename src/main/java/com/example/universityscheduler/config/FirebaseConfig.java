package com.example.universityscheduler.config;

import com.example.universityscheduler.config.property.FirebaseProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {

    private final FirebaseProperties firebaseProperties;

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    @Bean
    public FirebaseApp firebaseApp(GoogleCredentials googleCredentials) {
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(googleCredentials)
            .build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
        if(firebaseProperties.getServiceAccount() != null) {
            try (InputStream is = new ByteArrayInputStream(firebaseProperties.getServiceAccount().getBytes())) {
                return GoogleCredentials.fromStream(is);
            }
        }
        return GoogleCredentials.getApplicationDefault();
    }

}
