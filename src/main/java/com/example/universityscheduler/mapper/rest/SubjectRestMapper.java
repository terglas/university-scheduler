package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.model.SubjectInfo;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectRestMapper {

    Subject toEntity(SubjectInfo subjectDTO);
    SubjectInfo toDto(Subject subject);
}
