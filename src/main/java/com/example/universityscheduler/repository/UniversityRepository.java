package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UniversityRepository  extends JpaRepository<University, UUID> {
}
