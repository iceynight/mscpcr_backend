package com.mscpcr.mscpcr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.entity.AppUser.Usertype;
import com.mscpcr.mscpcr.repository.AppUserRepository;
import com.mscpcr.mscpcr.repository.DistrictRepository;

@Service
@Transactional
public class AppUserService {

    private final AppUserRepository appuserRepository;
    private final DistrictRepository districtRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appuserRepository,
                          DistrictRepository districtRepository,
                          PasswordEncoder passwordEncoder) {
        this.appuserRepository = appuserRepository;
        this.districtRepository = districtRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser createUser(AppUser user) {
        if (usernameExists(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (user.getDistrict() != null &&
            districtUserTypeExists(user.getDistrict().getId(), user.getUsertype())) {
            throw new IllegalArgumentException("A user with type '" + user.getUsertype() +
                "' already exists in district: " + user.getDistrict().getName());
        }

        user.setPasswordhash(passwordEncoder.encode(user.getPasswordhash()));
        return appuserRepository.save(user);
    }

    public boolean validateCredentials(String username, String rawPassword) {
        Optional<AppUser> user = appuserRepository.findByUsername(username);
        return user.isPresent() &&
               passwordEncoder.matches(rawPassword, user.get().getPasswordhash());
    }

    public Optional<AppUser> getUserById(Long id) {
        return appuserRepository.findById(id);
    }

    public Optional<AppUser> getUserByUsername(String username) {
        return appuserRepository.findByUsername(username);
    }

    public List<AppUser> getUsersByType(Usertype userType) {
        return appuserRepository.findByUsertype(userType);
    }

    public List<AppUser> getUsersByDistrict(Long districtId) {
        return appuserRepository.findByDistrictId(districtId);
    }

    public List<AppUser> getUsersByDistrictAndType(Long districtId, Usertype userType) {
        return appuserRepository.findByDistrictIdAndUsertype(districtId, userType);
    }

    public AppUser updateUser(AppUser user) {
        AppUser existingUser = appuserRepository.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Prevent changing to an existing username
        if (!existingUser.getUsername().equals(user.getUsername()) &&
            usernameExists(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Check for unique (district, usertype) if changed
        if (user.getDistrict() != null &&
            (!user.getDistrict().getId().equals(existingUser.getDistrict().getId()) ||
             !user.getUsertype().equals(existingUser.getUsertype()))) {
            if (districtUserTypeExists(user.getDistrict().getId(), user.getUsertype())) {
                throw new IllegalArgumentException("A user with type '" + user.getUsertype() +
                    "' already exists in district: " + user.getDistrict().getName());
            }
        }

        // Update password if provided
        if (user.getPasswordhash() != null && !user.getPasswordhash().isEmpty()) {
            user.setPasswordhash(passwordEncoder.encode(user.getPasswordhash()));
        } else {
            user.setPasswordhash(existingUser.getPasswordhash());
        }

        return appuserRepository.save(user);
    }

    public void deleteUser(Long id) {
        appuserRepository.deleteById(id);
    }

    public boolean usernameExists(String username) {
        return appuserRepository.existsByUsername(username);
    }

    public boolean districtUserTypeExists(Long districtId, Usertype userType) {
        return appuserRepository.existsByDistrictIdAndUsertype(districtId, userType);
    }

    public boolean isUniqueDistrictUserTypeCombination(Long districtId, Usertype userType, Long excludeUserId) {
        if (excludeUserId == null) {
            return !districtUserTypeExists(districtId, userType);
        }
        return !appuserRepository.existsByDistrictIdAndUsertypeAndIdNot(districtId, userType, excludeUserId);
    }
}
