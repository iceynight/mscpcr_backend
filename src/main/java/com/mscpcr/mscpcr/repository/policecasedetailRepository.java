package com.mscpcr.mscpcr.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.PoliceCaseDetail;

import java.util.List;
import java.util.Optional;

import com.mscpcr.mscpcr.entity.PoliceCaseDetail.policecasestatus;

@Repository
public interface PoliceCaseDetailRepository extends JpaRepository<PoliceCaseDetail, Long> {
    Optional<PoliceCaseDetail> findBylegalcaseId(Long caseId);
    List<PoliceCaseDetail> findByCasestatus(policecasestatus status);
    List<PoliceCaseDetail> findByIsforwardedtocourt(boolean isForwarded);
    List<PoliceCaseDetail> findByForwardedbyId(Long userId);
    List<PoliceCaseDetail> findByUpdatedbyId(Long userId);
    List<PoliceCaseDetail> findByPolicestationContainingIgnoreCase(String policeStation);
}