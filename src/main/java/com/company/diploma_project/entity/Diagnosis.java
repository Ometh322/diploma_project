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
@Table(name = "DIAGNOSIS")
@Entity
public class Diagnosis {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @NotNull
    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "PATIENT_ID", nullable = false, unique = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @InstanceName
    private Patient patient;

    @Column(name = "DAY_OF_DIAGNOSIS")
    private Integer day_of_diagnosis;

    @Column(name = "CONFIRMATION")
    @Lob
    private String confirmation;

    @Column(name = "DIAGNOSIS")
    @Lob
    private String diagnosis;

    @Column(name = "COMPLICATIONS")
    @Lob
    private String complications;

    @Column(name = "TERMS_OF_HOSPITALIZATION")
    private Integer terms_of_hospitalization;

    @Column(name = "PNEUMONIA", nullable = false)
    @NotNull
    private Boolean pneumonia = false;

    @Column(name = "D_DIMER")
    @Lob
    private String d_dimer;

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

    public String getD_dimer() {
        return d_dimer;
    }

    public void setD_dimer(String d_dimer) {
        this.d_dimer = d_dimer;
    }

    public Boolean getPneumonia() {
        return pneumonia;
    }

    public void setPneumonia(Boolean pneumonia) {
        this.pneumonia = pneumonia;
    }

    public Integer getTerms_of_hospitalization() {
        return terms_of_hospitalization;
    }

    public void setTerms_of_hospitalization(Integer terms_of_hospitalization) {
        this.terms_of_hospitalization = terms_of_hospitalization;
    }

    public String getComplications() {
        return complications;
    }

    public void setComplications(String complications) {
        this.complications = complications;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public Integer getDay_of_diagnosis() {
        return day_of_diagnosis;
    }

    public void setDay_of_diagnosis(Integer day_of_diagnosis) {
        this.day_of_diagnosis = day_of_diagnosis;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
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