package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    Optional<Subject> findByIdAndUniversityId(UUID id, UUID universityId);

    Page<Subject> findAllByUniversityId(Pageable pageable, UUID universityId);

}
