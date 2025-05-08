package com.mscpcr.mscpcr.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dcpucasedetail")
public class DcpuCaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "caseid", nullable = false)
    private LegalCase legalcase;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private dcpuaction actionbycwc;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private caseprogress caseprogress;
    
    private boolean isforwardedtopolice = false;
    private boolean issolved = false;
    
    private LocalDateTime forwardedat;
    private LocalDateTime solvedat;
    
    @ManyToOne
    @JoinColumn(name = "forwardedby")
    private AppUser forwardedby;
    
    @ManyToOne
    @JoinColumn(name = "solvedby")
    private AppUser solvedby;

    public void setLegalCase(LegalCase legalCase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCreatedat(LocalDateTime now) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCreatedby(AppUser currentUser) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setUpdatedat(LocalDateTime now) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // Enum for DCPU actions
public enum dcpuaction {
    DISPOSED,           // Case is solved and sent to admin
    UNDER_SUPERVISION,  // Case remains with DCPU
    TRANSFERRED         // Case is forwarded to police
}

// Enum for case progress options
public enum caseprogress {
    SIR,
    Councelling,
    Medical_Exam,
    Mental_Assessment,
    Institutional_Care,
    NonInstitutional_Care,
    Sponsorship,
    Foster_Care,
    Aftercare,
    Adoption
}
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LegalCase getlegalcase() {
        return legalcase;
    }

    public void setlegalcase(LegalCase legalcase) {
        this.legalcase = legalcase;
    }

    public dcpuaction getActionbycwc() {
        return actionbycwc;
    }

    public void setActionbycwc(dcpuaction actionbycwc) {
        this.actionbycwc = actionbycwc;
    }

    public caseprogress getCaseprogress() {
        return caseprogress;
    }

    public void setCaseprogress(caseprogress caseprogress) {
        this.caseprogress = caseprogress;
    }

    public boolean isIsforwardedtopolice() {
        return isforwardedtopolice;
    }

    public void setIsforwardedtopolice(boolean isforwardedtopolice) {
        this.isforwardedtopolice = isforwardedtopolice;
    }

    public boolean isIssolved() {
        return issolved;
    }

    public void setIssolved(boolean issolved) {
        this.issolved = issolved;
    }

    public LocalDateTime getForwardedat() {
        return forwardedat;
    }

    public void setForwardedat(LocalDateTime forwardedat) {
        this.forwardedat = forwardedat;
    }

    public LocalDateTime getSolvedat() {
        return solvedat;
    }

    public void setSolvedat(LocalDateTime solvedat) {
        this.solvedat = solvedat;
    }

    public AppUser getForwardedby() {
        return forwardedby;
    }

    public void setForwardedby(AppUser forwardedby) {
        this.forwardedby = forwardedby;
    }

    public AppUser getSolvedby() {
        return solvedby;
    }

    public void setSolvedby(AppUser solvedby) {
        this.solvedby = solvedby;
    }
}

