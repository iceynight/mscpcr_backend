package com.mscpcr.mscpcr.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.policecasedetail;

import java.util.List;
import java.util.Optional;

import com.mscpcr.mscpcr.entity.policecasedetail.policecasestatus;

@Repository
public interface policecasedetailRepository extends JpaRepository<policecasedetail, Long> {
    Optional<policecasedetail> findBylegalcaseId(Long caseId);
    List<policecasedetail> findByCasestatus(policecasestatus status);
    List<policecasedetail> findByIsforwardedtocourt(boolean isForwarded);
    List<policecasedetail> findByForwardedbyId(Long userId);
    List<policecasedetail> findByUpdatedbyId(Long userId);
    List<policecasedetail> findByPolicestationContainingIgnoreCase(String policeStation);
}