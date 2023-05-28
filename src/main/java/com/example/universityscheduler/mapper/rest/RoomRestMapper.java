package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Room;
import com.example.universityscheduler.model.RoomInfo;
import com.example.universityscheduler.model.RoomTimeIntervalInfo;
import com.example.universityscheduler.model.TimeIntervalWeek;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper
public interface RoomRestMapper {
    @Mapping(target = "university.id", source = "universityId")
    Room toEntity(RoomInfo roomInfo);
    @Mapping(target = "universityId", source = "university.id")
    RoomInfo toDto(Room room);
    @Mapping(target = "id", source = "roomId")
    Room toEntity(UUID roomId);

    RoomTimeIntervalInfo toDto(Room room, List<TimeIntervalWeek> timeIntervals);
}
