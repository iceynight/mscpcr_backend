package com.mscpcr.mscpcr.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "casehistory")
public class CaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "caseid", nullable = false)
    private LegalCase legalcase;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Actiontype actiontype;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Fromstage fromstage;
    
    @Enumerated(EnumType.STRING)
    private Tostage tostage;
    
    @ManyToOne
    @JoinColumn(name = "actionby", nullable = false)
    private AppUser actionby;
    
    @CreationTimestamp
    private LocalDateTime actionat;
    
    @Column(columnDefinition = "TEXT")
    private String notes;

    public enum Actiontype {
        created, updated, forwarded, statuschange, solved
    }
    
    public enum Fromstage {
        dcpu, police, court
    }
    
    public enum Tostage {
        dcpu, police, court, solved
    
    
    }

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

    public Actiontype getActiontype() {
        return actiontype;
    }

    public void setActiontype(Actiontype actiontype) {
        this.actiontype = actiontype;
    }

    public Fromstage getFromstage() {
        return fromstage;
    }

    public void setFromstage(Fromstage fromstage) {
        this.fromstage = fromstage;
    }

    public Tostage getTostage() {
        return tostage;
    }

    public void setTostage(Tostage tostage) {
        this.tostage = tostage;
    }

    public AppUser getActionby() {
        return actionby;
    }

    public void setActionby(AppUser actionby) {
        this.actionby = actionby;
    }

    public LocalDateTime getActionat() {
        return actionat;
    }

    public void setActionat(LocalDateTime actionat) {
        this.actionat = actionat;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    // Getters and setters
}


