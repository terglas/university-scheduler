package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.EducationalProgram;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface EducationalProgramService {

    EducationalProgram save(EducationalProgram educationalProgram);
    List<EducationalProgram> findAll(PageParams pageParams);
    EducationalProgram findById(UUID id);
    EducationalProgram update(EducationalProgram educationalProgram);
    void delete(UUID id);
}
