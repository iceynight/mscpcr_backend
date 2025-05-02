package com.mscpcr.mscpcr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.repository.AppUserRepository;

@Controller
public class LoginController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        Optional<AppUser> optionalUser = appUserRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invalid username");
            return "redirect:/login";
        }

        AppUser user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPasswordhash())) {
            redirectAttributes.addFlashAttribute("error", "Invalid password");
            return "redirect:/login";
        }

        if (!captcha.equalsIgnoreCase(captchaToken)) {
            redirectAttributes.addFlashAttribute("error", "Captcha does not match");
            return "redirect:/login";
        }

        if (user.getUsertype().toString().equalsIgnoreCase("admin")) {
            return "redirect:/admin/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Access denied");
            return "redirect:/login";
        }
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin-dashboard";
    }
}