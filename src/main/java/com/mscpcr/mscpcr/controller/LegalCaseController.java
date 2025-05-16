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

import jakarta.servlet.http.HttpSession;
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
                         Model model,
                         HttpSession session) {

        if (bindingResult.hasErrors()) {
            addEnumAttributes(model);
            return "dcpu-add-case";
        }

        try {
            AppUser currentUser = appUserService.getUserByUsername(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // 1. Create and populate the Legalcase
            Legalcase legalcase = createLegalcaseFromDTO(caseForm, currentUser);

            // 2. Create and populate the DcpuCaseDetail
            DcpuCaseDetail dcpuCaseDetail = createDcpuCaseDetailFromDTO(caseForm, currentUser);

            // 3. Handle any workflow transitions
            handleWorkflowTransitions(legalcase, dcpuCaseDetail, currentUser);

            // 4. Establish the bidirectional relationship
            legalcase.addDcpuCaseDetail(dcpuCaseDetail);

            // 5. Save only the Legalcase - the DcpuCaseDetail will cascade
            legalcaseRepository.save(legalcase);

            // Set notification in session to be displayed on police dashboard if transferred
            if (dcpuCaseDetail.getActionbycwc() == DcpuCaseDetail.dcpuaction.TRANSFERRED) {
                session.setAttribute("newCaseNotification",
                    "New case forwarded from DCPU of " + currentUser.getDistrict().getName());
            }

            return "redirect:/dcpu-dashboard";

        } catch (Exception e) {
            logger.error("Error saving case: {}", e.getMessage());
            model.addAttribute("error", "Failed to save case. Please try again.");
            addEnumAttributes(model);
            return "dcpu-add-case";
        }
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
}