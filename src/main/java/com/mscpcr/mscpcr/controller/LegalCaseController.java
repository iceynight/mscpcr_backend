package com.mscpcr.mscpcr.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.entity.District;
import com.mscpcr.mscpcr.entity.LegalCase;
import com.mscpcr.mscpcr.repository.DistrictRepository;
import com.mscpcr.mscpcr.repository.LegalCaseRepository;
import com.mscpcr.mscpcr.service.AppUserService;

@Controller
@RequestMapping("/cases")
public class LegalCaseController {

    @Autowired
    private LegalCaseRepository legalCaseRepository;

    @Autowired
    private AppUserService AppUserService;

    @Autowired
    private DistrictRepository districtRepository;

    @PostMapping("/add")
    public String addCase(@Validated @ModelAttribute LegalCase legalCase, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("districts", districtRepository.findAll());
            return "dcpu-add-case"; // return form with errors and districts
        }

        if (principal == null) {
            throw new IllegalStateException("No authenticated user found.");
        }

        AppUser currentUser = AppUserService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getName()));
        legalCase.setCreatedby(currentUser);
        legalCase.setCaseuuid(UUID.randomUUID().toString());
        legalCase.setCreatedat(LocalDateTime.now());
        legalCase.setUpdatedat(LocalDateTime.now());

        legalCaseRepository.save(legalCase);
        return "redirect:/dcpu-dashboard";
    }

    @GetMapping("/dcpu-add-case")
    public String showAddCaseForm(Model model) {
        model.addAttribute("legalCase", new LegalCase());
        List<District> districts = districtRepository.findAll();
        model.addAttribute("districts", districts);
        return "dcpu-add-case";
    }
}
