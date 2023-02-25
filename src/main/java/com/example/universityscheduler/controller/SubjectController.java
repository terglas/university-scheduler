package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.SubjectzApi;
import com.example.universityscheduler.model.SubjectInfo;
import com.example.universityscheduler.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class SubjectController implements SubjectzApi {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public ResponseEntity<SubjectInfo> create(SubjectInfo subjectDTO) {
        SubjectInfo savedSubjectDto = subjectService.save(subjectDTO);
        URI location = UriComponentsBuilder.fromPath("/subjects/id").buildAndExpand(savedSubjectDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedSubjectDto);
    }

    @Override
    public ResponseEntity<SubjectInfo> update(UUID id, SubjectInfo subjectDTO) {
        SubjectInfo savedSubjectDto = subjectService.update(subjectDTO);
        return ResponseEntity.ok(savedSubjectDto);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }
    @Override
    public ResponseEntity<SubjectInfo> findById(UUID id) {
        SubjectInfo subjectDto = subjectService.findById(id);
        return ResponseEntity.ok(subjectDto);
    }

    /*FIXME
    @Override
    public ResponseEntity<Page<SubjectInfo>> findAll(Pageable pageable) {
        Page<SubjectInfo> subjects = subjectService.findAll(pageable);
        return ResponseEntity.ok(subjects);
    }*/
}
