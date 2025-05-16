package com.mscpcr.mscpcr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/police")
public class PoliceDashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        Object notification = session.getAttribute("newCaseNotification");
        if (notification != null) {
            model.addAttribute("notification", notification);
            session.removeAttribute("newCaseNotification"); // Clear it after displaying
        }
        return "police-dashboard";
    }
}
