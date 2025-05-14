package com.mscpcr.mscpcr.repository;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.caseprogress;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.dcpuaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DcpuCaseDetailRepository extends JpaRepository<DcpuCaseDetail, Long> {
    Optional<DcpuCaseDetail> findByLegalcase_Caseid(Long caseid);
    List<DcpuCaseDetail> findByActionbycwc(dcpuaction action);
    List<DcpuCaseDetail> findByCaseprogress(caseprogress progress);
    List<DcpuCaseDetail> findByIsforwardedtopolice(boolean isForwarded);
    List<DcpuCaseDetail> findByForwardedbyId(Long userId);
}
