/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table (name = "CUST_CATEGORY_MAIN")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "CustCategoryMain.findAll", query = "SELECT c FROM CustCategoryMain c"),
//    @NamedQuery(name = "CustCategoryMain.findByCcCode", query = "SELECT c FROM CustCategoryMain c WHERE c.ccCode = :ccCode"),
//    @NamedQuery(name = "CustCategoryMain.findByCcName", query = "SELECT c FROM CustCategoryMain c WHERE c.ccName = :ccName"),
//    @NamedQuery(name = "CustCategoryMain.findByCcFlag", query = "SELECT c FROM CustCategoryMain c WHERE c.ccFlag = :ccFlag"),
//    @NamedQuery(name = "CustCategoryMain.findByWho1", query = "SELECT c FROM CustCategoryMain c WHERE c.who1 = :who1"),
//    @NamedQuery(name = "CustCategoryMain.findByWhen1", query = "SELECT c FROM CustCategoryMain c WHERE c.when1 = :when1"),
//    @NamedQuery(name = "CustCategoryMain.findByCcStatus", query = "SELECT c FROM CustCategoryMain c WHERE c.ccStatus = :ccStatus"),
//    @NamedQuery(name = "CustCategoryMain.findByCcType1", query = "SELECT c FROM CustCategoryMain c WHERE c.ccType1 = :ccType1"),
//    @NamedQuery(name = "CustCategoryMain.findByCcType2", query = "SELECT c FROM CustCategoryMain c WHERE c.ccType2 = :ccType2"),
//    @NamedQuery(name = "CustCategoryMain.findByCcTime", query = "SELECT c FROM CustCategoryMain c WHERE c.ccTime = :ccTime"),
//    @NamedQuery(name = "CustCategoryMain.findByCcAdd1", query = "SELECT c FROM CustCategoryMain c WHERE c.ccAdd1 = :ccAdd1"),
//    @NamedQuery(name = "CustCategoryMain.findByCcAdd2", query = "SELECT c FROM CustCategoryMain c WHERE c.ccAdd2 = :ccAdd2"),
//    @NamedQuery(name = "CustCategoryMain.findByCcAdd3", query = "SELECT c FROM CustCategoryMain c WHERE c.ccAdd3 = :ccAdd3"),
//    @NamedQuery(name = "CustCategoryMain.findByCcAdd4", query = "SELECT c FROM CustCategoryMain c WHERE c.ccAdd4 = :ccAdd4"),
//    @NamedQuery(name = "CustCategoryMain.findByCcDay", query = "SELECT c FROM CustCategoryMain c WHERE c.ccDay = :ccDay"),
//    @NamedQuery(name = "CustCategoryMain.findByCcOfficer", query = "SELECT c FROM CustCategoryMain c WHERE c.ccOfficer = :ccOfficer"),
//    @NamedQuery(name = "CustCategoryMain.findByCcFrom", query = "SELECT c FROM CustCategoryMain c WHERE c.ccFrom = :ccFrom"),
//    @NamedQuery(name = "CustCategoryMain.findByCcTo", query = "SELECT c FROM CustCategoryMain c WHERE c.ccTo = :ccTo"),
//    @NamedQuery(name = "CustCategoryMain.findByCcType", query = "SELECT c FROM CustCategoryMain c WHERE c.ccType = :ccType"),
//    @NamedQuery(name = "CustCategoryMain.findByCcReal", query = "SELECT c FROM CustCategoryMain c WHERE c.ccReal = :ccReal"),
//    @NamedQuery(name = "CustCategoryMain.findByCcDay2", query = "SELECT c FROM CustCategoryMain c WHERE c.ccDay2 = :ccDay2"),
//    @NamedQuery(name = "CustCategoryMain.findByCcFirstDivision", query = "SELECT c FROM CustCategoryMain c WHERE c.ccFirstDivision = :ccFirstDivision"),
//    @NamedQuery(name = "CustCategoryMain.findByCcBranch", query = "SELECT c FROM CustCategoryMain c WHERE c.ccBranch = :ccBranch"),
//    @NamedQuery(name = "CustCategoryMain.findByWho1ModDel", query = "SELECT c FROM CustCategoryMain c WHERE c.who1ModDel = :who1ModDel"),
//    @NamedQuery(name = "CustCategoryMain.findByWhen1ModDel", query = "SELECT c FROM CustCategoryMain c WHERE c.when1ModDel = :when1ModDel")})


public class CustCategoryMain implements Serializable
{

    private static final long serialVersionUID = 1L;


    public CustCategoryMain ()
    {
    }


    @Id
    @Basic (optional = false)
    @Column (name = "CC_CODE")
    private String ccCode;

