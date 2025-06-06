package com.mscpcr.mscpcr.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static boolean checkPassword(String rawPassword, String passwordhash) {
        // Implemented the method to check password validity
        return matches(rawPassword, passwordhash);
    }
}