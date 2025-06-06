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
@Table(name = "appuser")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String passwordhash;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Usertype usertype;
    
    @ManyToOne
    @JoinColumn(name = "districtid")
    private District district;
    
    private boolean isactive = true;
    
    @CreationTimestamp
    private LocalDateTime createdat;
    
    @ManyToOne
    @JoinColumn(name = "createdby")
    private AppUser createdby;


    
    // Getters and setters
    public enum Usertype {
        ADMIN, DCPU, POLICE, COURT
    }
    
    public Usertype getUserType() { //custom getter for thymeleaf
        return usertype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public Usertype getUsertype() {
        return usertype;
    }

    public void setUsertype(Usertype usertype) {
        this.usertype = usertype;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public AppUser getCreatedby() {
        return createdby;
    }

    public void setCreatedby(AppUser createdby) {
        this.createdby = createdby;
    }

}

