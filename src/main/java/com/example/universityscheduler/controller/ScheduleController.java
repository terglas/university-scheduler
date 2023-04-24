package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.SchedulezApi;
import com.example.universityscheduler.mapper.PageMapper;
import com.example.universityscheduler.mapper.SearchMapper;
import com.example.universityscheduler.mapper.rest.ScheduleRestMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.ScheduleExtendedInfo;
import com.example.universityscheduler.model.ScheduleInfo;
import com.example.universityscheduler.model.SearchType;
import com.example.universityscheduler.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ScheduleController implements SchedulezApi {

    private final ScheduleService scheduleService;
    private final ScheduleRestMapper scheduleRestMapper;
    private final PageMapper pageMapper;
    private final SearchMapper searchMapper;

    @Override
    public ResponseEntity<ScheduleInfo> create(ScheduleInfo scheduleInfo) {
        val schedule = scheduleRestMapper.toEntity(scheduleInfo);
        val savedScheduleInfo = scheduleRestMapper.toDto(scheduleService.save(schedule));
        URI location = UriComponentsBuilder.fromPath("/schedules/id").buildAndExpand(savedScheduleInfo.getId()).toUri();
        return ResponseEntity.created(location).body(savedScheduleInfo);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        scheduleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ScheduleInfo>> findAll(Optional<UUID> searchId, Optional<SearchType> type, Optional<PageParams> pageParams) {
        val page = pageMapper.toDto(pageParams);
        val searchQuery  = searchMapper.toSearchQuery(searchId, type);
        val schedules = scheduleService.findAll(searchQuery, page).stream()
                .map(scheduleRestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(schedules);
    }

    @Override
    public ResponseEntity<List<ScheduleExtendedInfo>> findAllExtended(Optional<UUID> searchId, Optional<SearchType> type, Optional<PageParams> pageParams) {
        val page = pageMapper.toDto(pageParams);
        val searchQuery  = searchMapper.toSearchQuery(searchId, type);
        val schedules = scheduleService.findAll(searchQuery, page).stream()
                .map(scheduleRestMapper::toExtendedDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(schedules);
    }

    @Override
    public ResponseEntity<ScheduleInfo> findById(UUID id) {
        val scheduleInfo = scheduleRestMapper.toDto(scheduleService.findById(id));
        return ResponseEntity.ok(scheduleInfo);
    }

    @Override
    public ResponseEntity<ScheduleInfo> update(UUID id, ScheduleInfo scheduleInfo) {
        val schedule = scheduleRestMapper.toEntity(scheduleInfo);
        val savedScheduleInfo = scheduleRestMapper.toDto(scheduleService.update(schedule));
        return ResponseEntity.ok(savedScheduleInfo);
    }

    @Override
    public ResponseEntity<ScheduleExtendedInfo> findExtendedById(UUID id) {
        val scheduleInfo = scheduleRestMapper.toExtendedDto(scheduleService.findById(id));
        return ResponseEntity.ok(scheduleInfo);
    }
}
