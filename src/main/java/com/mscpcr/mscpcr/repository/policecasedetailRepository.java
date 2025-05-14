package com.mscpcr.mscpcr.repository;

import com.mscpcr.mscpcr.entity.PoliceCaseDetail;
import com.mscpcr.mscpcr.entity.PoliceCaseDetail.policecasestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PoliceCaseDetailRepository extends JpaRepository<PoliceCaseDetail, Long> {
    Optional<PoliceCaseDetail> findByLegalcase_Caseid(Long caseid);
    List<PoliceCaseDetail> findByCasestatus(policecasestatus status);
    List<PoliceCaseDetail> findByIsforwardedtocourt(boolean isForwarded);
    List<PoliceCaseDetail> findByForwardedbyId(Long userId);
    List<PoliceCaseDetail> findByUpdatedbyId(Long userId);
    List<PoliceCaseDetail> findByPolicestationContainingIgnoreCase(String policeStation);
}