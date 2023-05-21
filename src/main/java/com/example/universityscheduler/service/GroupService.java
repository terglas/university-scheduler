package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    Group save(Group group);
    List<Group> findAll(PageParams pageParams, String search);
    List<Group> findAll(PageParams pageParams, String search, UUID universityId);
    List<Group> findAll(PageParams pageParams, String search, String universityCode);

    Group findById(UUID id);
    Group findById(UUID id, UUID universityId);
    Group findById(UUID id, String universityCode);
    Group update(Group group);
    void delete(UUID id);
}
