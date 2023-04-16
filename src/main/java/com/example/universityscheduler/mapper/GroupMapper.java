package com.example.universityscheduler.mapper;

import com.example.universityscheduler.domain.Group;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper
public interface GroupMapper {

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updateGroup(Group src, @MappingTarget Group target);
}
