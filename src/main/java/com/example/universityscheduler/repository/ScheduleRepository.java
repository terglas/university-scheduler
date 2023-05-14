package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    @Query(name = "Schedule.findByGroupId", nativeQuery = true, countName = "Schedule.countByGroupId")
    Page<Schedule> findByGroupId( @Param("groupId") UUID groupId, @Param("universityId") UUID universityId, Pageable pageable);

    Page<Schedule> findAllByTeacherIdAndTeacherUniversityId(UUID teacherId, UUID universityId, Pageable pageable);

    Page<Schedule> findAllBySubjectIdAndSubjectUniversityId(UUID subjectId, UUID universityId, Pageable pageable);

    Page<Schedule> findAllBySubjectUniversityId(UUID universityId, Pageable pageable);

    Optional<Schedule> findByIdAndSubjectUniversityId(UUID id, UUID universityId);

    List<Schedule> findByRoom(String room);
}
