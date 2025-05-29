package com.mscpcr.mscpcr.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mscpcr.mscpcr.entity.Legalcase.Casestatus;
import com.mscpcr.mscpcr.repository.LegalcaseRepository;

@Service
@Transactional
public class CaseStatisticsService {

    private final LegalcaseRepository legalcaseRepository;

    public CaseStatisticsService(LegalcaseRepository legalcaseRepository) {
        this.legalcaseRepository = legalcaseRepository;
    }

    public Map<String, Long> getDashboardStatistics() {
    Map<String, Long> stats = new LinkedHashMap<>();
    try {
        if (legalcaseRepository == null) {
            System.err.println("legalcaseRepository is null!");
            throw new IllegalStateException("Repository not injected");
        }
        long totalCases = legalcaseRepository.count();
        long solvedCases = legalcaseRepository.countByCurrentstatus(Casestatus.solved);
        long dcpuCases = legalcaseRepository.countByCurrentstatus(Casestatus.dcpuprocessing);
        long policeCases = legalcaseRepository.countByCurrentstatus(Casestatus.policeprocessing);
        long courtCases = legalcaseRepository.countByCurrentstatus(Casestatus.courtprocessing);

        System.out.println("totalCases: " + totalCases);
        System.out.println("solvedCases: " + solvedCases);
        System.out.println("dcpuCases: " + dcpuCases);
        System.out.println("policeCases: " + policeCases);
        System.out.println("courtCases: " + courtCases);

        stats.put("totalCases", totalCases);
        stats.put("totalCasesSolved", solvedCases);
        stats.put("totalCasesProcessing", totalCases - solvedCases);
        stats.put("casesInDCPU", dcpuCases);
        stats.put("processingInPolice", policeCases);
        stats.put("processingInCourt", courtCases);

    } catch (Exception e) {
        System.err.println("Error in getDashboardStatistics: " + e.getMessage());
        e.printStackTrace();
        stats.put("totalCases", 0L);
        stats.put("totalCasesSolved", 0L);
        stats.put("totalCasesProcessing", 0L);
        stats.put("casesInDCPU", 0L);
        stats.put("processingInPolice", 0L);
        stats.put("processingInCourt", 0L);
    }
    return stats;
}
}