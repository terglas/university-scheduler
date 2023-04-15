package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.TeacherMapper;
import com.example.universityscheduler.repository.TeacherRepository;
import com.example.universityscheduler.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
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
        Teacher foundTeacher = teacherRepository.findById(teacher.getId()).orElseThrow(() -> {
            throw new NotFoundException(String.format("Teacher not found: %S", teacher.getId()));
        });
        teacherMapper.updateTeacher(teacher, foundTeacher);
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(UUID id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("Teacher not found: %S", id));
        });
        teacherRepository.delete(teacher);
    }


    @Override
    public Teacher findById(UUID id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        return optionalTeacher.orElseThrow(
                () -> new NotFoundException(String.format("Teacher not found: %S", id)));
    }

    @Override
    public Page<Teacher> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }
}
