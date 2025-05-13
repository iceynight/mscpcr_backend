package com.mscpcr.mscpcr.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.caseprogress;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.dcpuaction;
import com.mscpcr.mscpcr.repository.DcpuCaseDetailRepository;

@Service
@Transactional
public class DcpuCaseDetailService {
    private final DcpuCaseDetailRepository dcpuCaseDetailRepository;

    public DcpuCaseDetailService(DcpuCaseDetailRepository dcpuCaseDetailRepository) {
        this.dcpuCaseDetailRepository = dcpuCaseDetailRepository;
    }

    public DcpuCaseDetail saveDcpuCaseDetail(DcpuCaseDetail dcpuCaseDetail) {
        return dcpuCaseDetailRepository.save(dcpuCaseDetail);
    }

    public Optional<DcpuCaseDetail> findByLegalcaseId(Long caseId) {
        return dcpuCaseDetailRepository.findBylegalcaseId(caseId);
    }

    public List<DcpuCaseDetail> findCasesByAction(dcpuaction action) {
        return dcpuCaseDetailRepository.findByActionbycwc(action);
    }

    public List<DcpuCaseDetail> findCasesByProgress(caseprogress progress) {
        return dcpuCaseDetailRepository.findByCaseprogress(progress);
    }

    public DcpuCaseDetail updateCaseProgress(Long caseId, caseprogress progress) {
        DcpuCaseDetail detail = dcpuCaseDetailRepository.findBylegalcaseId(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        detail.setCaseprogress(progress);
        return dcpuCaseDetailRepository.save(detail);
    }
}