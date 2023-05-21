package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Room;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    Room save(Room room);
    Room findById(UUID id);
    Room findById(UUID id, UUID universityId);
    Room findById(UUID id, String universityCode);
    List<Room> findAll(PageParams pageParams);
    List<Room> findAll(PageParams pageParams, UUID universityId);
    List<Room> findAll(PageParams pageParams, String universityCode);
    Room update(Room room);
    void delete(UUID id);
}
