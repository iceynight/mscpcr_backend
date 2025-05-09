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
import com.mscpcr.mscpcr.entity.LegalCase;
import com.mscpcr.mscpcr.repository.LegalCaseRepository;
import com.mscpcr.mscpcr.service.AppUserService;

@Controller
@RequestMapping("/cases")
public class LegalCaseController {

    private static final Logger logger = LoggerFactory.getLogger(LegalCaseController.class);

    @Autowired
    private LegalCaseRepository legalCaseRepository;

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

        logger.info("Received form data: {}", caseForm);

        if (bindingResult.hasErrors()) {
            logger.error("Validation errors: {}", bindingResult.getAllErrors());
            return "dcpu-add-case";
        }

        if (principal == null) {
            logger.error("No authenticated user found.");
            model.addAttribute("error", "No authenticated user found.");
            return "dcpu-add-case";
        }

        try {
            // Fetch the logged-in user
            AppUser currentUser = appUserService.getUserByUsername(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Prepare LegalCase
            LegalCase legalCase = caseForm.getLegalCase();
            legalCase.setCreatedby(currentUser);
            legalCase.setCreatedat(LocalDateTime.now());
            legalCase.setUpdatedat(LocalDateTime.now());

            // Prepare DcpuCaseDetail
            DcpuCaseDetail dcpuCaseDetail = caseForm.getDcpuCaseDetail();
            dcpuCaseDetail.setActionbycwc(caseForm.getDcpuCaseDetail().getActionbycwc());
            dcpuCaseDetail.setCaseprogress(caseForm.getDcpuCaseDetail().getCaseprogress());
            dcpuCaseDetail.setCreatedby(currentUser);
            dcpuCaseDetail.setCreatedat(LocalDateTime.now());
            dcpuCaseDetail.setUpdatedat(LocalDateTime.now());

            // Set bidirectional relationship
            legalCase.setDcpuCaseDetail(dcpuCaseDetail);
            dcpuCaseDetail.setLegalcase(legalCase);

            // Save the LegalCase (cascade will save DcpuCaseDetail)
            legalCaseRepository.save(legalCase);

            logger.info("Case saved successfully with ID: {}", legalCase.getId());
            return "redirect:/dcpu-dashboard";

        } catch (Exception e) {
            logger.error("Error saving case", e);
            model.addAttribute("error", "Failed to save case: " + e.getMessage());
            return "dcpu-add-case";
        }
    }
}