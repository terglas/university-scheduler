package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.TeacherzApi;
import com.example.universityscheduler.domain.Teacher;
import com.example.universityscheduler.mapper.rest.TeacherRestMapper;
import com.example.universityscheduler.model.TeacherInfo;
import com.example.universityscheduler.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TeacherController implements TeacherzApi {

    private final TeacherService teacherService;
    private final TeacherRestMapper teacherRestMapper;

    @Override
    public ResponseEntity<TeacherInfo> create(TeacherInfo teacherDTO) {
        Teacher teacher = teacherRestMapper.toEntity(teacherDTO);
        TeacherInfo savedTeacherDto = teacherRestMapper.toDto(teacherService.save(teacher));
        URI location = UriComponentsBuilder.fromPath("/teachers/id").buildAndExpand(savedTeacherDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedTeacherDto);
    }
    @Override
    public ResponseEntity<TeacherInfo> update(UUID id, TeacherInfo teacherDTO) {
        Teacher teacher = teacherRestMapper.toEntity(teacherDTO);
        TeacherInfo savedTeacherDto = teacherRestMapper.toDto(teacherService.update(teacher));
        return ResponseEntity.ok(savedTeacherDto);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        teacherService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TeacherInfo> findById(UUID id) {
        TeacherInfo teacherDto = teacherRestMapper.toDto(teacherService.findById(id));
        return ResponseEntity.ok(teacherDto);
    }

    /*FIXME create logic finding by page
    @Override
    public ResponseEntity<Page<TeacherInfo>> findAll(Pageable pageable) {
        Page<TeacherInfo> teachers = teacherService.findAll(pageable);
        return ResponseEntity.ok(teachers);
    }*/

    @Override
    public ResponseEntity<List<TeacherInfo>> findAll() {
        Page<TeacherInfo> teachers = teacherService.findAll(Pageable.unpaged())
                .map(teacherRestMapper::toDto);
        return ResponseEntity.ok(
                teachers.stream().map(e -> new TeacherInfo()
                        .name(e.getName())
                        .id(e.getId())).collect(Collectors.toList()));
    }
}
