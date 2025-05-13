package com.mscpcr.mscpcr.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.Legalcase;
import com.mscpcr.mscpcr.entity.Legalcase.Casestatus;

@Repository
public interface LegalcaseRepository extends JpaRepository<Legalcase, Long> {
    Optional<Legalcase> findByCaseuuid(String caseUuid);
    List<Legalcase> findByCurrentstatus(Casestatus status);
    List<Legalcase> findByDistrictId(Long districtId);
    List<Legalcase> findByCreatedbyId(Long userId);
    List<Legalcase> findByCreatedatBetween(LocalDateTime start, LocalDateTime end);
    List<Legalcase> findByChildnameContainingIgnoreCase(String childname);
    List<Legalcase> findByComplainantnameContainingIgnoreCase(String complainantname);
    
    
    
    @Query("SELECT l FROM Legalcase l WHERE l.currentstatus = :status AND l.district.id = :districtId")
    List<Legalcase> findByStatusAndDistrict(Casestatus status, Long districtId);
}