    @Basic (optional = false)
    @Column (name = "CC_NAME")
    private String ccName;
    @Column (name = "CC_FLAG")
    private String ccFlag;
    @Column (name = "WHO1")
    private String who1;
    @Column (name = "WHEN1")
    @Temporal (TemporalType.TIMESTAMP)
    private Date when1;
    @Column (name = "CC_STATUS")
    private String ccStatus;
    @Column (name = "CC_TYPE1")
    private String ccType1;
    @Column (name = "CC_TYPE2")
    private String ccType2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column (name = "CC_TIME")
    private BigDecimal ccTime;
    @Column (name = "CC_ADD1")
    private String ccAdd1;
    @Column (name = "CC_ADD2")
    private String ccAdd2;
    @Column (name = "CC_ADD3")
    private String ccAdd3;
    @Column (name = "CC_ADD4")
    private String ccAdd4;
    @Column (name = "CC_DAY")
    private String ccDay;
    @Column (name = "CC_OFFICER")
    private String ccOfficer;
    @Column (name = "CC_FROM")
    @Temporal (TemporalType.TIMESTAMP)
    private Date ccFrom;
    @Column (name = "CC_TO")
    @Temporal (TemporalType.TIMESTAMP)
    private Date ccTo;
    @Column (name = "CC_TYPE")
    private String ccType;
    @Column (name = "CC_REAL")
    private String ccReal;
    @Column (name = "CC_DAY2")
    private String ccDay2;
    @Column (name = "CC_FIRST_DIVISION")
    private String ccFirstDivision;
    @Column (name = "CC_BRANCH")
    private String ccBranch;
    @Column (name = "WHO1_MOD_DEL")
    private String who1ModDel;
    @Column (name = "WHEN1_MOD_DEL")
    @Temporal (TemporalType.TIMESTAMP)
    private Date when1ModDel;


    public CustCategoryMain (String ccCode)
    {
        this.ccCode = ccCode;
    }

    public CustCategoryMain (String ccCode, String ccName)
    {
        this.ccCode = ccCode;
        this.ccName = ccName;
    }

    public String getCcCode ()
    {
        return ccCode;
    }

    public void setCcCode (String ccCode)
    {
        this.ccCode = ccCode;
    }

    public String getCcName ()
    {
        return ccName;
    }

    public void setCcName (String ccName)
    {
        this.ccName = ccName;
    }

    public String getCcFlag ()
    {
        return ccFlag;
    }

    public void setCcFlag (String ccFlag)
    {
        this.ccFlag = ccFlag;
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

    public String getCcStatus ()
    {
        return ccStatus;
    }

    public void setCcStatus (String ccStatus)
    {
        this.ccStatus = ccStatus;
    }

    public String getCcType1 ()
    {
        return ccType1;
    }

    public void setCcType1 (String ccType1)
    {
        this.ccType1 = ccType1;
    }

    public String getCcType2 ()
    {
        return ccType2;
    }

    public void setCcType2 (String ccType2)
    {
        this.ccType2 = ccType2;
    }

    public BigDecimal getCcTime ()
    {
        return ccTime;
    }

    public void setCcTime (BigDecimal ccTime)
    {
        this.ccTime = ccTime;
    }

    public String getCcAdd1 ()
    {
        return ccAdd1;
    }

    public void setCcAdd1 (String ccAdd1)
    {
        this.ccAdd1 = ccAdd1;
    }

    public String getCcAdd2 ()
    {
        return ccAdd2;
    }

    public void setCcAdd2 (String ccAdd2)
    {
        this.ccAdd2 = ccAdd2;
    }

    public String getCcAdd3 ()
    {
        return ccAdd3;
    }

    public void setCcAdd3 (String ccAdd3)
    {
        this.ccAdd3 = ccAdd3;
    }

    public String getCcAdd4 ()
    {
        return ccAdd4;
    }

    public void setCcAdd4 (String ccAdd4)
    {
        this.ccAdd4 = ccAdd4;
    }

    public String getCcDay ()
    {
        return ccDay;
    }

    public void setCcDay (String ccDay)
    {
        this.ccDay = ccDay;
    }

    public String getCcOfficer ()
    {
        return ccOfficer;
    }

    public void setCcOfficer (String ccOfficer)
    {
        this.ccOfficer = ccOfficer;
    }

    public Date getCcFrom ()
    {
        return ccFrom;
    }

    public void setCcFrom (Date ccFrom)
    {
        this.ccFrom = ccFrom;
    }

    public Date getCcTo ()
    {
        return ccTo;
    }

    public void setCcTo (Date ccTo)
    {
        this.ccTo = ccTo;
    }

    public String getCcType ()
    {
        return ccType;
    }

    public void setCcType (String ccType)
    {
        this.ccType = ccType;
    }

    public String getCcReal ()
    {
        return ccReal;
    }

    public void setCcReal (String ccReal)
    {
        this.ccReal = ccReal;
    }

    public String getCcDay2 ()
    {
        return ccDay2;
    }

    public void setCcDay2 (String ccDay2)
    {
        this.ccDay2 = ccDay2;
    }

    public String getCcFirstDivision ()
    {
        return ccFirstDivision;
    }

    public void setCcFirstDivision (String ccFirstDivision)
    {
        this.ccFirstDivision = ccFirstDivision;
    }

    public String getCcBranch ()
    {
        return ccBranch;
    }

    public void setCcBranch (String ccBranch)
    {
        this.ccBranch = ccBranch;
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

    @Override
    public int hashCode ()
    {
        int hash = 0;
        hash += (ccCode != null ? ccCode.hashCode () : 0);
        return hash;
    }

    @Override
    public boolean equals (Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustCategoryMain))
        {
            return false;
        }
        CustCategoryMain other = (CustCategoryMain) object;
        if ((this.ccCode == null && other.ccCode != null) || (this.ccCode != null && !this.ccCode.equals (other.ccCode)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString ()
    {
        return "iroshan.com.smsserver.entity.CustCategoryMain[ ccCode=" + ccCode + " ]";
    }

}
