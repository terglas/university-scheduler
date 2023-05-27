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
    List<Schedule> findAll(Optional<SearchQuery> searchQuery, PageParams pageParams, UUID universityId);
    List<Schedule> findAll(Optional<SearchQuery> searchQuery,PageParams pageParams, String universityCode);

    List<Schedule> findAll(List<SearchQuery> searchQueries, PageParams pageParams);
    List<Schedule> findAll(List<SearchQuery> searchQueries, PageParams pageParams, UUID universityId);
    List<Schedule> findAll(List<SearchQuery> searchQueries, PageParams pageParams, String universityCode);

    Schedule findById(UUID id);
    Schedule findById(UUID id, UUID universityId);
    Schedule findById(UUID id, String universityCode);
    Schedule update(Schedule schedule);
    void delete(UUID id);
}
