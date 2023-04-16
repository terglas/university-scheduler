package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.University;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.UniversityMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.UniversityRepository;
import com.example.universityscheduler.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper;

    @Override
    public University save(University university) {
        return universityRepository.save(university);
    }

    @Override
    public University findById(UUID id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("University not found: %S", id)));
    }

    @Override
    public List<University> findAll(PageParams pageParams) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        return universityRepository.findAll(pageable).getContent();
    }

    @Override
    public University update(University university) {
        val foundUniversity = universityRepository.findById(university.getId())
                .orElseThrow(() -> new NotFoundException(String.format("University not found: %S", university.getId())));
        universityMapper.updateUniversity(university, foundUniversity);
        return universityRepository.save(university);
    }

    @Override
    public void delete(UUID id) {
        val university = universityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("University not found: %S", id)));
        universityRepository.delete(university);
    }
}
