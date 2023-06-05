package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.config.property.NotificationTimeProperties;
import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.exception.InternalException;
import com.example.universityscheduler.repository.NotificationRepository;
import com.example.universityscheduler.repository.ScheduleRepository;
import com.example.universityscheduler.service.NotificationEventService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@EnableAsync
@Service
@RequiredArgsConstructor
public class NotificationEventServiceImpl implements NotificationEventService {

    private final NotificationRepository notificationRepository;
    private final ScheduleRepository scheduleRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final NotificationTimeProperties notificationTimeProperties;

    @Async
    @Scheduled(fixedDelayString = "${scheduler.fixedDelay}")
    @Transactional
    @Override
    public void scheduleFixedDelayTask() {
        val currentTime = LocalTime.now().plusMinutes(notificationTimeProperties.getNotifyTimeInMinutes());
        val currentDate = LocalDate.now();

        val startTime = currentTime.minusMinutes(notificationTimeProperties.getTimeSpreadInMinutes());
        val endTime = currentTime.plusMinutes(notificationTimeProperties.getTimeSpreadInMinutes());
        val week = String.valueOf(Schedule.Week.valueOf(currentDate.getDayOfWeek().toString()));
        List<Schedule> schedules = scheduleRepository.findAllByStartTime(startTime, endTime, week);
        Set<UUID> groupIds = schedules.stream()
                .flatMap(schedule -> schedule.getGroups().stream())
                .map(Group::getId)
                .collect(Collectors.toSet());
        for (UUID id : groupIds) {
            List<com.example.universityscheduler.domain.Notification> notifications = notificationRepository.findAllByGroupsId(id);
            if (notifications.isEmpty()) {
                continue;
            }
            schedules.stream().filter(schedule -> schedule.getGroups().stream().anyMatch(group -> group.getId().equals(id))).forEach(schedule -> {
                        StringBuilder bodyBuilder = new StringBuilder();
                        bodyBuilder.append("Starts: ")
                                .append(String.format("%02d:%02d", schedule.getStartTime().getHour(), schedule.getStartTime().getMinute()))
                                .append(" in room ")
                                .append(schedule.getRoom().getName());
                        MulticastMessage msg = MulticastMessage.builder()
                                .addAllTokens(notifications.stream().map(com.example.universityscheduler.domain.Notification::getDeviceToken).collect(Collectors.toList()))
                                .setNotification(Notification.builder()
                                        .setTitle(schedule.getSubject().getTitle())
                                        .setBody(bodyBuilder.toString())
                                        .build())
                                .build();
                        try {
                            firebaseMessaging.sendMulticast(msg);
                        } catch (FirebaseMessagingException e) {
                            throw new InternalException("Message not sent", e);
                        }
                    }
            );
        }
    }
}
