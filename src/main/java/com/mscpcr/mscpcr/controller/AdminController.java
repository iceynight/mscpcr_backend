package com.mscpcr.mscpcr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // Mapping for the Admin Dashboard page
    @GetMapping("/admin-dashboard")
    public String dashboard() {
        return "admin-dashboard";  // Thymeleaf will look for admin-dashboard.html
    }

    // Mapping for the Create User page
    @GetMapping("/admin-dashboard/admin-create-user")
    public String createUserPage() {
        return "admin-create-user";  // This corresponds to admin-create-user.html
    }
}

