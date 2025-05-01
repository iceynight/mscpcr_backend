package com.mscpcr.mscpcr.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.service.AppUserService;


@Controller
public class UserController {
    
    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;
    
    public UserController(AppUserService userService,
                        PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                             @RequestParam String rawPassword) {
        AppUser user = new AppUser();
        user.setUsername(username);
        
        // Hash the password before saving
        user.setPasswordhash(passwordEncoder.encode(rawPassword));
        
        userService.createUser(user);
        return "redirect:/success";
    }
}