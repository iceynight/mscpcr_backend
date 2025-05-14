package com.mscpcr.mscpcr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mscpcr.mscpcr.entity.PoliceCaseDetail;
import com.mscpcr.mscpcr.entity.PoliceCaseDetail.policecasestatus;
import com.mscpcr.mscpcr.repository.PoliceCaseDetailRepository;

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

    public Optional<PoliceCaseDetail> findBylegalcase_Caseid(Long caseid) {
        return policeCaseDetailRepository.findByLegalcase_Caseid(caseid);
    }

    public List<PoliceCaseDetail> findCasesByStatus(policecasestatus status) {
        return policeCaseDetailRepository.findByCasestatus(status);
    }

    public PoliceCaseDetail updateCaseStatus(Long caseid, policecasestatus status) {
        PoliceCaseDetail detail = findBylegalcase_Caseid(caseid)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        detail.setCasestatus(status);
        return policeCaseDetailRepository.save(detail);
    }
}