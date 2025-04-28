package com.mscpcr.mscpcr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.legalcase;
import com.mscpcr.mscpcr.entity.legalcase.Casestatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface legalcaseRepository extends JpaRepository<legalcase, Long> {
    Optional<legalcase> findByCaseuuid(String caseUuid);
    List<legalcase> findByCurrentstatus(Casestatus status);
    List<legalcase> findByDistrictId(Long districtId);
    List<legalcase> findByCreatedbyId(Long userId);
    List<legalcase> findByCreatedatBetween(LocalDateTime start, LocalDateTime end);
    List<legalcase> findByChildnameContainingIgnoreCase(String childname);
    List<legalcase> findByComplainantnameContainingIgnoreCase(String complainantname);
    
    
    
    @Query("SELECT l FROM legalcase l WHERE l.currentstatus = :status AND l.district.id = :districtId")
    List<legalcase> findByStatusAndDistrict(Casestatus status, Long districtId);
}