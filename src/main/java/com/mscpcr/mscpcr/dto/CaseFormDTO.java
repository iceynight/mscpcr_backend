package com.mscpcr.mscpcr.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.Legalcase;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CaseFormDTO {
    // Legalcase fields
    @NotBlank(message = "SL No is required")
    private String slno;

    @NotNull(message = "Date of complaint is required")
    private LocalDate dateofcomplaint;

    @NotNull(message = "Date of receipt is required")
    private LocalDate dateofreceipt;

    @NotBlank(message = "Complainant name is required")
    private String complainantname;

    @NotBlank(message = "Child name is required")
    private String childname;

    @NotNull(message = "Child age is required")
    @Min(value = 0, message = "Child age must be positive")
    private Integer childage;

    @NotNull(message = "Child gender is required")
    private Legalcase.Gender childgender;

    @NotBlank(message = "Case summary is required")
    private String casesummary;

    @NotNull(message = "Incident date/time is required")
    private LocalDateTime incidentdatetime;

    @NotBlank(message = "Incident place is required")
    private String incidentplace;

    private String accusedrelationship;
    private boolean hasdisability = false;
    private String disabilitytype;

    @NotBlank(message = "Case type is required")
    private String casetype;

    private String additionalinfo;

    // DcpuCaseDetail fields
    @NotNull(message = "Action by CWC is required")
    private DcpuCaseDetail.dcpuaction actionbycwc;

    @NotNull(message = "Case progress is required")
    private DcpuCaseDetail.caseprogress caseprogress;

    // Complete getters and setters
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

    public Legalcase.Gender getChildgender() {
        return childgender;
    }

    public void setChildgender(Legalcase.Gender childgender) {
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

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    public DcpuCaseDetail.dcpuaction getActionbycwc() {
        return actionbycwc;
    }

    public void setActionbycwc(DcpuCaseDetail.dcpuaction actionbycwc) {
        this.actionbycwc = actionbycwc;
    }

    public DcpuCaseDetail.caseprogress getCaseprogress() {
        return caseprogress;
    }

    public void setCaseprogress(DcpuCaseDetail.caseprogress caseprogress) {
        this.caseprogress = caseprogress;
    }
}