package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.ScheduleMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.ScheduleRepository;
import com.example.universityscheduler.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<Schedule> findAll(PageParams pageParams) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
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
}
