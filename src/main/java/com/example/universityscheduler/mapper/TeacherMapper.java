package com.example.universityscheduler.mapper;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.domain.dto.TeacherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher toEntity(TeacherDTO teacherDTO);

    TeacherDTO toDto(Teacher teacher);
}
