package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.model.TeacherInfo;
import org.mapstruct.Mapper;

@Mapper
public interface TeacherRestMapper {

    Teacher toEntity(TeacherInfo teacherDTO);
    TeacherInfo toDto(Teacher teacher);

}
