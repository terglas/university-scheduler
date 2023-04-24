package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.model.SubjectExtendedInfo;
import com.example.universityscheduler.model.SubjectShortInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface SubjectRestMapper {

    Subject toEntity(SubjectExtendedInfo subjectDTO);
    Subject toEntity(SubjectShortInfo subjectDTO);
    SubjectExtendedInfo toExtendedDto(Subject subject);
    SubjectShortInfo toShortDto(Subject subject);

    @Mapping(target = "id", source = "subjectId")
    Subject toEntity(UUID subjectId);
}
