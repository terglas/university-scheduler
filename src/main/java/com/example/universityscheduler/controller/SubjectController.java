package com.example.universityscheduler.controller;

import com.example.universityscheduler.domain.dto.SubjectDTO;
import com.example.universityscheduler.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> save(@RequestBody SubjectDTO subjectDTO) {
        SubjectDTO savedSubjectDto = subjectService.save(subjectDTO);
        URI location = UriComponentsBuilder.fromPath("/subjects/id").buildAndExpand(savedSubjectDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedSubjectDto);
    }

    @PutMapping
    public ResponseEntity<SubjectDTO> update(@RequestBody SubjectDTO subjectDTO) {
        SubjectDTO savedSubjectDto = subjectService.update(subjectDTO);
        return ResponseEntity.ok(savedSubjectDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubjectDTO> deleteById(@PathVariable UUID id) {
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<SubjectDTO> findById(@PathVariable UUID id) {
        SubjectDTO subjectDto = subjectService.findById(id);
        return ResponseEntity.ok(subjectDto);
    }

    @GetMapping
    public ResponseEntity<Page<SubjectDTO>> findAll(Pageable pageable) {
        Page<SubjectDTO> subjects = subjectService.findAll(pageable);
        return ResponseEntity.ok(subjects);
    }
}
