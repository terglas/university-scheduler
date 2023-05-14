package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface TeacherService {

    Teacher save(Teacher teacher);
    Teacher findById(UUID id);
    Teacher findById(UUID id, UUID universityId);
    Teacher findById(UUID id, String universityCode);
    List<Teacher> findAll(PageParams pageParams);
    List<Teacher> findAll(PageParams pageParams, UUID universityId);
    List<Teacher> findAll(PageParams pageParams, String universityCode);
    Teacher update(Teacher teacher);
    void delete(UUID id);
}
