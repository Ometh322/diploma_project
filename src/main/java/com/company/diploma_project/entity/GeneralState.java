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
@Table(name = "GENERAL_STATE")
@Entity
public class GeneralState {
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

    @Column(name = "TEMPERATURE")
    private Double temperature;

    @Column(name = "CONDITION_")
    @Lob
    private String condition;

    @Column(name = "VOMIT_DAYS")
    private Integer vomit_days;

    @Column(name = "VOMITING_AMOUNT")
    @Lob
    private String vomiting_amount;

    @Column(name = "FECES_TYPE")
    @Lob
    private String feces_type;

    @Column(name = "FECES_FREQUENTLY")
    @Lob
    private String feces_frequently;

    @Column(name = "DIURESIS_AMOUNT")
    @Lob
    private String diuresis_amount;

    @Column(name = "DIURESIS_FREQUENTLY")
    @Lob
    private String diuresis_frequently;

    @Column(name = "EDEMA")
    @Lob
    private String edema;

    @Column(name = "HEMORRHAGIC_MANIFESTATIONS")
    @Lob
    private String hemorrhagic_manifestations;

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

    public String getHemorrhagic_manifestations() {
        return hemorrhagic_manifestations;
    }

    public void setHemorrhagic_manifestations(String hemorrhagic_manifestations) {
        this.hemorrhagic_manifestations = hemorrhagic_manifestations;
    }

    public String getEdema() {
        return edema;
    }

    public void setEdema(String edema) {
        this.edema = edema;
    }

    public String getDiuresis_frequently() {
        return diuresis_frequently;
    }

    public void setDiuresis_frequently(String diuresis_frequently) {
        this.diuresis_frequently = diuresis_frequently;
    }

    public String getDiuresis_amount() {
        return diuresis_amount;
    }

    public void setDiuresis_amount(String diuresis_amount) {
        this.diuresis_amount = diuresis_amount;
    }

    public String getFeces_frequently() {
        return feces_frequently;
    }

    public void setFeces_frequently(String feces_frequently) {
        this.feces_frequently = feces_frequently;
    }

    public String getFeces_type() {
        return feces_type;
    }

    public void setFeces_type(String feces_type) {
        this.feces_type = feces_type;
    }

    public String getVomiting_amount() {
        return vomiting_amount;
    }

    public void setVomiting_amount(String vomiting_amount) {
        this.vomiting_amount = vomiting_amount;
    }

    public Integer getVomit_days() {
        return vomit_days;
    }

    public void setVomit_days(Integer vomit_days) {
        this.vomit_days = vomit_days;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
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