package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.exception.ConflictException;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.ScheduleMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.SearchQuery;
import com.example.universityscheduler.model.TimeInterval;
import com.example.universityscheduler.repository.ScheduleRepository;
import com.example.universityscheduler.service.GroupService;
import com.example.universityscheduler.service.ScheduleService;
import com.example.universityscheduler.service.TeacherService;
import com.example.universityscheduler.service.UniversityService;
import com.example.universityscheduler.utils.IntervalUtils;
import com.example.universityscheduler.utils.SearchScheduleUtils;
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
    private final UserAccountService userAccountService;
    private final UniversityService universityService;

    @Override
    public Schedule save(Schedule schedule) {

        TimeInterval timeInterval = new TimeInterval()
                .startTime(schedule.getStartTime()
                        .toLocalTime())
                .endTime(schedule.getEndTime()
                        .toLocalTime());
        List<TimeInterval> teacherTimeIntervals = IntervalUtils.formInterval(teacherService.findById(schedule.getTeacher().getId()).getSchedules(), schedule.getWeek());
        // FIXME n + 1 problem
        List<TimeInterval> groupTimeInterval = schedule.getGroups().stream().map(g -> IntervalUtils.formInterval(groupService.findById(g.getId())
                .getSchedules(), schedule.getWeek())).flatMap(Collection::stream).collect(Collectors.toList());

        List<TimeInterval> roomTimeInterval = IntervalUtils.formInterval(scheduleRepository.findByRoomId(schedule.getRoom().getId()), schedule.getWeek());
        if (IntervalUtils.doesIntervalFit(timeInterval, teacherTimeIntervals) && IntervalUtils.doesIntervalFit(timeInterval, groupTimeInterval) && IntervalUtils.doesIntervalFit(timeInterval, roomTimeInterval)) {
            return scheduleRepository.save(schedule);
        }
        // TODO custom exception
        throw new ConflictException("Schedule does not fit");
    }

    @Override
    public List<Schedule> findAll(Optional<SearchQuery> query, PageParams pageParams) {
        val userAccount = userAccountService.getCurrentUser();
        return findAll(query, pageParams, userAccount.getUniversity().getId());
    }

    @Override
    public List<Schedule> findAll(Optional<SearchQuery> searchQuery, PageParams pageParams, UUID universityId) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        if (searchQuery.isPresent()) {
            val query = searchQuery.get();
            return getSchedulesByFilter(query, universityId, pageable);
        }
        return scheduleRepository.findAllBySubjectUniversityId(universityId, pageable).getContent();
    }

    @Override
    public List<Schedule> findAll(Optional<SearchQuery> searchQuery, PageParams pageParams, String universityCode) {
        if(universityCode == null) {
            return findAll(searchQuery, pageParams);
        }
        val university = universityService.findByCode(universityCode);
        return findAll(searchQuery, pageParams, university.getId());
    }

    @Override
    public List<Schedule> findAll(List<SearchQuery> searchQueries, PageParams pageParams) {
        val userAccount = userAccountService.getCurrentUser();
        return findAll(searchQueries, pageParams, userAccount.getUniversity().getId());
    }

    @Override
    public List<Schedule> findAll(List<SearchQuery> searchQueries, PageParams pageParams, UUID universityId) {
        // TODO implement pagination
        //val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        if (!searchQueries.isEmpty()) {
            val query = SearchScheduleUtils.findAllCompoundSchedules(searchQueries, universityId);
            return scheduleRepository.findAll(query);
        }
        return List.of();
    }

    @Override
    public List<Schedule> findAll(List<SearchQuery> searchQueries, PageParams pageParams, String universityCode) {
        if(universityCode == null) {
            return findAll(searchQueries, pageParams);
        }
        val university = universityService.findByCode(universityCode);
        return findAll(searchQueries, pageParams, university.getId());
    }

    @Override
    public Schedule findById(UUID id) {
        val userAccount = userAccountService.getCurrentUser();
        return findById(id, userAccount.getUniversity().getId());
    }

    @Override
    public Schedule findById(UUID id, UUID universityId) {
        return scheduleRepository.findByIdAndSubjectUniversityId(id, universityId).orElseThrow(
                () -> new NotFoundException(String.format("Subject not found: %S", id)));
    }

    @Override
    public Schedule findById(UUID id, String universityCode) {
        if(universityCode == null) {
            return findById(id);
        }
        val university = universityService.findByCode(universityCode);
        return findById(id, university.getId());
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


    private List<Schedule> getSchedulesByFilter(SearchQuery query, UUID universityId, Pageable pageable) {
        val type = query.getSearchType();
        val id = query.getSearchId();
        switch (type) {
            case TEACHER:
                return scheduleRepository.findAllByTeacherIdAndTeacherUniversityId(id, universityId, pageable).getContent();
            case GROUP:
                return scheduleRepository.findAllByGroupId(id, universityId, pageable).getContent();
            case SUBJECT:
                return scheduleRepository.findAllBySubjectIdAndSubjectUniversityId(id, universityId, pageable).getContent();
            //case ROOM:
                //return scheduleRepository.findAllByRoom(id, pageable).getContent();

            /* TODO
            case EDUCATIONAL_PROGRAM:
                return scheduleRepository.findByEducationalProgramId(id, pageable).getContent();
            */
            default:
                throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }
}
