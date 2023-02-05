package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository  extends JpaRepository<Teacher, UUID> {
}
