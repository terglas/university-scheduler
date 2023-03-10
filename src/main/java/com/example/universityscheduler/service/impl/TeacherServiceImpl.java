package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.domain.dto.TeacherDTO;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.TeacherMapper;
import com.example.universityscheduler.repository.TeacherRepository;
import com.example.universityscheduler.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {


    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) {
        Teacher teacher = TeacherMapper.INSTANCE.toEntity(teacherDTO);
        Teacher createdTeacher = teacherRepository.save(teacher);
        return TeacherMapper.INSTANCE.toDto(createdTeacher);
    }

    @Override
    public TeacherDTO update(TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findById(teacherDTO.getId()).orElseThrow(() -> {
            throw new NotFoundException(String.format("Teacher not found: %S", teacherDTO.getId()));
        });
        TeacherMapper.INSTANCE.updateTeacherFromDto(teacherDTO, teacher);
        Teacher createdTeacher = teacherRepository.save(teacher);
        return TeacherMapper.INSTANCE.toDto(createdTeacher);
    }

    @Override
    public void delete(UUID id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("Teacher not found: %S", id));
        });
        teacherRepository.delete(teacher);
    }


    @Override
    public TeacherDTO findById(UUID id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if(optionalTeacher.isEmpty()) {
            throw new NotFoundException(String.format("teacher not found: %S", id));
        }
        return TeacherMapper.INSTANCE.toDto(optionalTeacher.get());
    }

    @Override
    public Page<TeacherDTO> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable)
                .map(TeacherMapper.INSTANCE::toDto);
    }
}
