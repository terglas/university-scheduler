package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Notification;
import com.example.universityscheduler.repository.NotificationRepository;
import com.example.universityscheduler.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }
}
