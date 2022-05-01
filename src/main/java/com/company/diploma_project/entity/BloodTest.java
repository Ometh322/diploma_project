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
@Table(name = "BLOOD_TEST")
@Entity
public class BloodTest {
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

    @Column(name = "CRB_1")
    private Integer crb_1;

    @Column(name = "CRB_2")
    private Double crb_2;

    @Column(name = "PROCALCITONIN")
    @Lob
    private String procalcitonin;

    @Column(name = "RBC")
    private Double rbc;

    @Column(name = "HGB")
    private Double hgb;

    @Column(name = "HTC")
    private Double htc;

    @Column(name = "MCV")
    private Double mcv;

    @Column(name = "MCH")
    private Double mch;

    @Column(name = "MCHC")
    private Double mchc;

    @Column(name = "RDW")
    private Double rdw;

    @Column(name = "PLT")
    private Double plt;

    @Column(name = "MPV")
    private Double mpv;

    @Column(name = "PTC")
    private Double ptc;

    @Column(name = "WBC_1")
    private Double wbc_1;

    @Column(name = "WBC_2")
    private Double wbc_2;

    @Column(name = "NEU_PERCENT_1")
    private Double neu_percent_1;

    @Column(name = "NEU_PERCENT_2")
    private Double neu_percent_2;

    @Column(name = "NEU_ABS")
    private Double neu_abs;

    @Column(name = "LYMP_PERCENT_1")
    private Double lymp_percent_1;

    @Column(name = "LYMP_PERCENT_2")
    private Double lymp_percent_2;

    @Column(name = "LYMP_ABS_1")
    private Double lymp_abs_1;

    @Column(name = "LYMP_ABS_2")
    private Double lymp_abs_2;

    @Column(name = "MONO_PERCENT")
    private Double mono_percent;

    @Column(name = "MONO_ABS")
    private Double mono_abs;

    @Column(name = "EO_PERCENT_1")
    private Double eo_percent_1;

    @Column(name = "EO_PERCENT_2")
    private Double eo_percent_2;

    @Column(name = "EO_ABS_1")
    private Double eo_abs_1;

    @Column(name = "EO_ABS_2")
    private Double eo_abs_2;

    @Column(name = "BASO_PERCENT_1")
    private Double baso_percent_1;

    @Column(name = "BASO_PECENT_2")
    private Double baso_pecent_2;

    @Column(name = "BASO_ABS")
    private Double baso_abs;

    @Column(name = "SOE")
    private Double soe;

    @Column(name = "SOE_2")
    private Double soe_2;

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

    public void setHgb(Double hgb) {
        this.hgb = hgb;
    }

    public Double getHgb() {
        return hgb;
    }

    public void setLymp_abs_1(Double lymp_abs_1) {
        this.lymp_abs_1 = lymp_abs_1;
    }

    public Double getLymp_abs_1() {
        return lymp_abs_1;
    }

    public Double getSoe_2() {
        return soe_2;
    }

    public void setSoe_2(Double soe_2) {
        this.soe_2 = soe_2;
    }

    public Double getSoe() {
        return soe;
    }

    public void setSoe(Double soe) {
        this.soe = soe;
    }

    public Double getBaso_abs() {
        return baso_abs;
    }

    public void setBaso_abs(Double baso_abs) {
        this.baso_abs = baso_abs;
    }

    public Double getBaso_pecent_2() {
        return baso_pecent_2;
    }

    public void setBaso_pecent_2(Double baso_pecent_2) {
        this.baso_pecent_2 = baso_pecent_2;
    }

    public Double getBaso_percent_1() {
        return baso_percent_1;
    }

    public void setBaso_percent_1(Double baso_percent_1) {
        this.baso_percent_1 = baso_percent_1;
    }

    public Double getEo_abs_2() {
        return eo_abs_2;
    }

    public void setEo_abs_2(Double eo_abs_2) {
        this.eo_abs_2 = eo_abs_2;
    }

    public Double getEo_abs_1() {
        return eo_abs_1;
    }

