package com.mscpcr.mscpcr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mscpcr.mscpcr.service.CaseStatisticsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    
    private final CaseStatisticsService caseStatisticsService;

    @Autowired
    public AdminDashboardController(CaseStatisticsService caseStatisticsService) {
        this.caseStatisticsService = caseStatisticsService;
    }

    @GetMapping("/home")
    public String showDashboard(Model model, HttpSession session) {
        // Get all statistics in one call
        model.addAllAttributes(caseStatisticsService.getDashboardStatistics());
        
        // Handle notifications
        if (session.getAttribute("newCaseNotification") != null) {
            model.addAttribute("notification", session.getAttribute("newCaseNotification"));
            session.removeAttribute("newCaseNotification");
        }
        
        return "admin-dashboard";
    }
}