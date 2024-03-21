/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Iroshan
 */
@Entity
public class Payment_ent implements Serializable {

    public Payment_ent ()
    {
    }

    @Id
    private String PA_CODE;
    private String PA_DATE;
    private String PD_VALUE;
    private String PD_CHEQUE;
    private String CM_CODE;
    private String CM_DESC;
    private String PE_VALUE;
    private String IE_DESC;
    private String IE_MTYPE;
    private String MDESC;
    private String PE_TYPE;
    private String IE_CODE;
    private String CODE2;
    private String WHO1;
    private String PE_DESC;
    private String IE_DESC_ENGLISH;
    private String DEPO_ACCOUNT;
    private String IE_ACCOUNT_DIVISION;
    private String is_sms_sent;
    private String CM_TELE;

    public String getPA_CODE() {
        return PA_CODE;
    }

    public void setPA_CODE(String PA_CODE) {
        this.PA_CODE = PA_CODE;
    }

    public String getPA_DATE() {
        return PA_DATE;
    }

    public void setPA_DATE(String PA_DATE) {
        this.PA_DATE = PA_DATE;
    }

    public String getPD_VALUE() {
        return PD_VALUE;
    }

    public void setPD_VALUE(String PD_VALUE) {
        this.PD_VALUE = PD_VALUE;
    }

    public String getPD_CHEQUE() {
        return PD_CHEQUE;
    }

    public void setPD_CHEQUE(String PD_CHEQUE) {
        this.PD_CHEQUE = PD_CHEQUE;
    }

    public String getCM_CODE() {
        return CM_CODE;
    }

    public void setCM_CODE(String CM_CODE) {
        this.CM_CODE = CM_CODE;
    }

    public String getCM_DESC() {
        return CM_DESC;
    }

    public void setCM_DESC(String CM_DESC) {
        this.CM_DESC = CM_DESC;
    }

    public String getPE_VALUE() {
        return PE_VALUE;
    }

    public void setPE_VALUE(String PE_VALUE) {
        this.PE_VALUE = PE_VALUE;
    }

    public String getIE_DESC() {
        return IE_DESC;
    }

    public void setIE_DESC(String IE_DESC) {
        this.IE_DESC = IE_DESC;
    }

    public String getIE_MTYPE() {
        return IE_MTYPE;
    }

    public void setIE_MTYPE(String IE_MTYPE) {
        this.IE_MTYPE = IE_MTYPE;
    }

    public String getMDESC() {
        return MDESC;
    }

    public void setMDESC(String MDESC) {
        this.MDESC = MDESC;
    }

    public String getPE_TYPE() {
        return PE_TYPE;
    }

    public void setPE_TYPE(String PE_TYPE) {
        this.PE_TYPE = PE_TYPE;
    }

    public String getIE_CODE() {
        return IE_CODE;
    }

    public void setIE_CODE(String IE_CODE) {
        this.IE_CODE = IE_CODE;
    }

    public String getCODE2() {
        return CODE2;
    }

    public void setCODE2(String CODE2) {
        this.CODE2 = CODE2;
    }

    public String getWHO1() {
        return WHO1;
    }

    public void setWHO1(String WHO1) {
        this.WHO1 = WHO1;
    }

    public String getPE_DESC() {
        return PE_DESC;
    }

    public void setPE_DESC(String PE_DESC) {
        this.PE_DESC = PE_DESC;
    }

    public String getIE_DESC_ENGLISH() {
        return IE_DESC_ENGLISH;
    }

    public void setIE_DESC_ENGLISH(String IE_DESC_ENGLISH) {
        this.IE_DESC_ENGLISH = IE_DESC_ENGLISH;
    }

    public String getDEPO_ACCOUNT() {
        return DEPO_ACCOUNT;
    }

    public void setDEPO_ACCOUNT(String DEPO_ACCOUNT) {
        this.DEPO_ACCOUNT = DEPO_ACCOUNT;
    }

    public String getIE_ACCOUNT_DIVISION() {
        return IE_ACCOUNT_DIVISION;
    }

    public void setIE_ACCOUNT_DIVISION(String IE_ACCOUNT_DIVISION) {
        this.IE_ACCOUNT_DIVISION = IE_ACCOUNT_DIVISION;
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

}
