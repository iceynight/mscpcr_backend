package com.mscpcr.mscpcr.service;


import com.mscpcr.mscpcr.entity.dcpucasedetail;
import com.mscpcr.mscpcr.entity.dcpucasedetail.dcpuaction;
import com.mscpcr.mscpcr.entity.dcpucasedetail.caseprogress;
import com.mscpcr.mscpcr.repository.dcpucasedetailRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class dcpucasedetailService {
    private final dcpucasedetailRepository dcpuCaseDetailRepository;

    public dcpucasedetailService(dcpucasedetailRepository dcpuCaseDetailRepository) {
        this.dcpuCaseDetailRepository = dcpuCaseDetailRepository;
    }

    public dcpucasedetail saveDcpuCaseDetail(dcpucasedetail dcpuCaseDetail) {
        return dcpuCaseDetailRepository.save(dcpuCaseDetail);
    }

    public Optional<dcpucasedetail> findBylegalCaseId(Long caseId) {
        return dcpuCaseDetailRepository.findBylegalcaseId(caseId);
    }

    public List<dcpucasedetail> findCasesByAction(dcpuaction action) {
        return dcpuCaseDetailRepository.findByActionbycwc(action);
    }

    public List<dcpucasedetail> findCasesByProgress(caseprogress progress) {
        return dcpuCaseDetailRepository.findByCaseprogress(progress);
    }

    public dcpucasedetail updateCaseProgress(Long caseId, caseprogress progress) {
        dcpucasedetail detail = dcpuCaseDetailRepository.findBylegalcaseId(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        detail.setCaseprogress(progress);
        return dcpuCaseDetailRepository.save(detail);
    }
}