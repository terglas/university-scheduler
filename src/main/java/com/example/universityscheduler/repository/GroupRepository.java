package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroupRepository  extends JpaRepository<Group, UUID> {

    Page<Group> findAllByTitleContainsAndEducationalProgramUniversityId(String title, UUID universityId, Pageable pageable);
    Page<Group> findAllEducationalProgramUniversityId(Pageable pageable, UUID universityId);
   Optional<Group> findByIdAndEducationalProgramUniversityId(UUID groupId, UUID universityId);

}
