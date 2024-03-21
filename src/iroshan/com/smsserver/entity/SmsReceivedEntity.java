/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 *
 * @author Iroshan
 */
@Entity
@Table (name = "sms_received")
//@XmlRootElement
//@NamedQueries ({
//    @NamedQuery (name = "SmsReceived.findAll", query = "SELECT s FROM SmsReceived s"),
//    @NamedQuery (name = "SmsReceived.findBySmsReceivedId", query = "SELECT s FROM SmsReceived s WHERE s.smsReceivedId = :smsReceivedId"),
//    @NamedQuery (name = "SmsReceived.findBySmsReceivedPhone", query = "SELECT s FROM SmsReceived s WHERE s.smsReceivedPhone = :smsReceivedPhone"),
//    @NamedQuery (name = "SmsReceived.findBySmsReceivedKeyword", query = "SELECT s FROM SmsReceived s WHERE s.smsReceivedKeyword = :smsReceivedKeyword"),
//    @NamedQuery (name = "SmsReceived.findBySmsReceivedAcc", query = "SELECT s FROM SmsReceived s WHERE s.smsReceivedAcc = :smsReceivedAcc"),
//    @NamedQuery (name = "SmsReceived.findBySmsReceivedPassword", query = "SELECT s FROM SmsReceived s WHERE s.smsReceivedPassword = :smsReceivedPassword"),
//    @NamedQuery (name = "SmsReceived.findBySmsReceivedDate", query = "SELECT s FROM SmsReceived s WHERE s.smsReceivedDate = :smsReceivedDate")})
public class SmsReceivedEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Generated (value = GenerationTime.INSERT)
    @GeneratedValue
    @Column (name = "idAuto", insertable = false)
    private Integer idAuto;

    @Id
    @Column (name = "received_id")
    private Integer smsReceivedId;

    @Column (name = "phone_no")
    private String smsReceivedPhone;
    
    @Column (name = "message")
    private String smsReceivedText;

    @Column (name = "keyword")
    private String smsReceivedKeyword;

    @Column (name = "account_no")
    private String smsReceivedAcc;

    @Column (name = "password")
    private String smsReceivedPassword;

    @Column (name = "received_date")
    @Temporal (TemporalType.TIMESTAMP)
    private Date smsReceivedDate;

    public SmsReceivedEntity ()
    {
    }
                
    
    
    public Integer getIdAuto () {
        return idAuto;
    }

    public void setIdAuto (Integer idAuto) {
        this.idAuto = idAuto;
    }


    public Integer getSmsReceivedId () {
        return smsReceivedId;
    }

    public void setSmsReceivedId (Integer smsReceivedId) {
        this.smsReceivedId = smsReceivedId;
    }

    public String getSmsReceivedPhone () {
        return smsReceivedPhone;
    }

    public void setSmsReceivedPhone (String smsReceivedPhone) {
        this.smsReceivedPhone = smsReceivedPhone;
    }

    public String getSmsReceivedText ()
    {
        return smsReceivedText;
    }

    public void setSmsReceivedText (String smsReceivedText)
    {
        this.smsReceivedText = smsReceivedText;
    }

    public String getSmsReceivedKeyword () {
        return smsReceivedKeyword;
    }

    public void setSmsReceivedKeyword (String smsReceivedKeyword) {
        this.smsReceivedKeyword = smsReceivedKeyword;
    }

    public String getSmsReceivedAcc () {
        return smsReceivedAcc;
    }

    public void setSmsReceivedAcc (String smsReceivedAcc) {
        this.smsReceivedAcc = smsReceivedAcc;
    }

    public String getSmsReceivedPassword () {
        return smsReceivedPassword;
    }

    public void setSmsReceivedPassword (String smsReceivedPassword) {
        this.smsReceivedPassword = smsReceivedPassword;
    }

    public Date getSmsReceivedDate () {
        return smsReceivedDate;
    }

    public void setSmsReceivedDate (Date smsReceivedDate) {
        this.smsReceivedDate = smsReceivedDate;
    }



}
