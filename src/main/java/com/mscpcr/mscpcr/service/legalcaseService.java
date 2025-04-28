package com.mscpcr.mscpcr.service;


import com.mscpcr.mscpcr.entity.legalcase;
import com.mscpcr.mscpcr.entity.legalcase.Casestatus;
import com.mscpcr.mscpcr.repository.legalcaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class legalcaseService {
    private final legalcaseRepository legalcaseRepository;

    public legalcaseService(legalcaseRepository legalcaseRepository) {
        this.legalcaseRepository = legalcaseRepository;
    }

    public legalcase createCase(legalcase legalcase) {
        return legalcaseRepository.save(legalcase);
    }

    public Optional<legalcase> getCaseById(Long id) {
        return legalcaseRepository.findById(id);
    }

    public Optional<legalcase> getCaseByUuid(String caseUuid) {
        return legalcaseRepository.findByCaseuuid(caseUuid);
    }

    public List<legalcase> getCasesByStatus(Casestatus status) {
        return legalcaseRepository.findByCurrentstatus(status);
    }

    public List<legalcase> getCasesByUser(Long userId) {
        return legalcaseRepository.findByCreatedbyId(userId);
    }

    public List<legalcase> getCasesBetweenDates(LocalDateTime start, LocalDateTime end) {
        return legalcaseRepository.findByCreatedatBetween(start, end);
    }

    public Page<legalcase> getAllCases(Pageable pageable) {
        return legalcaseRepository.findAll(pageable);
    }

    public legalcase updateCase(legalcase legalcase) {
        return legalcaseRepository.save(legalcase);
    }

    public void deleteCase(Long id) {
        legalcaseRepository.deleteById(id);
    }
}