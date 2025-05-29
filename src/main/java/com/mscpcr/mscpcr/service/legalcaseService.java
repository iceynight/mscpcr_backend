package com.mscpcr.mscpcr.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.Legalcase;
import com.mscpcr.mscpcr.entity.Legalcase.Casestatus;
import com.mscpcr.mscpcr.repository.DcpuCaseDetailRepository;
import com.mscpcr.mscpcr.repository.LegalcaseRepository;

@Service
@Transactional
public class LegalcaseService {

    
    private final LegalcaseRepository legalcaseRepository;
    private final DcpuCaseDetailRepository dcpuCaseDetailRepository;

    @Autowired
    public LegalcaseService(LegalcaseRepository legalcaseRepository,
                          DcpuCaseDetailRepository dcpuCaseDetailRepository) {
        this.legalcaseRepository = legalcaseRepository;
        this.dcpuCaseDetailRepository = dcpuCaseDetailRepository;
    }

    // Existing CRUD methods remain unchanged
    public Legalcase createCase(Legalcase legalcase) {
        return legalcaseRepository.save(legalcase);
    }

    public Optional<Legalcase> getCaseById(Long id) {
        return legalcaseRepository.findById(id);
    }

    public Optional<Legalcase> getCaseByUuid(String caseUuid) {
        return legalcaseRepository.findByCaseuuid(caseUuid);
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

    public List<Legalcase> getAllCases() {
        return legalcaseRepository.findAll();
    }

    // Modified methods for accurate status tracking
    public List<Legalcase> getCasesByStatus(Casestatus status) {
        // Map DcpuCaseDetail actions to Casestatus
        List<Legalcase> cases = new ArrayList<>();
        
        switch(status) {
            case solved:
                cases = legalcaseRepository.findByLatestDcpuAction(DcpuCaseDetail.dcpuaction.DISPOSED);
                break;
            case dcpuprocessing:
                cases = legalcaseRepository.findByLatestDcpuAction(DcpuCaseDetail.dcpuaction.UNDER_SUPERVISION);
                break;
            case policeprocessing:
                cases = legalcaseRepository.findByLatestDcpuAction(DcpuCaseDetail.dcpuaction.TRANSFERRED);
                break;
            default:
                cases = legalcaseRepository.findByCurrentstatus(status);
        }
        
        System.out.println("Cases by Status (" + status + "): " + cases.size());
        return cases;
    }

    public long getTotalCases() {
        return legalcaseRepository.count();
    }

    public long getSolvedCases() {
        return legalcaseRepository.countByCurrentstatus(Casestatus.solved);
    }

    public long getProcessingCases() {
        return getTotalCases() - getSolvedCases();
    }

    // New methods for detailed statistics
    public long getDcpuProcessingCases() {
        return legalcaseRepository.countByCurrentstatus(Casestatus.dcpuprocessing);
    }

    public long getPoliceProcessingCases() {
        return legalcaseRepository.countByCurrentstatus(Casestatus.policeprocessing);
    }

     public long getCourtProcessingCases() {
        return legalcaseRepository.countByCurrentstatus(Casestatus.courtprocessing);
    }

    public Map<String, Long> getDashboardStatistics() {
        Map<String, Long> stats = new LinkedHashMap<>();

        long total = legalcaseRepository.count();
        long solved = getSolvedCases();
        long dcpu = getDcpuProcessingCases();
        long police = getPoliceProcessingCases();
        long court = getCourtProcessingCases();

    stats.put("totalCases", total);
        stats.put("totalCasesSolved", solved);
        stats.put("totalCasesProcessing", total - solved);
        stats.put("casesInDCPU", dcpu);
        stats.put("processingInPolice", police);
        stats.put("processingInCourt", court);

        return stats;
    }
}