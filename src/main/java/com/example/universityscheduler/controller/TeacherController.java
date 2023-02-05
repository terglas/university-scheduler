package com.example.universityscheduler.controller;

import com.example.universityscheduler.domain.dto.TeacherDTO;
import com.example.universityscheduler.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/add")
    public ResponseEntity<TeacherDTO> save(@RequestBody TeacherDTO teacherDTO) {
        try {
            TeacherDTO savedTeacherDto = teacherService.save(teacherDTO);
            URI location = UriComponentsBuilder.fromPath("/teachers/id").buildAndExpand(savedTeacherDto.getId()).toUri();
            return ResponseEntity.created(location).body(savedTeacherDto);
        } catch(Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<TeacherDTO> findById(@PathVariable UUID id) {
        try {
            TeacherDTO teacherDto = teacherService.findById(id);
            return ResponseEntity.ok(teacherDto);
        } catch(Exception e) {
            return ResponseEntity.notFound().header("Message",e.getMessage()).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<TeacherDTO>> findAll(Pageable pageable) {
        Page<TeacherDTO> teachers = teacherService.findAll(pageable);
        return ResponseEntity.ok(teachers);
    }
}
