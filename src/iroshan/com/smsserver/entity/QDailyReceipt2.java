/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Iroshan
 */
@Entity
@Table(name = "QRECEIPT_PAYMENT")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findAll", query = "SELECT i FROM IncomeExpenceSubManualEntity i"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeCode", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.incomeExpenceSubManualEntityPK.ieCode = :ieCode"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeTerm", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.ieTerm = :ieTerm"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeDate", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.ieDate = :ieDate"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByWho1", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.who1 = :who1"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByWhen1", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.when1 = :when1"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeRn", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.incomeExpenceSubManualEntityPK.ieRn = :ieRn"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeInt", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.ieInt = :ieInt"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeDayEnd", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.ieDayEnd = :ieDayEnd"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByWho1ModDel", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.who1ModDel = :who1ModDel"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByWhen1ModDel", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.when1ModDel = :when1ModDel"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeDeNonPerformingTerm", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.ieDeNonPerformingTerm = :ieDeNonPerformingTerm"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeDeNonPerformingInt", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.ieDeNonPerformingInt = :ieDeNonPerformingInt"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeDeNonPerformingPart", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.ieDeNonPerformingPart = :ieDeNonPerformingPart"),
//    @NamedQuery(name = "IncomeExpenceSubManualEntity.findByIeIntOld", query = "SELECT i FROM IncomeExpenceSubManualEntity i WHERE i.ieIntOld = :ieIntOld")})
public class QDailyReceipt2 implements Serializable {

    private static final long serialVersionUID = 1L;
//    @EmbeddedId

    @Basic(optional = false)
    @Id
    @Column(name = "IE_CODE")
    private String ieCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Id
    @Column(name = "IE_RN")
    private Double ieRn;
    @Basic(optional = false)
    @Column(name = "IE_TERM")
    private Double ieTerm;
    @Basic(optional = false)
    @Column(name = "IE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ieDate;
    @Basic(optional = false)
    @Column(name = "WHO1")
    private String who1;
    @Basic(optional = false)
    @Column(name = "WHEN1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when1;
    @Column(name = "IE_INT")
    private Double ieInt;
    @Column(name = "IE_DAY_END")
    private String ieDayEnd;
    @Column(name = "WHO1_MOD_DEL")
    private String who1ModDel;
    @Column(name = "WHEN1_MOD_DEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when1ModDel;
    @Column(name = "IE_DE_NON_PERFORMING_TERM")
    private Double ieDeNonPerformingTerm;
    @Column(name = "IE_DE_NON_PERFORMING_INT")
    private Double ieDeNonPerformingInt;
    @Column(name = "IE_DE_NON_PERFORMING_PART")
    private String ieDeNonPerformingPart;
    @Column(name = "IE_INT_OLD")
    private Double ieIntOld;

    public QDailyReceipt2() {
    }

    public String getIeCode ()
    {
        return ieCode;
    }

    public void setIeCode (String ieCode)
    {
        this.ieCode = ieCode;
    }

    public Double getIeRn ()
    {
        return ieRn;
    }

    public void setIeRn (Double ieRn)
    {
        this.ieRn = ieRn;
    }

    public Double getIeTerm ()
    {
        return ieTerm;
    }

    public void setIeTerm (Double ieTerm)
    {
        this.ieTerm = ieTerm;
    }

    public Date getIeDate ()
    {
        return ieDate;
    }

    public void setIeDate (Date ieDate)
    {
        this.ieDate = ieDate;
    }

    public String getWho1 ()
    {
        return who1;
    }

    public void setWho1 (String who1)
    {
        this.who1 = who1;
    }

    public Date getWhen1 ()
    {
        return when1;
    }

    public void setWhen1 (Date when1)
    {
        this.when1 = when1;
    }

    public Double getIeInt ()
    {
        return ieInt;
    }

    public void setIeInt (Double ieInt)
    {
        this.ieInt = ieInt;
    }

    public String getIeDayEnd ()
    {
        return ieDayEnd;
    }

    public void setIeDayEnd (String ieDayEnd)
    {
        this.ieDayEnd = ieDayEnd;
    }

    public String getWho1ModDel ()
    {
        return who1ModDel;
    }

    public void setWho1ModDel (String who1ModDel)
    {
        this.who1ModDel = who1ModDel;
    }

    public Date getWhen1ModDel ()
    {
        return when1ModDel;
    }

    public void setWhen1ModDel (Date when1ModDel)
    {
        this.when1ModDel = when1ModDel;
    }

    public Double getIeDeNonPerformingTerm ()
    {
        return ieDeNonPerformingTerm;
    }

    public void setIeDeNonPerformingTerm (Double ieDeNonPerformingTerm)
    {
        this.ieDeNonPerformingTerm = ieDeNonPerformingTerm;
    }

    public Double getIeDeNonPerformingInt ()
    {
        return ieDeNonPerformingInt;
    }

    public void setIeDeNonPerformingInt (Double ieDeNonPerformingInt)
    {
        this.ieDeNonPerformingInt = ieDeNonPerformingInt;
    }

    public String getIeDeNonPerformingPart ()
    {
        return ieDeNonPerformingPart;
    }

    public void setIeDeNonPerformingPart (String ieDeNonPerformingPart)
    {
        this.ieDeNonPerformingPart = ieDeNonPerformingPart;
    }

    public Double getIeIntOld ()
    {
        return ieIntOld;
    }

    public void setIeIntOld (Double ieIntOld)
    {
        this.ieIntOld = ieIntOld;
    }

}
