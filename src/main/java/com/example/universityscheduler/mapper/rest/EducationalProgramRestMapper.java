package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.EducationalProgram;
import com.example.universityscheduler.model.EducationalProgramInfo;
import org.mapstruct.Mapper;

@Mapper
public interface EducationalProgramRestMapper {

    EducationalProgram toEntity(EducationalProgramInfo educationalProgramInfo);
    EducationalProgramInfo toDto(EducationalProgram educationalProgram);
}
