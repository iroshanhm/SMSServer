/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.pojo;

import iroshan.com.smsserver.enums.MessageStatusEnum;

/**
 *
 * @author Iroshan
 */
public class Arrears_pojo {

    private String transactionCode;
    private String collectorCode;
    private String customerCode;
    private String tele;
    private String accountCode;

    private String capital;
    private String interest;
    private String fine;


    private String lastDate;

    private String loanGrantedAmount;
    private String loanGrantedDate;
    private String repaymentTerm;

    private String sumArrears;
    private String sumArrearsCapital;
    private String sumArrearsInterest;

    private String sumDueAmount;
    private String sumPayableAmount;

    private String fullSettlementDiscount;
    private String fullSettlementPayment;

  String message;
    String msgStatus = MessageStatusEnum.PENDING.toString ();

    public String getTransactionCode () {
        return transactionCode;
    }

    public void setTransactionCode (String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getCollectorCode () {
        return collectorCode;
    }

    public void setCollectorCode (String collectorCode) {
        this.collectorCode = collectorCode;
    }

    public String getTele () {
        return tele;
    }

    public void setTele (String tele) {
        this.tele = tele;
    }

    public String getCustomerCode () {
        return customerCode;
    }

    public void setCustomerCode (String customerCode) {
        this.customerCode = customerCode;
    }

    public String getAccountCode () {
        return accountCode;
    }

    public void setAccountCode (String accountCode) {
        this.accountCode = accountCode;
    }

    public String getCapital () {
        return capital;
    }

    public void setCapital (String capital) {
        this.capital = capital;
    }

    public String getInterest () {
        return interest;
    }

    public void setInterest (String interest) {
        this.interest = interest;
    }

    public String getFine () {
        return fine;
    }

    public void setFine (String fine) {
        this.fine = fine;
    }

    public String getLastDate () {
        return lastDate;
    }

    public void setLastDate (String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLoanGrantedAmount () {
        return loanGrantedAmount;
    }

    public void setLoanGrantedAmount (String loanGrantedAmount) {
        this.loanGrantedAmount = loanGrantedAmount;
    }

    public String getLoanGrantedDate () {
        return loanGrantedDate;
    }

    public void setLoanGrantedDate (String loanGrantedDate) {
        this.loanGrantedDate = loanGrantedDate;
    }

    public String getRepaymentTerm () {
        return repaymentTerm;
    }

    public void setRepaymentTerm (String repaymentTerm) {
        this.repaymentTerm = repaymentTerm;
    }

    public String getSumArrears () {
        return sumArrears;
    }

    public void setSumArrears (String sumArrears) {
        this.sumArrears = sumArrears;
    }

    public String getSumArrearsCapital () {
        return sumArrearsCapital;
    }

    public void setSumArrearsCapital (String sumArrearsCapital) {
        this.sumArrearsCapital = sumArrearsCapital;
    }

    public String getSumArrearsInterest () {
        return sumArrearsInterest;
    }

    public void setSumArrearsInterest (String sumArrearsInterest) {
        this.sumArrearsInterest = sumArrearsInterest;
    }

    public String getSumDueAmount () {
        return sumDueAmount;
    }

    public void setSumDueAmount (String sumDueAmount) {
        this.sumDueAmount = sumDueAmount;
    }

    public String getSumPayableAmount () {
        return sumPayableAmount;
    }

    public void setSumPayableAmount (String sumPayableAmount) {
        this.sumPayableAmount = sumPayableAmount;
    }

    public String getFullSettlementDiscount () {
        return fullSettlementDiscount;
    }

    public void setFullSettlementDiscount (String fullSettlementDiscount) {
        this.fullSettlementDiscount = fullSettlementDiscount;
    }

    public String getFullSettlementPayment () {
        return fullSettlementPayment;
    }

    public void setFullSettlementPayment (String fullSettlementPayment) {
        this.fullSettlementPayment = fullSettlementPayment;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getMsgStatus () {
        return msgStatus;
    }

    public void setMsgStatus (String msgStatus) {
        this.msgStatus = msgStatus;
    }




}
