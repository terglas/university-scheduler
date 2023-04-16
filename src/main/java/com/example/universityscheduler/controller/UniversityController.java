package com.example.universityscheduler.controller;

import com.example.universityscheduler.api.UniversitiezApi;
import com.example.universityscheduler.domain.University;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UniversityController implements UniversitiezApi {

    private final UniversityService universityService;
    private final UniversityRestMapper universityRestMapper;
    private final PageMapper pageMapper;

    @Override
    public ResponseEntity<UniversityInfo> create(UniversityInfo universityInfo) {
        University university = universityRestMapper.toEntity(universityInfo);
        UniversityInfo savedUniversityInfo = universityRestMapper.toDto(universityService.save(university));
        URI location = UriComponentsBuilder.fromPath("/universities/id").buildAndExpand(savedUniversityInfo.getId()).toUri();
        return ResponseEntity.created(location).body(savedUniversityInfo);
    }

    @Override
    public ResponseEntity<List<UniversityInfo>> findAll(Optional<PageParams> pageParams) {
        val page = pageMapper.toDto(pageParams);
        val universities = universityService.findAll(page).stream()
                .map(universityRestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(universities);
    }
}
