package com.mscpcr.mscpcr.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mscpcr.mscpcr.entity.Legalcase;
import com.mscpcr.mscpcr.entity.Legalcase.Casestatus;
import com.mscpcr.mscpcr.repository.LegalcaseRepository;

@Service
@Transactional
public class LegalcaseService {
    private final LegalcaseRepository legalcaseRepository;

    public LegalcaseService(LegalcaseRepository legalcaseRepository) {
        this.legalcaseRepository = legalcaseRepository;
    }

    public Legalcase createCase(Legalcase legalcase) {
        return legalcaseRepository.save(legalcase);
    }

    public Optional<Legalcase> getCaseById(Long id) {
        return legalcaseRepository.findById(id);
    }

    public Optional<Legalcase> getCaseByUuid(String caseUuid) {
        return legalcaseRepository.findByCaseuuid(caseUuid);
    }

    public List<Legalcase> getCasesByStatus(Casestatus status) {
        return legalcaseRepository.findByCurrentstatus(status);
    }

    public List<Legalcase> getCasesByUser(Long userId) {
        return legalcaseRepository.findByCreatedbyId(userId);
    }

    public List<Legalcase> getCasesBetweenDates(LocalDateTime start, LocalDateTime end) {
        return legalcaseRepository.findByCreatedatBetween(start, end);
    }

    public Page<Legalcase> getAllCases(Pageable pageable) {
        return legalcaseRepository.findAll(pageable);
    }

    public Legalcase updateCase(Legalcase legalcase) {
        return legalcaseRepository.save(legalcase);
    }

    public void deleteCase(Long id) {
        legalcaseRepository.deleteById(id);
    }

    
}