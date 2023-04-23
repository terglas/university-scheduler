package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.ScheduleMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.SearchQuery;
import com.example.universityscheduler.model.SearchType;
import com.example.universityscheduler.repository.ScheduleRepository;
import com.example.universityscheduler.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> findAll(Optional<SearchQuery> query, PageParams pageParams) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        if(query.isPresent()) {
            val searchQuery = query.get();
            return getSchedulesByFilter(searchQuery.getSearchType(), searchQuery.getSearchId(), pageable);
        }
        return scheduleRepository.findAll(pageable).getContent();
    }

    @Override
    public Schedule findById(UUID id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Schedule not found: %S", id)));
    }

    @Override
    public Schedule update(Schedule schedule) {
        val foundSchedule = scheduleRepository.findById(schedule.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Schedule not found: %S", schedule.getId())));
        scheduleMapper.updateSchedule(schedule, foundSchedule);
        return scheduleRepository.save(schedule);
    }

    @Override
    public void delete(UUID id) {
        val schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Schedule not found: %S", id)));
        scheduleRepository.delete(schedule);
    }


    private List<Schedule> getSchedulesByFilter(SearchType type, UUID id, Pageable pageable) {
        switch (type) {
            case TEACHER:
                return scheduleRepository.findByTeacherId(id, pageable).getContent();
            case GROUP:
                return scheduleRepository.findByGroupId(id, pageable).getContent();
            case SUBJECT:
                return scheduleRepository.findBySubjectId(id, pageable).getContent();
            /* TODO
            case UNIVERSITY:
                return scheduleRepository.findByUniversityId(id, pageable).getContent();
            case EDUCATIONAL_PROGRAM:
                return scheduleRepository.findByEducationalProgramId(id, pageable).getContent();
            */
            default:
                throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }
}
