package com.mscpcr.mscpcr.service;


import com.mscpcr.mscpcr.entity.LegalCase;
import com.mscpcr.mscpcr.entity.LegalCase.Casestatus;
import com.mscpcr.mscpcr.repository.LegalCaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LegalCaseService {
    private final LegalCaseRepository legalcaseRepository;

    public LegalCaseService(LegalCaseRepository legalcaseRepository) {
        this.legalcaseRepository = legalcaseRepository;
    }

    public LegalCase createCase(LegalCase legalcase) {
        return legalcaseRepository.save(legalcase);
    }

    public Optional<LegalCase> getCaseById(Long id) {
        return legalcaseRepository.findById(id);
    }

    public Optional<LegalCase> getCaseByUuid(String caseUuid) {
        return legalcaseRepository.findByCaseuuid(caseUuid);
    }

    public List<LegalCase> getCasesByStatus(Casestatus status) {
        return legalcaseRepository.findByCurrentstatus(status);
    }

    public List<LegalCase> getCasesByUser(Long userId) {
        return legalcaseRepository.findByCreatedbyId(userId);
    }

    public List<LegalCase> getCasesBetweenDates(LocalDateTime start, LocalDateTime end) {
        return legalcaseRepository.findByCreatedatBetween(start, end);
    }

    public Page<LegalCase> getAllCases(Pageable pageable) {
        return legalcaseRepository.findAll(pageable);
    }

    public LegalCase updateCase(LegalCase legalcase) {
        return legalcaseRepository.save(legalcase);
    }

    public void deleteCase(Long id) {
        legalcaseRepository.deleteById(id);
    }
}