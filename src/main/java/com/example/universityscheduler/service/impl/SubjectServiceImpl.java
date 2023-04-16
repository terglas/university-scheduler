package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.SubjectMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.SubjectRepository;
import com.example.universityscheduler.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject update(Subject subject) {
        val foundSubject = subjectRepository.findById(subject.getId()).orElseThrow(() -> {
            throw new NotFoundException(String.format("Subject not found: %S", subject.getId()));
        });
        subjectMapper.updateSubject(subject, foundSubject);
        return subjectRepository.save(foundSubject);
    }

    @Override
    public void delete(UUID id) {
        val subject = subjectRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("Subject not found: %S", id));
        });
        subjectRepository.delete(subject);
    }

    @Override
    public Subject findById(UUID id) {
        val optionalSubject = subjectRepository.findById(id);
        return optionalSubject.orElseThrow(
                () -> new NotFoundException(String.format("Subject not found: %S", id))
        );
    }

    @Override
    public List<Subject> findAll(PageParams params) {
        val pageable = PageRequest.of(params.getPageCurrent() - 1, params.getPageSize());
        return subjectRepository.findAll(pageable).getContent();
    }
}
