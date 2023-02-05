package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.EducationalProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EducationalProgramRepository extends JpaRepository<EducationalProgram, UUID> {
}
