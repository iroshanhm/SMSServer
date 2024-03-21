/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author Iroshan
 */
@Entity
@Table (name = "INCOME_EXPENCE")
//@XmlRootElement
//@NamedQueries (
//{
//    @NamedQuery (name = "IncomeExpence.findAll", query = "SELECT i FROM IncomeExpence i"),
//    @NamedQuery (name = "IncomeExpence.findByIeCode", query = "SELECT i FROM IncomeExpence i WHERE i.ieCode = :ieCode"),
//    @NamedQuery (name = "IncomeExpence.findByIeFlag", query = "SELECT i FROM IncomeExpence i WHERE i.ieFlag = :ieFlag"),
//    @NamedQuery (name = "IncomeExpence.findByIeDesc", query = "SELECT i FROM IncomeExpence i WHERE i.ieDesc = :ieDesc"),
//    @NamedQuery (name = "IncomeExpence.findByWho1", query = "SELECT i FROM IncomeExpence i WHERE i.who1 = :who1"),
//    @NamedQuery (name = "IncomeExpence.findByWhen1", query = "SELECT i FROM IncomeExpence i WHERE i.when1 = :when1"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype11", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype11 = :ieTrtype11"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype21", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype21 = :ieTrtype21"),
//    @NamedQuery (name = "IncomeExpence.findByIeSubType", query = "SELECT i FROM IncomeExpence i WHERE i.ieSubType = :ieSubType"),
//    @NamedQuery (name = "IncomeExpence.findByIeIntType", query = "SELECT i FROM IncomeExpence i WHERE i.ieIntType = :ieIntType"),
//    @NamedQuery (name = "IncomeExpence.findByIeIntVal", query = "SELECT i FROM IncomeExpence i WHERE i.ieIntVal = :ieIntVal"),
//    @NamedQuery (name = "IncomeExpence.findByIeIntType2", query = "SELECT i FROM IncomeExpence i WHERE i.ieIntType2 = :ieIntType2"),
//    @NamedQuery (name = "IncomeExpence.findByIeDates", query = "SELECT i FROM IncomeExpence i WHERE i.ieDates = :ieDates"),
//    @NamedQuery (name = "IncomeExpence.findByIeTax", query = "SELECT i FROM IncomeExpence i WHERE i.ieTax = :ieTax"),
//    @NamedQuery (name = "IncomeExpence.findByIeTaxType", query = "SELECT i FROM IncomeExpence i WHERE i.ieTaxType = :ieTaxType"),
//    @NamedQuery (name = "IncomeExpence.findByIeIncomeType", query = "SELECT i FROM IncomeExpence i WHERE i.ieIncomeType = :ieIncomeType"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype12", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype12 = :ieTrtype12"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype13", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype13 = :ieTrtype13"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype22", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype22 = :ieTrtype22"),
//    @NamedQuery (name = "IncomeExpence.findByIeIntMethod", query = "SELECT i FROM IncomeExpence i WHERE i.ieIntMethod = :ieIntMethod"),
//    @NamedQuery (name = "IncomeExpence.findByIeEqual", query = "SELECT i FROM IncomeExpence i WHERE i.ieEqual = :ieEqual"),
//    @NamedQuery (name = "IncomeExpence.findByIeMinimum", query = "SELECT i FROM IncomeExpence i WHERE i.ieMinimum = :ieMinimum"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype23", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype23 = :ieTrtype23"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype31", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype31 = :ieTrtype31"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype41", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype41 = :ieTrtype41"),
//    @NamedQuery (name = "IncomeExpence.findByIeRmax", query = "SELECT i FROM IncomeExpence i WHERE i.ieRmax = :ieRmax"),
//    @NamedQuery (name = "IncomeExpence.findByIePmax", query = "SELECT i FROM IncomeExpence i WHERE i.iePmax = :iePmax"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype32", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype32 = :ieTrtype32"),
//    @NamedQuery (name = "IncomeExpence.findByIeTrtype42", query = "SELECT i FROM IncomeExpence i WHERE i.ieTrtype42 = :ieTrtype42"),
//    @NamedQuery (name = "IncomeExpence.findByIeCardType", query = "SELECT i FROM IncomeExpence i WHERE i.ieCardType = :ieCardType"),
//    @NamedQuery (name = "IncomeExpence.findByIeChqTrtype11", query = "SELECT i FROM IncomeExpence i WHERE i.ieChqTrtype11 = :ieChqTrtype11"),
//    @NamedQuery (name = "IncomeExpence.findByIeChqTrtype12", query = "SELECT i FROM IncomeExpence i WHERE i.ieChqTrtype12 = :ieChqTrtype12"),
//    @NamedQuery (name = "IncomeExpence.findByIeChqTrtype13", query = "SELECT i FROM IncomeExpence i WHERE i.ieChqTrtype13 = :ieChqTrtype13"),
//    @NamedQuery (name = "IncomeExpence.findByIeChqTrtype21", query = "SELECT i FROM IncomeExpence i WHERE i.ieChqTrtype21 = :ieChqTrtype21"),
//    @NamedQuery (name = "IncomeExpence.findByIeChqTrtype22", query = "SELECT i FROM IncomeExpence i WHERE i.ieChqTrtype22 = :ieChqTrtype22"),
//    @NamedQuery (name = "IncomeExpence.findByIeChqTrtype23", query = "SELECT i FROM IncomeExpence i WHERE i.ieChqTrtype23 = :ieChqTrtype23"),
//    @NamedQuery (name = "IncomeExpence.findByIePayment", query = "SELECT i FROM IncomeExpence i WHERE i.iePayment = :iePayment"),
//    @NamedQuery (name = "IncomeExpence.findByIeLrate", query = "SELECT i FROM IncomeExpence i WHERE i.ieLrate = :ieLrate"),
//    @NamedQuery (name = "IncomeExpence.findByIeIo", query = "SELECT i FROM IncomeExpence i WHERE i.ieIo = :ieIo"),
//    @NamedQuery (name = "IncomeExpence.findByIeOtherTrtype11", query = "SELECT i FROM IncomeExpence i WHERE i.ieOtherTrtype11 = :ieOtherTrtype11"),
//    @NamedQuery (name = "IncomeExpence.findByIeOtherTrtype12", query = "SELECT i FROM IncomeExpence i WHERE i.ieOtherTrtype12 = :ieOtherTrtype12"),
//    @NamedQuery (name = "IncomeExpence.findByIeOtherTrtype13", query = "SELECT i FROM IncomeExpence i WHERE i.ieOtherTrtype13 = :ieOtherTrtype13"),
//    @NamedQuery (name = "IncomeExpence.findByIeOtherTrtype21", query = "SELECT i FROM IncomeExpence i WHERE i.ieOtherTrtype21 = :ieOtherTrtype21"),
//    @NamedQuery (name = "IncomeExpence.findByIeOtherTrtype22", query = "SELECT i FROM IncomeExpence i WHERE i.ieOtherTrtype22 = :ieOtherTrtype22"),
//    @NamedQuery (name = "IncomeExpence.findByIeOtherTrtype23", query = "SELECT i FROM IncomeExpence i WHERE i.ieOtherTrtype23 = :ieOtherTrtype23"),
//    @NamedQuery (name = "IncomeExpence.findByIeMtype", query = "SELECT i FROM IncomeExpence i WHERE i.ieMtype = :ieMtype"),
//    @NamedQuery (name = "IncomeExpence.findByIeIntPayment", query = "SELECT i FROM IncomeExpence i WHERE i.ieIntPayment = :ieIntPayment"),
//    @NamedQuery (name = "IncomeExpence.findByIeIntAfter", query = "SELECT i FROM IncomeExpence i WHERE i.ieIntAfter = :ieIntAfter"),
//    @NamedQuery (name = "IncomeExpence.findByIeIntIncrease", query = "SELECT i FROM IncomeExpence i WHERE i.ieIntIncrease = :ieIntIncrease"),
//    @NamedQuery (name = "IncomeExpence.findByIeMainNumber", query = "SELECT i FROM IncomeExpence i WHERE i.ieMainNumber = :ieMainNumber"),
//    @NamedQuery (name = "IncomeExpence.findByIeAccountType", query = "SELECT i FROM IncomeExpence i WHERE i.ieAccountType = :ieAccountType"),
//    @NamedQuery (name = "IncomeExpence.findByIeAccountDivision", query = "SELECT i FROM IncomeExpence i WHERE i.ieAccountDivision = :ieAccountDivision"),
//    @NamedQuery (name = "IncomeExpence.findByIePayWay", query = "SELECT i FROM IncomeExpence i WHERE i.iePayWay = :iePayWay"),
//    @NamedQuery (name = "IncomeExpence.findByIePayTerm", query = "SELECT i FROM IncomeExpence i WHERE i.iePayTerm = :iePayTerm"),
//    @NamedQuery (name = "IncomeExpence.findByIeLoanType", query = "SELECT i FROM IncomeExpence i WHERE i.ieLoanType = :ieLoanType"),
//    @NamedQuery (name = "IncomeExpence.findByIeIncomeType2", query = "SELECT i FROM IncomeExpence i WHERE i.ieIncomeType2 = :ieIncomeType2"),
//    @NamedQuery (name = "IncomeExpence.findByIeInOut", query = "SELECT i FROM IncomeExpence i WHERE i.ieInOut = :ieInOut"),
//    @NamedQuery (name = "IncomeExpence.findByIeNonInt", query = "SELECT i FROM IncomeExpence i WHERE i.ieNonInt = :ieNonInt"),
//    @NamedQuery (name = "IncomeExpence.findByIeMinIntDays", query = "SELECT i FROM IncomeExpence i WHERE i.ieMinIntDays = :ieMinIntDays"),
//    @NamedQuery (name = "IncomeExpence.findByIeName2", query = "SELECT i FROM IncomeExpence i WHERE i.ieName2 = :ieName2"),
//    @NamedQuery (name = "IncomeExpence.findByIeIntType2Multy", query = "SELECT i FROM IncomeExpence i WHERE i.ieIntType2Multy = :ieIntType2Multy"),
//    @NamedQuery (name = "IncomeExpence.findByIePawn", query = "SELECT i FROM IncomeExpence i WHERE i.iePawn = :iePawn"),
//    @NamedQuery (name = "IncomeExpence.findByIeGraceToPayment", query = "SELECT i FROM IncomeExpence i WHERE i.ieGraceToPayment = :ieGraceToPayment"),
//    @NamedQuery (name = "IncomeExpence.findByIeGraceType", query = "SELECT i FROM IncomeExpence i WHERE i.ieGraceType = :ieGraceType"),
//    @NamedQuery (name = "IncomeExpence.findByIeRepaymentDis", query = "SELECT i FROM IncomeExpence i WHERE i.ieRepaymentDis = :ieRepaymentDis"),
//    @NamedQuery (name = "IncomeExpence.findByIeNonIntRatio", query = "SELECT i FROM IncomeExpence i WHERE i.ieNonIntRatio = :ieNonIntRatio"),
//    @NamedQuery (name = "IncomeExpence.findByIeNonIntRatio2", query = "SELECT i FROM IncomeExpence i WHERE i.ieNonIntRatio2 = :ieNonIntRatio2"),
//    @NamedQuery (name = "IncomeExpence.findByIeMonthEnd", query = "SELECT i FROM IncomeExpence i WHERE i.ieMonthEnd = :ieMonthEnd"),
//    @NamedQuery (name = "IncomeExpence.findByIeManualTermsEssential", query = "SELECT i FROM IncomeExpence i WHERE i.ieManualTermsEssential = :ieManualTermsEssential"),
//    @NamedQuery (name = "IncomeExpence.findByIeTypeOfEqualTerms", query = "SELECT i FROM IncomeExpence i WHERE i.ieTypeOfEqualTerms = :ieTypeOfEqualTerms"),
//    @NamedQuery (name = "IncomeExpence.findByIeStampDuty", query = "SELECT i FROM IncomeExpence i WHERE i.ieStampDuty = :ieStampDuty"),
//    @NamedQuery (name = "IncomeExpence.findByIeWhenDoubleEntry", query = "SELECT i FROM IncomeExpence i WHERE i.ieWhenDoubleEntry = :ieWhenDoubleEntry"),
//    @NamedQuery (name = "IncomeExpence.findByIeOrginal", query = "SELECT i FROM IncomeExpence i WHERE i.ieOrginal = :ieOrginal"),
//    @NamedQuery (name = "IncomeExpence.findByWho1ModDel", query = "SELECT i FROM IncomeExpence i WHERE i.who1ModDel = :who1ModDel"),
//    @NamedQuery (name = "IncomeExpence.findByWhen1ModDel", query = "SELECT i FROM IncomeExpence i WHERE i.when1ModDel = :when1ModDel")
//})
public class IncomeExpenceEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic (optional = false)
    @Column (name = "IE_CODE")
    private String ieCode;
    @Basic (optional = false)
    @Column (name = "IE_FLAG")
    private String ieFlag;
    @Column (name = "IE_DESC")
    private String ieDesc;
    @Basic (optional = false)
    @Column (name = "WHO1")
    private String who1;
    @Basic (optional = false)
    @Column (name = "WHEN1")
    @Temporal (TemporalType.TIMESTAMP)
    private Date when1;
    @Column (name = "IE_TRTYPE11")
    private String ieTrtype11;
    @Column (name = "IE_TRTYPE21")
    private String ieTrtype21;
    @Basic (optional = false)
    @Column (name = "IE_SUB_TYPE")
    private String ieSubType;
    @Basic (optional = false)
    @Column (name = "IE_INT_TYPE")
    private String ieIntType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic (optional = false)
    @Column (name = "IE_INT_VAL")
    private BigDecimal ieIntVal;
    @Basic (optional = false)
    @Column (name = "IE_INT_TYPE2")
    private String ieIntType2;
    @Basic (optional = false)
    @Column (name = "IE_DATES")
    private BigDecimal ieDates;
    @Basic (optional = false)
    @Column (name = "IE_TAX")
    private BigDecimal ieTax;
    @Basic (optional = false)
    @Column (name = "IE_TAX_TYPE")
    private String ieTaxType;
    @Column (name = "IE_INCOME_TYPE")
    private String ieIncomeType;
    @Column (name = "IE_TRTYPE12")
    private String ieTrtype12;
    @Column (name = "IE_TRTYPE13")
    private String ieTrtype13;
    @Column (name = "IE_TRTYPE22")
    private String ieTrtype22;
    @Basic (optional = false)
    @Column (name = "IE_INT_METHOD")
    private String ieIntMethod;
    @Basic (optional = false)
    @Column (name = "IE_EQUAL")
    private BigDecimal ieEqual;
    @Basic (optional = false)
    @Column (name = "IE_MINIMUM")
    private BigDecimal ieMinimum;
    @Column (name = "IE_TRTYPE23")
    private String ieTrtype23;
    @Column (name = "IE_TRTYPE31")
    private String ieTrtype31;
    @Column (name = "IE_TRTYPE41")
    private String ieTrtype41;
    @Basic (optional = false)
    @Column (name = "IE_RMAX")
    private BigDecimal ieRmax;
    @Basic (optional = false)
    @Column (name = "IE_PMAX")
    private BigDecimal iePmax;
    @Column (name = "IE_TRTYPE32")
    private String ieTrtype32;
    @Column (name = "IE_TRTYPE42")
    private String ieTrtype42;
    @Column (name = "IE_CARD_TYPE")
    private String ieCardType;
    @Column (name = "IE_CHQ_TRTYPE11")
    private String ieChqTrtype11;
    @Column (name = "IE_CHQ_TRTYPE12")
    private String ieChqTrtype12;
    @Column (name = "IE_CHQ_TRTYPE13")
    private String ieChqTrtype13;
    @Column (name = "IE_CHQ_TRTYPE21")
    private String ieChqTrtype21;
    @Column (name = "IE_CHQ_TRTYPE22")
    private String ieChqTrtype22;
    @Column (name = "IE_CHQ_TRTYPE23")
    private String ieChqTrtype23;
    @Column (name = "IE_PAYMENT")
    private String iePayment;
    @Column (name = "IE_LRATE")
    private BigDecimal ieLrate;
    @Column (name = "IE_IO")
    private String ieIo;
    @Column (name = "IE_OTHER_TRTYPE11")
    private String ieOtherTrtype11;
    @Column (name = "IE_OTHER_TRTYPE12")
    private String ieOtherTrtype12;
    @Column (name = "IE_OTHER_TRTYPE13")
    private String ieOtherTrtype13;
    @Column (name = "IE_OTHER_TRTYPE21")
    private String ieOtherTrtype21;
    @Column (name = "IE_OTHER_TRTYPE22")
    private String ieOtherTrtype22;
    @Column (name = "IE_OTHER_TRTYPE23")
    private String ieOtherTrtype23;
    @Column (name = "IE_MTYPE")
    private String ieMtype;
    @Column (name = "IE_INT_PAYMENT")
    private String ieIntPayment;
    @Column (name = "IE_INT_AFTER")
    private String ieIntAfter;
    @Column (name = "IE_INT_INCREASE")
    private String ieIntIncrease;
    @Column (name = "IE_MAIN_NUMBER")
    private String ieMainNumber;
    @Column (name = "IE_ACCOUNT_TYPE")
    private String ieAccountType;
    @Column (name = "IE_ACCOUNT_DIVISION")
    private String ieAccountDivision;
    @Column (name = "IE_PAY_WAY")
    private String iePayWay;
    @Column (name = "IE_PAY_TERM")
    private BigDecimal iePayTerm;
    @Column (name = "IE_LOAN_TYPE")
    private String ieLoanType;
    @Column (name = "IE_INCOME_TYPE2")
    private String ieIncomeType2;
    @Column (name = "IE_IN_OUT")
    private String ieInOut;
    @Column (name = "IE_NON_INT")
    private String ieNonInt;
    @Column (name = "IE_MIN_INT_DAYS")
    private BigDecimal ieMinIntDays;
    @Column (name = "IE_NAME2")
    private String ieName2;
    @Column (name = "IE_INT_TYPE2_MULTY")
    private BigDecimal ieIntType2Multy;
    @Column (name = "IE_PAWN")
    private String iePawn;
    @Column (name = "IE_GRACE_TO_PAYMENT")
    private String ieGraceToPayment;
    @Column (name = "IE_GRACE_TYPE")
    private String ieGraceType;
    @Column (name = "IE_REPAYMENT_DIS")
    private String ieRepaymentDis;
    @Column (name = "IE_NON_INT_RATIO")
    private BigDecimal ieNonIntRatio;
    @Column (name = "IE_NON_INT_RATIO2")
    private BigDecimal ieNonIntRatio2;
    @Column (name = "IE_MONTH_END")
    private String ieMonthEnd;
    @Column (name = "IE_MANUAL_TERMS_ESSENTIAL")
    private String ieManualTermsEssential;
    @Column (name = "IE_TYPE_OF_EQUAL_TERMS")
    private String ieTypeOfEqualTerms;
    @Column (name = "IE_STAMP_DUTY")
    private String ieStampDuty;
    @Column (name = "IE_WHEN_DOUBLE_ENTRY")
    private String ieWhenDoubleEntry;
    @Column (name = "IE_ORGINAL")
    private String ieOrginal;
    @Column (name = "WHO1_MOD_DEL")
    private String who1ModDel;
    @Column (name = "WHEN1_MOD_DEL")
    @Temporal (TemporalType.TIMESTAMP)
    private Date when1ModDel;

    
    public IncomeExpenceEntity ()
    {
    }

    public IncomeExpenceEntity (String ieCode)
    {
        this.ieCode = ieCode;
    }

    public IncomeExpenceEntity (String ieCode, String ieFlag, String who1, Date when1, String ieSubType, String ieIntType, BigDecimal ieIntVal, String ieIntType2, BigDecimal ieDates, BigDecimal ieTax, String ieTaxType, String ieIntMethod, BigDecimal ieEqual, BigDecimal ieMinimum, BigDecimal ieRmax, BigDecimal iePmax)
    {
        this.ieCode = ieCode;
        this.ieFlag = ieFlag;
        this.who1 = who1;
        this.when1 = when1;
        this.ieSubType = ieSubType;
        this.ieIntType = ieIntType;
        this.ieIntVal = ieIntVal;
        this.ieIntType2 = ieIntType2;
        this.ieDates = ieDates;
        this.ieTax = ieTax;
        this.ieTaxType = ieTaxType;
        this.ieIntMethod = ieIntMethod;
        this.ieEqual = ieEqual;
        this.ieMinimum = ieMinimum;
        this.ieRmax = ieRmax;
        this.iePmax = iePmax;
    }

    public String getIeCode ()
    {
        return ieCode;
    }

    public void setIeCode (String ieCode)
    {
        this.ieCode = ieCode;
    }

    public String getIeFlag ()
    {
        return ieFlag;
    }

    public void setIeFlag (String ieFlag)
    {
        this.ieFlag = ieFlag;
    }

    public String getIeDesc ()
    {
        return ieDesc;
    }

    public void setIeDesc (String ieDesc)
    {
        this.ieDesc = ieDesc;
    }

    public String getWho1 ()
    {
        return who1;
    }

    public void setWho1 (String who1)
    {
        this.who1 = who1;
    }

    public Date getWhen1 ()
    {
        return when1;
    }

    public void setWhen1 (Date when1)
    {
        this.when1 = when1;
    }

    public String getIeTrtype11 ()
    {
        return ieTrtype11;
    }

    public void setIeTrtype11 (String ieTrtype11)
    {
        this.ieTrtype11 = ieTrtype11;
    }

    public String getIeTrtype21 ()
    {
        return ieTrtype21;
    }

    public void setIeTrtype21 (String ieTrtype21)
    {
        this.ieTrtype21 = ieTrtype21;
    }

    public String getIeSubType ()
    {
        return ieSubType;
    }

    public void setIeSubType (String ieSubType)
    {
        this.ieSubType = ieSubType;
    }

    public String getIeIntType ()
    {
        return ieIntType;
    }

    public void setIeIntType (String ieIntType)
    {
        this.ieIntType = ieIntType;
    }

    public BigDecimal getIeIntVal ()
    {
        return ieIntVal;
    }

    public void setIeIntVal (BigDecimal ieIntVal)
    {
        this.ieIntVal = ieIntVal;
    }

    public String getIeIntType2 ()
    {
        return ieIntType2;
    }

    public void setIeIntType2 (String ieIntType2)
    {
        this.ieIntType2 = ieIntType2;
    }

    public BigDecimal getIeDates ()
    {
        return ieDates;
    }

    public void setIeDates (BigDecimal ieDates)
    {
        this.ieDates = ieDates;
    }

    public BigDecimal getIeTax ()
    {
        return ieTax;
    }

    public void setIeTax (BigDecimal ieTax)
    {
        this.ieTax = ieTax;
    }

    public String getIeTaxType ()
    {
        return ieTaxType;
    }

    public void setIeTaxType (String ieTaxType)
    {
        this.ieTaxType = ieTaxType;
    }

    public String getIeIncomeType ()
    {
        return ieIncomeType;
    }

    public void setIeIncomeType (String ieIncomeType)
    {
        this.ieIncomeType = ieIncomeType;
    }

    public String getIeTrtype12 ()
    {
        return ieTrtype12;
    }

    public void setIeTrtype12 (String ieTrtype12)
    {
        this.ieTrtype12 = ieTrtype12;
    }

    public String getIeTrtype13 ()
    {
        return ieTrtype13;
    }

    public void setIeTrtype13 (String ieTrtype13)
    {
        this.ieTrtype13 = ieTrtype13;
    }

    public String getIeTrtype22 ()
    {
        return ieTrtype22;
    }

    public void setIeTrtype22 (String ieTrtype22)
    {
        this.ieTrtype22 = ieTrtype22;
    }

    public String getIeIntMethod ()
    {
        return ieIntMethod;
    }

    public void setIeIntMethod (String ieIntMethod)
    {
        this.ieIntMethod = ieIntMethod;
    }

    public BigDecimal getIeEqual ()
    {
        return ieEqual;
    }

    public void setIeEqual (BigDecimal ieEqual)
    {
        this.ieEqual = ieEqual;
    }

    public BigDecimal getIeMinimum ()
    {
        return ieMinimum;
    }

    public void setIeMinimum (BigDecimal ieMinimum)
    {
        this.ieMinimum = ieMinimum;
    }

    public String getIeTrtype23 ()
    {
        return ieTrtype23;
    }

    public void setIeTrtype23 (String ieTrtype23)
    {
        this.ieTrtype23 = ieTrtype23;
    }

    public String getIeTrtype31 ()
    {
        return ieTrtype31;
    }

    public void setIeTrtype31 (String ieTrtype31)
    {
        this.ieTrtype31 = ieTrtype31;
    }

    public String getIeTrtype41 ()
    {
        return ieTrtype41;
    }

    public void setIeTrtype41 (String ieTrtype41)
    {
        this.ieTrtype41 = ieTrtype41;
    }

    public BigDecimal getIeRmax ()
    {
        return ieRmax;
    }

    public void setIeRmax (BigDecimal ieRmax)
    {
        this.ieRmax = ieRmax;
    }

    public BigDecimal getIePmax ()
    {
        return iePmax;
    }

    public void setIePmax (BigDecimal iePmax)
    {
        this.iePmax = iePmax;
    }

    public String getIeTrtype32 ()
    {
        return ieTrtype32;
    }

    public void setIeTrtype32 (String ieTrtype32)
    {
        this.ieTrtype32 = ieTrtype32;
    }

    public String getIeTrtype42 ()
    {
        return ieTrtype42;
    }

    public void setIeTrtype42 (String ieTrtype42)
    {
        this.ieTrtype42 = ieTrtype42;
    }

    public String getIeCardType ()
    {
        return ieCardType;
    }

    public void setIeCardType (String ieCardType)
    {
        this.ieCardType = ieCardType;
    }

    public String getIeChqTrtype11 ()
    {
        return ieChqTrtype11;
    }

    public void setIeChqTrtype11 (String ieChqTrtype11)
    {
        this.ieChqTrtype11 = ieChqTrtype11;
    }

    public String getIeChqTrtype12 ()
    {
        return ieChqTrtype12;
    }

    public void setIeChqTrtype12 (String ieChqTrtype12)
    {
        this.ieChqTrtype12 = ieChqTrtype12;
    }

    public String getIeChqTrtype13 ()
    {
        return ieChqTrtype13;
    }

    public void setIeChqTrtype13 (String ieChqTrtype13)
    {
        this.ieChqTrtype13 = ieChqTrtype13;
    }

    public String getIeChqTrtype21 ()
    {
        return ieChqTrtype21;
    }

    public void setIeChqTrtype21 (String ieChqTrtype21)
    {
        this.ieChqTrtype21 = ieChqTrtype21;
    }

    public String getIeChqTrtype22 ()
    {
        return ieChqTrtype22;
    }

    public void setIeChqTrtype22 (String ieChqTrtype22)
    {
        this.ieChqTrtype22 = ieChqTrtype22;
    }

    public String getIeChqTrtype23 ()
    {
        return ieChqTrtype23;
    }

    public void setIeChqTrtype23 (String ieChqTrtype23)
    {
        this.ieChqTrtype23 = ieChqTrtype23;
    }

    public String getIePayment ()
    {
        return iePayment;
    }

    public void setIePayment (String iePayment)
    {
        this.iePayment = iePayment;
    }

    public BigDecimal getIeLrate ()
    {
        return ieLrate;
    }

    public void setIeLrate (BigDecimal ieLrate)
    {
        this.ieLrate = ieLrate;
    }

    public String getIeIo ()
    {
        return ieIo;
    }

    public void setIeIo (String ieIo)
    {
        this.ieIo = ieIo;
    }

    public String getIeOtherTrtype11 ()
    {
        return ieOtherTrtype11;
    }

    public void setIeOtherTrtype11 (String ieOtherTrtype11)
    {
        this.ieOtherTrtype11 = ieOtherTrtype11;
    }

    public String getIeOtherTrtype12 ()
    {
        return ieOtherTrtype12;
    }

    public void setIeOtherTrtype12 (String ieOtherTrtype12)
    {
        this.ieOtherTrtype12 = ieOtherTrtype12;
    }

    public String getIeOtherTrtype13 ()
    {
        return ieOtherTrtype13;
    }

    public void setIeOtherTrtype13 (String ieOtherTrtype13)
    {
        this.ieOtherTrtype13 = ieOtherTrtype13;
    }

    public String getIeOtherTrtype21 ()
    {
        return ieOtherTrtype21;
    }

    public void setIeOtherTrtype21 (String ieOtherTrtype21)
    {
        this.ieOtherTrtype21 = ieOtherTrtype21;
    }

    public String getIeOtherTrtype22 ()
    {
        return ieOtherTrtype22;
    }

    public void setIeOtherTrtype22 (String ieOtherTrtype22)
    {
        this.ieOtherTrtype22 = ieOtherTrtype22;
    }

    public String getIeOtherTrtype23 ()
    {
        return ieOtherTrtype23;
    }

    public void setIeOtherTrtype23 (String ieOtherTrtype23)
    {
        this.ieOtherTrtype23 = ieOtherTrtype23;
    }

    public String getIeMtype ()
    {
        return ieMtype;
    }

    public void setIeMtype (String ieMtype)
    {
        this.ieMtype = ieMtype;
    }

    public String getIeIntPayment ()
    {
        return ieIntPayment;
    }

    public void setIeIntPayment (String ieIntPayment)
    {
        this.ieIntPayment = ieIntPayment;
    }

    public String getIeIntAfter ()
    {
        return ieIntAfter;
    }

    public void setIeIntAfter (String ieIntAfter)
    {
        this.ieIntAfter = ieIntAfter;
    }

    public String getIeIntIncrease ()
    {
        return ieIntIncrease;
    }

    public void setIeIntIncrease (String ieIntIncrease)
    {
        this.ieIntIncrease = ieIntIncrease;
    }

    public String getIeMainNumber ()
    {
        return ieMainNumber;
    }

    public void setIeMainNumber (String ieMainNumber)
    {
        this.ieMainNumber = ieMainNumber;
    }

    public String getIeAccountType ()
    {
        return ieAccountType;
    }

    public void setIeAccountType (String ieAccountType)
    {
        this.ieAccountType = ieAccountType;
    }

    public String getIeAccountDivision ()
    {
        return ieAccountDivision;
    }

    public void setIeAccountDivision (String ieAccountDivision)
    {
        this.ieAccountDivision = ieAccountDivision;
    }

    public String getIePayWay ()
    {
        return iePayWay;
    }

    public void setIePayWay (String iePayWay)
    {
        this.iePayWay = iePayWay;
    }

    public BigDecimal getIePayTerm ()
    {
        return iePayTerm;
    }

    public void setIePayTerm (BigDecimal iePayTerm)
    {
        this.iePayTerm = iePayTerm;
    }

    public String getIeLoanType ()
    {
        return ieLoanType;
    }

    public void setIeLoanType (String ieLoanType)
    {
        this.ieLoanType = ieLoanType;
    }

    public String getIeIncomeType2 ()
    {
        return ieIncomeType2;
    }

    public void setIeIncomeType2 (String ieIncomeType2)
    {
        this.ieIncomeType2 = ieIncomeType2;
    }

    public String getIeInOut ()
    {
        return ieInOut;
    }

    public void setIeInOut (String ieInOut)
    {
        this.ieInOut = ieInOut;
    }

    public String getIeNonInt ()
    {
        return ieNonInt;
    }

    public void setIeNonInt (String ieNonInt)
    {
        this.ieNonInt = ieNonInt;
    }

    public BigDecimal getIeMinIntDays ()
    {
        return ieMinIntDays;
    }

    public void setIeMinIntDays (BigDecimal ieMinIntDays)
    {
        this.ieMinIntDays = ieMinIntDays;
    }

    public String getIeName2 ()
    {
        return ieName2;
    }

    public void setIeName2 (String ieName2)
    {
        this.ieName2 = ieName2;
    }

    public BigDecimal getIeIntType2Multy ()
    {
        return ieIntType2Multy;
    }

    public void setIeIntType2Multy (BigDecimal ieIntType2Multy)
    {
        this.ieIntType2Multy = ieIntType2Multy;
    }

    public String getIePawn ()
    {
        return iePawn;
    }

    public void setIePawn (String iePawn)
    {
        this.iePawn = iePawn;
    }

    public String getIeGraceToPayment ()
    {
        return ieGraceToPayment;
    }

    public void setIeGraceToPayment (String ieGraceToPayment)
    {
        this.ieGraceToPayment = ieGraceToPayment;
    }

    public String getIeGraceType ()
    {
        return ieGraceType;
    }

    public void setIeGraceType (String ieGraceType)
    {
        this.ieGraceType = ieGraceType;
    }

    public String getIeRepaymentDis ()
    {
        return ieRepaymentDis;
    }

    public void setIeRepaymentDis (String ieRepaymentDis)
    {
        this.ieRepaymentDis = ieRepaymentDis;
    }

    public BigDecimal getIeNonIntRatio ()
    {
        return ieNonIntRatio;
    }

    public void setIeNonIntRatio (BigDecimal ieNonIntRatio)
    {
        this.ieNonIntRatio = ieNonIntRatio;
    }

    public BigDecimal getIeNonIntRatio2 ()
    {
        return ieNonIntRatio2;
    }

    public void setIeNonIntRatio2 (BigDecimal ieNonIntRatio2)
    {
        this.ieNonIntRatio2 = ieNonIntRatio2;
    }

    public String getIeMonthEnd ()
    {
        return ieMonthEnd;
    }

    public void setIeMonthEnd (String ieMonthEnd)
    {
        this.ieMonthEnd = ieMonthEnd;
    }

    public String getIeManualTermsEssential ()
    {
        return ieManualTermsEssential;
    }

    public void setIeManualTermsEssential (String ieManualTermsEssential)
    {
        this.ieManualTermsEssential = ieManualTermsEssential;
    }

    public String getIeTypeOfEqualTerms ()
    {
        return ieTypeOfEqualTerms;
    }

    public void setIeTypeOfEqualTerms (String ieTypeOfEqualTerms)
    {
        this.ieTypeOfEqualTerms = ieTypeOfEqualTerms;
    }

    public String getIeStampDuty ()
    {
        return ieStampDuty;
    }

    public void setIeStampDuty (String ieStampDuty)
    {
        this.ieStampDuty = ieStampDuty;
    }

    public String getIeWhenDoubleEntry ()
    {
        return ieWhenDoubleEntry;
    }

    public void setIeWhenDoubleEntry (String ieWhenDoubleEntry)
    {
        this.ieWhenDoubleEntry = ieWhenDoubleEntry;
    }

    public String getIeOrginal ()
    {
        return ieOrginal;
    }

    public void setIeOrginal (String ieOrginal)
    {
        this.ieOrginal = ieOrginal;
    }

    public String getWho1ModDel ()
    {
        return who1ModDel;
    }

    public void setWho1ModDel (String who1ModDel)
    {
        this.who1ModDel = who1ModDel;
    }

    public Date getWhen1ModDel ()
    {
        return when1ModDel;
    }

    public void setWhen1ModDel (Date when1ModDel)
    {
        this.when1ModDel = when1ModDel;
    }

    @Override
    public int hashCode ()
    {
        int hash = 0;
        hash += (ieCode != null ? ieCode.hashCode () : 0);
        return hash;
    }

    @Override
    public boolean equals (Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncomeExpenceEntity))
        {
            return false;
        }
        IncomeExpenceEntity other = (IncomeExpenceEntity) object;
        if ((this.ieCode == null && other.ieCode != null) || (this.ieCode != null && !this.ieCode.equals (other.ieCode)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString ()
    {
        return "iroshan.com.smsserver.entity.IncomeExpence[ ieCode=" + ieCode + " ]";
    }
    
}
