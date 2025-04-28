package com.mscpcr.mscpcr.service;


import com.mscpcr.mscpcr.entity.appuser;
import com.mscpcr.mscpcr.entity.appuser.Usertype;
import com.mscpcr.mscpcr.repository.appuserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class appuserService {
    private final appuserRepository appuserRepository;

    public appuserService(appuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }

    public appuser createUser(appuser user) {
        // Password is already hashed before reaching service (handled in controller)
        return appuserRepository.save(user);
    }

    public Optional<appuser> getUserById(Long id) {
        return appuserRepository.findById(id);
    }

    public Optional<appuser> getUserByUsername(String username) {
        return appuserRepository.findByUsername(username);
    }

    public List<appuser> getUsersByType(Usertype userType) {
        return appuserRepository.findByUsertype(userType);
    }

    public List<appuser> getUsersByDistrict(Long districtId) {
        return appuserRepository.findByDistrictId(districtId);
    }

    public appuser updateUser(appuser user) {
        return appuserRepository.save(user);
    }

    public void deleteUser(Long id) {
        appuserRepository.deleteById(id);
    }

    public boolean usernameExists(String username) {
        return appuserRepository.existsByUsername(username);
    }
}