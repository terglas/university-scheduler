package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.model.SubjectInfo;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.SubjectMapper;
import com.example.universityscheduler.repository.SubjectRepository;
import com.example.universityscheduler.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectInfo save(SubjectInfo subjectDTO) {
        Subject subject = SubjectMapper.INSTANCE.toEntity(subjectDTO);
        Subject createdSubject = subjectRepository.save(subject);
        return SubjectMapper.INSTANCE.toDto(createdSubject);
    }

    @Override
    public SubjectInfo update(SubjectInfo subjectDTO) {
        Subject subject = subjectRepository.findById(subjectDTO.getId()).orElseThrow(() -> {
            throw new NotFoundException(String.format("Subject not found: %S", subjectDTO.getId()));
        });
        SubjectMapper.INSTANCE.updateSubjectFromDto(subjectDTO, subject);
        Subject createdSubject = subjectRepository.save(subject);
        return SubjectMapper.INSTANCE.toDto(createdSubject);
    }

    @Override
    public void delete(UUID id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("Subject not found: %S", id));
        });
        subjectRepository.delete(subject);
    }

    @Override
    public SubjectInfo findById(UUID id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if(optionalSubject.isEmpty()) {
            throw new NotFoundException(String.format("subject not found: %S", id));
        }
        return SubjectMapper.INSTANCE.toDto(optionalSubject.get());
    }

    @Override
    public Page<SubjectInfo> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .map(SubjectMapper.INSTANCE::toDto);
    }
}
