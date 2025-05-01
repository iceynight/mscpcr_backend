package com.mscpcr.mscpcr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.LegalCase;
import com.mscpcr.mscpcr.entity.LegalCase.Casestatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LegalCaseRepository extends JpaRepository<LegalCase, Long> {
    Optional<LegalCase> findByCaseuuid(String caseUuid);
    List<LegalCase> findByCurrentstatus(Casestatus status);
    List<LegalCase> findByDistrictId(Long districtId);
    List<LegalCase> findByCreatedbyId(Long userId);
    List<LegalCase> findByCreatedatBetween(LocalDateTime start, LocalDateTime end);
    List<LegalCase> findByChildnameContainingIgnoreCase(String childname);
    List<LegalCase> findByComplainantnameContainingIgnoreCase(String complainantname);
    
    
    
    @Query("SELECT l FROM legalcase l WHERE l.currentstatus = :status AND l.district.id = :districtId")
    List<LegalCase> findByStatusAndDistrict(Casestatus status, Long districtId);
}