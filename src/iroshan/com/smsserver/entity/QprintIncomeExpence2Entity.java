package iroshan.com.smsserver.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * The persistent class for the QPRINT_INCOME_EXPENCE database table.
 *
 */
@Entity
@Table(name = "QPRINT_INCOME_EXPENCE2")
//@NamedQuery(name="QprintIncomeExpence.findAll", query="SELECT q FROM QprintIncomeExpence q")
public class QprintIncomeExpence2Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    public QprintIncomeExpence2Entity ()
    {
    }

    
    
    
    
    
    /*  
     * 
     *  @Column(name="DESC2")
     private Object desc2;

     @Column(name="FT_AT")
     private Object ftAt;

     @Column(name="PA_SUPPLIER")
     private Object paSupplier;

     @Column(name="PE_RN")
     private double peRn;
	

	
     private Object rDESC;
	
     private Object rPRINT;
		
     @Column(name="TT")
     private Object tt;

     @Column(name="PA_CODE")
     private Object paCode;


     *
     *
     *
     */
//	@SuppressWarnings("unused")
//	@Id
//	private Integer id;
    @Id
    @Column(name = "PE_EXPENCE")
    private String peExpence;

    @Id
    private Double q2;

    @Id
    @Column(name = "PA_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paDate;

    @Id
    @Column(name = "PE_TYPE")
    private Integer peType;

    @Id
    @Column(name = "RE_SECOND_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reSecondDate;

    @Id
    @Column(name = "RE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reDate;

    @Id
    @Column(name = "NEXT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
//    @Type(type = "timestamp")
    private Date nextDate;

    @Id
    @Column(name = "RE_CODE")
    private String reCode;

    @Id
    @Column(name = "DOC_TYPE")
    private String docType;

    @Id
    @Column(name = "TFL")
    private String tel;

    public String getPeExpence() {
        return peExpence;
    }

    public void setPeExpence(String peExpence) {
        this.peExpence = peExpence;
    }

    public Double getQ2() {
        return q2;
    }

    public void setQ2(Double q2) {
        this.q2 = q2;
    }

    public Date getPaDate() {
        return paDate;
    }

    public void setPaDate(Date paDate) {
        this.paDate = paDate;
    }

    public Integer getPeType () {
        return peType;
    }

    public void setPeType (Integer peType) {
        this.peType = peType;
    }


    
    public Date getReSecondDate() {
        return reSecondDate;
    }

    public void setReSecondDate(Date reSecondDate) {
        this.reSecondDate = reSecondDate;
    }

    public Date getReDate() {
        return reDate;
    }

    public void setReDate(Date reDate) {
        this.reDate = reDate;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public String getReCode() {
        return reCode;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


}
