package com.mscpcr.mscpcr.entity;

import java.math.BigDecimal;
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
@Table(name = "courtcasedetail")
public class courtcasedetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "caseid", nullable = false)
    private legalcase legalcase;
    
    @Column(nullable = false)
    private String statevs;
    
    @Column(nullable = false)
    private String casenumber;
    
    @Column(nullable = false)
    private String sectionoflaw;
    
    private LocalDate lasthearingdate;
    
    @Column(columnDefinition = "TEXT")
    private String hearingjudgement;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrialState trialstate = TrialState.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompensationStatus victimcompensation = CompensationStatus.NILL;
    
    private LocalDate awarddate;
    private LocalDate caseclosedate;
    
    private BigDecimal amountawarded;
    
    @ManyToOne
    @JoinColumn(name = "closedby")
    private appuser closedby;
    
    @ManyToOne
    @JoinColumn(name = "updatedby", nullable = false)
    private appuser updatedby;
    
    @UpdateTimestamp
    private LocalDateTime updatedat;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public legalcase getLegalcase() {
        return legalcase;
    }

    public void setLegalcase(legalcase legalcase) {
        this.legalcase = legalcase;
    }

    public String getStatevs() {
        return statevs;
    }

    public void setStatevs(String statevs) {
        this.statevs = statevs;
    }

    public String getCasenumber() {
        return casenumber;
    }

    public void setCasenumber(String casenumber) {
        this.casenumber = casenumber;
    }

    public String getSectionoflaw() {
        return sectionoflaw;
    }

    public void setSectionoflaw(String sectionoflaw) {
        this.sectionoflaw = sectionoflaw;
    }

    public LocalDate getLasthearingdate() {
        return lasthearingdate;
    }

    public void setLasthearingdate(LocalDate lasthearingdate) {
        this.lasthearingdate = lasthearingdate;
    }

    public String getHearingjudgement() {
        return hearingjudgement;
    }

    public void setHearingjudgement(String hearingjudgement) {
        this.hearingjudgement = hearingjudgement;
    }

    public TrialState getTrialstate() {
        return trialstate;
    }

    public void setTrialstate(TrialState trialstate) {
        this.trialstate = trialstate;
    }

    public CompensationStatus getVictimcompensation() {
        return victimcompensation;
    }

    public void setVictimcompensation(CompensationStatus victimcompensation) {
        this.victimcompensation = victimcompensation;
    }

    public LocalDate getAwarddate() {
        return awarddate;
    }

    public void setAwarddate(LocalDate awarddate) {
        this.awarddate = awarddate;
    }

    public LocalDate getCaseclosedate() {
        return caseclosedate;
    }

    public void setCaseclosedate(LocalDate caseclosedate) {
        this.caseclosedate = caseclosedate;
    }

    public BigDecimal getAmountawarded() {
        return amountawarded;
    }

    public void setAmountawarded(BigDecimal amountawarded) {
        this.amountawarded = amountawarded;
    }

    public appuser getClosedby() {
        return closedby;
    }

    public void setClosedby(appuser closedby) {
        this.closedby = closedby;
    }

    public appuser getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(appuser updatedby) {
        this.updatedby = updatedby;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    // Enums as static inner classes
    public enum TrialState {
        PENDING,    // P - Case is pending 
        ACQUITTED,  // Acquitted verdict
        CONVICTED   // Convicted verdict
    }

    public enum CompensationStatus {
        INTERIM,      // I - Interim compensation awarded
        FINAL,        // F - Final compensation awarded
        NILL   // NA - No compensation awarded
    }
}