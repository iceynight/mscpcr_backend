package com.mscpcr.mscpcr.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordhash()) // Use the hashed password
                .roles(user.getUsertype().toString().toUpperCase()) // Convert usertype to ROLE format
                .build();
    }
}
