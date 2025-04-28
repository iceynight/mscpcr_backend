package com.mscpcr.mscpcr.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.casehistory;

import java.util.List;

@Repository
public interface casehistoryRepository extends JpaRepository<casehistory, Long> {
    List<casehistory> findBylegalcaseIdOrderByActionatDesc(Long caseId);
    List<casehistory> findByActionbyId(Long userId);
    List<casehistory> findBylegalcaseIdAndFromstage(Long caseId, String fromStage);
}
