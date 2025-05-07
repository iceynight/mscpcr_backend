package com.mscpcr.mscpcr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findByCode(String code);
    List<District> findByNameContainingIgnoreCase(String name);
    boolean existsByCode(String code);
}