package com.mscpcr.mscpcr.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/dcpu-dashboard")
    public String dcpuDashboard(Model model) {
        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username of the logged-in user
            AppUser user = userService.getUserByUsername(username).orElse(null); // Fetch user details from the database
            model.addAttribute("user", user); // Add user to the model
        } else {
            model.addAttribute("user", null); // Add null if no user is authenticated
        }
        return "dcpu-dashboard";
    }
    @GetMapping("/police-dashboard")
    public String policeDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            AppUser user = userService.getUserByUsername(username).orElse(null);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }
        return "police-dashboard";
    }
    @GetMapping("/court-dashboard")
    public String courtDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            AppUser user = userService.getUserByUsername(username).orElse(null);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }
        return "court-dashboard";
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