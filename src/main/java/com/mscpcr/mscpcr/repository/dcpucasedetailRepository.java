package com.mscpcr.mscpcr.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;

import java.util.List;
import java.util.Optional;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail.caseprogress;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.dcpuaction;

@Repository
public interface DcpuCaseDetailRepository extends JpaRepository<DcpuCaseDetail, Long> {
    Optional<DcpuCaseDetail> findBylegalcaseId(Long caseId);
    List<DcpuCaseDetail> findByActionbycwc(dcpuaction action);
    List<DcpuCaseDetail> findByCaseprogress(caseprogress progress);
    List<DcpuCaseDetail> findByIsforwardedtopolice(boolean isForwarded);
    List<DcpuCaseDetail> findByForwardedbyId(Long userId);
}
