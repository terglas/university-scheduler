package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    @Query(name = "Schedule.findByGroupId", nativeQuery = true)
    Page<Schedule> findByGroupId( @Param("groupId") UUID groupId, Pageable pageable);

    Page<Schedule> findByTeacherId(UUID teacherId, Pageable pageable);

    Page<Schedule> findBySubjectId(UUID subjectId, Pageable pageable);
}
