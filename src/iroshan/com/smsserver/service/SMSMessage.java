/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.Company_Profile;
import iroshan.com.common.utility.MyRounding;

/**
 *
 * @author Iroshan
 */
public class SMSMessage {

    String message = null;

//============================================================================================================
    public String msgForReceiptFormat1(String cusNamePara, String ACNoPara, String amountPara, String datePara) {

//        if (Company_Profile.getCompanyCode().trim().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.LLIPL.toString().trim())) {
        if (Company_Profile.getJurnalMessageType().trim().equalsIgnoreCase(Company_Profile.JurnalMessageTypeEnum.JA.toString().trim())) {
            String cusName = cusNamePara;
            String acNo = ACNoPara.trim();
            String amount = amountPara;
            String date = datePara;

            message = "Dear " + cusName + ",\n Your A/C ";
//            message = message.concat (acNo);
            message = message.concat(" is Credited by ");
            message = message.concat("Rs. " + amount + ". ");

            if (date != null) {
                message = message.concat(" for Date : ");
                message = message.concat(date);
                message = message.concat(".  ");
            }

//        if (amountDue != null)
//        {
//            message = message.concat ("\nYou have Outstanding Rs. " + amountDue + ".");
//        }
            message = message.concat("\nThank You.     ");
            message = message.concat("\n" + Company_Profile.getCompanyName());
        }
//        }
        return message;
    }

    public String msgForReceiptFormat2(String cusNamePara, String cusOfficeNoPara, String ACNoPara, String amountPara, String datePara) {

//        if (Company_Profile.getCompanyCode ().trim ().equalsIgnoreCase (Company_Profile.CompanyCodeEnum.LLIPL.toString ().trim ()))
//        {
//            if (Company_Profile.getJurnalMessageType ().trim ().equalsIgnoreCase (Company_Profile.JurnalMessageTypeEnum.JA.toString ().trim ()))
//            {
        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;
        String cusOfficeNo = cusOfficeNoPara;

        if (cusName == null || cusName.isEmpty()) {
            cusName = "Customer";
        }

        message = "Dear " + cusName;

        if (cusOfficeNo != null) {
            if (!cusOfficeNo.isEmpty()) {
                message = message.concat("(" + cusOfficeNo + ")");
            }
        }

        message = message.concat(", ");
        message = message.concat("Your A/C :");
        message = message.concat(acNo);
        message = message.concat(" is Credited by ");
        message = message.concat("Rs. " + amount + ". ");

        if (date != null) {
            message = message.concat(" for Date : ");
            message = message.concat(date);
            message = message.concat(".  ");
        }

//        if (amountDue != null)
//        {
//            message = message.concat ("\nYou have Outstanding Rs. " + amountDue + ".");
//        }
        message = message.concat("\nThank You.     ");
        message = message.concat("\n" + Company_Profile.getCompanyName());
//            }
//        }

        return message;
    }

