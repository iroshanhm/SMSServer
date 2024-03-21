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

/**
 *
 * @author IROshan
 */
@Entity
@Table(name = "SMS_COUNT")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "SmsCount.findAll", query = "SELECT s FROM SmsCount s"),
//    @NamedQuery(name = "SmsCount.findByIdAuto", query = "SELECT s FROM SmsCount s WHERE s.idAuto = :idAuto"),
//    @NamedQuery(name = "SmsCount.findById", query = "SELECT s FROM SmsCount s WHERE s.id = :id"),
//    @NamedQuery(name = "SmsCount.findBySmsType", query = "SELECT s FROM SmsCount s WHERE s.smsType = :smsType"),
//    @NamedQuery(name = "SmsCount.findBySmsCount", query = "SELECT s FROM SmsCount s WHERE s.smsCount = :smsCount"),
//    @NamedQuery(name = "SmsCount.findBySentDatetime", query = "SELECT s FROM SmsCount s WHERE s.sentDatetime = :sentDatetime")})
public class SmsCount_Entity implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Generated(value = "GenerationTime.INSERT")
//    @GenericGenerator(name = "idAuto", strategy = "sequence")

    @Basic(optional = false)
//    @Generated(value = "Generation.INSERT")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID_AUTO", insertable = false)
    private int idAuto;


    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SMS_TYPE")
    private String smsType;
    @Column(name = "SMS_COUNT")
    private Integer smsCount;
    @Column(name = "SENT_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDatetime;

    public SmsCount_Entity() {
    }

    public SmsCount_Entity(Integer id) {
        this.id = id;
    }

    public SmsCount_Entity(Integer id, int idAuto) {
        this.id = id;
        this.idAuto = idAuto;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public Integer getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(Integer smsCount) {
        this.smsCount = smsCount;
    }

    public Date getSentDatetime() {
        return sentDatetime;
    }

    public void setSentDatetime(Date sentDatetime) {
        this.sentDatetime = sentDatetime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmsCount_Entity)) {
            return false;
        }
        SmsCount_Entity other = (SmsCount_Entity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iroshan.com.smsserver.entity.SmsCount[ id=" + id + " ]";
    }

}
