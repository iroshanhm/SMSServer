/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author Iroshan
 */

@Entity
public class Receipt_ent implements Serializable{

    @Id
    private String RE_CODE;
    private String RE_CUSTOMER;
    private String RE_DATE;
    private String RD_VALUE;
    private String RD_CHEQUE;
    
    private String CM_DESC;
    private String RI_VALUE;
    private String RI_TYPE;
    private String IE_DESC;
    private String WHAT;
    private String IE_CODE;
    private String MCODE;
    private String DESC2;
    
    private String IE_MTYPE;
    private String RI_DESC;
    private String RE_CODE2;
    private String RE_CODE3;
    private String IE_ACCOUNT_DIVISION;
    private String WHo1;
    private String is_sms_sent;
    private String CM_TELE;
    
    @Transient
    private String loanDue;

    public Receipt_ent ()
    {
    }

    
    
    
    
    public String getRE_CODE() {
        return RE_CODE;
    }

    public void setRE_CODE(String RE_CODE) {
        this.RE_CODE = RE_CODE;
    }

    public String getRE_CUSTOMER() {
        return RE_CUSTOMER;
    }

    public void setRE_CUSTOMER(String RE_CUSTOMER) {
        this.RE_CUSTOMER = RE_CUSTOMER;
    }

    public String getRE_DATE() {
        return RE_DATE;
    }

    public void setRE_DATE(String RE_DATE) {
        this.RE_DATE = RE_DATE;
    }

    public String getRD_VALUE() {
        return RD_VALUE;
    }

    public void setRD_VALUE(String RD_VALUE) {
        this.RD_VALUE = RD_VALUE;
    }

    public String getRD_CHEQUE() {
        return RD_CHEQUE;
    }

    public void setRD_CHEQUE(String RD_CHEQUE) {
        this.RD_CHEQUE = RD_CHEQUE;
    }

    public String getCM_DESC() {
        return CM_DESC;
    }

    public void setCM_DESC(String CM_DESC) {
        this.CM_DESC = CM_DESC;
    }

    public String getRI_VALUE() {
        return RI_VALUE;
    }

    public void setRI_VALUE(String RI_VALUE) {
        this.RI_VALUE = RI_VALUE;
    }

    public String getRI_TYPE() {
        return RI_TYPE;
    }

    public void setRI_TYPE(String RI_TYPE) {
        this.RI_TYPE = RI_TYPE;
    }

    public String getIE_DESC() {
        return IE_DESC;
    }

    public void setIE_DESC(String IE_DESC) {
        this.IE_DESC = IE_DESC;
    }

    public String getWHAT() {
        return WHAT;
    }

    public void setWHAT(String WHAT) {
        this.WHAT = WHAT;
    }

    public String getIE_CODE() {
        return IE_CODE;
    }

    public void setIE_CODE(String IE_CODE) {
        this.IE_CODE = IE_CODE;
    }

    public String getMCODE() {
        return MCODE;
    }

    public void setMCODE(String MCODE) {
        this.MCODE = MCODE;
    }

    public String getDESC2() {
        return DESC2;
    }

    public void setDESC2(String DESC2) {
        this.DESC2 = DESC2;
    }

    public String getIE_MTYPE() {
        return IE_MTYPE;
    }

    public void setIE_MTYPE(String IE_MTYPE) {
        this.IE_MTYPE = IE_MTYPE;
    }

    public String getRI_DESC() {
        return RI_DESC;
    }

    public void setRI_DESC(String RI_DESC) {
        this.RI_DESC = RI_DESC;
    }

    public String getRE_CODE2() {
        return RE_CODE2;
    }

    public void setRE_CODE2(String RE_CODE2) {
        this.RE_CODE2 = RE_CODE2;
    }

    public String getRE_CODE3() {
        return RE_CODE3;
    }

    public void setRE_CODE3(String RE_CODE3) {
        this.RE_CODE3 = RE_CODE3;
    }

    public String getIE_ACCOUNT_DIVISION() {
        return IE_ACCOUNT_DIVISION;
    }

    public void setIE_ACCOUNT_DIVISION(String IE_ACCOUNT_DIVISION) {
        this.IE_ACCOUNT_DIVISION = IE_ACCOUNT_DIVISION;
    }

    public String getWHo1() {
        return WHo1;
    }

    public void setWHo1(String WHo1) {
        this.WHo1 = WHo1;
    }

    public String getIs_sms_sent() {
        return is_sms_sent;
    }

    public void setIs_sms_sent(String is_sms_sent) {
        this.is_sms_sent = is_sms_sent;
    }

    public String getCM_TELE() {
        return CM_TELE;
    }

    public void setCM_TELE(String CM_TELE) {
        this.CM_TELE = CM_TELE;
    }

    public String getLoanDue () {
        return loanDue;
    }

    public void setLoanDue (String loanOutstanding) {
        this.loanDue = loanOutstanding;
    }
    
    
}
