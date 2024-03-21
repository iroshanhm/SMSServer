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
import javax.persistence.Transient;

/**
 *
 * @author Iroshan
 */
@Entity
@Table(name = "CUSTOMER")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
//    @NamedQuery(name = "Customer.findByCmCode", query = "SELECT c FROM Customer c WHERE c.cmCode = :cmCode"),
//    @NamedQuery(name = "Customer.findByCmFlag", query = "SELECT c FROM Customer c WHERE c.cmFlag = :cmFlag"),
//    @NamedQuery(name = "Customer.findByCmDesc", query = "SELECT c FROM Customer c WHERE c.cmDesc = :cmDesc"),
//    @NamedQuery(name = "Customer.findByWho1", query = "SELECT c FROM Customer c WHERE c.who1 = :who1"),
//    @NamedQuery(name = "Customer.findByWhen1", query = "SELECT c FROM Customer c WHERE c.when1 = :when1"),
//    @NamedQuery(name = "Customer.findByCmType", query = "SELECT c FROM Customer c WHERE c.cmType = :cmType"),
//    @NamedQuery(name = "Customer.findByCmNmcode", query = "SELECT c FROM Customer c WHERE c.cmNmcode = :cmNmcode"),
//    @NamedQuery(name = "Customer.findByCmIncome", query = "SELECT c FROM Customer c WHERE c.cmIncome = :cmIncome"),
//    @NamedQuery(name = "Customer.findByCmExpence", query = "SELECT c FROM Customer c WHERE c.cmExpence = :cmExpence"),
//    @NamedQuery(name = "Customer.findByCmDate", query = "SELECT c FROM Customer c WHERE c.cmDate = :cmDate"),
//    @NamedQuery(name = "Customer.findByPnumber", query = "SELECT c FROM Customer c WHERE c.pnumber = :pnumber"),
//    @NamedQuery(name = "Customer.findByCmName", query = "SELECT c FROM Customer c WHERE c.cmName = :cmName"),
//    @NamedQuery(name = "Customer.findByCmSurname", query = "SELECT c FROM Customer c WHERE c.cmSurname = :cmSurname"),
//    @NamedQuery(name = "Customer.findByCmBdate", query = "SELECT c FROM Customer c WHERE c.cmBdate = :cmBdate"),
//    @NamedQuery(name = "Customer.findByCmAdd1", query = "SELECT c FROM Customer c WHERE c.cmAdd1 = :cmAdd1"),
//    @NamedQuery(name = "Customer.findByCmAdd2", query = "SELECT c FROM Customer c WHERE c.cmAdd2 = :cmAdd2"),
//    @NamedQuery(name = "Customer.findByCmAdd3", query = "SELECT c FROM Customer c WHERE c.cmAdd3 = :cmAdd3"),
//    @NamedQuery(name = "Customer.findByCmAdd4", query = "SELECT c FROM Customer c WHERE c.cmAdd4 = :cmAdd4"),
//    @NamedQuery(name = "Customer.findByCmTele", query = "SELECT c FROM Customer c WHERE c.cmTele = :cmTele"),
//    @NamedQuery(name = "Customer.findByCmReligion", query = "SELECT c FROM Customer c WHERE c.cmReligion = :cmReligion"),
//    @NamedQuery(name = "Customer.findByCmEdate", query = "SELECT c FROM Customer c WHERE c.cmEdate = :cmEdate"),
//    @NamedQuery(name = "Customer.findByCmSex", query = "SELECT c FROM Customer c WHERE c.cmSex = :cmSex"),
//    @NamedQuery(name = "Customer.findByCmStat", query = "SELECT c FROM Customer c WHERE c.cmStat = :cmStat"),
//    @NamedQuery(name = "Customer.findByPnumber21", query = "SELECT c FROM Customer c WHERE c.pnumber21 = :pnumber21"),
//    @NamedQuery(name = "Customer.findByPnumber22", query = "SELECT c FROM Customer c WHERE c.pnumber22 = :pnumber22"),
//    @NamedQuery(name = "Customer.findByPnumber23", query = "SELECT c FROM Customer c WHERE c.pnumber23 = :pnumber23"),
//    @NamedQuery(name = "Customer.findByCmId", query = "SELECT c FROM Customer c WHERE c.cmId = :cmId"),
//    @NamedQuery(name = "Customer.findByCmType2", query = "SELECT c FROM Customer c WHERE c.cmType2 = :cmType2"),
//    @NamedQuery(name = "Customer.findByCmExpire", query = "SELECT c FROM Customer c WHERE c.cmExpire = :cmExpire"),
//    @NamedQuery(name = "Customer.findByCmExpired", query = "SELECT c FROM Customer c WHERE c.cmExpired = :cmExpired"),
//    @NamedQuery(name = "Customer.findByCmCategory", query = "SELECT c FROM Customer c WHERE c.cmCategory = :cmCategory"),
//    @NamedQuery(name = "Customer.findByPnumber2", query = "SELECT c FROM Customer c WHERE c.pnumber2 = :pnumber2"),
//    @NamedQuery(name = "Customer.findByCmOfficeNo", query = "SELECT c FROM Customer c WHERE c.cmOfficeNo = :cmOfficeNo"),
//    @NamedQuery(name = "Customer.findByCmEpf", query = "SELECT c FROM Customer c WHERE c.cmEpf = :cmEpf"),
//    @NamedQuery(name = "Customer.findByCmCategory3", query = "SELECT c FROM Customer c WHERE c.cmCategory3 = :cmCategory3"),
//    @NamedQuery(name = "Customer.findByCmExit", query = "SELECT c FROM Customer c WHERE c.cmExit = :cmExit"),
//    @NamedQuery(name = "Customer.findByCmDead", query = "SELECT c FROM Customer c WHERE c.cmDead = :cmDead"),
//    @NamedQuery(name = "Customer.findByCmNameEnglish", query = "SELECT c FROM Customer c WHERE c.cmNameEnglish = :cmNameEnglish"),
//    @NamedQuery(name = "Customer.findByCmAdd1English", query = "SELECT c FROM Customer c WHERE c.cmAdd1English = :cmAdd1English"),
//    @NamedQuery(name = "Customer.findByCmAdd2English", query = "SELECT c FROM Customer c WHERE c.cmAdd2English = :cmAdd2English"),
//    @NamedQuery(name = "Customer.findByCmAdd3English", query = "SELECT c FROM Customer c WHERE c.cmAdd3English = :cmAdd3English"),
//    @NamedQuery(name = "Customer.findByCmAdd4English", query = "SELECT c FROM Customer c WHERE c.cmAdd4English = :cmAdd4English"),
//    @NamedQuery(name = "Customer.findByCmEmployementEnglish", query = "SELECT c FROM Customer c WHERE c.cmEmployementEnglish = :cmEmployementEnglish"),
//    @NamedQuery(name = "Customer.findByCmEmployementAdd1English", query = "SELECT c FROM Customer c WHERE c.cmEmployementAdd1English = :cmEmployementAdd1English"),
//    @NamedQuery(name = "Customer.findByCmEmployementAdd2English", query = "SELECT c FROM Customer c WHERE c.cmEmployementAdd2English = :cmEmployementAdd2English"),
//    @NamedQuery(name = "Customer.findByCmEmployementAdd3English", query = "SELECT c FROM Customer c WHERE c.cmEmployementAdd3English = :cmEmployementAdd3English"),
//    @NamedQuery(name = "Customer.findByCmEmployementAdd4English", query = "SELECT c FROM Customer c WHERE c.cmEmployementAdd4English = :cmEmployementAdd4English"),
//    @NamedQuery(name = "Customer.findByCmExcessInt", query = "SELECT c FROM Customer c WHERE c.cmExcessInt = :cmExcessInt"),
//    @NamedQuery(name = "Customer.findByCmByWhat", query = "SELECT c FROM Customer c WHERE c.cmByWhat = :cmByWhat"),
//    @NamedQuery(name = "Customer.findByCmStopDate", query = "SELECT c FROM Customer c WHERE c.cmStopDate = :cmStopDate"),
//    @NamedQuery(name = "Customer.findByCmReal", query = "SELECT c FROM Customer c WHERE c.cmReal = :cmReal"),
//    @NamedQuery(name = "Customer.findByCmShortName", query = "SELECT c FROM Customer c WHERE c.cmShortName = :cmShortName"),
//    @NamedQuery(name = "Customer.findByCmOpCashInhand", query = "SELECT c FROM Customer c WHERE c.cmOpCashInhand = :cmOpCashInhand"),
//    @NamedQuery(name = "Customer.findByCmStatOrg", query = "SELECT c FROM Customer c WHERE c.cmStatOrg = :cmStatOrg"),
//    @NamedQuery(name = "Customer.findByCmCategory2", query = "SELECT c FROM Customer c WHERE c.cmCategory2 = :cmCategory2"),
//    @NamedQuery(name = "Customer.findByWho1ModDel", query = "SELECT c FROM Customer c WHERE c.who1ModDel = :who1ModDel"),
//    @NamedQuery(name = "Customer.findByWhen1ModDel", query = "SELECT c FROM Customer c WHERE c.when1ModDel = :when1ModDel")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private String mFOCode;

    @Transient
    private String addreesFull;

    @Transient
    private String centerName;
    
    @Transient
    private String groupName;

    
    @Id
    @Basic(optional = false)
    @Column(name = "CM_CODE")
    private String cmCode;

    @Basic(optional = false)
    @Column(name = "CM_FLAG")
    private String cmFlag;
    @Column(name = "CM_DESC")
    private String cmDesc;
    @Basic(optional = false)
    @Column(name = "WHO1")
    private String who1;
    @Basic(optional = false)
    @Column(name = "WHEN1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when1;
    @Column(name = "CM_TYPE")
    private String cmType;
    @Column(name = "CM_NMCODE")
    private String cmNmcode;
    @Column(name = "CM_INCOME")
    private String cmIncome;
    @Column(name = "CM_EXPENCE")
    private String cmExpence;
    @Column(name = "CM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cmDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PNUMBER")
    private Double pnumber;
    @Column(name = "CM_NAME")
    private String cmName;
    @Column(name = "CM_SURNAME")
    private String cmSurname;
    @Column(name = "CM_BDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cmBdate;
    @Column(name = "CM_ADD1")
    private String cmAdd1;
    @Column(name = "CM_ADD2")
    private String cmAdd2;
    @Column(name = "CM_ADD3")
    private String cmAdd3;
    @Column(name = "CM_ADD4")
    private String cmAdd4;
    @Column(name = "CM_TELE")
    private String cmTele = "";
    @Column(name = "CM_RELIGION")
    private String cmReligion;
    @Column(name = "CM_EDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cmEdate;
    @Column(name = "CM_SEX")
    private String cmSex;
    @Column(name = "CM_STAT")
    private String cmStat;
    @Column(name = "PNUMBER21")
    private Double pnumber21;
    @Column(name = "PNUMBER22")
    private Double pnumber22;
    @Column(name = "PNUMBER23")
    private Double pnumber23;
    @Column(name = "CM_ID")
    private String cmId;
    @Column(name = "CM_TYPE2")
    private String cmType2;
    @Column(name = "CM_EXPIRE")
    private String cmExpire;
    @Column(name = "CM_EXPIRED")
    private String cmExpired;
    @Column(name = "CM_CATEGORY")
    private String cmCategory;
    @Column(name = "PNUMBER2")
    private Double pnumber2;
    @Column(name = "CM_OFFICE_NO")
    private String cmOfficeNo;
    @Column(name = "CM_EPF")
    private String cmEpf;
    @Column(name = "CM_CATEGORY3")
    private String cmCategory3;
    @Column(name = "CM_EXIT")
    private String cmExit;
    @Column(name = "CM_DEAD")
    private String cmDead;
    @Column(name = "CM_NAME_ENGLISH")
    private String cmNameEnglish;
    @Column(name = "CM_ADD1_ENGLISH")
    private String cmAdd1English;
    @Column(name = "CM_ADD2_ENGLISH")
    private String cmAdd2English;
    @Column(name = "CM_ADD3_ENGLISH")
    private String cmAdd3English;
    @Column(name = "CM_ADD4_ENGLISH")
    private String cmAdd4English;
    @Column(name = "CM_EMPLOYEMENT_ENGLISH")
    private String cmEmployementEnglish;
    @Column(name = "CM_EMPLOYEMENT_ADD1_ENGLISH")
    private String cmEmployementAdd1English;
    @Column(name = "CM_EMPLOYEMENT_ADD2_ENGLISH")
    private String cmEmployementAdd2English;
    @Column(name = "CM_EMPLOYEMENT_ADD3_ENGLISH")
    private String cmEmployementAdd3English;
    @Column(name = "CM_EMPLOYEMENT_ADD4_ENGLISH")
    private String cmEmployementAdd4English;
    @Column(name = "CM_EXCESS_INT")
    private Double cmExcessInt;
    @Column(name = "CM_BY_WHAT")
    private String cmByWhat;
    @Column(name = "CM_STOP_DATE")
    private String cmStopDate;
    @Column(name = "CM_REAL")
    private String cmReal;
    @Column(name = "CM_SHORT_NAME")
    private String cmShortName;
    @Column(name = "CM_OP_CASH_INHAND")
    private Double cmOpCashInhand;
    @Column(name = "CM_STAT_ORG")
    private String cmStatOrg;
    @Column(name = "CM_CATEGORY2")
    private String cmCategory2;
    @Column(name = "WHO1_MOD_DEL")
    private String who1ModDel;
    @Column(name = "WHEN1_MOD_DEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when1ModDel;

    public Customer() {
    }

    public Customer(String cmCode) {
        this.cmCode = cmCode;
    }

    public Customer(String cmCode, String cmFlag, String who1, Date when1, Double pnumber) {
        this.cmCode = cmCode;
        this.cmFlag = cmFlag;
        this.who1 = who1;
        this.when1 = when1;
        this.pnumber = pnumber;
    }

    public String getmFOCode() {
        return mFOCode;
    }

    public void setmFOCode(String mFOCode) {
        this.mFOCode = mFOCode;
    }

    
    
    public String getAddreesFull() {
        return addreesFull;
    }

    
    public void setAddreesFull(String addreesFull) {
        this.addreesFull = addreesFull;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    
    
    
    
    public String getCmCode() {
        return cmCode;
    }

    public void setCmCode(String cmCode) {
        this.cmCode = cmCode;
    }

    public String getCmFlag() {
        return cmFlag;
    }

    public void setCmFlag(String cmFlag) {
        this.cmFlag = cmFlag;
    }

    public String getCmDesc() {
        return cmDesc;
    }

    public void setCmDesc(String cmDesc) {
        this.cmDesc = cmDesc;
    }

    public String getWho1() {
        return who1;
    }

    public void setWho1(String who1) {
        this.who1 = who1;
    }

    public Date getWhen1() {
        return when1;
    }

    public void setWhen1(Date when1) {
        this.when1 = when1;
    }

    public String getCmType() {
        return cmType;
    }

    public void setCmType(String cmType) {
        this.cmType = cmType;
    }

    public String getCmNmcode() {
        return cmNmcode;
    }

    public void setCmNmcode(String cmNmcode) {
        this.cmNmcode = cmNmcode;
    }

    public String getCmIncome() {
        return cmIncome;
    }

    public void setCmIncome(String cmIncome) {
        this.cmIncome = cmIncome;
    }

    public String getCmExpence() {
        return cmExpence;
    }

    public void setCmExpence(String cmExpence) {
        this.cmExpence = cmExpence;
    }

    public Date getCmDate() {
        return cmDate;
    }

    public void setCmDate(Date cmDate) {
        this.cmDate = cmDate;
    }

    public Double getPnumber() {
        return pnumber;
    }

    public void setPnumber(Double pnumber) {
        this.pnumber = pnumber;
    }

    public String getCmName() {
        return cmName;
    }

    public void setCmName(String cmName) {
        this.cmName = cmName;
    }

    public String getCmSurname() {
        return cmSurname;
    }

    public void setCmSurname(String cmSurname) {
        this.cmSurname = cmSurname;
    }

    public Date getCmBdate() {
        return cmBdate;
    }

    public void setCmBdate(Date cmBdate) {
        this.cmBdate = cmBdate;
    }

    public String getCmAdd1() {
        return cmAdd1;
    }

    public void setCmAdd1(String cmAdd1) {
        this.cmAdd1 = cmAdd1;
    }

    public String getCmAdd2() {
        return cmAdd2;
    }

    public void setCmAdd2(String cmAdd2) {
        this.cmAdd2 = cmAdd2;
    }

    public String getCmAdd3() {
        return cmAdd3;
    }

    public void setCmAdd3(String cmAdd3) {
        this.cmAdd3 = cmAdd3;
    }

    public String getCmAdd4() {
        return cmAdd4;
    }

    public void setCmAdd4(String cmAdd4) {
        this.cmAdd4 = cmAdd4;
    }

    public String getCmTele() {
        return cmTele;
    }

    public void setCmTele(String cmTele) {
        this.cmTele = cmTele;
    }

    public String getCmReligion() {
        return cmReligion;
    }

    public void setCmReligion(String cmReligion) {
        this.cmReligion = cmReligion;
    }

    public Date getCmEdate() {
        return cmEdate;
    }

    public void setCmEdate(Date cmEdate) {
        this.cmEdate = cmEdate;
    }

    public String getCmSex() {
        return cmSex;
    }

    public void setCmSex(String cmSex) {
        this.cmSex = cmSex;
    }

    public String getCmStat() {
        return cmStat;
    }

    public void setCmStat(String cmStat) {
        this.cmStat = cmStat;
    }

    public Double getPnumber21() {
        return pnumber21;
    }

    public void setPnumber21(Double pnumber21) {
        this.pnumber21 = pnumber21;
    }

    public Double getPnumber22() {
        return pnumber22;
    }

    public void setPnumber22(Double pnumber22) {
        this.pnumber22 = pnumber22;
    }

    public Double getPnumber23() {
        return pnumber23;
    }

    public void setPnumber23(Double pnumber23) {
        this.pnumber23 = pnumber23;
    }

    public String getCmId() {
        return cmId;
    }

    public void setCmId(String cmId) {
        this.cmId = cmId;
    }

    public String getCmType2() {
        return cmType2;
    }

    public void setCmType2(String cmType2) {
        this.cmType2 = cmType2;
    }

    public String getCmExpire() {
        return cmExpire;
    }

    public void setCmExpire(String cmExpire) {
        this.cmExpire = cmExpire;
    }

    public String getCmExpired() {
        return cmExpired;
    }

    public void setCmExpired(String cmExpired) {
        this.cmExpired = cmExpired;
    }

    public String getCmCategory() {
        return cmCategory;
    }

    public void setCmCategory(String cmCategory) {
        this.cmCategory = cmCategory;
    }

    public Double getPnumber2() {
        return pnumber2;
    }

    public void setPnumber2(Double pnumber2) {
        this.pnumber2 = pnumber2;
    }

    public String getCmOfficeNo() {
        return cmOfficeNo;
    }

    public void setCmOfficeNo(String cmOfficeNo) {
        this.cmOfficeNo = cmOfficeNo;
    }

    public String getCmEpf() {
        return cmEpf;
    }

    public void setCmEpf(String cmEpf) {
        this.cmEpf = cmEpf;
    }

    public String getCmCategory3() {
        return cmCategory3;
    }

    public void setCmCategory3(String cmCategory3) {
        this.cmCategory3 = cmCategory3;
    }

    public String getCmExit() {
        return cmExit;
    }

    public void setCmExit(String cmExit) {
        this.cmExit = cmExit;
    }

    public String getCmDead() {
        return cmDead;
    }

    public void setCmDead(String cmDead) {
        this.cmDead = cmDead;
    }

    public String getCmNameEnglish() {
        return cmNameEnglish;
    }

    public void setCmNameEnglish(String cmNameEnglish) {
        this.cmNameEnglish = cmNameEnglish;
    }

    public String getCmAdd1English() {
        return cmAdd1English;
    }

    public void setCmAdd1English(String cmAdd1English) {
        this.cmAdd1English = cmAdd1English;
    }

    public String getCmAdd2English() {
        return cmAdd2English;
    }

    public void setCmAdd2English(String cmAdd2English) {
        this.cmAdd2English = cmAdd2English;
    }

    public String getCmAdd3English() {
        return cmAdd3English;
    }

    public void setCmAdd3English(String cmAdd3English) {
        this.cmAdd3English = cmAdd3English;
    }

    public String getCmAdd4English() {
        return cmAdd4English;
    }

    public void setCmAdd4English(String cmAdd4English) {
        this.cmAdd4English = cmAdd4English;
    }

    public String getCmEmployementEnglish() {
        return cmEmployementEnglish;
    }

    public void setCmEmployementEnglish(String cmEmployementEnglish) {
        this.cmEmployementEnglish = cmEmployementEnglish;
    }

    public String getCmEmployementAdd1English() {
        return cmEmployementAdd1English;
    }

    public void setCmEmployementAdd1English(String cmEmployementAdd1English) {
        this.cmEmployementAdd1English = cmEmployementAdd1English;
    }

    public String getCmEmployementAdd2English() {
        return cmEmployementAdd2English;
    }

    public void setCmEmployementAdd2English(String cmEmployementAdd2English) {
        this.cmEmployementAdd2English = cmEmployementAdd2English;
    }

    public String getCmEmployementAdd3English() {
        return cmEmployementAdd3English;
    }

    public void setCmEmployementAdd3English(String cmEmployementAdd3English) {
        this.cmEmployementAdd3English = cmEmployementAdd3English;
    }

    public String getCmEmployementAdd4English() {
        return cmEmployementAdd4English;
    }

    public void setCmEmployementAdd4English(String cmEmployementAdd4English) {
        this.cmEmployementAdd4English = cmEmployementAdd4English;
    }

    public Double getCmExcessInt() {
        return cmExcessInt;
    }

    public void setCmExcessInt(Double cmExcessInt) {
        this.cmExcessInt = cmExcessInt;
    }

    public String getCmByWhat() {
        return cmByWhat;
    }

    public void setCmByWhat(String cmByWhat) {
        this.cmByWhat = cmByWhat;
    }

    public String getCmStopDate() {
        return cmStopDate;
    }

    public void setCmStopDate(String cmStopDate) {
        this.cmStopDate = cmStopDate;
    }

    public String getCmReal() {
        return cmReal;
    }

    public void setCmReal(String cmReal) {
        this.cmReal = cmReal;
    }

    public String getCmShortName() {
        return cmShortName;
    }

    public void setCmShortName(String cmShortName) {
        this.cmShortName = cmShortName;
    }

    public Double getCmOpCashInhand() {
        return cmOpCashInhand;
    }

    public void setCmOpCashInhand(Double cmOpCashInhand) {
        this.cmOpCashInhand = cmOpCashInhand;
    }

    public String getCmStatOrg() {
        return cmStatOrg;
    }

    public void setCmStatOrg(String cmStatOrg) {
        this.cmStatOrg = cmStatOrg;
    }

    public String getCmCategory2() {
        return cmCategory2;
    }

    public void setCmCategory2(String cmCategory2) {
        this.cmCategory2 = cmCategory2;
    }

    public String getWho1ModDel() {
        return who1ModDel;
    }

    public void setWho1ModDel(String who1ModDel) {
        this.who1ModDel = who1ModDel;
    }

    public Date getWhen1ModDel() {
        return when1ModDel;
    }

    public void setWhen1ModDel(Date when1ModDel) {
        this.when1ModDel = when1ModDel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cmCode != null ? cmCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.cmCode == null && other.cmCode != null) || (this.cmCode != null && !this.cmCode.equals(other.cmCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iroshan.com.venturapos.entity.Customer[ cmCode=" + cmCode + " ]";
    }

}
