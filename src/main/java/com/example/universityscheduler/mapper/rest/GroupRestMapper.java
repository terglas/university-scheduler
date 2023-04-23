package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.model.GroupInfo;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface GroupRestMapper {

    Group toEntity(GroupInfo groupDTO);
    GroupInfo toDto(Group group);
    Group toEntity(UUID id);
}
