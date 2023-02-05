package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.dto.TeacherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TeacherService {

    TeacherDTO save(TeacherDTO teacherDTO);
    TeacherDTO findById(UUID id);
    Page<TeacherDTO> findAll(Pageable pageable);
}
