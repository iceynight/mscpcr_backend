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
        // Validate username uniqueness
        if (usernameExists(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Validate district-userType combination
        if (user.getDistrict() != null &&
            districtUserTypeExists(user.getDistrict().getId(), user.getUsertype())) {
            throw new IllegalArgumentException(user.getUsertype() + " user already exists for this district");
        }

        // Hash password before saving
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
        // Prevent changing to existing username
        Optional<AppUser> existingUser = appuserRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // If password was changed, hash the new one
        if (user.getPasswordhash() != null && !user.getPasswordhash().isEmpty()) {
            String currentHash = appuserRepository.findById(user.getId())
                    .map(AppUser::getPasswordhash)
                    .orElse("");

            if (!passwordEncoder.matches(user.getPasswordhash(), currentHash)) {
                user.setPasswordhash(passwordEncoder.encode(user.getPasswordhash()));
            }
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
