package com.example.universityscheduler.mapper;

import com.example.universityscheduler.domain.Schedule;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface ScheduleMapper {

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updateSchedule(Schedule src, @MappingTarget Schedule target);
}
