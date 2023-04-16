package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.model.GroupInfo;
import org.mapstruct.Mapper;

@Mapper
public interface GroupRestMapper {

    Group toEntity(GroupInfo groupDTO);
    GroupInfo toDto(Group group);
}
