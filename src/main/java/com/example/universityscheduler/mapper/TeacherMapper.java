package com.example.universityscheduler.mapper;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.model.TeacherInfo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher toEntity(TeacherInfo teacherDTO);
    TeacherInfo toDto(Teacher teacher);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromDto(TeacherInfo teacherDTO, @MappingTarget Teacher teacher);

}
