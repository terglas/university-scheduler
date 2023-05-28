package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Room;
import com.example.universityscheduler.domain.Schedule.Week;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.TimeInterval;

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
    List<TimeInterval> findTimeIntervals(UUID id, Week week);
    List<TimeInterval> findTimeIntervals(UUID id, Week week, UUID universityId);
    List<TimeInterval> findTimeIntervals(UUID id, Week week, String universityCode);
    Room update(Room room);
    void delete(UUID id);
}
