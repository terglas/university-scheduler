package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.TeacherMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.TeacherRepository;
import com.example.universityscheduler.service.TeacherService;
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
        return teacherRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Teacher not found: %S", id)));
    }

    @Override
    public List<Teacher> findAll(PageParams page) {
        val pageable = PageRequest.of(page.getPageCurrent() - 1, page.getPageSize());
        return teacherRepository.findAll(pageable).getContent();
    }
}
