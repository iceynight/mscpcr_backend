package com.mscpcr.mscpcr.controller;

import java.util.Map;

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
        Map<String, Long> stats = caseStatisticsService.getDashboardStatistics();

        model.addAttribute("totalCases", stats.getOrDefault("totalCases", 0L));
        model.addAttribute("totalCasesSolved", stats.getOrDefault("totalCasesSolved", 0L));
        model.addAttribute("totalCasesProcessing", stats.getOrDefault("totalCasesProcessing", 0L));
        model.addAttribute("casesInDCPU", stats.getOrDefault("casesInDCPU", 0L));
        model.addAttribute("processingInPolice", stats.getOrDefault("processingInPolice", 0L));
        model.addAttribute("processingInCourt", stats.getOrDefault("processingInCourt", 0L));

        return "admin-dashboard";
    }
}