package com.mscpcr.mscpcr.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.CaseHistory;

import java.util.List;

@Repository
public interface CaseHistoryRepository extends JpaRepository<CaseHistory, Long> {
    List<CaseHistory> findBylegalcaseIdOrderByActionatDesc(Long caseId);
    List<CaseHistory> findByActionbyId(Long userId);
    List<CaseHistory> findBylegalcaseIdAndFromstage(Long caseId, String fromStage);
}
