package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.model.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface SubjectRestMapper {

    Subject toEntity(SubjectInfo subjectDTO);
    SubjectInfo toDto(Subject subject);

    @Mapping(target = "id", source = "subjectId")
    Subject toEntity(UUID subjectId);
}
