package com.mscpcr.mscpcr.service;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.caseprogress;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.dcpuaction;
import com.mscpcr.mscpcr.repository.DcpuCaseDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Optional<DcpuCaseDetail> findByLegalcase_Caseid(Long caseid) {
        return dcpuCaseDetailRepository.findByLegalcase_Caseid(caseid);
    }

    public List<DcpuCaseDetail> findCasesByAction(dcpuaction action) {
        return dcpuCaseDetailRepository.findByActionbycwc(action);
    }

    public List<DcpuCaseDetail> findCasesByProgress(caseprogress progress) {
        return dcpuCaseDetailRepository.findByCaseprogress(progress);
    }

    // BUG FIX: Handle the case where multiple DcpuCaseDetail records may exist for a Legalcase.
    // Always update the latest (most recent) DcpuCaseDetail for the given Legalcase.
    public DcpuCaseDetail updateCaseProgress(Long caseid, caseprogress progress) {
        List<DcpuCaseDetail> details = dcpuCaseDetailRepository.findAllByLegalcase_CaseidOrderByCreatedatDesc(caseid);
        if (details.isEmpty()) {
            throw new IllegalArgumentException("DCPU Case Detail not found for Legalcase ID: " + caseid);
        }
        DcpuCaseDetail latestDetail = details.get(0);
        latestDetail.setCaseprogress(progress);
        return dcpuCaseDetailRepository.save(latestDetail);
    }
}