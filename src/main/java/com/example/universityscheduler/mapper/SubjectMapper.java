package com.example.universityscheduler.mapper;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.domain.dto.SubjectDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    Subject toEntity(SubjectDTO subjectDTO);
    SubjectDTO toDto(Subject subject);
    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updateSubjectFromDto(SubjectDTO subjectDTO, @MappingTarget Subject subject);
}
