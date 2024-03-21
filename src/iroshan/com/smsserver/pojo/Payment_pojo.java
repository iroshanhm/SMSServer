/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.pojo;

import java.util.Date;

/**
 *
 * @author Iroshan
 */
public class Payment_pojo {

    String paymentNo;
    String cusCode;
    String cusName;
     String cusNameEnglish;
    String cusOfficeNo;
    String acNumber;
    String telNo;
    Double amount;
    Double loanAmount;
    Integer loanOrSaving;
    Date pay_date;

    public String getPaymentNo () {
        return paymentNo;
    }

    public void setPaymentNo (String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getCusCode () {
        return cusCode;
    }

    public void setCusCode (String cusCode) {
        this.cusCode = cusCode;
    }

    public String getCusName ()
    {
        return cusName;
    }

    public void setCusName (String cusName)
    {
        this.cusName = cusName;
    }

    public String getCusOfficeNo ()
    {
        return cusOfficeNo;
    }

    public void setCusOfficeNo (String cusOfficeNo)
    {
        this.cusOfficeNo = cusOfficeNo;
    }

    public String getAcNumber () {
        return acNumber;
    }

    public void setAcNumber (String acNumber) {
        this.acNumber = acNumber;
    }

    public String getTelNo () {
        return telNo;
    }

    public void setTelNo (String telNo) {
        this.telNo = telNo;
    }

    public Double getAmount () {
        return amount;
    }

    public void setAmount (Double amount) {
        this.amount = amount;
    }

    public Double getLoanAmount () {
        return loanAmount;
    }

    public void setLoanAmount (Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanOrSaving () {
        return loanOrSaving;
    }

    public void setLoanOrSaving (Integer loanOrSaving) {
        this.loanOrSaving = loanOrSaving;
    }

    public String getCusNameEnglish() {
        return cusNameEnglish;
    }

    public void setCusNameEnglish(String cusNameEnglish) {
        this.cusNameEnglish = cusNameEnglish;
    }

    public Date getPay_date() {
        return pay_date;
    }

    public void setPay_date(Date pay_date) {
        this.pay_date = pay_date;
    }

    

    
    
    
}
