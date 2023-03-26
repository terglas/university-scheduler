package com.example.universityscheduler.repository;

import com.example.universityscheduler.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAccountRepository  extends JpaRepository<UserAccount, UUID> {

    boolean existsByEmail(String email);

    UserAccount findByEmail(String email);
}