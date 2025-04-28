package com.mscpcr.mscpcr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.courtcasedetail;
import com.mscpcr.mscpcr.entity.courtcasedetail.CompensationStatus;
import com.mscpcr.mscpcr.entity.courtcasedetail.TrialState;

import java.util.List;
import java.util.Optional;

@Repository
public interface courtcasedetailRepository extends JpaRepository<courtcasedetail, Long> {
    Optional<courtcasedetail> findBylegalcaseId(Long caseId);
    List<courtcasedetail> findByTrialstate(TrialState state);
    List<courtcasedetail> findByVictimcompensation(CompensationStatus status);
    List<courtcasedetail> findByTrialstate(String trialState);
    List<courtcasedetail> findByVictimcompensation(String compensationStatus);
    List<courtcasedetail> findByUpdatedbyId(Long userId);
}