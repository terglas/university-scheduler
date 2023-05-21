package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.EducationalProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EducationalProgramRepository extends JpaRepository<EducationalProgram, UUID> {

    Optional<EducationalProgram> findByIdAndUniversityId(UUID id, UUID universityId);
    Page<EducationalProgram> findAllByUniversityId(Pageable pageable, UUID universityId);
    @Query(name = "EducationalProgram.findCourses", nativeQuery = true, countName = "EducationalProgram.countByCourses")
    Page<Integer> findCourses(@Param("educationalProgramId") UUID educationalProgramId, @Param("universityId") UUID universityId, Pageable pageParams);

}
