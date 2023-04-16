package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface TeacherService {

    Teacher save(Teacher teacher);
    Teacher findById(UUID id);
    List<Teacher> findAll(PageParams pageParams);
    Teacher update(Teacher teacher);
    void delete(UUID id);
}
