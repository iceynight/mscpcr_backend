package com.mscpcr.mscpcr.repository;

import com.mscpcr.mscpcr.entity.CaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseHistoryRepository extends JpaRepository<CaseHistory, Long> {
    List<CaseHistory> findBylegalcase_CaseidOrderByActionatDesc(Long caseid);
    List<CaseHistory> findByActionbyId(Long userId);
    List<CaseHistory> findByLegalcase_CaseidAndFromstage(Long caseid, String fromStage); // Changed method name
}
