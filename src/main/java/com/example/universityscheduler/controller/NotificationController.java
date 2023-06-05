package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.NotificationzApi;
import com.example.universityscheduler.mapper.rest.NotificationRestMapper;
import com.example.universityscheduler.model.NotificationEvent;
import com.example.universityscheduler.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NotificationController implements NotificationzApi {

    private final NotificationService notificationService;
    private final NotificationRestMapper notificationRestMapper;

    @Override
    public ResponseEntity<NotificationEvent> registerDevice(NotificationEvent notificationEvent) {
        val notification = notificationService.save(notificationRestMapper.toEntity(notificationEvent));
        return ResponseEntity.ok(notificationRestMapper.toDto(notification));
    }
}
