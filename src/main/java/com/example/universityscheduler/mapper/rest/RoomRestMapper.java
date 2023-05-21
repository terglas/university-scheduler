package com.example.universityscheduler.mapper.rest;

import com.example.universityscheduler.domain.Room;
import com.example.universityscheduler.model.RoomInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface RoomRestMapper {

    Room toEntity(RoomInfo roomInfo);
    RoomInfo toDto(Room room);
    @Mapping(target = "id", source = "roomId")
    Room toEntity(UUID roomId);
}
