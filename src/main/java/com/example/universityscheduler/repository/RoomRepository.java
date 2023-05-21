package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByIdAndUniversityId(UUID id, UUID universityId);

    Page<Room> findAllByUniversityId(Pageable pageable, UUID universityId);
}
