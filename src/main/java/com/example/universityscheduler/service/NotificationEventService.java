package com.example.universityscheduler.service;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface NotificationEventService {

    void scheduleFixedDelayTask() throws FirebaseMessagingException;
}
