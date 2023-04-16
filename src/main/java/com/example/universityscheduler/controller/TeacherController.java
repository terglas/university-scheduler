package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.TeacherzApi;
import com.example.universityscheduler.mapper.PageMapper;
import com.example.universityscheduler.mapper.rest.TeacherRestMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.TeacherInfo;
import com.example.universityscheduler.service.TeacherService;
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
public class TeacherController implements TeacherzApi {

    private final TeacherService teacherService;
    private final TeacherRestMapper teacherRestMapper;
    private final PageMapper pageMapper;

    @Override
    public ResponseEntity<TeacherInfo> create(TeacherInfo teacherDTO) {
        val teacher = teacherRestMapper.toEntity(teacherDTO);
        val savedTeacherDto = teacherRestMapper.toDto(teacherService.save(teacher));
        URI location = UriComponentsBuilder.fromPath("/teachers/id").buildAndExpand(savedTeacherDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedTeacherDto);
    }
    @Override
    public ResponseEntity<TeacherInfo> update(UUID id, TeacherInfo teacherDTO) {
        val teacher = teacherRestMapper.toEntity(teacherDTO);
        val savedTeacherDto = teacherRestMapper.toDto(teacherService.update(teacher));
        return ResponseEntity.ok(savedTeacherDto);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        teacherService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TeacherInfo> findById(UUID id) {
        val teacherDto = teacherRestMapper.toDto(teacherService.findById(id));
        return ResponseEntity.ok(teacherDto);
    }

    @Override
    public ResponseEntity<List<TeacherInfo>> findAll(Optional<PageParams> pageParams) {
        val params = pageMapper.toDto(pageParams);
        val teachers = teacherService.findAll(params).stream()
                .map(teacherRestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teachers);
    }
}
