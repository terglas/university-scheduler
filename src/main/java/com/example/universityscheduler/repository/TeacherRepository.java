package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository  extends JpaRepository<Teacher, UUID> {

   Optional<Teacher> findByIdAndUniversityId(UUID id, UUID universityId);

   Page<Teacher> findAllByUniversityId(Pageable pageable, UUID universityId);
}
