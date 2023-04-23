package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.GroupzApi;
import com.example.universityscheduler.mapper.PageMapper;
import com.example.universityscheduler.mapper.rest.GroupRestMapper;
import com.example.universityscheduler.model.GroupInfo;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.service.GroupService;
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
public class GroupController implements GroupzApi {

    private final GroupService groupService;
    private final GroupRestMapper groupRestMapper;
    private final PageMapper pageMapper;

    @Override
    public ResponseEntity<GroupInfo> create(GroupInfo groupInfo) {
        val group = groupRestMapper.toEntity(groupInfo);
        val savedGroup = groupRestMapper.toDto(groupService.save(group));
        URI location = UriComponentsBuilder.fromPath("/groups/id").buildAndExpand(savedGroup.getId()).toUri();
        return ResponseEntity.created(location).body(savedGroup);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        groupService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<GroupInfo>> findAll(Optional<PageParams> pageParams, Optional<String> search) {
        val page = pageMapper.toDto(pageParams);
        val groups = groupService.findAll(page, search).stream()
                .map(groupRestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groups);
    }

    @Override
    public ResponseEntity<GroupInfo> findById(UUID id) {
        val group = groupService.findById(id);
        val groupDto = groupRestMapper.toDto(group);
        return ResponseEntity.ok(groupDto);
    }

    @Override
    public ResponseEntity<GroupInfo> update(UUID id, GroupInfo groupInfo) {
        val group = groupRestMapper.toEntity(groupInfo);
        val updatedGroup = groupRestMapper.toDto(groupService.update(group));
        return ResponseEntity.ok(updatedGroup);
    }
}
