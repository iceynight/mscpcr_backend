package com.mscpcr.mscpcr.repository;


import com.mscpcr.mscpcr.entity.appuser;
import com.mscpcr.mscpcr.entity.appuser.Usertype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface appuserRepository extends JpaRepository<appuser, Long> {
    Optional<appuser> findByUsername(String username);
    List<appuser> findByUsertype(Usertype usertype);
    List<appuser> findByDistrictId(Long districtId);
    List<appuser> findByUsertypeAndDistrictId(Usertype usertype, Long districtId);
    boolean existsByUsername(String username);
}
