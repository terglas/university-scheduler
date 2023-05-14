package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.EducationalProgramzApi;
import com.example.universityscheduler.mapper.PageMapper;
import com.example.universityscheduler.mapper.rest.EducationalProgramRestMapper;
import com.example.universityscheduler.model.EducationalProgramExtendedInfo;
import com.example.universityscheduler.model.EducationalProgramInfo;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.SubjectShortInfo;
import com.example.universityscheduler.service.EducationalProgramService;
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
public class EducationalProgramController implements EducationalProgramzApi {

    private final EducationalProgramService educationalProgramService;
    private final EducationalProgramRestMapper educationalProgramRestMapper;
    private final PageMapper pageMapper;

    @Override
    public ResponseEntity<EducationalProgramInfo> create(EducationalProgramInfo educationalProgramDTO) {
        val educationalProgram = educationalProgramRestMapper.toEntity(educationalProgramDTO);
        val savedEducationProgramDto = educationalProgramRestMapper.toDto(educationalProgramService.save(educationalProgram));
        URI location = UriComponentsBuilder.fromPath("/educationalPrograms/id").buildAndExpand(savedEducationProgramDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedEducationProgramDto);
    }

    @Override
    public ResponseEntity<EducationalProgramInfo> update(UUID id, EducationalProgramInfo educationalProgramDTO) {
        val educationalProgram = educationalProgramService.update(educationalProgramRestMapper.toEntity(educationalProgramDTO));
        val educationalProgramInfo = educationalProgramRestMapper.toDto(educationalProgramService.update(educationalProgram));
        return ResponseEntity.ok(educationalProgramInfo);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        educationalProgramService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<EducationalProgramInfo> findById(UUID id, Optional<String> universityCode) {
        val educationalProgram = educationalProgramService.findById(id);
        val educationalProgramDto = educationalProgramRestMapper.toDto(educationalProgram);
        return ResponseEntity.ok(educationalProgramDto);
    }

    @Override
    public ResponseEntity<List<EducationalProgramInfo>> findAll(Optional<PageParams> pageParams, Optional<String> universityCode) {
        val page = pageMapper.toDto(pageParams);
        val educationalPrograms = educationalProgramService.findAll(page).stream()
                .map(educationalProgramRestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(educationalPrograms);
    }

    @Override
    public ResponseEntity<SubjectShortInfo> addSubject(UUID id, UUID subjectId) {
        educationalProgramService.addSubject(id, subjectId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteSubject(UUID id, UUID subjectId) {
        educationalProgramService.removeSubject(id, subjectId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<EducationalProgramExtendedInfo> findAllSubjects(UUID id, Optional<String> universityCode) {
        val educationalProgram = educationalProgramRestMapper.toExtendedDto(educationalProgramService.findById(id));
        return ResponseEntity.ok(educationalProgram);
    }
}
