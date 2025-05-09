package com.mscpcr.mscpcr.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "legalcase")
@Getter
@Setter
public class LegalCase {

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
    private String additionalinfo;
    
    @Enumerated(EnumType.STRING)
    private Casestatus currentstatus = Casestatus.dcpuprocessing;
    
    @ManyToOne
    @JoinColumn(name = "createdby", nullable = false)
    private AppUser createdby;
    
    @CreationTimestamp
    private LocalDateTime createdat;
    
    @UpdateTimestamp
    private LocalDateTime updatedat;
    
    private LocalDateTime solvedat;
    
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
    
    @OneToOne(mappedBy = "legalcase", cascade = CascadeType.ALL, orphanRemoval = true)
    private DcpuCaseDetail dcpuCaseDetail;

    @PrePersist
    protected void onCreate() {
        if (caseuuid == null) {
            caseuuid = UUID.randomUUID().toString();
        }
    }

    public void addDcpuCaseDetail(DcpuCaseDetail detail) {
        detail.setLegalcase(this);
        this.dcpuCaseDetail = detail;
    }

    public enum Gender {
        male, female, other
    }
    
    public enum Casestatus {
        dcpuprocessing, policeprocessing, courtprocessing, solved, closed
    }
}