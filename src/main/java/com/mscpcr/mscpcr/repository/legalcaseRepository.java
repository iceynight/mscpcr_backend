package com.mscpcr.mscpcr.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.Legalcase;
import com.mscpcr.mscpcr.entity.Legalcase.Casestatus;

@Repository
public interface LegalcaseRepository extends JpaRepository<Legalcase, Long> {

 @Query("SELECT COUNT(l) FROM Legalcase l WHERE l.currentstatus = 'SOLVED'")
    long countSolvedCasesDebug();
    
    @Query("SELECT COUNT(l) FROM Legalcase l WHERE l.currentstatus = 'DCPUPROCESSING'")
    long countDcpuCasesDebug();

    Optional<Legalcase> findByCaseuuid(String caseUuid);
    List<Legalcase> findByCurrentstatus(Casestatus status);
    List<Legalcase> findByDistrictId(Long districtId);
    List<Legalcase> findByCreatedbyId(Long userId);
    List<Legalcase> findByCreatedatBetween(LocalDateTime start, LocalDateTime end);
    List<Legalcase> findByChildnameContainingIgnoreCase(String childname);
    List<Legalcase> findByComplainantnameContainingIgnoreCase(String complainantname);

    long count();
    long countByCurrentstatus(Legalcase.Casestatus status);
    long countByCurrentstatusNot(Legalcase.Casestatus status);
    
    @Query("SELECT COUNT(DISTINCT l) FROM Legalcase l JOIN l.dcpuCaseDetails d " +
           "WHERE d.id = (SELECT MAX(d2.id) FROM DcpuCaseDetail d2 WHERE d2.legalcase = l) " +
           "AND d.actionbycwc = com.mscpcr.mscpcr.entity.DcpuCaseDetail.dcpuaction.DISPOSED")
    long countDisposedCases();
    
    @Query("SELECT COUNT(DISTINCT l) FROM Legalcase l JOIN l.dcpuCaseDetails d " +
           "WHERE d.id = (SELECT MAX(d2.id) FROM DcpuCaseDetail d2 WHERE d2.legalcase = l) " +
           "AND d.actionbycwc = com.mscpcr.mscpcr.entity.DcpuCaseDetail.dcpuaction.UNDER_SUPERVISION")
    long countDcpuProcessingCases();
    
    @Query("SELECT COUNT(DISTINCT l) FROM Legalcase l JOIN l.dcpuCaseDetails d " +
           "WHERE d.id = (SELECT MAX(d2.id) FROM DcpuCaseDetail d2 WHERE d2.legalcase = l) " +
           "AND d.actionbycwc = com.mscpcr.mscpcr.entity.DcpuCaseDetail.dcpuaction.TRANSFERRED")
    long countPoliceProcessingCases();

    @Query("SELECT l FROM Legalcase l WHERE l.id IN " +
       "(SELECT d.legalcase.id FROM DcpuCaseDetail d WHERE d.id = " +
       "(SELECT MAX(d2.id) FROM DcpuCaseDetail d2 WHERE d2.legalcase = d.legalcase) " +
       "AND d.actionbycwc = :action)")
List<Legalcase> findByLatestDcpuAction(@Param("action") DcpuCaseDetail.dcpuaction action);
    
     @Query(value = """
        SELECT COUNT(DISTINCT l.id)
        FROM legalcase l
        WHERE EXISTS (
            SELECT 1 FROM dcpucasedetail d 
            WHERE d.legalcase_id = l.id
            AND d.actionbycwc = :status
            AND d.id = (
                SELECT MAX(d2.id) 
                FROM dcpucasedetail d2 
                WHERE d2.legalcase_id = l.id
            )
        )
        """, nativeQuery = true)
    long countByLatestDcpuAction(@Param("status") String status);
    
    @Query(value = """
        SELECT l.*
        FROM legalcase l
        WHERE EXISTS (
            SELECT 1 FROM dcpucasedetail d 
            WHERE d.legalcase_id = l.id
            AND d.actionbycwc = :status
            AND d.id = (
                SELECT MAX(d2.id) 
                FROM dcpucasedetail d2 
                WHERE d2.legalcase_id = l.id
            )
        )
        """, nativeQuery = true)
    List<Legalcase> findByLatestDcpuAction(@Param("status") String status);
}