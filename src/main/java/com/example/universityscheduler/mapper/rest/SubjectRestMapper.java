package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.model.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectRestMapper {

    SubjectRestMapper INSTANCE = Mappers.getMapper(SubjectRestMapper.class);

    Subject toEntity(SubjectInfo subjectDTO);
    SubjectInfo toDto(Subject subject);
}
