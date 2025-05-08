package com.mscpcr.mscpcr.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mscpcr.mscpcr.dto.CaseFormWrapper;
import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.LegalCase;
import com.mscpcr.mscpcr.repository.DcpuCaseDetailRepository;
import com.mscpcr.mscpcr.repository.LegalCaseRepository;
import com.mscpcr.mscpcr.service.AppUserService;

@Controller
@RequestMapping("/cases")
public class LegalCaseController {

    @Autowired
    private LegalCaseRepository legalCaseRepository;

    @Autowired
    private DcpuCaseDetailRepository dcpuCaseDetailRepository;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/dcpu-add-case")
    public String showAddCaseForm(Model model) {
        // Load a blank form
        model.addAttribute("caseForm", new CaseFormWrapper());
        return "dcpu-add-case";
    }

    @PostMapping("/add")
    public String addCase(@Validated @ModelAttribute("caseForm") CaseFormWrapper caseForm, 
                          BindingResult bindingResult, 
                          Principal principal, 
                          Model model) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("caseForm", caseForm); // Retain the form data
            return "dcpu-add-case"; // Return form with errors
        }

        // Check if the Principal is null
        if (principal == null) {
            model.addAttribute("error", "No authenticated user found.");
            return "dcpu-add-case";
        }

        // Fetch the logged-in user
        AppUser currentUser = appUserService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found."));

        // Save LegalCase
        LegalCase legalCase = caseForm.getLegalCase();
        legalCase.setCreatedby(currentUser);
        legalCase.setCreatedat(LocalDateTime.now());
        legalCase.setUpdatedat(LocalDateTime.now());
        legalCaseRepository.save(legalCase);

        // Save DcpuCaseDetail
        DcpuCaseDetail dcpuCaseDetail = caseForm.getDcpuCaseDetail();
        dcpuCaseDetail.setLegalCase(legalCase); // Link LegalCase
        dcpuCaseDetail.setCreatedby(currentUser);
        dcpuCaseDetail.setForwardedby(currentUser); // Set forwardedby only if applicable
        dcpuCaseDetail.setSolvedby(null); // Set solvedby to null initially
        dcpuCaseDetail.setCreatedat(LocalDateTime.now());
        dcpuCaseDetail.setUpdatedat(LocalDateTime.now());
        dcpuCaseDetail.setForwardedat(null); // Set forwardedat to null initially
        dcpuCaseDetail.setSolvedat(null); // Set solvedat to null initially
        dcpuCaseDetailRepository.save(dcpuCaseDetail);

        // Redirect to dashboard
        return "redirect:/dcpu-dashboard";
    }
}