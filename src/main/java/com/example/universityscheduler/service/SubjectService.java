package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubjectService {

    Subject save(Subject subject);
    Subject findById(UUID id);
    Page<Subject> findAll(Pageable pageable);
    Subject update(Subject subject);
    void delete(UUID id);
}
