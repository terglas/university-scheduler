package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.SearchQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleService {

    Schedule save(Schedule schedule);
    List<Schedule> findAll(Optional<SearchQuery> searchQuery, PageParams pageParams);
    Schedule findById(UUID id);
    Schedule update(Schedule schedule);
    void delete(UUID id);
}
