package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Room;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.RoomMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.RoomRepository;
import com.example.universityscheduler.service.RoomService;
import com.example.universityscheduler.service.UniversityService;
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
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserAccountService userAccountService;
    private final UniversityService universityService;
    private final RoomMapper roomMapper;

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room findById(UUID id) {
        val userAccount = userAccountService.getCurrentUser();
        return findById(id, userAccount.getUniversity().getId());
    }

    @Override
    public Room findById(UUID id, UUID universityId) {
        return roomRepository.findByIdAndUniversityId(id, universityId).orElseThrow(
                () -> new NotFoundException(String.format("Room not found: %S", id)));
    }

    @Override
    public Room findById(UUID id, String universityCode) {
        if (universityCode == null) {
            return findById(id);
        }
        val university = universityService.findByCode(universityCode);
        return findById(id, university.getId());
    }

    @Override
    public List<Room> findAll(PageParams pageParams) {
        val userAccount = userAccountService.getCurrentUser();
        return findAll(pageParams, userAccount.getUniversity().getId());
    }

    @Override
    public List<Room> findAll(PageParams pageParams, UUID universityId) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        return roomRepository.findAllByUniversityId(pageable, universityId).getContent();
    }

    @Override
    public List<Room> findAll(PageParams pageParams, String universityCode) {
        if (universityCode == null) {
            return findAll(pageParams);
        }
        val university = universityService.findByCode(universityCode);
        return findAll(pageParams, university.getId());
    }

    @Override
    public Room update(Room room) {
        val foundSubject = roomRepository.findById(room.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Room not found: %S", room.getId())));
        roomMapper.updateRoom(room, foundSubject);
        return roomRepository.save(foundSubject);
    }

    @Override
    public void delete(UUID id) {
        val room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Room not found: %S", id)));
        roomRepository.delete(room);
    }
}
