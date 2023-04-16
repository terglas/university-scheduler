package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.EducationalProgram;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.EducationalProgramMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.EducationalProgramRepository;
import com.example.universityscheduler.service.EducationalProgramService;
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
public class EducationalProgramServiceImpl implements EducationalProgramService {

    private final EducationalProgramRepository educationalProgramRepository;
    private final EducationalProgramMapper educationalProgramMapper;

    @Override
    public EducationalProgram save(EducationalProgram educationalProgram) {
        return educationalProgramRepository.save(educationalProgram);
    }

    @Override
    public List<EducationalProgram> findAll(PageParams pageParams) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        return educationalProgramRepository.findAll(pageable).getContent();
    }

    @Override
    public EducationalProgram findById(UUID id) {
        return educationalProgramRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Educational program not found: %S", id))
        );
    }

    @Override
    public EducationalProgram update(EducationalProgram educationalProgram) {
        val foundEducationalProgram = educationalProgramRepository.findById(educationalProgram.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Educational program not found: %S", educationalProgram.getId())));
        educationalProgramMapper.updateEducationalProgram(educationalProgram, foundEducationalProgram);
        return educationalProgramRepository.save(foundEducationalProgram);
    }

    @Override
    public void delete(UUID id) {
        val educationalProgram = educationalProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Educational program not found: %S", id)));
        educationalProgramRepository.delete(educationalProgram);
    }
}
