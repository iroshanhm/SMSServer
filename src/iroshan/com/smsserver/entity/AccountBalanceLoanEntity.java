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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author IROshan
 */
@Entity
@Table(name = "ACCOUNT_BALANCE_LOAN")

public class AccountBalanceLoanEntity implements Serializable {


      private static final long serialVersionUID = 1L;
      @Id
      @Column(name = "AB_ACCOUNT")
      private String abAccount;
      @Column(name = "WHO1")
      private String who1;
      @Column(name = "WHEN1")
      @Temporal(TemporalType.TIMESTAMP)
      private Date when1;
      @Column(name = "AB_FIRST_ARREARS_TERM_DATE")
      @Temporal(TemporalType.TIMESTAMP)
      private Date abFirstArrearsTermDate;
      @Column(name = "AB_LAST_ADDED_TERM_DATE")
      @Temporal(TemporalType.TIMESTAMP)
      private Date abLastAddedTermDate;
      @Column(name = "AB_LAST_REPAYMENT_DATE")
      @Temporal(TemporalType.TIMESTAMP)
      private Date abLastRepaymentDate;
      @Column(name = "AB_LAST_DISBURSMENT_DATE")
      @Temporal(TemporalType.TIMESTAMP)
      private Date abLastDisbursmentDate;
      @Column(name = "AB_RECORD_UPDATED_LAST_DATE")
      @Temporal(TemporalType.TIMESTAMP)
      private Date abRecordUpdatedLastDate;
      @Column(name = "AB_RECORD_UPDATION_TYPE")
      private String abRecordUpdationType;
      @Column(name = "AB_CURRENT_LOAN_STATUS")
      private String abCurrentLoanStatus;
      @Column(name = "AB_PREVIOUS_LOAN_STATUS")
      private String abPreviousLoanStatus;
      @Column(name = "AB_PERFORMING_LOAN")
      private String abPerformingLoan;


      // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
      @Column(name = "AB_CAP_TOTAL_DUE")
      private Double abCapTotalDue;
      @Column(name = "AB_INT_TOTAL_DUE")
      private Double abIntTotalDue;
      // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
      @Column(name = "AB_CAP_DUE_OUTSTANDING")
      private Double abCapDueOutstanding;
      @Column(name = "AB_INT_DUE_OUTSTANDING")
      private Double abIntDueOutstanding;
      @Column(name = "AB_FINE_DUE_OUTSTANDING")
      private Double abFineDueOutstanding;
      @Column(name = "AB_TOTAL_DISBURSMENT")
      private Double abTotalDisbursment;
      @Column(name = "AB_RENTAL")
      private Double abRental;
      @Column(name = "AB_LAST_ADDED_CAP_TERM")
      private Double abLastAddedCapTerm;
      @Column(name = "AB_LAST_ADDED_INT_TERM")
      private Double abLastAddedIntTerm;
      @Column(name = "AB_LAST_ADDED_FINE_TERM")
      private Double abLastAddedFineTerm;
      @Column(name = "AB_LAST_REPAYMENT_CAP")
      private Double abLastRepaymentCap;
      @Column(name = "AB_LAST_REPAYMENT_INT")
      private Double abLastRepaymentInt;
      @Column(name = "AB_LAST_REPAYMENT_FINE")
      private Double abLastRepaymentFine;
      @Column(name = "AB_TOTAL_TERMS_DUE")
      private Double abTotalTermsDue;
      @Column(name = "AB_TOTAL_REPAYMENT_CAP")
      private Double abTotalRepaymentCap;
      @Column(name = "AB_TOTAL_REPAYMENT_INT")
      private Double abTotalRepaymentInt;
      @Column(name = "AB_TOTAL_REPAYMENT_FINE")
      private Double abTotalRepaymentFine;
      @Column(name = "AB_TOTAL_OF_ALL_CAP_TERMS")
      private Double abTotalOfAllCapTerms;
      @Column(name = "AB_TOTAL_OF_ALL_INT_TERMS")
      private Double abTotalOfAllIntTerms;
      @Column(name = "AB_BALANCE_OF_ALL_CAP_TERMS")
      private Double abBalanceOfAllCapTerms;
      @Column(name = "AB_BALANCE_OF_ALL_INT_TERMS")
      private Double abBalanceOfAllIntTerms;
      @Column(name = "AB_TOTAL_DISBURSMENT_INT")
      private Double abTotalDisbursmentInt;
      @Column(name = "AB_TOTAL_DISBURSMENT_FINE")
      private Double abTotalDisbursmentFine;
      @Column(name = "AB_BALANCE_OF_TRANSFER_TO_NPI")
      private Double abBalanceOfTransferToNpi;
      @Column(name = "AB_NEXT_CAP_DUE")
      private Double abNextCapDue;
      @Column(name = "AB_NEXT_INT_DUE")
      private Double abNextIntDue;
      @Column(name = "AB_OTHER1_TOTAL_DUE")
      private Double abOther1TotalDue;
      @Column(name = "AB_OTHER2_TOTAL_DUE")
      private Double abOther2TotalDue;
      @Column(name = "AB_OTHER3_TOTAL_DUE")
      private Double abOther3TotalDue;
      @Column(name = "AB_OTHER1_DUE_OUTSTANDING")
      private Double abOther1DueOutstanding;
      @Column(name = "AB_OTHER2_DUE_OUTSTANDING")
      private Double abOther2DueOutstanding;
      @Column(name = "AB_OTHER3_DUE_OUTSTANDING")
      private Double abOther3DueOutstanding;
      @Column(name = "AB_TOTAL_REPAYMENT_OTHER1")
      private Double abTotalRepaymentOther1;
      @Column(name = "AB_TOTAL_REPAYMENT_OTHER2")
      private Double abTotalRepaymentOther2;
      @Column(name = "AB_TOTAL_REPAYMENT_OTHER3")
      private Double abTotalRepaymentOther3;
      @Column(name = "AB_LAST_REPAYMENT_OTHER1")
      private Double abLastRepaymentOther1;
      @Column(name = "AB_LAST_REPAYMENT_OTHER2")
      private Double abLastRepaymentOther2;
      @Column(name = "AB_LAST_REPAYMENT_OTHER3")
      private Double abLastRepaymentOther3;
      @Column(name = "AB_LAST_ADDED_OTHER1_TERM")
      private Double abLastAddedOther1Term;
      @Column(name = "AB_LAST_ADDED_OTHER2_TERM")
      private Double abLastAddedOther2Term;
      @Column(name = "AB_LAST_ADDED_OTHER3_TERM")
      private Double abLastAddedOther3Term;
      @Column(name = "AB_NEXT_OTHER1_DUE")
      private Double abNextOther1Due;
      @Column(name = "AB_NEXT_OTHER2_DUE")
      private Double abNextOther2Due;
      @Column(name = "AB_NEXT_OTHER3_DUE")
      private Double abNextOther3Due;
      @Column(name = "AB_OTHER4_OUTSTANDING")
      private Double abOther4Outstanding;
      @Column(name = "AB_TOTAL_OF_ALL_OTHER1_TERMS")
      private Double abTotalOfAllOther1Terms;
      @Column(name = "AB_TOTAL_OF_ALL_OTHER2_TERMS")
      private Double abTotalOfAllOther2Terms;
      @Column(name = "AB_TOTAL_OF_ALL_OTHER3_TERMS")
      private Double abTotalOfAllOther3Terms;


      public AccountBalanceLoanEntity() {
      }

      public AccountBalanceLoanEntity(String abAccount) {
            this.abAccount = abAccount;
      }

      public String getAbAccount() {
            return abAccount;
      }

