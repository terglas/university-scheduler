package com.example.universityscheduler.service.impl;


import com.example.universityscheduler.domain.Group;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.GroupMapper;
import com.example.universityscheduler.model.CourseInfo;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.GroupRepository;
import com.example.universityscheduler.service.GroupService;
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
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserAccountService userAccountService;
    private final UniversityService universityService;

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> findAll(PageParams pageParams, String search) {
        val userAccount = userAccountService.getCurrentUser();
        return findAll(pageParams, search, userAccount.getUniversity().getId());
    }

    @Override
    public List<Group> findAll(PageParams pageParams, String search, UUID universityId) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        if(!search.isBlank()) {
            return groupRepository.findAllByTitleContainsAndEducationalProgramUniversityId(search, universityId, pageable).getContent();
        }
        return groupRepository.findAllByEducationalProgramUniversityId(pageable, universityId).getContent();
    }

    @Override
    public List<Group> findAll(PageParams pageParams, String search, String universityCode) {
        if(universityCode == null) {
            return findAll(pageParams, search);
        }
        val university = universityService.findByCode(universityCode);
        return findAll(pageParams, search, university.getId());
    }

    @Override
    public List<Group> findAll(UUID educationalProgramId, PageParams pageParams) {
        val userAccount = userAccountService.getCurrentUser();
        return findAll(educationalProgramId, userAccount.getUniversity().getId() ,pageParams);
    }

    @Override
    public List<Group> findAll(UUID educationalProgramId, UUID universityId, PageParams pageParams) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        return groupRepository.findAllByEducationalProgramIdAndEducationalProgramUniversityId(educationalProgramId, universityId, pageable).getContent();
    }

    @Override
    public List<Group> findAll(UUID educationalProgramId, String universityCode, PageParams pageParams) {
        if(universityCode == null) {
            return findAll(educationalProgramId, pageParams);
        }
        val university = universityService.findByCode(universityCode);
        return findAll(educationalProgramId, university.getId(), pageParams);
    }

    @Override
    public List<Group> findAll(CourseInfo courseinfo, PageParams pageParams) {
        val userAccount = userAccountService.getCurrentUser();
        return findAll(courseinfo, userAccount.getUniversity().getId() ,pageParams);
    }

    @Override
    public List<Group> findAll(CourseInfo courseinfo, UUID universityId, PageParams pageParams) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        return groupRepository.findAllByEducationalProgramIdAndCourseAndEducationalProgramUniversityId(courseinfo.getEducationalProgramId(), courseinfo.getCourseNumber(), universityId, pageable).getContent();
    }

    @Override
    public List<Group> findAll(CourseInfo courseinfo, String universityCode, PageParams page) {
        if(universityCode == null) {
            return findAll(courseinfo, page);
        }
        val university = universityService.findByCode(universityCode);
        return findAll(courseinfo, university.getId(), page);
    }

    @Override
    public Group findById(UUID id) {
        val userAccount = userAccountService.getCurrentUser();
        return findById(id, userAccount.getUniversity().getId());
    }

    @Override
    public Group findById(UUID id, UUID universityId) {
        return groupRepository.findByIdAndEducationalProgramUniversityId(id, universityId).orElseThrow(
                () -> new NotFoundException(String.format("Group not found: %S", id)));
    }

    @Override
    public Group findById(UUID id, String universityCode) {
        if(universityCode == null) {
            return findById(id);
        }
        val university = universityService.findByCode(universityCode);
        return findById(id, university.getId());
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
