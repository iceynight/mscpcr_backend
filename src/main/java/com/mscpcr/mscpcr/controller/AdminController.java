package com.mscpcr.mscpcr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @GetMapping("/admin-dashboard")
    public String dashboard() {
        return "admin-dashboard";
    }
    

    
    @GetMapping("/admin/create-user")
    public String createUserPage() {
        return "admin-create-user"; 
    }
    @PostMapping("/admin/create-user")
public String registerUser(
    @RequestParam String district,
    @RequestParam String userType,
    @RequestParam String username,
    @RequestParam String password,
    RedirectAttributes redirectAttributes) {

    // TODO: Save user logic here

    redirectAttributes.addFlashAttribute("message", "User registered successfully!");
    return "redirect:/admin-dashboard";
}

}

