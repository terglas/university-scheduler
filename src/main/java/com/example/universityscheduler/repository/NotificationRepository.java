package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findAllByGroupsId(UUID groupId);

}
