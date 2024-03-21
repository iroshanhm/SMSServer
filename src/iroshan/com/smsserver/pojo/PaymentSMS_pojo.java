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
public class PaymentSMS_pojo {

    Boolean isSelected;
    String paymentNo;
    String cusCode;
    String cusName;
    String cusOfficeNo;
    String telNo;
    String message;
    String msgStatus = MessageStatusEnum.PENDING.toString ();

    public PaymentSMS_pojo ()
    {
    }

    public PaymentSMS_pojo (Boolean isSelected, String paymentNo, String cusCode,String cusNamePara,String cusOfficeNoPara, String telNo, String message) {
        this.isSelected = isSelected;
        this.paymentNo = paymentNo;
        this.cusCode = cusCode;
        this.cusName = cusNamePara;
        this.cusOfficeNo = cusOfficeNoPara;
        this.telNo = telNo;
        this.message = message;
    }

    public Boolean getIsSelected ()
    {
        return isSelected;
    }

    public void setIsSelected (Boolean isSelected)
    {
        this.isSelected = isSelected;
    }

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

    public String getTelNo () {
        return telNo;
    }

    public void setTelNo (String telNo) {
        this.telNo = telNo;
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

    public String getCusOfficeNo ()
    {
        return cusOfficeNo;
    }

    public void setCusOfficeNo (String cusOfficeNo)
    {
        this.cusOfficeNo = cusOfficeNo;
    }
    
    
    
}
