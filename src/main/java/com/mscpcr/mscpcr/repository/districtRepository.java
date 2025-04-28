package com.mscpcr.mscpcr.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.district;

import java.util.List;
import java.util.Optional;

@Repository
public interface districtRepository extends JpaRepository<district, Long> {
    Optional<district> findByCode(String code);
    List<district> findByNameContainingIgnoreCase(String name);
    boolean existsByCode(String code);
}