package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.SubjectzApi;
import com.example.universityscheduler.mapper.PageMapper;
import com.example.universityscheduler.mapper.rest.SubjectRestMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.SubjectExtendedInfo;
import com.example.universityscheduler.model.SubjectShortInfo;
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
    public ResponseEntity<SubjectExtendedInfo> create(SubjectExtendedInfo subjectDTO) {
        val subject = subjectRestMapper.toEntity(subjectDTO);
        val savedSubjectDto = subjectRestMapper.toExtendedDto(subjectService.save(subject));
        URI location = UriComponentsBuilder.fromPath("/subjects/id").buildAndExpand(savedSubjectDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedSubjectDto);
    }

    @Override
    public ResponseEntity<SubjectExtendedInfo> update(UUID id, SubjectExtendedInfo subjectDTO) {
        val subject = subjectService.update(subjectRestMapper.toEntity(subjectDTO));
        val subjectInfo = subjectRestMapper.toExtendedDto(subjectService.update(subject));
        return ResponseEntity.ok(subjectInfo);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }
    @Override
    public ResponseEntity<SubjectExtendedInfo> findById(UUID id) {
        val subject = subjectService.findById(id);
        val subjectDto = subjectRestMapper.toExtendedDto(subject);
        return ResponseEntity.ok(subjectDto);
    }

    @Override
    public ResponseEntity<List<SubjectShortInfo>> findAll(Optional<PageParams> pageParams) {
        val page = pageMapper.toDto(pageParams);
        val subjects = subjectService.findAll(page).stream()
                .map(subjectRestMapper::toShortDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subjects);
    }

    @Override
    public ResponseEntity<List<SubjectExtendedInfo>> findAllExtended(Optional<PageParams> pageParams) {
        val page = pageMapper.toDto(pageParams);
        val subjects = subjectService.findAll(page).stream()
                .map(subjectRestMapper::toExtendedDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subjects);
    }
}
