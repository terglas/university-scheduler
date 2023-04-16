package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.University;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface UniversityService {

    University save(University university);
    University findById(UUID id);
    List<University> findAll(PageParams pageParams);
    University update(University university);
    void delete(UUID id);

}
