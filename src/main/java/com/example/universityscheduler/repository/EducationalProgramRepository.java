package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.EducationalProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EducationalProgramRepository extends JpaRepository<EducationalProgram, UUID> {

    Optional<EducationalProgram> findByIdAndUniversityId(UUID id, UUID universityId);

    Page<EducationalProgram> findAllByUniversityId(Pageable pageable, UUID universityId);


}
