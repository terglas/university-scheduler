package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepository  extends JpaRepository<Group, UUID> {
}
