package com.example.universityscheduler.service;

import com.example.universityscheduler.model.TeacherInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TeacherService {

    TeacherInfo save(TeacherInfo teacherDTO);
    TeacherInfo findById(UUID id);
    Page<TeacherInfo> findAll(Pageable pageable);
    TeacherInfo update(TeacherInfo teacherDTO);
    void delete(UUID id);
}
