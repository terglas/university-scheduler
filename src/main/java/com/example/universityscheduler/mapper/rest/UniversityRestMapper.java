package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.University;
import com.example.universityscheduler.model.UniversityInfo;
import org.mapstruct.Mapper;

@Mapper
public interface UniversityRestMapper {

    University toEntity(UniversityInfo universityInfo);
    UniversityInfo toDto(University university);
}
