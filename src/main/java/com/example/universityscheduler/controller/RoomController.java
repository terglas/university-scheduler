package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.RoomzApi;
import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.mapper.PageMapper;
import com.example.universityscheduler.mapper.rest.RoomRestMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.RoomInfo;
import com.example.universityscheduler.model.RoomTimeIntervalInfo;
import com.example.universityscheduler.model.Week;
import com.example.universityscheduler.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomzApi {

    private final RoomService roomService;
    private final RoomRestMapper roomRestMapper;
    private final PageMapper pageMapper;

    @Override
    public ResponseEntity<RoomInfo> create(RoomInfo roomInfo) {
        val room = roomService.save(roomRestMapper.toEntity(roomInfo));
        return ResponseEntity.ok(roomRestMapper.toDto(room));
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID roomId) {
        roomService.delete(roomId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<RoomInfo>> findAll(Optional<PageParams> pageParams, Optional<String> universityCode) {
        val page = pageMapper.toDto(pageParams);
        val rooms = roomService.findAll(page, universityCode.orElse(null)).stream()
                .map(roomRestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rooms);
    }

    @Override
    public ResponseEntity<RoomInfo> findById(UUID roomId, Optional<String> universityCode) {
        val room = roomService.findById(roomId, universityCode.orElse(null));
        return ResponseEntity.ok(roomRestMapper.toDto(room));
    }

    @Override
    public ResponseEntity<RoomTimeIntervalInfo> findTimeIntervalsByRoomId(UUID roomId, Week week, Optional<String> universityCode) {
        val room = roomService.findById(roomId, universityCode.orElse(null));
        val timeIntervals = roomService.findTimeIntervals(roomId, Schedule.Week.valueOf(week.getValue()), universityCode.orElse(null));
        return ResponseEntity.ok(roomRestMapper.toDto(room, timeIntervals));
    }

    @Override
    public ResponseEntity<RoomInfo> update(UUID roomId, RoomInfo roomInfo) {
        val room = roomService.update(roomRestMapper.toEntity(roomInfo));
        return ResponseEntity.ok(roomRestMapper.toDto(room));
    }
}
