package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.exception.ConflictException;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.ScheduleMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.SearchQuery;
import com.example.universityscheduler.model.SearchType;
import com.example.universityscheduler.model.TimeInterval;
import com.example.universityscheduler.repository.ScheduleRepository;
import com.example.universityscheduler.service.GroupService;
import com.example.universityscheduler.service.ScheduleService;
import com.example.universityscheduler.service.TeacherService;
import com.example.universityscheduler.utils.IntervalUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final TeacherService teacherService;
    private final GroupService groupService;

    @Override
    public Schedule save(Schedule schedule) {

        TimeInterval timeInterval = new TimeInterval()
                .startTime(schedule.getStartTime()
                        .toLocalTime())
                .endTime(schedule.getEndTime()
                        .toLocalTime());
        List<TimeInterval> teacherTimeIntervals = IntervalUtils.formInterval(teacherService.findById(schedule.getTeacher().getId()).getSchedules());
        // FIXME n + 1 problem
        List<TimeInterval> groupTimeInterval = schedule.getGroups().stream().map(g -> IntervalUtils.formInterval(groupService.findById(g.getId())
                .getSchedules())).flatMap(Collection::stream).collect(Collectors.toList());

        List<TimeInterval> roomTimeInterval = IntervalUtils.formInterval(scheduleRepository.findByRoom(schedule.getRoom()));
        if (IntervalUtils.doesIntervalFit(timeInterval, teacherTimeIntervals) && IntervalUtils.doesIntervalFit(timeInterval, groupTimeInterval) && IntervalUtils.doesIntervalFit(timeInterval, roomTimeInterval)) {
            return scheduleRepository.save(schedule);
        }
        // TODO custom exception
        throw new ConflictException("Schedule does not fit");
    }

    @Override
    public List<Schedule> findAll(Optional<SearchQuery> query, PageParams pageParams) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        if (query.isPresent()) {
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