      public void setAbAccount(String abAccount) {
            this.abAccount = abAccount;
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

      public Date getAbFirstArrearsTermDate() {
            return abFirstArrearsTermDate;
      }

      public void setAbFirstArrearsTermDate(Date abFirstArrearsTermDate) {
            this.abFirstArrearsTermDate = abFirstArrearsTermDate;
      }

      public Date getAbLastAddedTermDate() {
            return abLastAddedTermDate;
      }

      public void setAbLastAddedTermDate(Date abLastAddedTermDate) {
            this.abLastAddedTermDate = abLastAddedTermDate;
      }

      public Date getAbLastRepaymentDate() {
            return abLastRepaymentDate;
      }

      public void setAbLastRepaymentDate(Date abLastRepaymentDate) {
            this.abLastRepaymentDate = abLastRepaymentDate;
      }

      public Date getAbLastDisbursmentDate() {
            return abLastDisbursmentDate;
      }

      public void setAbLastDisbursmentDate(Date abLastDisbursmentDate) {
            this.abLastDisbursmentDate = abLastDisbursmentDate;
      }

      public Date getAbRecordUpdatedLastDate() {
            return abRecordUpdatedLastDate;
      }

      public void setAbRecordUpdatedLastDate(Date abRecordUpdatedLastDate) {
            this.abRecordUpdatedLastDate = abRecordUpdatedLastDate;
      }

      public String getAbRecordUpdationType() {
            return abRecordUpdationType;
      }

      public void setAbRecordUpdationType(String abRecordUpdationType) {
            this.abRecordUpdationType = abRecordUpdationType;
      }

      public String getAbCurrentLoanStatus() {
            return abCurrentLoanStatus;
      }

      public void setAbCurrentLoanStatus(String abCurrentLoanStatus) {
            this.abCurrentLoanStatus = abCurrentLoanStatus;
      }

      public String getAbPreviousLoanStatus() {
            return abPreviousLoanStatus;
      }

      public void setAbPreviousLoanStatus(String abPreviousLoanStatus) {
            this.abPreviousLoanStatus = abPreviousLoanStatus;
      }

      public String getAbPerformingLoan() {
            return abPerformingLoan;
      }

      public void setAbPerformingLoan(String abPerformingLoan) {
            this.abPerformingLoan = abPerformingLoan;
      }

      public Double getAbCapTotalDue() {
            return abCapTotalDue;
      }

      public void setAbCapTotalDue(Double abCapTotalDue) {
            this.abCapTotalDue = abCapTotalDue;
      }

      public Double getAbIntTotalDue() {
            return abIntTotalDue;
      }

      public void setAbIntTotalDue(Double abIntTotalDue) {
            this.abIntTotalDue = abIntTotalDue;
      }

      public Double getAbCapDueOutstanding() {
            return abCapDueOutstanding;
      }

      public void setAbCapDueOutstanding(Double abCapDueOutstanding) {
            this.abCapDueOutstanding = abCapDueOutstanding;
      }

      public Double getAbIntDueOutstanding() {
            return abIntDueOutstanding;
      }

      public void setAbIntDueOutstanding(Double abIntDueOutstanding) {
            this.abIntDueOutstanding = abIntDueOutstanding;
      }

      public Double getAbFineDueOutstanding() {
            return abFineDueOutstanding;
      }

      public void setAbFineDueOutstanding(Double abFineDueOutstanding) {
            this.abFineDueOutstanding = abFineDueOutstanding;
      }

      public Double getAbTotalDisbursment() {
            return abTotalDisbursment;
      }

      public void setAbTotalDisbursment(Double abTotalDisbursment) {
            this.abTotalDisbursment = abTotalDisbursment;
      }

      public Double getAbRental() {
            return abRental;
      }

      public void setAbRental(Double abRental) {
            this.abRental = abRental;
      }

      public Double getAbLastAddedCapTerm() {
            return abLastAddedCapTerm;
      }

      public void setAbLastAddedCapTerm(Double abLastAddedCapTerm) {
            this.abLastAddedCapTerm = abLastAddedCapTerm;
      }

      public Double getAbLastAddedIntTerm() {
            return abLastAddedIntTerm;
      }

      public void setAbLastAddedIntTerm(Double abLastAddedIntTerm) {
            this.abLastAddedIntTerm = abLastAddedIntTerm;
      }

      public Double getAbLastAddedFineTerm() {
            return abLastAddedFineTerm;
      }

      public void setAbLastAddedFineTerm(Double abLastAddedFineTerm) {
            this.abLastAddedFineTerm = abLastAddedFineTerm;
      }

      public Double getAbLastRepaymentCap() {
            return abLastRepaymentCap;
      }

      public void setAbLastRepaymentCap(Double abLastRepaymentCap) {
            this.abLastRepaymentCap = abLastRepaymentCap;
      }

      public Double getAbLastRepaymentInt() {
            return abLastRepaymentInt;
      }

      public void setAbLastRepaymentInt(Double abLastRepaymentInt) {
            this.abLastRepaymentInt = abLastRepaymentInt;
      }

      public Double getAbLastRepaymentFine() {
            return abLastRepaymentFine;
      }

      public void setAbLastRepaymentFine(Double abLastRepaymentFine) {
            this.abLastRepaymentFine = abLastRepaymentFine;
      }

      public Double getAbTotalTermsDue() {
            return abTotalTermsDue;
      }

      public void setAbTotalTermsDue(Double abTotalTermsDue) {
            this.abTotalTermsDue = abTotalTermsDue;
      }

      public Double getAbTotalRepaymentCap() {
            return abTotalRepaymentCap;
      }

      public void setAbTotalRepaymentCap(Double abTotalRepaymentCap) {
            this.abTotalRepaymentCap = abTotalRepaymentCap;
      }

      public Double getAbTotalRepaymentInt() {
            return abTotalRepaymentInt;
      }

      public void setAbTotalRepaymentInt(Double abTotalRepaymentInt) {
            this.abTotalRepaymentInt = abTotalRepaymentInt;
      }

      public Double getAbTotalRepaymentFine() {
            return abTotalRepaymentFine;
      }

      public void setAbTotalRepaymentFine(Double abTotalRepaymentFine) {
            this.abTotalRepaymentFine = abTotalRepaymentFine;
      }

      public Double getAbTotalOfAllCapTerms() {
            return abTotalOfAllCapTerms;
      }

      public void setAbTotalOfAllCapTerms(Double abTotalOfAllCapTerms) {
            this.abTotalOfAllCapTerms = abTotalOfAllCapTerms;
      }

      public Double getAbTotalOfAllIntTerms() {
            return abTotalOfAllIntTerms;
      }

      public void setAbTotalOfAllIntTerms(Double abTotalOfAllIntTerms) {
            this.abTotalOfAllIntTerms = abTotalOfAllIntTerms;
      }

      public Double getAbBalanceOfAllCapTerms() {
            return abBalanceOfAllCapTerms;
      }

      public void setAbBalanceOfAllCapTerms(Double abBalanceOfAllCapTerms) {
            this.abBalanceOfAllCapTerms = abBalanceOfAllCapTerms;
      }

      public Double getAbBalanceOfAllIntTerms() {
            return abBalanceOfAllIntTerms;
      }

      public void setAbBalanceOfAllIntTerms(Double abBalanceOfAllIntTerms) {
            this.abBalanceOfAllIntTerms = abBalanceOfAllIntTerms;
      }

      public Double getAbTotalDisbursmentInt() {
            return abTotalDisbursmentInt;
      }

      public void setAbTotalDisbursmentInt(Double abTotalDisbursmentInt) {
            this.abTotalDisbursmentInt = abTotalDisbursmentInt;
      }

      public Double getAbTotalDisbursmentFine() {
            return abTotalDisbursmentFine;
      }

      public void setAbTotalDisbursmentFine(Double abTotalDisbursmentFine) {
            this.abTotalDisbursmentFine = abTotalDisbursmentFine;
      }

      public Double getAbBalanceOfTransferToNpi() {
            return abBalanceOfTransferToNpi;
      }

      public void setAbBalanceOfTransferToNpi(Double abBalanceOfTransferToNpi) {
            this.abBalanceOfTransferToNpi = abBalanceOfTransferToNpi;
      }

      public Double getAbNextCapDue() {
            return abNextCapDue;
      }

      public void setAbNextCapDue(Double abNextCapDue) {
            this.abNextCapDue = abNextCapDue;
      }

      public Double getAbNextIntDue() {
            return abNextIntDue;
      }

      public void setAbNextIntDue(Double abNextIntDue) {
            this.abNextIntDue = abNextIntDue;
      }

      public Double getAbOther1TotalDue() {
            return abOther1TotalDue;
      }

      public void setAbOther1TotalDue(Double abOther1TotalDue) {
            this.abOther1TotalDue = abOther1TotalDue;
      }

      public Double getAbOther2TotalDue() {
            return abOther2TotalDue;
      }

      public void setAbOther2TotalDue(Double abOther2TotalDue) {
            this.abOther2TotalDue = abOther2TotalDue;
      }

      public Double getAbOther3TotalDue() {
            return abOther3TotalDue;
      }

      public void setAbOther3TotalDue(Double abOther3TotalDue) {
            this.abOther3TotalDue = abOther3TotalDue;
      }

      public Double getAbOther1DueOutstanding() {
            return abOther1DueOutstanding;
      }

      public void setAbOther1DueOutstanding(Double abOther1DueOutstanding) {
            this.abOther1DueOutstanding = abOther1DueOutstanding;
      }

      public Double getAbOther2DueOutstanding() {
            return abOther2DueOutstanding;
      }

      public void setAbOther2DueOutstanding(Double abOther2DueOutstanding) {
            this.abOther2DueOutstanding = abOther2DueOutstanding;
      }

      public Double getAbOther3DueOutstanding() {
            return abOther3DueOutstanding;
      }

      public void setAbOther3DueOutstanding(Double abOther3DueOutstanding) {
            this.abOther3DueOutstanding = abOther3DueOutstanding;
      }

      public Double getAbTotalRepaymentOther1() {
            return abTotalRepaymentOther1;
      }

      public void setAbTotalRepaymentOther1(Double abTotalRepaymentOther1) {
            this.abTotalRepaymentOther1 = abTotalRepaymentOther1;
      }

      public Double getAbTotalRepaymentOther2() {
            return abTotalRepaymentOther2;
      }

      public void setAbTotalRepaymentOther2(Double abTotalRepaymentOther2) {
            this.abTotalRepaymentOther2 = abTotalRepaymentOther2;
      }

      public Double getAbTotalRepaymentOther3() {
            return abTotalRepaymentOther3;
      }

      public void setAbTotalRepaymentOther3(Double abTotalRepaymentOther3) {
            this.abTotalRepaymentOther3 = abTotalRepaymentOther3;
      }

      public Double getAbLastRepaymentOther1() {
            return abLastRepaymentOther1;
      }

      public void setAbLastRepaymentOther1(Double abLastRepaymentOther1) {
            this.abLastRepaymentOther1 = abLastRepaymentOther1;
      }

      public Double getAbLastRepaymentOther2() {
            return abLastRepaymentOther2;
      }

      public void setAbLastRepaymentOther2(Double abLastRepaymentOther2) {
            this.abLastRepaymentOther2 = abLastRepaymentOther2;
      }

      public Double getAbLastRepaymentOther3() {
            return abLastRepaymentOther3;
      }

      public void setAbLastRepaymentOther3(Double abLastRepaymentOther3) {
            this.abLastRepaymentOther3 = abLastRepaymentOther3;
      }

      public Double getAbLastAddedOther1Term() {
            return abLastAddedOther1Term;
      }

      public void setAbLastAddedOther1Term(Double abLastAddedOther1Term) {
            this.abLastAddedOther1Term = abLastAddedOther1Term;
      }

      public Double getAbLastAddedOther2Term() {
            return abLastAddedOther2Term;
      }

      public void setAbLastAddedOther2Term(Double abLastAddedOther2Term) {
            this.abLastAddedOther2Term = abLastAddedOther2Term;
      }

      public Double getAbLastAddedOther3Term() {
            return abLastAddedOther3Term;
      }

      public void setAbLastAddedOther3Term(Double abLastAddedOther3Term) {
            this.abLastAddedOther3Term = abLastAddedOther3Term;
      }

      public Double getAbNextOther1Due() {
            return abNextOther1Due;
      }

      public void setAbNextOther1Due(Double abNextOther1Due) {
            this.abNextOther1Due = abNextOther1Due;
      }

      public Double getAbNextOther2Due() {
            return abNextOther2Due;
      }

      public void setAbNextOther2Due(Double abNextOther2Due) {
            this.abNextOther2Due = abNextOther2Due;
      }

      public Double getAbNextOther3Due() {
            return abNextOther3Due;
      }

      public void setAbNextOther3Due(Double abNextOther3Due) {
            this.abNextOther3Due = abNextOther3Due;
      }

      public Double getAbOther4Outstanding() {
            return abOther4Outstanding;
      }

      public void setAbOther4Outstanding(Double abOther4Outstanding) {
            this.abOther4Outstanding = abOther4Outstanding;
      }

      public Double getAbTotalOfAllOther1Terms() {
            return abTotalOfAllOther1Terms;
      }

      public void setAbTotalOfAllOther1Terms(Double abTotalOfAllOther1Terms) {
            this.abTotalOfAllOther1Terms = abTotalOfAllOther1Terms;
      }

      public Double getAbTotalOfAllOther2Terms() {
            return abTotalOfAllOther2Terms;
      }

      public void setAbTotalOfAllOther2Terms(Double abTotalOfAllOther2Terms) {
            this.abTotalOfAllOther2Terms = abTotalOfAllOther2Terms;
      }

      public Double getAbTotalOfAllOther3Terms() {
            return abTotalOfAllOther3Terms;
      }

      public void setAbTotalOfAllOther3Terms(Double abTotalOfAllOther3Terms) {
            this.abTotalOfAllOther3Terms = abTotalOfAllOther3Terms;
      }








      @Override
      public int hashCode() {
            int hash = 0;
            hash += (abAccount != null ? abAccount.hashCode() : 0);
            return hash;
      }

      @Override
      public boolean equals(Object object) {
            // TODO: Warning - this method won't work in the case the id fields are not set
            if (!(object instanceof AccountBalanceLoanEntity)) {
                  return false;
            }
            AccountBalanceLoanEntity other = (AccountBalanceLoanEntity) object;
            if ((this.abAccount == null && other.abAccount != null) || (this.abAccount != null && !this.abAccount.equals(other.abAccount))) {
                  return false;
            }
            return true;
      }

      @Override
      public String toString() {
            return "com.model.AccountBalanceLoan[ abAccount=" + abAccount + " ]";
      }

}
