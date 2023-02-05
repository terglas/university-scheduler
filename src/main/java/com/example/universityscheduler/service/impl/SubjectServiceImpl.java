package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.domain.dto.SubjectDTO;
import com.example.universityscheduler.exception.DataBaseRuntimeException;
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
    public SubjectDTO save(SubjectDTO subjectDTO) {
        Subject subject = SubjectMapper.INSTANCE.toEntity(subjectDTO);
        Subject createdSubject = subjectRepository.save(subject);
        return SubjectMapper.INSTANCE.toDto(createdSubject);
    }

    @Override
    public SubjectDTO findById(UUID id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if(optionalSubject.isEmpty()) {
            throw new DataBaseRuntimeException(String.format("subject not found: %S", id));
        }
        return SubjectMapper.INSTANCE.toDto(optionalSubject.get());
    }

    @Override
    public Page<SubjectDTO> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .map(SubjectMapper.INSTANCE::toDto);
    }
}
