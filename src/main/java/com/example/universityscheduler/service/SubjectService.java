package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    Subject save(Subject subject);
    Subject findById(UUID id);
    Subject findById(UUID id, UUID universityId);
    Subject findById(UUID id, String universityCode);
    List<Subject> findAll(PageParams pageParams);
    List<Subject> findAll(PageParams pageParams, UUID universityId);
    List<Subject> findAll(PageParams pageParams, String universityCode);
    Subject update(Subject subject);
    void delete(UUID id);
}
