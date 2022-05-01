package com.company.diploma_project.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "THERAPY")
@Entity
public class Therapy {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @InstanceName
    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "PATIENT_ID", nullable = false, unique = true)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;

    @Column(name = "GENERAL")
    @Lob
    private String general;

    @Column(name = "GENERAL_DAY")
    private Integer general_day;

    @Column(name = "OXYGEN_THERAPY")
    @Lob
    private String oxygen_therapy;

    @Column(name = "OXYGEN_THERAPY_DAY")
    private Integer oxygen_therapy_day;

    @Column(name = "ANTIBACTERIAL")
    @Lob
    private String antibacterial;

    @Column(name = "ANTIBACTERIAL_DAY")
    private Integer antibacterial_day;

    @Column(name = "GKS")
    @Lob
    private String gks;

    @Column(name = "GKS_DAY")
    private Integer gks_day;

    @Column(name = "ANTICOAGULANTS")
    @Lob
    private String anticoagulants;

    @Column(name = "ANTICOAGULANTS_DAY")
    private Integer anticoagulants_day;

    @Column(name = "INTENSIVE_CARE")
    private Boolean intensive_care;

    @Column(name = "INTENSIVE_CARE_DAY")
    private Integer intensive_care_day;

    @Column(name = "OTHER_THERAPY")
    @Lob
    private String other_therapy;

    @Column(name = "OTHER_THERAPY_DAY")
    private Integer other_therapy_day;

    @JmixGeneratedValue
    @Column(name = "UUID")
    private UUID uuid;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    public Integer getOther_therapy_day() {
        return other_therapy_day;
    }

    public void setOther_therapy_day(Integer other_therapy_day) {
        this.other_therapy_day = other_therapy_day;
    }

    public String getOther_therapy() {
        return other_therapy;
    }

    public void setOther_therapy(String other_therapy) {
        this.other_therapy = other_therapy;
    }

    public Integer getIntensive_care_day() {
        return intensive_care_day;
    }

    public void setIntensive_care_day(Integer intensive_care_day) {
        this.intensive_care_day = intensive_care_day;
    }

    public Boolean getIntensive_care() {
        return intensive_care;
    }

    public void setIntensive_care(Boolean intensive_care) {
        this.intensive_care = intensive_care;
    }

    public Integer getAnticoagulants_day() {
        return anticoagulants_day;
    }

    public void setAnticoagulants_day(Integer anticoagulants_day) {
        this.anticoagulants_day = anticoagulants_day;
    }

    public String getAnticoagulants() {
        return anticoagulants;
    }

    public void setAnticoagulants(String anticoagulants) {
        this.anticoagulants = anticoagulants;
    }

    public Integer getGks_day() {
        return gks_day;
    }

    public void setGks_day(Integer gks_day) {
        this.gks_day = gks_day;
    }

    public String getGks() {
        return gks;
    }

    public void setGks(String gks) {
        this.gks = gks;
    }

    public Integer getAntibacterial_day() {
        return antibacterial_day;
    }

    public void setAntibacterial_day(Integer antibacterial_day) {
        this.antibacterial_day = antibacterial_day;
    }

    public String getAntibacterial() {
        return antibacterial;
    }

    public void setAntibacterial(String antibacterial) {
        this.antibacterial = antibacterial;
    }

    public Integer getOxygen_therapy_day() {
        return oxygen_therapy_day;
    }

    public void setOxygen_therapy_day(Integer oxygen_therapy_day) {
        this.oxygen_therapy_day = oxygen_therapy_day;
    }

    public String getOxygen_therapy() {
        return oxygen_therapy;
    }

    public void setOxygen_therapy(String oxygen_therapy) {
        this.oxygen_therapy = oxygen_therapy;
    }

    public Integer getGeneral_day() {
        return general_day;
    }

    public void setGeneral_day(Integer general_day) {
        this.general_day = general_day;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}