package com.mscpcr.mscpcr.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "legalcase")
@Getter
@Setter
public class Legalcase {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Explicitly map to the 'id' column
    private Long caseid;  
    
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

    @OneToMany(mappedBy = "legalcase", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
private List<DcpuCaseDetail> dcpuCaseDetails = new ArrayList<>();

    /**
     * Adds a DcpuCaseDetail to this Legalcase.
     * Ensures the bidirectional relationship is maintained.
     */
    public void addDcpuCaseDetail(DcpuCaseDetail detail) {
        if (detail != null) {
            detail.setLegalcase(this); // Set owning side
            this.dcpuCaseDetails.add(detail); // Set inverse side
        }
    }

    public void removeDcpuCaseDetail(DcpuCaseDetail detail) {
        this.dcpuCaseDetails.remove(detail);
        detail.setLegalcase(null);
    }

    @PrePersist
    protected void onCreate() {
        if (caseuuid == null) {
            caseuuid = UUID.randomUUID().toString();
        }
    }
   

    public enum Gender {
        male, female, other
    }

    public enum Casestatus {
        dcpuprocessing, policeprocessing, courtprocessing, solved, closed
    }
}
