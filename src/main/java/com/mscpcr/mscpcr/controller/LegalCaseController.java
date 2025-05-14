package com.mscpcr.mscpcr.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; // Add this import
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mscpcr.mscpcr.dto.CaseFormDTO;
import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.Legalcase;
import com.mscpcr.mscpcr.repository.DcpuCaseDetailRepository;
import com.mscpcr.mscpcr.repository.DistrictRepository;
import com.mscpcr.mscpcr.repository.LegalcaseRepository;
import com.mscpcr.mscpcr.service.AppUserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cases")
public class LegalcaseController {
    private static final Logger logger = LoggerFactory.getLogger(LegalcaseController.class);

    private final LegalcaseRepository legalcaseRepository;
    private final DcpuCaseDetailRepository dcpuCaseDetailRepository;
    private final AppUserService appUserService;
    private final DistrictRepository districtRepository;

    @Autowired
    public LegalcaseController(LegalcaseRepository legalcaseRepository,
                             DcpuCaseDetailRepository dcpuCaseDetailRepository,
                             AppUserService appUserService,
                             DistrictRepository districtRepository) {
        this.legalcaseRepository = legalcaseRepository;
        this.dcpuCaseDetailRepository = dcpuCaseDetailRepository;
        this.appUserService = appUserService;
        this.districtRepository = districtRepository;
    }

    @GetMapping("/dcpu-add-case")
    public String showAddCaseForm(Model model) {
        model.addAttribute("caseForm", new CaseFormDTO());
        addEnumAttributes(model);
        return "dcpu-add-case";
    }

    @PostMapping("/add")
    @Transactional
    public String addCase(@Valid @ModelAttribute("caseForm") CaseFormDTO caseForm,
                         BindingResult bindingResult,
                         Principal principal,
                         Model model) {
        
        if (bindingResult.hasErrors()) {
            addEnumAttributes(model);
            return "dcpu-add-case";
        }

        AppUser currentUser = appUserService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Legalcase legalcase = createLegalcaseFromDTO(caseForm, currentUser);
        legalcase = legalcaseRepository.save(legalcase); // Save legalcase first

        DcpuCaseDetail dcpuCaseDetail = createDcpuCaseDetailFromDTO(caseForm, currentUser);
        dcpuCaseDetail.setLegalcase(legalcase); // Set legalcase to detail
        
        handleWorkflowTransitions(legalcase, dcpuCaseDetail, currentUser);
        saveCaseWithDetails(legalcase, dcpuCaseDetail);
        
        return "redirect:/dcpu-dashboard";
    }

    private void addEnumAttributes(Model model) {
        model.addAttribute("genders", Legalcase.Gender.values());
        model.addAttribute("actions", DcpuCaseDetail.dcpuaction.values());
        model.addAttribute("progressOptions", DcpuCaseDetail.caseprogress.values());
    }

    private Legalcase createLegalcaseFromDTO(CaseFormDTO caseForm, AppUser currentUser) {
        Legalcase legalcase = new Legalcase();
        legalcase.setSlno(caseForm.getSlno());
        legalcase.setDateofcomplaint(caseForm.getDateofcomplaint());
        legalcase.setDateofreceipt(caseForm.getDateofreceipt());
        legalcase.setComplainantname(caseForm.getComplainantname());
        legalcase.setChildname(caseForm.getChildname());
        legalcase.setChildage(caseForm.getChildage());
        legalcase.setChildgender(caseForm.getChildgender());
        legalcase.setCasesummary(caseForm.getCasesummary());
        legalcase.setIncidentdatetime(caseForm.getIncidentdatetime());
        legalcase.setIncidentplace(caseForm.getIncidentplace());
        legalcase.setAccusedrelationship(caseForm.getAccusedrelationship());
        legalcase.setHasdisability(caseForm.isHasdisability());
        legalcase.setDisabilitytype(caseForm.getDisabilitytype());
        legalcase.setCasetype(caseForm.getCasetype());
        legalcase.setAdditionalinfo(caseForm.getAdditionalinfo());
        legalcase.setCreatedby(currentUser);
        legalcase.setDistrict(currentUser.getDistrict());
        legalcase.setCurrentstatus(Legalcase.Casestatus.dcpuprocessing);
        return legalcase;
    }

    private DcpuCaseDetail createDcpuCaseDetailFromDTO(CaseFormDTO caseForm, AppUser currentUser) {
        DcpuCaseDetail detail = new DcpuCaseDetail();
        detail.setActionbycwc(caseForm.getActionbycwc());
        detail.setCaseprogress(caseForm.getCaseprogress());
        detail.setCreatedby(currentUser);
        return detail;
    }

    private void handleWorkflowTransitions(Legalcase legalcase, DcpuCaseDetail detail, AppUser currentUser) {
        if (detail.getActionbycwc() == DcpuCaseDetail.dcpuaction.TRANSFERRED) {
            detail.setIsforwardedtopolice(true);
            detail.setForwardedat(LocalDateTime.now());
            detail.setForwardedby(currentUser);
            legalcase.setCurrentstatus(Legalcase.Casestatus.policeprocessing);
        } else if (detail.getActionbycwc() == DcpuCaseDetail.dcpuaction.DISPOSED) {
            detail.setIssolved(true);
            detail.setSolvedat(LocalDateTime.now());
            detail.setSolvedby(currentUser);
            legalcase.setCurrentstatus(Legalcase.Casestatus.solved);
            legalcase.setSolvedat(LocalDateTime.now());
        }
    }

    private void saveCaseWithDetails(Legalcase legalcase, DcpuCaseDetail detail) {
        dcpuCaseDetailRepository.save(detail);
        legalcase.addDcpuCaseDetail(detail);
    }
}