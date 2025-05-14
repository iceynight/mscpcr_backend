package com.mscpcr.mscpcr.entity;

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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dcpucasedetail")
@Getter
@Setter
public class DcpuCaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dcpucaseid;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "legalcase_id", nullable = false)
    private Legalcase legalcase;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private dcpuaction actionbycwc;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private caseprogress caseprogress;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isforwardedtopolice = false;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean issolved = false;
    
    private LocalDateTime forwardedat;
    private LocalDateTime solvedat;
    
    @ManyToOne
    @JoinColumn(name = "forwardedby")
    private AppUser forwardedby;
    
    @ManyToOne
    @JoinColumn(name = "solvedby")
    private AppUser solvedby;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdat;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedat;
    
    @ManyToOne
    @JoinColumn(name = "createdby", nullable = false)
    private AppUser createdby;

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

    // Properly implemented methods (previously throwing UnsupportedOperationException)
    public void setLegalcase(Legalcase legalcase) {
        this.legalcase = legalcase;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public void setCreatedby(AppUser createdby) {
        this.createdby = createdby;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    // Helper method to establish the relationship
    public void associateWithLegalcase(Legalcase legalcase) {
        this.setLegalcase(legalcase);
        // Remove the following line as Legalcase does not have setDcpuCaseDetail method
        // if (legalcase != null) {
        //     legalcase.setDcpuCaseDetail(this);
        // }
    }
}