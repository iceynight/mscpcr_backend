package com.mscpcr.mscpcr.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.LegalCases;

@Repository
public interface LegalCasesRepository extends JpaRepository<LegalCases, Long> {

    @Query("SELECT lc.currentStatus FROM LegalCases lc")
    List<String> findAllCurrentStatus();
}
