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
@Table (name = "VIEW_SMS_JURNAL")

public class ViewSMSJurnal_entity implements Serializable
{

    private static final long serialVersionUID = 1L;



    @Id
    @Column (name = "FD_CODE")
    private String FD_CODE;

    @Id
    @Column (name = "FD_VALUE")
    private Double FD_VALUE;

    @Column (name = "FD_RN")
    private Integer FD_RN;

    @Id
    @Column (name = "FD_TYPE")
    private Integer FD_TYPE;

    @Id
    @Column (name = "FD_TOMEMBER")
    private String FD_TOMEMBER;

    @Id
    @Column (name = "FD_TOEXPENCE")
    private String FD_TOEXPENCE;


    @Column (name = "IE_SUB_TYPE")
    private String IE_SUB_TYPE;

    @Column (name = "IE_CODE")
    private String IE_CODE;

    @Column (name = "FT_DATE")
    @Temporal (TemporalType.TIMESTAMP)
    private Date FT_DATE;

    @Column (name = "IS_SMS_SENT")
    private String IS_SMS_SENT;

    @Column (name = "CM_DESC")
    private String CM_DESC;
  
    @Column (name = "CM_NAME_ENGLISH")
    private String CM_NAME_ENGLISH;

    @Column (name = "CM_TELE")
    private String CM_TELE;

    @Column (name = "CM_OFFICE_NO")
    private String CM_OFFICE_NO;

    @Column (name = "CC_CODE")
    private String CC_CODE;

    @Column (name = "CC_NAME")
    private String CC_NAME;

    @Column (name = "CENTER_CODE")
    private String CENTER_CODE;

    @Column (name = "CENTER_NAME")
    private String CENTER_NAME;

    @Transient
    private Double arrearsAmount;

    public ViewSMSJurnal_entity ()
    {
    }

    
    
    

    public String getFD_CODE ()
    {
        return FD_CODE;
    }

    public void setFD_CODE (String FD_CODE)
    {
        this.FD_CODE = FD_CODE;
    }

    public Double getFD_VALUE ()
    {
        return FD_VALUE;
    }

    public void setFD_VALUE (Double FD_VALUE)
    {
        this.FD_VALUE = FD_VALUE;
    }

    public Integer getFD_RN ()
    {
        return FD_RN;
    }

    public void setFD_RN (Integer FD_RN)
    {
        this.FD_RN = FD_RN;
    }

    public Integer getFD_TYPE ()
    {
        return FD_TYPE;
    }

    public void setFD_TYPE (Integer FD_TYPE)
    {
        this.FD_TYPE = FD_TYPE;
    }

    public String getFD_TOMEMBER ()
    {
        return FD_TOMEMBER;
    }

    public void setFD_TOMEMBER (String FD_TOMEMBER)
    {
        this.FD_TOMEMBER = FD_TOMEMBER;
    }

    public String getFD_TOEXPENCE ()
    {
        return FD_TOEXPENCE;
    }

    public void setFD_TOEXPENCE (String FD_TOEXPENCE)
    {
        this.FD_TOEXPENCE = FD_TOEXPENCE;
    }

    public String getIE_SUB_TYPE ()
    {
        return IE_SUB_TYPE;
    }

    public void setIE_SUB_TYPE (String IE_SUB_TYPE)
    {
        this.IE_SUB_TYPE = IE_SUB_TYPE;
    }

    public String getIE_CODE ()
    {
        return IE_CODE;
    }

    public void setIE_CODE (String IE_CODE)
    {
        this.IE_CODE = IE_CODE;
    }

    public Date getFT_DATE ()
    {
        return FT_DATE;
    }

    public void setFT_DATE (Date FT_DATE)
    {
        this.FT_DATE = FT_DATE;
    }

    public String getIS_SMS_SENT ()
    {
        return IS_SMS_SENT;
    }

    public void setIS_SMS_SENT (String IS_SMS_SENT)
    {
        this.IS_SMS_SENT = IS_SMS_SENT;
    }

    public String getCM_DESC ()
    {
        return CM_DESC;
    }

    public void setCM_DESC (String CM_DESC)
    {
        this.CM_DESC = CM_DESC;
    }

    public String getCM_TELE ()
    {
        return CM_TELE;
    }

    public void setCM_TELE (String CM_TELE)
    {
        this.CM_TELE = CM_TELE;
    }

    public String getCM_OFFICE_NO ()
    {
        return CM_OFFICE_NO;
    }

    public void setCM_OFFICE_NO (String CM_OFFICE_NO)
    {
        this.CM_OFFICE_NO = CM_OFFICE_NO;
    }

    public String getCC_CODE ()
    {
        return CC_CODE;
    }

    public void setCC_CODE (String CC_CODE)
    {
        this.CC_CODE = CC_CODE;
    }

    public String getCC_NAME ()
    {
        return CC_NAME;
    }

    public void setCC_NAME (String CC_NAME)
    {
        this.CC_NAME = CC_NAME;
    }

    public String getCENTER_CODE ()
    {
        return CENTER_CODE;
    }

    public void setCENTER_CODE (String CENTER_CODE)
    {
        this.CENTER_CODE = CENTER_CODE;
    }

    public String getCENTER_NAME ()
    {
        return CENTER_NAME;
    }

    public void setCENTER_NAME (String CENTER_NAME)
    {
        this.CENTER_NAME = CENTER_NAME;
    }

    public Double getArrearsAmount ()
    {
        return arrearsAmount;
    }

    public void setArrearsAmount (Double arrearsAmount)
    {
        this.arrearsAmount = arrearsAmount;
    }

    public String getCM_NAME_ENGLISH() {
        return CM_NAME_ENGLISH;
    }

    public void setCM_NAME_ENGLISH(String CM_NAME_ENGLISH) {
        this.CM_NAME_ENGLISH = CM_NAME_ENGLISH;
    }



}
