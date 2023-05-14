package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.UniversitiezApi;
import com.example.universityscheduler.mapper.PageMapper;
import com.example.universityscheduler.mapper.rest.UniversityRestMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.model.UniversityInfo;
import com.example.universityscheduler.service.UniversityService;
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
public class UniversityController implements UniversitiezApi {

    private final UniversityService universityService;
    private final UniversityRestMapper universityRestMapper;
    private final PageMapper pageMapper;

    @Override
    public ResponseEntity<UniversityInfo> create(UniversityInfo universityInfo) {
        val university = universityRestMapper.toEntity(universityInfo);
        val savedUniversityInfo = universityRestMapper.toDto(universityService.save(university));
        URI location = UriComponentsBuilder.fromPath("/universities/id").buildAndExpand(savedUniversityInfo.getId()).toUri();
        return ResponseEntity.created(location).body(savedUniversityInfo);
    }

    //FIXME findAll for one to one relation?
    @Override
    public ResponseEntity<List<UniversityInfo>> findAll(Optional<PageParams> pageParams) {
        val page = pageMapper.toDto(pageParams);
        val universities = universityService.findAll(page).stream()
                .map(universityRestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(universities);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        universityService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UniversityInfo> findById(UUID id) {
        val universityInfo = universityRestMapper.toDto(universityService.findById(id));
        return ResponseEntity.ok(universityInfo);
    }

    @Override
    public ResponseEntity<UniversityInfo> update(UUID id, UniversityInfo universityInfo) {
        val university = universityRestMapper.toEntity(universityInfo);
        val savedUniversityInfo = universityRestMapper.toDto(universityService.update(university));
        return ResponseEntity.ok(savedUniversityInfo);
    }

    @Override
    public ResponseEntity<UniversityInfo> findByCode(String code) {
        val universityInfo = universityRestMapper.toDto(universityService.findByCode(code));
        return ResponseEntity.ok(universityInfo);
    }
}
