package com.mscpcr.mscpcr.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.entity.AppUser.Usertype;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findByUsertype(Usertype usertype);
    List<AppUser> findByDistrictId(Long districtId);
    List<AppUser> findByUsertypeAndDistrictId(Usertype usertype, Long districtId);
    boolean existsByUsername(String username);
    boolean existsByDistrictIdAndUsertype(Long districtId, Usertype usertype);
    
    boolean existsByDistrictIdAndUsertypeAndIdNot(Long districtId, Usertype usertype, Long id);
    
    List<AppUser> findByDistrictIdAndUsertype(Long districtId, Usertype usertype);
}
