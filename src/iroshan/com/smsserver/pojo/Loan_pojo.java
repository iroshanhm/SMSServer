/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.pojo;

import iroshan.com.smsserver.enums.MessageStatusEnum;
import java.util.Date;

/**
 *
 * @author Iroshan
 */
public class Loan_pojo {

    Boolean isSelected;

    String cusCode;

    String cusName;

    String cusOfficeNo;

    String acNumber;

    String telephoneNo;

    Date disburseDate;
    
    String disburseDate_str;

    Double disburseAmount;

    Double capitalFullLoan;

    Double interestFullLoan;

    Double totalFullLoan;

    Double outstandingCapital;

    Double outstandingInterest;

    Double totalOutstanding;

    Double dueCapital;

    Double dueInterest;

    Double dueFine;

    Double totalDue;

    Double ArearsCapital;

    Double ArearsInterst;

    Double ArearsFine;

    Double TotalArears;

    Double noOfArrearsTerms;

    Double noOfTerms;

    Double termAmount;

    String message;

    String msgStatus = MessageStatusEnum.PENDING.toString();

    Double fine;

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getCusCode() {
        return cusCode;
    }

    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusOfficeNo() {
        return cusOfficeNo;
    }

    public void setCusOfficeNo(String cusOfficeNo) {
        this.cusOfficeNo = cusOfficeNo;
    }

    public String getAcNumber() {
        return acNumber;
    }

    public void setAcNumber(String acNumber) {
        this.acNumber = acNumber;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public Date getDisburseDate() {
        return disburseDate;
    }

    public void setDisburseDate(Date disburseDate) {
        this.disburseDate = disburseDate;
    }

    public Double getDisburseAmount() {
        return disburseAmount;
    }

    public void setDisburseAmount(Double disburseAmount) {
        this.disburseAmount = disburseAmount;
    }

    public Double getCapitalFullLoan() {
        return capitalFullLoan;
    }

    public void setCapitalFullLoan(Double capitalFullLoan) {
        this.capitalFullLoan = capitalFullLoan;
    }

    public Double getInterestFullLoan() {
        return interestFullLoan;
    }

    public void setInterestFullLoan(Double interestFullLoan) {
        this.interestFullLoan = interestFullLoan;
    }

    public Double getTotalFullLoan() {
        return totalFullLoan;
    }

    public void setTotalFullLoan(Double totalFullLoan) {
        this.totalFullLoan = totalFullLoan;
    }

    public Double getOutstandingCapital() {
        return outstandingCapital;
    }

    public void setOutstandingCapital(Double outstandingCapital) {
        this.outstandingCapital = outstandingCapital;
    }

    public Double getOutstandingInterest() {
        return outstandingInterest;
    }

    public void setOutstandingInterest(Double outstandingInterest) {
        this.outstandingInterest = outstandingInterest;
    }

    public Double getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(Double totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public Double getDueCapital() {
        return dueCapital;
    }

    public void setDueCapital(Double dueCapital) {
        this.dueCapital = dueCapital;
    }

    public Double getDueInterest() {
        return dueInterest;
    }

    public void setDueInterest(Double dueInterest) {
        this.dueInterest = dueInterest;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public Double getArearsCapital() {
        return ArearsCapital;
    }

    public void setArearsCapital(Double ArearsCapital) {
        this.ArearsCapital = ArearsCapital;
    }

    public Double getArearsInterst() {
        return ArearsInterst;
    }

    public void setArearsInterst(Double ArearsInterst) {
        this.ArearsInterst = ArearsInterst;
    }

    public Double getTotalArears() {
        return TotalArears;
    }

    public void setTotalArears(Double TotalArears) {
        this.TotalArears = TotalArears;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public Double getNoOfTerms() {
        return noOfTerms;
    }

    public void setNoOfTerms(Double noOfTerms) {
        this.noOfTerms = noOfTerms;
    }

    public Double getTermAmount() {
        return termAmount;
    }

    public void setTermAmount(Double termAmount) {
        this.termAmount = termAmount;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public Double getNoOfArrearsTerms() {
        return noOfArrearsTerms;
    }

    public void setNoOfArrearsTerms(Double noOfArrearsTerms) {
        this.noOfArrearsTerms = noOfArrearsTerms;
    }

      public String getDisburseDate_str() {
            return disburseDate_str;
      }

      public void setDisburseDate_str(String disburseDate_str) {
            this.disburseDate_str = disburseDate_str;
      }

      public Double getDueFine() {
            return dueFine;
      }

      public void setDueFine(Double dueFine) {
            this.dueFine = dueFine;
      }

      public Double getArearsFine() {
            return ArearsFine;
      }

      public void setArearsFine(Double ArearsFine) {
            this.ArearsFine = ArearsFine;
      }

}
