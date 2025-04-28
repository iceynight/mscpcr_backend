package com.mscpcr.mscpcr.service;

import com.mscpcr.mscpcr.entity.policecasedetail;
import com.mscpcr.mscpcr.entity.policecasedetail.policecasestatus;
import com.mscpcr.mscpcr.repository.policecasedetailRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class policecasedetailService {
    private final policecasedetailRepository policeCaseDetailRepository;

    public policecasedetailService(policecasedetailRepository policeCaseDetailRepository) {
        this.policeCaseDetailRepository = policeCaseDetailRepository;
    }

    public policecasedetail savePoliceCaseDetail(policecasedetail policeCaseDetail) {
        return policeCaseDetailRepository.save(policeCaseDetail);
    }

    public Optional<policecasedetail> findBylegalCaseId(Long caseId) {
        return policeCaseDetailRepository.findBylegalcaseId(caseId);
    }

    public List<policecasedetail> findCasesByStatus(policecasestatus status) {
        return policeCaseDetailRepository.findByCasestatus(status);
    }

    public policecasedetail updateCaseStatus(Long caseId, policecasestatus status) {
        policecasedetail detail = policeCaseDetailRepository.findBylegalcaseId(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        detail.setCasestatus(status);
        return policeCaseDetailRepository.save(detail);
    }
}