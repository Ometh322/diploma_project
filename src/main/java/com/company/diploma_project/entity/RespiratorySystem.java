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
@Table(name = "RESPIRATORY_SYSTEM")
@Entity
public class RespiratorySystem {
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

    @Column(name = "COUGH_NATURE")
    @Lob
    private String cough_nature;

    @Column(name = "COUGH_FREQUENCY")
    @Lob
    private String cough_frequency;

    @Column(name = "DETACHABLE")
    @Lob
    private String detachable;

    @Column(name = "RASPIRATORY_RATE")
    private Double raspiratory_rate;

    @Column(name = "SATURATION")
    @Lob
    private String saturation;

    @Column(name = "AUSCULTATION")
    @Lob
    private String auscultation;

    @Column(name = "PERCUSSION")
    @Lob
    private String percussion;

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

    public String getPercussion() {
        return percussion;
    }

    public void setPercussion(String percussion) {
        this.percussion = percussion;
    }

    public String getAuscultation() {
        return auscultation;
    }

    public void setAuscultation(String auscultation) {
        this.auscultation = auscultation;
    }

    public String getSaturation() {
        return saturation;
    }

    public void setSaturation(String saturation) {
        this.saturation = saturation;
    }

    public Double getRaspiratory_rate() {
        return raspiratory_rate;
    }

    public void setRaspiratory_rate(Double raspiratory_rate) {
        this.raspiratory_rate = raspiratory_rate;
    }

    public String getDetachable() {
        return detachable;
    }

    public void setDetachable(String detachable) {
        this.detachable = detachable;
    }

    public String getCough_frequency() {
        return cough_frequency;
    }

    public void setCough_frequency(String cough_frequency) {
        this.cough_frequency = cough_frequency;
    }

    public String getCough_nature() {
        return cough_nature;
    }

    public void setCough_nature(String cough_nature) {
        this.cough_nature = cough_nature;
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