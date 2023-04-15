package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TeacherService {

    Teacher save(Teacher teacher);
    Teacher findById(UUID id);
    Page<Teacher> findAll(Pageable pageable);
    Teacher update(Teacher teacher);
    void delete(UUID id);
}
