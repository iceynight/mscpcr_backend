package com.mscpcr.mscpcr.service;

import com.mscpcr.mscpcr.entity.CourtCaseDetail;
import com.mscpcr.mscpcr.entity.CourtCaseDetail.CompensationStatus;
import com.mscpcr.mscpcr.entity.CourtCaseDetail.TrialState;
import com.mscpcr.mscpcr.repository.CourtCaseDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<CourtCaseDetail> findByLegalcase_Caseid(Long caseid) {
        return courtCaseDetailRepository.findByLegalcase_Caseid(caseid);
    }

    public List<CourtCaseDetail> findCasesByTrialState(TrialState state) {
        return courtCaseDetailRepository.findByTrialstate(state);
    }

    public CourtCaseDetail updateVerdict(Long caseid, TrialState state,
                                       CompensationStatus compensation,
                                       LocalDate awardDate, BigDecimal amount) {
        CourtCaseDetail detail = findByLegalcase_Caseid(caseid)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        detail.setTrialstate(state);
        detail.setVictimcompensation(compensation);
        detail.setAwarddate(awardDate);
        detail.setAmountawarded(amount);
        return courtCaseDetailRepository.save(detail);
    }
}