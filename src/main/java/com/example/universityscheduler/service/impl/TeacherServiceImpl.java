package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.domain.University;
import com.example.universityscheduler.domain.UserAccount;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.TeacherMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.TeacherRepository;
import com.example.universityscheduler.service.TeacherService;
import com.example.universityscheduler.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {


    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final UserAccountService userAccountService;
    private final UniversityService universityService;

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher update(Teacher teacher) {
        val foundTeacher = teacherRepository.findById(teacher.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Teacher not found: %S", teacher.getId())));
        teacherMapper.updateTeacher(teacher, foundTeacher);
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(UUID id) {
        val teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Teacher not found: %S", id)));
        teacherRepository.delete(teacher);
    }


    @Override
    public Teacher findById(UUID id) {
        UserAccount userAccount = userAccountService.getCurrentUser();
        return findById(id, userAccount.getUniversity().getId());
    }

    @Override
    public Teacher findById(UUID id, UUID universityId) {
        return teacherRepository.findByIdAndUniversityId(id, universityId)
                .orElseThrow(() -> new NotFoundException(String.format("Teacher not found: %S", id)));
    }

    @Override
    public Teacher findById(UUID id, String universityCode) {
        if(universityCode == null) {
            return findById(id);
        }
        University university = universityService.findByCode(universityCode);
        return findById(id, university.getId());
    }

    @Override
    public List<Teacher> findAll(PageParams pageParams) {
        UserAccount userAccount = userAccountService.getCurrentUser();
        return findAll(pageParams, userAccount.getUniversity().getId());
    }

    @Override
    public List<Teacher> findAll(PageParams pageParams, UUID universityId) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        return teacherRepository.findAllByUniversityId(pageable, universityId).getContent();
    }

    @Override
    public List<Teacher> findAll(PageParams pageParams, String universityCode) {
        if(universityCode == null) {
            return findAll(pageParams);
        }
        University university = universityService.findByCode(universityCode);
        return findAll(pageParams, university.getId());
    }
}
