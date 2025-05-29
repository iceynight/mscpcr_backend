package com.mscpcr.mscpcr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mscpcr.mscpcr.repository.LegalCasesRepository;

@Service
public class LegalCasesService {
    private final LegalCasesRepository legalCasesRepository;

    public LegalCasesService(LegalCasesRepository legalCasesRepository) {
        this.legalCasesRepository = legalCasesRepository;
    }

    public List<String> getAllCurrentStatuses() {
        return legalCasesRepository.findAllCurrentStatus();
    }
}
