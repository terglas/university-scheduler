package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    Subject save(Subject subject);
    Subject findById(UUID id);
    List<Subject> findAll(PageParams pageParams);
    Subject update(Subject subject);
    void delete(UUID id);
}
