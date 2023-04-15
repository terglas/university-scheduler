package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Subject;
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
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject update(Subject subject) {
        Subject foundSubject = subjectRepository.findById(subject.getId()).orElseThrow(() -> {
            throw new NotFoundException(String.format("Subject not found: %S", subject.getId()));
        });
        SubjectMapper.INSTANCE.updateSubject(subject, foundSubject);
        return subjectRepository.save(foundSubject);
    }

    @Override
    public void delete(UUID id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("Subject not found: %S", id));
        });
        subjectRepository.delete(subject);
    }

    @Override
    public Subject findById(UUID id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.orElseThrow(
                () -> new NotFoundException(String.format("Subject not found: %S", id))
        );
    }

    @Override
    public Page<Subject> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }
}
