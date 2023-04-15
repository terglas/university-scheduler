package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.SubjectzApi;
import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.mapper.rest.SubjectRestMapper;
import com.example.universityscheduler.model.SubjectInfo;
import com.example.universityscheduler.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubjectController implements SubjectzApi {

    private final SubjectService subjectService;
    private final SubjectRestMapper subjectRestMapper;

    @Override
    public ResponseEntity<SubjectInfo> create(SubjectInfo subjectDTO) {
        Subject subject = subjectRestMapper.toEntity(subjectDTO);
        SubjectInfo savedSubjectDto = subjectRestMapper.toDto(subjectService.save(subject));
        URI location = UriComponentsBuilder.fromPath("/subjects/id").buildAndExpand(savedSubjectDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedSubjectDto);
    }

    @Override
    public ResponseEntity<SubjectInfo> update(UUID id, SubjectInfo subjectDTO) {
        Subject subject = subjectService.update(subjectRestMapper.toEntity(subjectDTO));
        SubjectInfo subjectInfo = subjectRestMapper.toDto(subjectService.update(subject));
        return ResponseEntity.ok(subjectInfo);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }
    @Override
    public ResponseEntity<SubjectInfo> findById(UUID id) {
        Subject subject = subjectService.findById(id);
        SubjectInfo subjectDto = subjectRestMapper.toDto(subject);
        return ResponseEntity.ok(subjectDto);
    }

    /*FIXME
    @Override
    public ResponseEntity<Page<SubjectInfo>> findAll(Pageable pageable) {
        Page<SubjectInfo> subjects = subjectService.findAll(pageable)
                .map(SubjectMapper.INSTANCE::toDto);
        return ResponseEntity.ok(subjects);
    }*/
}
