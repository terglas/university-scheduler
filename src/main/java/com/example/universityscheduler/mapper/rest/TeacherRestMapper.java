package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.model.TeacherExtendedInfo;
import com.example.universityscheduler.model.TeacherShortInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface TeacherRestMapper {

    @Mapping(target = "university.id", source = "universityId")
    Teacher toEntity(TeacherExtendedInfo teacherDTO);
    Teacher toEntity(TeacherShortInfo teacherDTO);

    @Mapping(target = "universityId", source = "university.id")
    TeacherExtendedInfo toExtendedDto(Teacher teacher);
    TeacherShortInfo toShortDto(Teacher teacher);
    @Mapping(target = "id", source = "teacherId")
    Teacher toEntity(UUID teacherId);

}
