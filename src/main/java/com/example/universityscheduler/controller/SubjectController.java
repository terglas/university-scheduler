package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.SubjectzApi;
import com.example.universityscheduler.mapper.PageMapper;
import com.example.universityscheduler.mapper.rest.SubjectRestMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.SubjectInfo;
import com.example.universityscheduler.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SubjectController implements SubjectzApi {

    private final SubjectService subjectService;
    private final SubjectRestMapper subjectRestMapper;
    private final PageMapper pageMapper;

    @Override
    public ResponseEntity<SubjectInfo> create(SubjectInfo subjectDTO) {
        val subject = subjectRestMapper.toEntity(subjectDTO);
        val savedSubjectDto = subjectRestMapper.toDto(subjectService.save(subject));
        URI location = UriComponentsBuilder.fromPath("/subjects/id").buildAndExpand(savedSubjectDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedSubjectDto);
    }

    @Override
    public ResponseEntity<SubjectInfo> update(UUID id, SubjectInfo subjectDTO) {
        val subject = subjectService.update(subjectRestMapper.toEntity(subjectDTO));
        val subjectInfo = subjectRestMapper.toDto(subjectService.update(subject));
        return ResponseEntity.ok(subjectInfo);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }
    @Override
    public ResponseEntity<SubjectInfo> findById(UUID id) {
        val subject = subjectService.findById(id);
        val subjectDto = subjectRestMapper.toDto(subject);
        return ResponseEntity.ok(subjectDto);
    }

    @Override
    public ResponseEntity<List<SubjectInfo>> findAll(Optional<PageParams> pageParams) {
        val page = pageMapper.toDto(pageParams);
        val subjects = subjectService.findAll(page).stream()
                .map(subjectRestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subjects);
    }
}
