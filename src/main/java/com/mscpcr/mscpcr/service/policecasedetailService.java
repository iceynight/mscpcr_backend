package com.mscpcr.mscpcr.service;

import com.mscpcr.mscpcr.entity.PoliceCaseDetail;
import com.mscpcr.mscpcr.entity.PoliceCaseDetail.policecasestatus;
import com.mscpcr.mscpcr.repository.PoliceCaseDetailRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PoliceCaseDetailService {
    private final PoliceCaseDetailRepository policeCaseDetailRepository;

    public PoliceCaseDetailService(PoliceCaseDetailRepository policeCaseDetailRepository) {
        this.policeCaseDetailRepository = policeCaseDetailRepository;
    }

    public PoliceCaseDetail savePoliceCaseDetail(PoliceCaseDetail policeCaseDetail) {
        return policeCaseDetailRepository.save(policeCaseDetail);
    }

    public Optional<PoliceCaseDetail> findBylegalCaseId(Long caseId) {
        return policeCaseDetailRepository.findBylegalcaseId(caseId);
    }

    public List<PoliceCaseDetail> findCasesByStatus(policecasestatus status) {
        return policeCaseDetailRepository.findByCasestatus(status);
    }

    public PoliceCaseDetail updateCaseStatus(Long caseId, policecasestatus status) {
        PoliceCaseDetail detail = policeCaseDetailRepository.findBylegalcaseId(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        detail.setCasestatus(status);
        return policeCaseDetailRepository.save(detail);
    }
}