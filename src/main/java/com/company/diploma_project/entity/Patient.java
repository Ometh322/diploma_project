package com.company.diploma_project.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "PATIENT")
@Entity
public class Patient {
    @InstanceName
    @Column(name = "ID", nullable = false)
    @Id
    @JmixGeneratedValue
    private Long id;

    @Column(name = "DAYS")
    private Integer days;

    @Column(name = "MONTHS")
    private Integer months;

    @NotNull
    @Column(name = "YEARS")
    private Integer years;

    @Column(name = "CONCOMITANT_PATHOLOGY")
    @Lob
    private String concomitant_pathology;

    @Column(name = "EPIDEMIOLOGICAL_HISTORY")
    @Lob
    private String epidemiological_history;

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

    public String getEpidemiological_history() {
        return epidemiological_history;
    }

    public void setEpidemiological_history(String epidemiological_history) {
        this.epidemiological_history = epidemiological_history;
    }

    public String getConcomitant_pathology() {
        return concomitant_pathology;
    }

    public void setConcomitant_pathology(String concomitant_pathology) {
        this.concomitant_pathology = concomitant_pathology;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
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