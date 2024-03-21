/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.pojo;

/**
 *
 * @author Iroshan
 */
public class SMSSend_pojo
{
    Integer key;
    String messageType;
    int idSMSReceive;
    String phoneNo;
    String message;

    public Integer getKey ()
    {
        return key;
    }

    public void setKey (Integer key)
    {
        this.key = key;
    }

    public String getMessageType ()
    {
        return messageType;
    }

    public void setMessageType (String messageType)
    {
        this.messageType = messageType;
    }

    public int getIdSMSReceive ()
    {
        return idSMSReceive;
    }

    public void setIdSMSReceive (int idSMSReceive)
    {
        this.idSMSReceive = idSMSReceive;
    }

    public String getPhoneNo ()
    {
        return phoneNo;
    }

    public void setPhoneNo (String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    
}
