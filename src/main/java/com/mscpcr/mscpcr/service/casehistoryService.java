package com.mscpcr.mscpcr.service;



import com.mscpcr.mscpcr.entity.casehistory;
import com.mscpcr.mscpcr.repository.casehistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class casehistoryService {
    private final casehistoryRepository casehistoryRepository;

    public casehistoryService(casehistoryRepository casehistoryRepository) {
        this.casehistoryRepository = casehistoryRepository;
    }

    public casehistory createCaseHistory(casehistory history) {
        return casehistoryRepository.save(history);
    }

    public List<casehistory> getHistoryByCaseId(Long caseId) {
        return casehistoryRepository.findBylegalcaseIdOrderByActionatDesc(caseId);
    }

    public List<casehistory> getHistoryByUserId(Long userId) {
        return casehistoryRepository.findByActionbyId(userId);
    }
}