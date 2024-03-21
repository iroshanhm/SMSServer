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
@Table(name = "CUST_CATEGORY")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "CustCategoryEntity.findAll", query = "SELECT c FROM CustCategoryEntity c"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcCode", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccCode = :ccCode"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcName", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccName = :ccName"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcFlag", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccFlag = :ccFlag"),
//    @NamedQuery(name = "CustCategoryEntity.findByWho1", query = "SELECT c FROM CustCategoryEntity c WHERE c.who1 = :who1"),
//    @NamedQuery(name = "CustCategoryEntity.findByWhen1", query = "SELECT c FROM CustCategoryEntity c WHERE c.when1 = :when1"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcMain", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccMain = :ccMain"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcAdd1", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccAdd1 = :ccAdd1"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcAdd2", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccAdd2 = :ccAdd2"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcAdd3", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccAdd3 = :ccAdd3"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcAdd4", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccAdd4 = :ccAdd4"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcOption1", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccOption1 = :ccOption1"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcOption2", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccOption2 = :ccOption2"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcNameEnglish", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccNameEnglish = :ccNameEnglish"),
//    @NamedQuery(name = "CustCategoryEntity.findByCcOrginal", query = "SELECT c FROM CustCategoryEntity c WHERE c.ccOrginal = :ccOrginal"),
//    @NamedQuery(name = "CustCategoryEntity.findByWho1ModDel", query = "SELECT c FROM CustCategoryEntity c WHERE c.who1ModDel = :who1ModDel"),
//    @NamedQuery(name = "CustCategoryEntity.findByWhen1ModDel", query = "SELECT c FROM CustCategoryEntity c WHERE c.when1ModDel = :when1ModDel")})
public class CustCategory_ent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    
    
    @Id
    @Basic(optional = false)
    @Column(name = "CC_CODE")
    private String ccCode;
    
    @Basic(optional = false)
    @Column(name = "CC_NAME")
    private String ccName;
    @Column(name = "CC_FLAG")
    private String ccFlag;
    @Column(name = "WHO1")
    private String who1;
    @Column(name = "WHEN1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when1;
    @Column(name = "CC_MAIN")
    private String ccMain;
    @Column(name = "CC_ADD1")
    private String ccAdd1;
    @Column(name = "CC_ADD2")
    private String ccAdd2;
    @Column(name = "CC_ADD3")
    private String ccAdd3;
    @Column(name = "CC_ADD4")
    private String ccAdd4;
    @Column(name = "CC_OPTION1")
    private String ccOption1;
    @Column(name = "CC_OPTION2")
    private String ccOption2;
    @Column(name = "CC_NAME_ENGLISH")
    private String ccNameEnglish;
    @Column(name = "CC_ORGINAL")
    private String ccOrginal;
    @Column(name = "WHO1_MOD_DEL")
    private String who1ModDel;
    @Column(name = "WHEN1_MOD_DEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when1ModDel;

    public CustCategory_ent() {
    }

    public CustCategory_ent(String ccCode) {
        this.ccCode = ccCode;
    }

    public CustCategory_ent(String ccCode, String ccName) {
        this.ccCode = ccCode;
        this.ccName = ccName;
    }

    public String getCcCode() {
        return ccCode;
    }

    public void setCcCode(String ccCode) {
        this.ccCode = ccCode;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcFlag() {
        return ccFlag;
    }

    public void setCcFlag(String ccFlag) {
        this.ccFlag = ccFlag;
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

    public String getCcMain() {
        return ccMain;
    }

    public void setCcMain(String ccMain) {
        this.ccMain = ccMain;
    }

    public String getCcAdd1() {
        return ccAdd1;
    }

    public void setCcAdd1(String ccAdd1) {
        this.ccAdd1 = ccAdd1;
    }

    public String getCcAdd2() {
        return ccAdd2;
    }

    public void setCcAdd2(String ccAdd2) {
        this.ccAdd2 = ccAdd2;
    }

    public String getCcAdd3() {
        return ccAdd3;
    }

    public void setCcAdd3(String ccAdd3) {
        this.ccAdd3 = ccAdd3;
    }

    public String getCcAdd4() {
        return ccAdd4;
    }

    public void setCcAdd4(String ccAdd4) {
        this.ccAdd4 = ccAdd4;
    }

    public String getCcOption1() {
        return ccOption1;
    }

    public void setCcOption1(String ccOption1) {
        this.ccOption1 = ccOption1;
    }

    public String getCcOption2() {
        return ccOption2;
    }

    public void setCcOption2(String ccOption2) {
        this.ccOption2 = ccOption2;
    }

    public String getCcNameEnglish() {
        return ccNameEnglish;
    }

    public void setCcNameEnglish(String ccNameEnglish) {
        this.ccNameEnglish = ccNameEnglish;
    }

    public String getCcOrginal() {
        return ccOrginal;
    }

    public void setCcOrginal(String ccOrginal) {
        this.ccOrginal = ccOrginal;
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
        hash += (ccCode != null ? ccCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustCategory_ent)) {
            return false;
        }
        CustCategory_ent other = (CustCategory_ent) object;
        if ((this.ccCode == null && other.ccCode != null) || (this.ccCode != null && !this.ccCode.equals(other.ccCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iroshan.com.venturapos.entity.CustCategoryEntity[ ccCode=" + ccCode + " ]";
    }
    
}
