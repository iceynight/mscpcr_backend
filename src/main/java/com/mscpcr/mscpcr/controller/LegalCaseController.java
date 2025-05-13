package com.mscpcr.mscpcr.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.mscpcr.mscpcr.entity.Legalcase;
import com.mscpcr.mscpcr.repository.LegalcaseRepository;
import com.mscpcr.mscpcr.service.AppUserService;

@Controller
@RequestMapping("/cases")
public class LegalcaseController {

    private static final Logger logger = LoggerFactory.getLogger(LegalcaseController.class);

    @Autowired
    private LegalcaseRepository legalcaseRepository;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/dcpu-add-case")
    public String showAddCaseForm(Model model) {
        model.addAttribute("caseForm", new CaseFormWrapper());
        return "dcpu-add-case";
    }

    @PostMapping("/add")
    @Transactional
    public String addCase(@Validated @ModelAttribute("caseForm") CaseFormWrapper caseForm,
                          BindingResult bindingResult,
                          Principal principal,
                          Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("caseForm", caseForm);
            return "dcpu-add-case";
        }

        if (principal == null) {
            model.addAttribute("error", "No authenticated user found.");
            return "dcpu-add-case";
        }

        // Retrieve the current user
        AppUser currentUser = appUserService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found."));

        Legalcase legalcase = caseForm.getLegalcase();
        DcpuCaseDetail dcpuCaseDetail = caseForm.getDcpuCaseDetail();

        // Set auditing fields for Legalcase
        legalcase.setCreatedby(currentUser);
        legalcase.setCreatedat(LocalDateTime.now());
        legalcase.setUpdatedat(LocalDateTime.now());

        // Set auditing fields for DcpuCaseDetail
        dcpuCaseDetail.setCreatedby(currentUser);
        dcpuCaseDetail.setCreatedat(LocalDateTime.now());
        dcpuCaseDetail.setUpdatedat(LocalDateTime.now());

        // Add DcpuCaseDetail to Legalcase using the helper method
        legalcase.addDcpuCaseDetail(dcpuCaseDetail);

        // Save the Legalcase (cascade will save DcpuCaseDetail as well)
        legalcaseRepository.save(legalcase);

        // Redirect to DCPU dashboard
        return "redirect:/dcpu-dashboard";
    }
}