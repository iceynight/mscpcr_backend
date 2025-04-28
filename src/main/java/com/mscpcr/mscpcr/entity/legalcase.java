package com.mscpcr.mscpcr.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
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

import jakarta.persistence.Table;

@Entity
@Table(name = "legalcase")
public class legalcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String caseuuid;
    
    @Column(nullable = false)
    private String slno;
    
    @Column(nullable = false)
    private LocalDate dateofcomplaint;
    
    @Column(nullable = false)
    private LocalDate dateofreceipt;
    
    @Column(nullable = false)
    private String complainantname;
    
    @Column(nullable = false)
    private String childname;
    
    @Column(nullable = false)
    private Integer childage;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender childgender;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String casesummary;
    
    @Column(nullable = false)
    private LocalDateTime incidentdatetime;
    
    @Column(nullable = false)
    private String incidentplace;
    
    private String accusedrelationship;
    
    private boolean hasdisability = false;
    
    private String disabilitytype;
    
    @Column(nullable = false)
    private String casetype;
    
    @Column(columnDefinition = "TEXT")
    private String actionbycwc;
    
    @Column(columnDefinition = "TEXT")
    private String additionalinfo;
    
    @Enumerated(EnumType.STRING)
    private Casestatus currentstatus = Casestatus.dcpuprocessing;
    
    @ManyToOne
    @JoinColumn(name = "createdby", nullable = false)
    private appuser createdby;
    
    @CreationTimestamp
    private LocalDateTime createdat;
    
    @UpdateTimestamp
    private LocalDateTime updatedat;
    
    private LocalDateTime solvedat;
    @ManyToOne
    @JoinColumn(name = "district_id") // matches your database column
    private district district;
    
    // Getters and setters
    public enum Gender {
        male, female, other
    }
    
    public enum Casestatus {
        dcpuprocessing, policeprocessing, courtprocessing, solved, closed
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseuuid() {
        return caseuuid;
    }

    public void setCaseuuid(String caseuuid) {
        this.caseuuid = caseuuid;
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public LocalDate getDateofcomplaint() {
        return dateofcomplaint;
    }

    public void setDateofcomplaint(LocalDate dateofcomplaint) {
        this.dateofcomplaint = dateofcomplaint;
    }

    public LocalDate getDateofreceipt() {
        return dateofreceipt;
    }

    public void setDateofreceipt(LocalDate dateofreceipt) {
        this.dateofreceipt = dateofreceipt;
    }

    public String getComplainantname() {
        return complainantname;
    }

    public void setComplainantname(String complainantname) {
        this.complainantname = complainantname;
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public Integer getChildage() {
        return childage;
    }

    public void setChildage(Integer childage) {
        this.childage = childage;
    }

    public Gender getChildgender() {
        return childgender;
    }

    public void setChildgender(Gender childgender) {
        this.childgender = childgender;
    }

    public String getCasesummary() {
        return casesummary;
    }

    public void setCasesummary(String casesummary) {
        this.casesummary = casesummary;
    }

    public LocalDateTime getIncidentdatetime() {
        return incidentdatetime;
    }

    public void setIncidentdatetime(LocalDateTime incidentdatetime) {
        this.incidentdatetime = incidentdatetime;
    }

    public String getIncidentplace() {
        return incidentplace;
    }

    public void setIncidentplace(String incidentplace) {
        this.incidentplace = incidentplace;
    }

    public String getAccusedrelationship() {
        return accusedrelationship;
    }

    public void setAccusedrelationship(String accusedrelationship) {
        this.accusedrelationship = accusedrelationship;
    }

    public boolean isHasdisability() {
        return hasdisability;
    }

    public void setHasdisability(boolean hasdisability) {
        this.hasdisability = hasdisability;
    }

    public String getDisabilitytype() {
        return disabilitytype;
    }

    public void setDisabilitytype(String disabilitytype) {
        this.disabilitytype = disabilitytype;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public String getActionbycwc() {
        return actionbycwc;
    }

    public void setActionbycwc(String actionbycwc) {
        this.actionbycwc = actionbycwc;
    }

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    public Casestatus getCurrentstatus() {
        return currentstatus;
    }

    public void setCurrentstatus(Casestatus currentstatus) {
        this.currentstatus = currentstatus;
    }

    public appuser getCreatedby() {
        return createdby;
    }

    public void setCreatedby(appuser createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public LocalDateTime getSolvedat() {
        return solvedat;
    }

    public void setSolvedat(LocalDateTime solvedat) {
        this.solvedat = solvedat;
    }
    public district getDistrict() {
        return district;
    }

    public void setDistrict(district district) {
        this.district = district;
    }

}

