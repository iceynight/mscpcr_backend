package com.mscpcr.mscpcr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            @RequestParam String captchaToken,
            RedirectAttributes redirectAttributes) {

        if (!"admin".equals(username)) {
            redirectAttributes.addAttribute("error", "Invalid username");
            return "redirect:/login";
        }

        if (!"admin123".equals(password)) {
            redirectAttributes.addAttribute("error", "Invalid password");
            return "redirect:/login";
        }

        if (!captcha.equalsIgnoreCase(captchaToken)) {
            redirectAttributes.addAttribute("error", "Captcha does not match");
            return "redirect:/login";
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin-dashboard"; // Create admin-dashboard.html in templates/
    }
}
