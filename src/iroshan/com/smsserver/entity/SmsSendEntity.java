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

/**
 *
 * @author Iroshan
 */
@Entity
@Table (name = "SMS_SENT")
//@XmlRootElement
//@NamedQueries ({
//    @NamedQuery (name = "SmsSent.findAll", query = "SELECT s FROM SmsSent s"),
//    @NamedQuery (name = "SmsSent.findByIdAuto", query = "SELECT s FROM SmsSent s WHERE s.idAuto = :idAuto"),
//    @NamedQuery (name = "SmsSent.findByIdsmsreceive", query = "SELECT s FROM SmsSent s WHERE s.idsmsreceive = :idsmsreceive"),
//    @NamedQuery (name = "SmsSent.findByRecipientsmssend", query = "SELECT s FROM SmsSent s WHERE s.recipientsmssend = :recipientsmssend"),
//    @NamedQuery (name = "SmsSent.findByMessagesmssend", query = "SELECT s FROM SmsSent s WHERE s.messagesmssend = :messagesmssend"),
//    @NamedQuery (name = "SmsSent.findBySentdatesmssend", query = "SELECT s FROM SmsSent s WHERE s.sentdatesmssend = :sentdatesmssend")})
public class SmsSendEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "idAuto")
    private Integer idAuto;


    @Column (name = "msg_type")
    private String msgType;


    @Column (name = "received_id")
    private int idsmsreceive;


    @Column (name = "recipient")
    private String recipientsmssend;


    @Column (name = "message")
    private String messagesmssend;


    @Column (name = "sent_date")
    @Temporal (TemporalType.TIMESTAMP)
    private Date sentdatesmssend;

    
    
    @Column (name = "msg_status")
    private String msgStatus;

    public SmsSendEntity ()
    {
    }

    
    
    
    public Integer getIdAuto () {
        return idAuto;
    }

    public void setIdAuto (Integer idAuto) {
        this.idAuto = idAuto;
    }



    public String getMsgType () {
        return msgType;
    }

    public void setMsgType (String msgType) {
        this.msgType = msgType;
    }

    public int getIdsmsreceive () {
        return idsmsreceive;
    }

    public void setIdsmsreceive (int idsmsreceive) {
        this.idsmsreceive = idsmsreceive;
    }

    public String getRecipientsmssend () {
        return recipientsmssend;
    }

    public void setRecipientsmssend (String recipientsmssend) {
        this.recipientsmssend = recipientsmssend;
    }

    public String getMessagesmssend () {
        return messagesmssend;
    }

    public void setMessagesmssend (String messagesmssend) {
        this.messagesmssend = messagesmssend;
    }

    public Date getSentdatesmssend () {
        return sentdatesmssend;
    }

    public void setSentdatesmssend (Date sentdatesmssend) {
        this.sentdatesmssend = sentdatesmssend;
    }

    public String getMsgStatus ()
    {
        return msgStatus;
    }

    public void setMsgStatus (String msgStatus)
    {
        this.msgStatus = msgStatus;
    }




}
