package com.example.universityscheduler.mapper;

import com.example.universityscheduler.domain.EducationalProgram;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface EducationalProgramMapper {

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updateEducationalProgram(EducationalProgram src, @MappingTarget EducationalProgram target);
}
