package com.mscpcr.mscpcr.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.dcpucasedetail;

import java.util.List;
import java.util.Optional;

import com.mscpcr.mscpcr.entity.dcpucasedetail.caseprogress;
import com.mscpcr.mscpcr.entity.dcpucasedetail.dcpuaction;

@Repository
public interface dcpucasedetailRepository extends JpaRepository<dcpucasedetail, Long> {
    Optional<dcpucasedetail> findBylegalcaseId(Long caseId);
    List<dcpucasedetail> findByActionbycwc(dcpuaction action);
    List<dcpucasedetail> findByCaseprogress(caseprogress progress);
    List<dcpucasedetail> findByIsforwardedtopolice(boolean isForwarded);
    List<dcpucasedetail> findByForwardedbyId(Long userId);
}
