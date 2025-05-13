package com.mscpcr.mscpcr.service;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mscpcr.mscpcr.entity.CourtCaseDetail;
import com.mscpcr.mscpcr.entity.CourtCaseDetail.CompensationStatus;
import com.mscpcr.mscpcr.entity.CourtCaseDetail.TrialState;
import com.mscpcr.mscpcr.repository.CourtCaseDetailRepository;

@Service
@Transactional
public class CourtCaseDetailService {
    private final CourtCaseDetailRepository courtCaseDetailRepository;

    public CourtCaseDetailService(CourtCaseDetailRepository courtCaseDetailRepository) {
        this.courtCaseDetailRepository = courtCaseDetailRepository;
    }

    public CourtCaseDetail saveCourtCaseDetail(CourtCaseDetail courtCaseDetail) {
        return courtCaseDetailRepository.save(courtCaseDetail);
    }

    public Optional<CourtCaseDetail> findBylegalcaseId(Long caseId) {
        return courtCaseDetailRepository.findBylegalcaseId(caseId);
    }

    public List<CourtCaseDetail> findCasesByTrialState(TrialState state) {
        return courtCaseDetailRepository.findByTrialstate(state);
    }

    public CourtCaseDetail updateVerdict(Long caseId, TrialState state, 
                                       CompensationStatus compensation, 
                                       LocalDate awardDate, BigDecimal amount) {
        CourtCaseDetail detail = courtCaseDetailRepository.findBylegalcaseId(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        detail.setTrialstate(state);
        detail.setVictimcompensation(compensation);
        detail.setAwarddate(awardDate);
        detail.setAmountawarded(amount);
        return courtCaseDetailRepository.save(detail);
    }
}