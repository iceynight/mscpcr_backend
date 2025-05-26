package com.mscpcr.mscpcr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.caseprogress;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail.dcpuaction;

@Repository
public interface DcpuCaseDetailRepository extends JpaRepository<DcpuCaseDetail, Long> {

    List<DcpuCaseDetail> findByActionbycwc(dcpuaction action);

    List<DcpuCaseDetail> findByCaseprogress(caseprogress progress);

    Optional<DcpuCaseDetail> findByLegalcase_Caseid(Long caseid);

    List<DcpuCaseDetail> findAllByLegalcase_CaseidOrderByCreatedatDesc(Long caseid);
}