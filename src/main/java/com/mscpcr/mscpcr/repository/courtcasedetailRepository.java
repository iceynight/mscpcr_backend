package com.mscpcr.mscpcr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.CourtCaseDetail;
import com.mscpcr.mscpcr.entity.CourtCaseDetail.CompensationStatus;
import com.mscpcr.mscpcr.entity.CourtCaseDetail.TrialState;

@Repository
public interface CourtCaseDetailRepository extends JpaRepository<CourtCaseDetail, Long> {
    Optional<CourtCaseDetail> findByLegalcase_Caseid(Long caseid);
    List<CourtCaseDetail> findByTrialstate(TrialState state);
    List<CourtCaseDetail> findByVictimcompensation(CompensationStatus status);
    List<CourtCaseDetail> findByTrialstate(String trialState);
    List<CourtCaseDetail> findByVictimcompensation(String compensationStatus);
    List<CourtCaseDetail> findByUpdatedbyId(Long userId);
}