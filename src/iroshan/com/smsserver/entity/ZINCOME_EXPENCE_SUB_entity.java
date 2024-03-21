/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Type;

/**
 *
 * @author Iroshan
 */

@Entity
@Table (name = "ZINCOME_EXPENCE_SUB")
public class ZINCOME_EXPENCE_SUB_entity implements Serializable
{


    @Id
    @Column (name = "IE_SUB_TYPE")
    private String IE_SUB_TYPE;
    @Id
    @Column (name = "IE_ACTIVE")
    private String IE_ACTIVE;

    @Id
    @Column (name = "IE_CODE")
    private String IE_CODE;

    @Id
    @Column (name = "CM_DESC_2")
    private String CM_DESC_2;

//    @Id
//    @Column (name = "CM_OFFICE_NO")
//    private String CM_OFFICE_NO;

    @Id
    @Column (name = "IE_MEMBER")
    private String IE_MEMBER;
    @Id
    @Column (name = "IE_VALUE")
    private Double IE_VALUE;
    @Id
    @Column (name = "IE_NO_TERM")
    private Double IE_NO_TERM;
    @Id
    @Column (name = "IE_FROM")
    @Temporal (TemporalType.TIMESTAMP)
    private Date IE_FROM;

    @Id
    @Column (name = "IE_PAYDATE")
//    @Temporal (TemporalType.TIMESTAMP)
    private String IE_PAYDATE;

    @Id
    @Column (name = "IE_MAIN")
    private String IE_MAIN;

    @Id
    @Column (name = "CC_MAIN_2")
    private String CC_MAIN_2;

//    @Id
//    @Column (name = "CM_TELE")
//    private String CM_TELE;

    
    
    
    public ZINCOME_EXPENCE_SUB_entity ()
    {
    }


    public String getIE_SUB_TYPE ()
    {
        return IE_SUB_TYPE;
    }

    public void setIE_SUB_TYPE (String IE_SUB_TYPE)
    {
        this.IE_SUB_TYPE = IE_SUB_TYPE;
    }

    public String getIE_ACTIVE ()
    {
        return IE_ACTIVE;
    }

    public void setIE_ACTIVE (String IE_ACTIVE)
    {
        this.IE_ACTIVE = IE_ACTIVE;
    }

    public String getIE_CODE ()
    {
        return IE_CODE;
    }

    public void setIE_CODE (String IE_CODE)
    {
        this.IE_CODE = IE_CODE;
    }

    public String getCM_DESC_2 ()
    {
        return CM_DESC_2;
    }

    public void setCM_DESC_2 (String CM_DESC_2)
    {
        this.CM_DESC_2 = CM_DESC_2;
    }

//    public String getCM_OFFICE_NO ()
//    {
//        return CM_OFFICE_NO;
//    }
//
//    public void setCM_OFFICE_NO (String CM_OFFICE_NO)
//    {
//        this.CM_OFFICE_NO = CM_OFFICE_NO;
//    }

    public String getIE_MEMBER ()
    {
        return IE_MEMBER;
    }

    public void setIE_MEMBER (String IE_MEMBER)
    {
        this.IE_MEMBER = IE_MEMBER;
    }

    public Double getIE_VALUE ()
    {
        return IE_VALUE;
    }

    public void setIE_VALUE (Double IE_VALUE)
    {
        this.IE_VALUE = IE_VALUE;
    }

    public Double getIE_NO_TERM ()
    {
        return IE_NO_TERM;
    }

    public void setIE_NO_TERM (Double IE_NO_TERM)
    {
        this.IE_NO_TERM = IE_NO_TERM;
    }

    public Date getIE_FROM ()
    {
        return IE_FROM;
    }

    public void setIE_FROM (Date IE_FROM)
    {
        this.IE_FROM = IE_FROM;
    }

//    public String getCM_TELE ()
//    {
//        return CM_TELE;
//    }
//
//    public void setCM_TELE (String CM_TELE)
//    {
//        this.CM_TELE = CM_TELE;
//    }

    public String getIE_MAIN ()
    {
        return IE_MAIN;
    }

    public void setIE_MAIN (String IE_MAIN)
    {
        this.IE_MAIN = IE_MAIN;
    }

    public String getCC_MAIN_2 ()
    {
        return CC_MAIN_2;
    }

    public void setCC_MAIN_2 (String CC_MAIN_2)
    {
        this.CC_MAIN_2 = CC_MAIN_2;
    }

      public String getIE_PAYDATE() {
            return IE_PAYDATE;
      }

      public void setIE_PAYDATE(String IE_PAYDATE) {
            this.IE_PAYDATE = IE_PAYDATE;
      }


    



    
    
    
    
    
}
