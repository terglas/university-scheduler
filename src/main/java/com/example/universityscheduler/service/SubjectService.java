package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.dto.SubjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubjectService {

    SubjectDTO save(SubjectDTO subjectDTO);
    SubjectDTO findById(UUID id);
    Page<SubjectDTO> findAll(Pageable pageable);
}
