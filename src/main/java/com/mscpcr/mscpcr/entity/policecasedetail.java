package com.mscpcr.mscpcr.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "policecasedetail")
public class PoliceCaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "caseid", nullable = false)
    private Legalcase legalcase;
    
    @Column(nullable = false)
    private String policestation;
    
    @Column(nullable = false)
    private String firnumber;
    
    @Column(nullable = false)
    private String sectionoflaw;
    
    @Column(nullable = false)
    private LocalDate dateoffir;
    
    private boolean referredtocwc = false;
    
    private LocalDate medicalexaminationdate;
    
    private Integer timetochargesheet;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private policecasestatus casestatus;
    
    private boolean isforwardedtocourt = false;
    private boolean issolved = false;
    
    private LocalDateTime forwardedat;
    private LocalDateTime solvedat;
    
    @ManyToOne
    @JoinColumn(name = "forwardedby")
    private AppUser forwardedby;
    
    @ManyToOne
    @JoinColumn(name = "solvedby")
    private AppUser solvedby;
    
    @ManyToOne
    @JoinColumn(name = "updatedby", nullable = false)
    private AppUser updatedby;
    
    @UpdateTimestamp
    private LocalDateTime updatedat;
    
    
public enum policecasestatus {
    UNDER_INVESTIGATION,  // Case is still with police
    CHARGESHEET_ISSUED,   // Case is forwarded to court
    DISPOSED              // Case is solved and sent to admin
}
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Legalcase getlegalcase() {
        return legalcase;
    }

    public void setlegalcase(Legalcase legalcase) {
        this.legalcase = legalcase;
    }

    public String getPolicestation() {
        return policestation;
    }

    public void setPolicestation(String policestation) {
        this.policestation = policestation;
    }

    public String getFirnumber() {
        return firnumber;
    }

    public void setFirnumber(String firnumber) {
        this.firnumber = firnumber;
    }

    public String getSectionoflaw() {
        return sectionoflaw;
    }

    public void setSectionoflaw(String sectionoflaw) {
        this.sectionoflaw = sectionoflaw;
    }

    public LocalDate getDateoffir() {
        return dateoffir;
    }

    public void setDateoffir(LocalDate dateoffir) {
        this.dateoffir = dateoffir;
    }

    public boolean isReferredtocwc() {
        return referredtocwc;
    }

    public void setReferredtocwc(boolean referredtocwc) {
        this.referredtocwc = referredtocwc;
    }

    public LocalDate getMedicalexaminationdate() {
        return medicalexaminationdate;
    }

    public void setMedicalexaminationdate(LocalDate medicalexaminationdate) {
        this.medicalexaminationdate = medicalexaminationdate;
    }

    public Integer getTimetochargesheet() {
        return timetochargesheet;
    }

    public void setTimetochargesheet(Integer timetochargesheet) {
        this.timetochargesheet = timetochargesheet;
    }

    public policecasestatus getCasestatus() {
        return casestatus;
    }

    public void setCasestatus(policecasestatus casestatus) {
        this.casestatus = casestatus;
    }

    public boolean isIsforwardedtocourt() {
        return isforwardedtocourt;
    }

    public void setIsforwardedtocourt(boolean isforwardedtocourt) {
        this.isforwardedtocourt = isforwardedtocourt;
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

    public AppUser getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(AppUser updatedby) {
        this.updatedby = updatedby;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }
}

