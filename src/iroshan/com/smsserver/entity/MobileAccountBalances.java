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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gim
 */
@Entity
@Table(name = "MOBILE_ACCOUNT_BALANCES")

public class MobileAccountBalances implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MA_CODE")
    private String maCode;
    @Column(name = "MA_MEMBER_CODE")
    private String maMemberCode;
    @Basic(optional = false)
    @Column(name = "MA_MEMBER_NAME")
    private String maMemberName;
    @Column(name = "MA_MEMBER_ADD1")
    private String maMemberAdd1;
    @Column(name = "MA_MEMBER_ADD2")
    private String maMemberAdd2;
    @Column(name = "MA_MEMBER_ADD3")
    private String maMemberAdd3;
    @Column(name = "MA_MEMBER_ADD4")
    private String maMemberAdd4;
    @Column(name = "MA_LOAN_FROM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date maLoanFromDate;
    @Column(name = "MA_LOAN_MATUARITY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date maLoanMatuarityDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MA_LOAN_NO_OF_TERMS")
    private Double maLoanNoOfTerms;
    @Column(name = "MA_LOAN_RENTAL")
    private Double maLoanRental;
    @Column(name = "MA_LOAN_APPROVED_AMOUNT")
    private Double maLoanApprovedAmount;
    @Column(name = "MA_LOAN_DISBURSED_AMOUNT")
    private Double maLoanDisbursedAmount;
    @Column(name = "MA_LOAN_DISBURSED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date maLoanDisbursedDate;
    @Column(name = "MA_LOAN_INTEREST_RATE")
    private Double maLoanInterestRate;
    @Column(name = "MA_LOAN_DUE_NO_OF_TERMS")
//    private Double maLoanDueNoOfTerms;
    private Double maLoanDueNoOfTerms;
    @Column(name = "MA_LOAN_DUE_CAPITAL")
    private Double maLoanDueCapital;
    @Column(name = "MA_LOAN_DUE_INTEREST")
    private Double maLoanDueInterest;
    @Column(name = "MA_LOAN_TOTAL_REPAYMENT_CAPITAL")
    private Double maLoanTotalRepaymentCapital;
    @Column(name = "MA_LOAN_TOTAL_REPAYMENT_INTEREST")
    private Double maLoanTotalRepaymentInterest;
    @Column(name = "MA_LOAN_TOTAL_EXCESS_REPAYMENT_CAPITAL")
    private Double maLoanTotalExcessRepaymentCapital;
    @Column(name = "MA_LOAN_TOTAL_EXCESS_REPAYMENT_INTEREST")
    private Double maLoanTotalExcessRepaymentInterest;
    @Column(name = "MA_LOAN_ARREARS_CAPITAL")
    private Double maLoanArrearsCapital;
    @Column(name = "MA_LOAN_ARREARS_INTEREST")
    private Double maLoanArrearsInterest;
    @Basic(optional = false)
    @Column(name = "WHO1")
    private String who1;
    @Basic(optional = false)
    @Column(name = "WHEN1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when1;
    @Column(name = "MA_LOAN_OUTSTANDING")
    private Double maLoanOutstanding;
    @Column(name = "MA_MEMBER_ID")
    private String maMemberId;
    @Column(name = "WHO1_MOD_DEL")
    private String who1ModDel;
    @Column(name = "WHEN1_MOD_DEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date when1ModDel;
    @Column(name = "MA_LOAN_ARREARS_NO_OF_TERMS")
    private Double maLoanArrearsNoOfTerms;
    
    @Column(name = "MA_LOAN_ARREARS_FINE")
    private Double maLoanArrearsfine;
    
    
    public MobileAccountBalances() {
    }

    public MobileAccountBalances(String maCode) {
        this.maCode = maCode;
    }

    public MobileAccountBalances(String maCode, String maMemberName, String who1, Date when1) {
        this.maCode = maCode;
        this.maMemberName = maMemberName;
        this.who1 = who1;
        this.when1 = when1;
    }

    public String getMaCode() {
        return maCode;
    }

    public void setMaCode(String maCode) {
        this.maCode = maCode;
    }

    public String getMaMemberCode() {
        return maMemberCode;
    }

    public void setMaMemberCode(String maMemberCode) {
        this.maMemberCode = maMemberCode;
    }

    public String getMaMemberName() {
        return maMemberName;
    }

    public void setMaMemberName(String maMemberName) {
        this.maMemberName = maMemberName;
    }

    public String getMaMemberAdd1() {
        return maMemberAdd1;
    }

    public void setMaMemberAdd1(String maMemberAdd1) {
        this.maMemberAdd1 = maMemberAdd1;
    }

    public String getMaMemberAdd2() {
        return maMemberAdd2;
    }

    public void setMaMemberAdd2(String maMemberAdd2) {
        this.maMemberAdd2 = maMemberAdd2;
    }

    public String getMaMemberAdd3() {
        return maMemberAdd3;
    }

    public void setMaMemberAdd3(String maMemberAdd3) {
        this.maMemberAdd3 = maMemberAdd3;
    }

    public String getMaMemberAdd4() {
        return maMemberAdd4;
    }

    public void setMaMemberAdd4(String maMemberAdd4) {
        this.maMemberAdd4 = maMemberAdd4;
    }

    public Date getMaLoanFromDate() {
        return maLoanFromDate;
    }

    public void setMaLoanFromDate(Date maLoanFromDate) {
        this.maLoanFromDate = maLoanFromDate;
    }

    public Date getMaLoanMatuarityDate() {
        return maLoanMatuarityDate;
    }

    public void setMaLoanMatuarityDate(Date maLoanMatuarityDate) {
        this.maLoanMatuarityDate = maLoanMatuarityDate;
    }

    public Double getMaLoanNoOfTerms() {
        return maLoanNoOfTerms;
    }

    public void setMaLoanNoOfTerms(Double maLoanNoOfTerms) {
        this.maLoanNoOfTerms = maLoanNoOfTerms;
    }

    public Double getMaLoanRental() {
        return maLoanRental;
    }

    public void setMaLoanRental(Double maLoanRental) {
        this.maLoanRental = maLoanRental;
    }

    public Double getMaLoanApprovedAmount() {
        return maLoanApprovedAmount;
    }

    public void setMaLoanApprovedAmount(Double maLoanApprovedAmount) {
        this.maLoanApprovedAmount = maLoanApprovedAmount;
    }

    public Double getMaLoanDisbursedAmount() {
        return maLoanDisbursedAmount;
    }

    public void setMaLoanDisbursedAmount(Double maLoanDisbursedAmount) {
        this.maLoanDisbursedAmount = maLoanDisbursedAmount;
    }

    public Date getMaLoanDisbursedDate() {
        return maLoanDisbursedDate;
    }

    public void setMaLoanDisbursedDate(Date maLoanDisbursedDate) {
        this.maLoanDisbursedDate = maLoanDisbursedDate;
    }

    public Double getMaLoanInterestRate() {
        return maLoanInterestRate;
    }

    public void setMaLoanInterestRate(Double maLoanInterestRate) {
        this.maLoanInterestRate = maLoanInterestRate;
    }

  


    public Double getMaLoanDueCapital() {
        return maLoanDueCapital;
    }

    public void setMaLoanDueCapital(Double maLoanDueCapital) {
        this.maLoanDueCapital = maLoanDueCapital;
    }

    public Double getMaLoanDueInterest() {
        return maLoanDueInterest;
    }

    public void setMaLoanDueInterest(Double maLoanDueInterest) {
        this.maLoanDueInterest = maLoanDueInterest;
    }

    public Double getMaLoanTotalRepaymentCapital() {
        return maLoanTotalRepaymentCapital;
    }

    public void setMaLoanTotalRepaymentCapital(Double maLoanTotalRepaymentCapital) {
        this.maLoanTotalRepaymentCapital = maLoanTotalRepaymentCapital;
    }

    public Double getMaLoanTotalRepaymentInterest() {
        return maLoanTotalRepaymentInterest;
    }

    public void setMaLoanTotalRepaymentInterest(Double maLoanTotalRepaymentInterest) {
        this.maLoanTotalRepaymentInterest = maLoanTotalRepaymentInterest;
    }

    public Double getMaLoanTotalExcessRepaymentCapital() {
        return maLoanTotalExcessRepaymentCapital;
    }

    public void setMaLoanTotalExcessRepaymentCapital(Double maLoanTotalExcessRepaymentCapital) {
        this.maLoanTotalExcessRepaymentCapital = maLoanTotalExcessRepaymentCapital;
    }

    public Double getMaLoanTotalExcessRepaymentInterest() {
        return maLoanTotalExcessRepaymentInterest;
    }

    public void setMaLoanTotalExcessRepaymentInterest(Double maLoanTotalExcessRepaymentInterest) {
        this.maLoanTotalExcessRepaymentInterest = maLoanTotalExcessRepaymentInterest;
    }

    public Double getMaLoanArrearsCapital() {
        return maLoanArrearsCapital;
    }

    public void setMaLoanArrearsCapital(Double maLoanArrearsCapital) {
        this.maLoanArrearsCapital = maLoanArrearsCapital;
    }

    public Double getMaLoanArrearsInterest() {
        return maLoanArrearsInterest;
    }

    public void setMaLoanArrearsInterest(Double maLoanArrearsInterest) {
        this.maLoanArrearsInterest = maLoanArrearsInterest;
    }

    public String getWho1() {
        return who1;
    }

    public void setWho1(String who1) {
        this.who1 = who1;
    }

    public Date getWhen1() {
        return when1;
    }

    public void setWhen1(Date when1) {
        this.when1 = when1;
    }

    public Double getMaLoanOutstanding() {
        return maLoanOutstanding;
    }

    public void setMaLoanOutstanding(Double maLoanOutstanding) {
        this.maLoanOutstanding = maLoanOutstanding;
    }

    public String getMaMemberId() {
        return maMemberId;
    }

    public void setMaMemberId(String maMemberId) {
        this.maMemberId = maMemberId;
    }

    public String getWho1ModDel() {
        return who1ModDel;
    }

    public void setWho1ModDel(String who1ModDel) {
        this.who1ModDel = who1ModDel;
    }

    public Date getWhen1ModDel() {
        return when1ModDel;
    }

    public void setWhen1ModDel(Date when1ModDel) {
        this.when1ModDel = when1ModDel;
    }

    public Double getMaLoanArrearsNoOfTerms() {
        return maLoanArrearsNoOfTerms;
    }

    public void setMaLoanArrearsNoOfTerms(Double maLoanArrearsNoOfTerms) {
        this.maLoanArrearsNoOfTerms = maLoanArrearsNoOfTerms;
    }

    public Double getMaLoanArrearsfine() {
        return maLoanArrearsfine;
    }

    public void setMaLoanArrearsfine(Double maLoanArrearsfine) {
        this.maLoanArrearsfine = maLoanArrearsfine;
    }

    public Double getMaLoanDueNoOfTerms() {
        return maLoanDueNoOfTerms;
    }

    public void setMaLoanDueNoOfTerms(Double maLoanDueNoOfTerms) {
        this.maLoanDueNoOfTerms = maLoanDueNoOfTerms;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maCode != null ? maCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MobileAccountBalances)) {
            return false;
        }
        MobileAccountBalances other = (MobileAccountBalances) object;
        if ((this.maCode == null && other.maCode != null) || (this.maCode != null && !this.maCode.equals(other.maCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iroshan.com.smsserver.entity.MobileAccountBalances[ maCode=" + maCode + " ]";
    }
    
}
