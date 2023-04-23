package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.model.TeacherInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface TeacherRestMapper {

    Teacher toEntity(TeacherInfo teacherDTO);
    TeacherInfo toDto(Teacher teacher);
    @Mapping(target = "id", source = "teacherId")
    Teacher toEntity(UUID teacherId);

}