    public String msgForReceipt(String cusNamePara, String ACNoPara, String amountPara, String ieCode, String amountDue, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Dear " + cusName + ",\n Your A/C : ";
        message = message.concat(acNo);
        message = message.concat(" is Credited by ");
        message = message.concat("Rs. " + amount + ". ");

        if (date != null) {
            message = message.concat(" for Date : ");
            message = message.concat(date);
            message = message.concat(".");
        }
        if (amountDue != null) {
            message = message.concat("\nYou have Outstanding Rs. " + amountDue + ".");
        }

        message = message.concat("\nThank You.     ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForReceipt(String cusNamePara, String ACNoPara, String amountPara, String amountDue, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Dear " + cusName + ",\n Your A/C : ";
        message = message.concat(acNo);
        message = message.concat(" is Credited by ");
        message = message.concat("Rs. " + amount + ". ");

        if (date != null) {
            message = message.concat(" for Date : ");
            message = message.concat(date);
            message = message.concat(".");
        }
        if (amountDue != null) {
            message = message.concat("\nYou have Outstanding Rs. " + amountDue + ".");
        }

        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForReceiptWithArrears(String cusNamePara, String cusOfficeNoPara, String ACNoPara, String amountReceiptPara, String amountArrearsPara, String datePara) {

        String cusName = cusNamePara;
        String cusOfficeNo = cusOfficeNoPara;
        String acNo = ACNoPara.trim();
        String amount = amountReceiptPara;
        String date = datePara;

        message = "Dear " + cusName;

        if (!cusOfficeNo.isEmpty()) {
            message = message.concat("(" + cusOfficeNo + ")");
        } else {
//               cusName = cusName.trim();
        }
        message = message.concat(", ");
        message = message.concat("Your A/C :");
        message = message.concat(acNo);
        message = message.concat(" Receipt ");
        message = message.concat("Rs. " + amount + " ");

        if (date != null) {
            message = message.concat(" for Date : ");
            message = message.concat(date);
            message = message.concat(".");
        }
        if (amountArrearsPara != null) {
            message = message.concat("\n Arrears Rs. " + amountArrearsPara + ".");
        }

        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForReceiptWithArrearsFormat2(String cusNamePara, String cusOfficeNoPara, String ACNoPara, String amountReceiptPara, String amountArrearsPara, String datePara) {

        String cusName = cusNamePara;
        String cusOfficeNo = cusOfficeNoPara;
        String acNo = ACNoPara.trim();
        String amount = amountReceiptPara;
        String date = datePara;

        message = "Dear " + cusName + "";

//          if (!cusOfficeNo.isEmpty()) {
//               message = message.concat("(" + cusOfficeNo + ")");
//          } 
        message = message.concat(", ");
        message = message.concat(" Receipt ");
        message = message.concat("Rs. " + amount + "");

        if (amountArrearsPara != null) {
            message = message.concat(", Arrears Rs. " + amountArrearsPara + ".");
        }

        if (date != null) {
            message = message.concat(" on ");
            message = message.concat(date);
            message = message.concat(",");
        }

        message = message.concat("\n Thank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

//============================================================================================================
    public String msgForPayment(String cusNamePara, String ACNoPara, String amountPara, String ieCode, String amountDue, String datePara) {
        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        if (cusName == null || cusName.isEmpty()) {
            cusName = "Customer";
        }
        message = "Dear " + cusName + ",\n Your A/C : ";
        message = message.concat(acNo);
        message = message.concat(" is Debited by ");
        message = message.concat("Rs. " + amount + " ");

        if (date != null) {
            message = message.concat(" for Date : ");
            message = message.concat(date);
            message = message.concat(".");
        }

        if (amountDue != null) {
            message = message.concat("\nYou have Outstanding Rs. " + amountDue + ".");
        }

        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForPayment(String cusNamePara, String ACNoPara, String amountPara, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        if (cusName == null || cusName.isEmpty()) {
            cusName = "Customer";
        }
//        message = "Dear " + cusName + ",\n Your A/C : ";
        message = "Dear " + cusName + ",";
        message = message.concat (acNo);
        message = message.concat(" is Debited by ");
        message = message.concat("Rs. " + amount + " ");

        if (date != null) {
            message = message.concat(" for Date : ");
            message = message.concat(date);
        }

        message = message.concat(".  ");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForPayment_SewaCredit(String paymentNo_Para, String cusNamePara, String ACNoPara, String amountPara, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;
        String paymentNo = paymentNo_Para;

        message = "Thank you for the payment of Rs. " + amount + " or your loan AC No " + acNo + " on " + date + ". "
                + "Receipt No is " + paymentNo + ". Call More Info 114340486.";

        return message;
    }

    public String msgForArrears(String cusNamePara, String ACNoPara, String amountPara, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Dear " + cusName + ",";

//        message = message.concat("\n Your A/C : ");
//        message = message.concat(acNo);
//        message = message.concat(" is ");
        message = message.concat(" Arrears with ");

        message = message.concat("Rs. " + amount + " ");
        message = message.concat("for the Date of ");

        message = message.concat(date);
        message = message.concat(".");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForArrearsFormat2(String cusNamePara, String ACNoPara, String amountPara, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

//        Dear (Name) , Your total arrears outstanding as at (Date)  is (Amount)  
        message = "Dear " + cusName + ",";

//        message = message.concat("\n Your A/C : ");
//        message = message.concat(acNo);
//        message = message.concat(" is ");
        message = message.concat(" Your total arrears outstanding as at ");
        message = message.concat(date);
        message = message.concat(" is ");
        message = message.concat("Rs. " + amount + " ");
        message = message.concat(" Please do the settlement immediately. Ignore if settled.");

        return message;
    }

    /**
     * Arrears with arrears instalments
     *
     * @param cusNamePara
     * @param cusSubOfficeNoPara
     * @param ACNoPara
     * @param noOfArrearsTermsPara
     * @param amountPara
     * @param datePara
     * @return
     */
    public String msgForArrearsFormat2(String cusNamePara, String cusSubOfficeNoPara, String ACNoPara, Double noOfArrearsTermsPara, String amountPara, String datePara) {

        String cusName = cusNamePara;
        String cusSubOfficeNo = cusSubOfficeNoPara;
        String acNo = ACNoPara.trim();
        Double noOfArrearsTerms = noOfArrearsTermsPara;
        String amount = amountPara;
        String date = datePara;

        message = "Dear " + cusName;

//          if (!cusSubOfficeNo.isEmpty()) {
//               message = message.concat("(" + cusSubOfficeNo + ")");
//          }
        message = message.concat(", ");
        message = message.concat(" Arrears ");
        message = message.concat("Rs. " + amount + "");
        if (noOfArrearsTerms != null) {
            message = message.concat(" in ");
            message = message.concat(MyRounding.roundToWithoutDecimal(noOfArrearsTerms));
            message = message.concat(" installment/s");
        }
        message = message.concat(" on ");
        message = message.concat(date);
        message = message.concat(".");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForOutstanding(String cusNamePara, String ACNoPara, String amountPara, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Dear " + cusName + ",\n Your A/C : ";
        message = message.concat(acNo);
        message = message.concat(" is Outstanding with ");
        message = message.concat("Rs. " + amount + " ");
        message = message.concat(" for the Date of ");
        message = message.concat(date);
        message = message.concat(".");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;

    }

    public String msgForEarlySettlementFormat1(String cusNamePara, String ACNoPara, String amountPara, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Dear " + cusName + ",";
//        message = "Dear " + cusName + ",\n Your A/C : ";
//        message = message.concat(acNo);
        message = message.concat(" Your early settlement amount is ");
        message = message.concat("Rs. " + amount + " ");
        message = message.concat(" for the Date of ");
        message = message.concat(date);
        message = message.concat(".");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;

    }

    public String msgForDueLoans(String cusNamePara, String ACNoPara, String amountPara, String datePara) {
        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Dear " + cusName + ",\n Your A/C : ";
        message = message.concat(acNo);
        message = message.concat(" is Due with ");
        message = message.concat("Rs. " + amount + " ");
        message = message.concat(" for the Date of  ");
        message = message.concat(date);
        message = message.concat(".");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;

    }

    public String msgForDueLoans_SewaCredit(String cusNamePara, String ACNoPara, String amountPara, String datePara) {
        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Ref Your Loan No " + acNo + ", Payment of Rs." + amount + " is due as at " + date + ", Kindly settle soon as possible. If settle please ignore. Call More Info 114340486.";
        return message;

    }

    public String msgForSavingDetails(String ACNoPara, String amountPara, String datePara) {

        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Dear Customer,\n Your A/C : ";
        message = message.concat(acNo);
        message = message.concat(" Balance ");
        message = message.concat("Rs. " + amount + " ");
        message = message.concat(" for Date : ");
        message = message.concat(date);
        message = message.concat(".");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;

    }

    public String msgForLoanDetails(String ACNoPara, String arrearsPara, String duePara, String outstandingPara, String datePara) {

        String acNo = ACNoPara.trim();
        String arrears = arrearsPara;
        String due = duePara;
        String outstanding = outstandingPara;
        String date = datePara;

        message = "Dear Customer,\n Your A/C : ";
        message = message.concat(acNo);

        message = message.concat(" Arrears ");
        message = message.concat("Rs. " + arrears + " ");

        message = message.concat(" ,\n ");

        message = message.concat(" Due ");
        message = message.concat("Rs. " + due + " ");

        message = message.concat(" ,\n ");

        message = message.concat(" Outstanding ");
        message = message.concat("Rs. " + outstanding + " ");

        message = message.concat(" ,\n ");

        message = message.concat(" for Date : ");
        message = message.concat(date);

        message = message.concat(".");
        message = message.concat("\nThank You");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

//    =============================================================================================
    public String msgForJurnalFormat1(String cusNamePara, String ACNoPara, String amountPara, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Dear " + cusName + ",\n Your A/C ";
//            message = message.concat (acNo);
        message = message.concat(" is Credited by ");
        message = message.concat("Rs. " + amount + ". ");

        if (date != null) {
            message = message.concat(" for Date : ");
            message = message.concat(date);
            message = message.concat(".  ");
        }

//        if (amountDue != null)
//        {
//            message = message.concat ("\nYou have Outstanding Rs. " + amountDue + ".");
//        }
        message = message.concat("\nThank You.     ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForJurnalFormat1_SewaCredit(String receipt_No_Para, String cusNamePara, String ACNoPara, String amountPara, String datePara) {

        String receipt_No = receipt_No_Para;
        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountPara;
        String date = datePara;

        message = "Thank you for the payment of Rs. " + amount + " or your loan AC No " + acNo + " on " + date + ". "
                + "Receipt No is " + receipt_No + ". Call More Info 114340486.";

        return message;
    }

    public String msgForJurnalWithArrears(String cusNamePara, String ACNoPara, String amountReceiptPara, String amountArrearsPara, String datePara) {

        String cusName = cusNamePara;
        String acNo = ACNoPara.trim();
        String amount = amountReceiptPara;
        String date = datePara;

        message = "Dear " + cusName + ",";
//        message = message.concat ("\n Your A/C : ");
//        message = message.concat (acNo);
//        message = message.concat (" is ");
        message = message.concat("Your Payment ");
        message = message.concat("Rs." + amount + ".");

        if (date != null) {
            message = message.concat("for Date:");
            message = message.concat(date);
            message = message.concat(".");
        }
        if (amountArrearsPara != null) {
            message = message.concat("You have a Arrears Rs." + amountArrearsPara + ".");
        }

        message = message.concat("Thank You.");
        message = message.concat(Company_Profile.getCompanyName());

        return message;
    }

//     LOAN DISBURSEMENT MESSAGE ===============================================
    public String msgForLoanDisbursementFormat1(String cusNamePara, String cusSubOfficeNoPara, String ACNoPara, String noOfArrearsTermsPara,
            String loanValuePara, String dayPara, String suffixPara) {

        String cusName = cusNamePara;
        String noOfArrearsTerms = noOfArrearsTermsPara;
        String loanValue = loanValuePara;
        String day = dayPara;
        String suffix = suffixPara;
        message = "Dear " + cusName;

//          if (!cusSubOfficeNo.isEmpty()) {
//               message = message.concat("(" + cusSubOfficeNo + ")");
//          }
        message = message.concat(", ");
        message = message.concat(" Loan amount ");
        message = message.concat("Rs. " + loanValue + "");
        message = message.concat(" with ");
        message = message.concat(noOfArrearsTerms);
        message = message.concat(" rentals.");
//        message = message.concat(" rentals due on ");
//        message = message.concat(day);
//        message = message.concat(suffix);
//        message = message.concat(" every month.");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    public String msgForLoanDisbursementFormat2(String cusNamePara, String cusSubOfficeNoPara, String ACNoPara, String noOfArrearsTermsPara,
            String loanValuePara, String dayPara, String suffixPara) {

        String cusName = cusNamePara;
        String noOfArrearsTerms = noOfArrearsTermsPara;
        String loanValue = loanValuePara;
        String day = dayPara;
        String suffix = suffixPara;
        message = "Dear " + cusName;

//          if (!cusSubOfficeNo.isEmpty()) {
//               message = message.concat("(" + cusSubOfficeNo + ")");
//          }
        message = message.concat(", ");
        message = message.concat(" rental amount ");
        message = message.concat("Rs. " + loanValue + "");
        message = message.concat(" with ");
        message = message.concat(noOfArrearsTerms);
        message = message.concat(" rentals.");
//        message = message.concat(" rentals due on ");
//        message = message.concat(day);
//        message = message.concat(suffix);
//        message = message.concat(" every month.");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

    String msgForLoanDisbursementFormat3(String cusNamePara, String cusOfficeNo, String acCode, String noOfTermsStr, String termAmountStrPara, String loanVlueStr, String dayOfMOnth, String suffixPara) {
        String cusName = cusNamePara;
        String noOfArrearsTerms = noOfTermsStr;
        String termAmountStr = termAmountStrPara;
        String loanValue = loanVlueStr;
        String day = dayOfMOnth;
        String suffix = suffixPara;

        message = "Dear " + cusName;

//          if (!cusSubOfficeNo.isEmpty()) {
//               message = message.concat("(" + cusSubOfficeNo + ")");
//          }
        message = message.concat(", ");
        message = message.concat(" Disbursement ");
        message = message.concat("Rs. " + loanValue + "");
        message = message.concat(" with amount ");
        message = message.concat(termAmountStr);
//        message = message.concat(" and ");
//        message = message.concat(noOfArrearsTerms);
        message = message.concat(" rentals.");
//        message = message.concat(" rentals due on ");
//        message = message.concat(day);
//        message = message.concat(suffix);
//        message = message.concat(" every month.");
        message = message.concat("\nThank You. ");
        message = message.concat("\n" + Company_Profile.getCompanyName());

        return message;
    }

}
