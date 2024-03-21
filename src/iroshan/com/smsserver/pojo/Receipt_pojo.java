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
public class Receipt_pojo
{

    String receiptNo;
    String customerCode;
    String cusName;
    String cusNameEnglish;
    String cusOfficeNo;
    String ACNumber;
    String tele;
    Double receiptAmount;
    Double outstandingAmount;
    Double arrearsAmount;
    Integer loanOrSaving;
    Date receiptDate;
    
    
    public String getReceiptNo ()
    {
        return receiptNo;
    }

    public void setReceiptNo (String receiptNo)
    {
        this.receiptNo = receiptNo;
    }

    public String getCustomerCode ()
    {
        return customerCode;
    }

    public void setCustomerCode (String customerCode)
    {
        this.customerCode = customerCode;
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




    public String getACNumber ()
    {
        return ACNumber;
    }

    public void setACNumber (String ACNumber)
    {
        this.ACNumber = ACNumber;
    }

    public String getTele ()
    {
        return tele;
    }

    public void setTele (String tele)
    {
        this.tele = tele;
    }

    public Double getReceiptAmount ()
    {
        return receiptAmount;
    }

    public void setReceiptAmount (Double receiptAmount)
    {
        this.receiptAmount = receiptAmount;
    }

    public Double getOutstandingAmount ()
    {
        return outstandingAmount;
    }

    public void setOutstandingAmount (Double DueAmount)
    {
        this.outstandingAmount = DueAmount;
    }

    public Integer getLoanOrSaving ()
    {
        return loanOrSaving;
    }

    public void setLoanOrSaving (Integer loanOrSaving)
    {
        this.loanOrSaving = loanOrSaving;
    }

    public Date getReceiptDate ()
    {
        return receiptDate;
    }

    public void setReceiptDate (Date receiptDate)
    {
        this.receiptDate = receiptDate;
    }

    public Double getArrearsAmount ()
    {
        return arrearsAmount;
    }

    public void setArrearsAmount (Double arrearsAmount)
    {
        this.arrearsAmount = arrearsAmount;
    }

    public String getCusNameEnglish() {
        return cusNameEnglish;
    }

    public void setCusNameEnglish(String cusNameEnglish) {
        this.cusNameEnglish = cusNameEnglish;
    }


}
