package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository  extends JpaRepository<Schedule, UUID> {
}
