package com.example.universityscheduler.service;

import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.model.PageParams;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupService {

    Group save(Group group);
    List<Group> findAll(PageParams pageParams, Optional<String> search);
    Group findById(UUID id);
    Group update(Group group);
    void delete(UUID id);
}
