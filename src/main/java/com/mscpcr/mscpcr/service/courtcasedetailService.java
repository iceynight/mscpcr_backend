package com.mscpcr.mscpcr.service;



import com.mscpcr.mscpcr.entity.courtcasedetail;
import com.mscpcr.mscpcr.entity.courtcasedetail.TrialState;
import com.mscpcr.mscpcr.entity.courtcasedetail.CompensationStatus;
import com.mscpcr.mscpcr.repository.courtcasedetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class courtcasedetailService {
    private final courtcasedetailRepository courtCaseDetailRepository;

    public courtcasedetailService(courtcasedetailRepository courtCaseDetailRepository) {
        this.courtCaseDetailRepository = courtCaseDetailRepository;
    }

    public courtcasedetail saveCourtCaseDetail(courtcasedetail courtCaseDetail) {
        return courtCaseDetailRepository.save(courtCaseDetail);
    }

    public Optional<courtcasedetail> findBylegalCaseId(Long caseId) {
        return courtCaseDetailRepository.findBylegalcaseId(caseId);
    }

    public List<courtcasedetail> findCasesByTrialState(TrialState state) {
        return courtCaseDetailRepository.findByTrialstate(state);
    }

    public courtcasedetail updateVerdict(Long caseId, TrialState state, 
                                       CompensationStatus compensation, 
                                       LocalDate awardDate, BigDecimal amount) {
        courtcasedetail detail = courtCaseDetailRepository.findBylegalcaseId(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        detail.setTrialstate(state);
        detail.setVictimcompensation(compensation);
        detail.setAwarddate(awardDate);
        detail.setAmountawarded(amount);
        return courtCaseDetailRepository.save(detail);
    }
}