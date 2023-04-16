package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.University;
import com.example.universityscheduler.model.UniversityInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UniversityRestMapper {

    @Mapping(target = "userAccount.id", source = "userId")
    University toEntity(UniversityInfo universityInfo);

    @Mapping(target = "userId", source = "userAccount.id")
    UniversityInfo toDto(University university);
}
