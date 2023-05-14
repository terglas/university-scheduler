package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UniversityRepository  extends JpaRepository<University, UUID> {
    Optional<University> findByCode(String code);
}