    public void setEo_abs_1(Double eo_abs_1) {
        this.eo_abs_1 = eo_abs_1;
    }

    public Double getEo_percent_2() {
        return eo_percent_2;
    }

    public void setEo_percent_2(Double eo_percent_2) {
        this.eo_percent_2 = eo_percent_2;
    }

    public Double getEo_percent_1() {
        return eo_percent_1;
    }

    public void setEo_percent_1(Double eo_percent_1) {
        this.eo_percent_1 = eo_percent_1;
    }

    public Double getMono_abs() {
        return mono_abs;
    }

    public void setMono_abs(Double mono_abs) {
        this.mono_abs = mono_abs;
    }

    public Double getMono_percent() {
        return mono_percent;
    }

    public void setMono_percent(Double mono_percent) {
        this.mono_percent = mono_percent;
    }

    public Double getLymp_abs_2() {
        return lymp_abs_2;
    }

    public void setLymp_abs_2(Double lymp_abs_2) {
        this.lymp_abs_2 = lymp_abs_2;
    }

    public Double getLymp_percent_2() {
        return lymp_percent_2;
    }

    public void setLymp_percent_2(Double lymp_percent_2) {
        this.lymp_percent_2 = lymp_percent_2;
    }

    public Double getLymp_percent_1() {
        return lymp_percent_1;
    }

    public void setLymp_percent_1(Double lymp_percent_1) {
        this.lymp_percent_1 = lymp_percent_1;
    }

    public Double getNeu_abs() {
        return neu_abs;
    }

    public void setNeu_abs(Double neu_abs) {
        this.neu_abs = neu_abs;
    }

    public Double getNeu_percent_2() {
        return neu_percent_2;
    }

    public void setNeu_percent_2(Double neu_percent_2) {
        this.neu_percent_2 = neu_percent_2;
    }

    public Double getNeu_percent_1() {
        return neu_percent_1;
    }

    public void setNeu_percent_1(Double neu_percent_1) {
        this.neu_percent_1 = neu_percent_1;
    }

    public Double getWbc_2() {
        return wbc_2;
    }

    public void setWbc_2(Double wbc_2) {
        this.wbc_2 = wbc_2;
    }

    public Double getWbc_1() {
        return wbc_1;
    }

    public void setWbc_1(Double wbc_1) {
        this.wbc_1 = wbc_1;
    }

    public Double getPtc() {
        return ptc;
    }

    public void setPtc(Double ptc) {
        this.ptc = ptc;
    }

    public Double getMpv() {
        return mpv;
    }

    public void setMpv(Double mpv) {
        this.mpv = mpv;
    }

    public Double getPlt() {
        return plt;
    }

    public void setPlt(Double plt) {
        this.plt = plt;
    }

    public Double getRdw() {
        return rdw;
    }

    public void setRdw(Double rdw) {
        this.rdw = rdw;
    }

    public Double getMchc() {
        return mchc;
    }

    public void setMchc(Double mchc) {
        this.mchc = mchc;
    }

    public Double getMch() {
        return mch;
    }

    public void setMch(Double mch) {
        this.mch = mch;
    }

    public Double getMcv() {
        return mcv;
    }

    public void setMcv(Double mcv) {
        this.mcv = mcv;
    }

    public Double getHtc() {
        return htc;
    }

    public void setHtc(Double htc) {
        this.htc = htc;
    }

    public Double getRbc() {
        return rbc;
    }

    public void setRbc(Double rbc) {
        this.rbc = rbc;
    }

    public String getProcalcitonin() {
        return procalcitonin;
    }

    public void setProcalcitonin(String procalcitonin) {
        this.procalcitonin = procalcitonin;
    }

    public Double getCrb_2() {
        return crb_2;
    }

    public void setCrb_2(Double crb_2) {
        this.crb_2 = crb_2;
    }

    public Integer getCrb_1() {
        return crb_1;
    }

    public void setCrb_1(Integer crb_1) {
        this.crb_1 = crb_1;
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