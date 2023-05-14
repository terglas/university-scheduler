package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.Subject;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.SubjectMapper;
import com.example.universityscheduler.model.PageParams;
import com.example.universityscheduler.repository.SubjectRepository;
import com.example.universityscheduler.service.SubjectService;
import com.example.universityscheduler.service.UniversityService;
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
    private final UserAccountService userAccountService;
    private final UniversityService universityService;

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject update(Subject subject) {
        val foundSubject = subjectRepository.findById(subject.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Subject not found: %S", subject.getId())));
        subjectMapper.updateSubject(subject, foundSubject);
        return subjectRepository.save(foundSubject);
    }

    @Override
    public void delete(UUID id) {
        val subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Subject not found: %S", id)));
        subjectRepository.delete(subject);
    }

    @Override
    public Subject findById(UUID id) {
        val userAccount = userAccountService.getCurrentUser();
        return findById(id, userAccount.getUniversity().getId());
    }

    @Override
    public Subject findById(UUID id, UUID universityId) {
        return subjectRepository.findByIdAndUniversityId(id, universityId).orElseThrow(
                () -> new NotFoundException(String.format("Subject not found: %S", id)));
    }

    @Override
    public Subject findById(UUID id, String universityCode) {
        if(universityCode == null) {
            return findById(id);
        }
        val university = universityService.findByCode(universityCode);
        return findById(id, university.getId());
    }

    @Override
    public List<Subject> findAll(PageParams pageParams) {
        val userAccount = userAccountService.getCurrentUser();
        return findAll(pageParams, userAccount.getUniversity().getId());
    }

    @Override
    public List<Subject> findAll(PageParams pageParams, UUID universityId) {
        val pageable = PageRequest.of(pageParams.getPageCurrent() - 1, pageParams.getPageSize());
        return subjectRepository.findAllByUniversityId(pageable, universityId).getContent();
    }

    @Override
    public List<Subject> findAll(PageParams pageParams, String universityCode) {
        if(universityCode == null) {
            return findAll(pageParams);
        }
        val university = universityService.findByCode(universityCode);
        return findAll(pageParams, university.getId());
    }
}
