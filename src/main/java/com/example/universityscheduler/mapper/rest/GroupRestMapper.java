package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.model.GroupInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface GroupRestMapper {

    @Mapping(target = "educationalProgram.id", source = "educationalProgramId")
    Group toEntity(GroupInfo groupDTO);
    @Mapping(target = "educationalProgramId", source = "educationalProgram.id")
    @Mapping(target = "educationalProgramName", source = "educationalProgram.title")
    GroupInfo toDto(Group group);

    @Mapping(target = "id", source = "groupId")
    Group toEntity(UUID groupId);
}
