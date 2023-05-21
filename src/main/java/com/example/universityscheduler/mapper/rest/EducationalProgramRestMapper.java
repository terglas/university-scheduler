package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.EducationalProgram;
import com.example.universityscheduler.model.CourseInfo;
import com.example.universityscheduler.model.EducationalProgramExtendedInfo;
import com.example.universityscheduler.model.EducationalProgramInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface EducationalProgramRestMapper {

    @Mapping(target = "university.id", source = "universityId")
    EducationalProgram toEntity(EducationalProgramInfo educationalProgramInfo);

    @Mapping(target = "universityId", source = "university.id")
    EducationalProgramInfo toDto(EducationalProgram educationalProgram);

    @Mapping(target = "universityId", source = "university.id")
    EducationalProgramExtendedInfo toExtendedDto(EducationalProgram educationalProgram);

    @Mapping(target = "courseNumber", source = "course")
    CourseInfo toCourseDto(Integer course, UUID educationalProgramId);
}
