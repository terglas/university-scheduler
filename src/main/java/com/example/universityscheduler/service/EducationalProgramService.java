package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.EducationalProgram;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface EducationalProgramService {

    EducationalProgram save(EducationalProgram educationalProgram);
    List<EducationalProgram> findAll(PageParams pageParams);
    List<EducationalProgram> findAll(PageParams pageParams, UUID universityId);
    List<EducationalProgram> findAll(PageParams pageParams, String universityCode);
    EducationalProgram findById(UUID id);
    EducationalProgram findById(UUID id, UUID universityId);
    EducationalProgram findById(UUID id, String universityCode);
    EducationalProgram update(EducationalProgram educationalProgram);
    void delete(UUID id);

    // add subject to educational program
    void addSubject(UUID educationalProgramId, UUID subjectId);
    // remove subject from educational program
    void removeSubject(UUID educationalProgramId, UUID subjectId);
    // get all subjects from educational program
    // get all educational programs by subject
}
