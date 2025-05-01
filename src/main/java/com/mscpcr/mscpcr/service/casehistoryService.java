package com.mscpcr.mscpcr.service;



import com.mscpcr.mscpcr.entity.CaseHistory;
import com.mscpcr.mscpcr.repository.CaseHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CaseHistoryService {
    private final CaseHistoryRepository casehistoryRepository;

    public CaseHistoryService(CaseHistoryRepository casehistoryRepository) {
        this.casehistoryRepository = casehistoryRepository;
    }

    public CaseHistory createCaseHistory(CaseHistory history) {
        return casehistoryRepository.save(history);
    }

    public List<CaseHistory> getHistoryByCaseId(Long caseId) {
        return casehistoryRepository.findBylegalcaseIdOrderByActionatDesc(caseId);
    }

    public List<CaseHistory> getHistoryByUserId(Long userId) {
        return casehistoryRepository.findByActionbyId(userId);
    }
}