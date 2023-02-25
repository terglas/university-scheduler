package com.example.universityscheduler.service;

import com.example.universityscheduler.model.SubjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubjectService {

    SubjectInfo save(SubjectInfo subjectDTO);
    SubjectInfo findById(UUID id);
    Page<SubjectInfo> findAll(Pageable pageable);
    SubjectInfo update(SubjectInfo subjectDTO);
    void delete(UUID id);
}
