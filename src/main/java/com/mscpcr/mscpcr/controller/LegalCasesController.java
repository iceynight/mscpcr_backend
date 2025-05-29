package com.mscpcr.mscpcr.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mscpcr.mscpcr.service.LegalCasesService;


@RestController
@RequestMapping("/api/legalcase")
public class LegalCasesController {

    private final LegalCasesService legalCasesService;

    public LegalCasesController(LegalCasesService legalCasesService) {
        this.legalCasesService = legalCasesService;
    }

    @GetMapping("/currentstatuses")
    public List<String> getAllCurrentStatuses() {
        return legalCasesService.getAllCurrentStatuses();
    }
}
