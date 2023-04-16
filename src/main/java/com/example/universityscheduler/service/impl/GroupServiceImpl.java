package com.example.universityscheduler.service.impl;


import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.GroupMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.GroupRepository;
import com.example.universityscheduler.service.GroupService;
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
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> findAll(PageParams pageParams) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        return groupRepository.findAll(pageable).getContent();
    }

    @Override
    public Group findById(UUID id) {
        return groupRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Group not found: %S", id)));
    }

    @Override
    public Group update(Group group) {
        val foundGroup = groupRepository.findById(group.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Group not found: %S", group.getId())));
        groupMapper.updateGroup(group, foundGroup);
        return groupRepository.save(group);
    }

    @Override
    public void delete(UUID id) {
        val group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Group not found: %S", id)));
        groupRepository.delete(group);
    }
}
