package iroshan;

import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.enums.GatewayProvider;
import iroshan.com.smsserver.enums.GatewayType;
import java.util.Date;

/**
 *
 * @author Iroshan
 */
public class Company_Profile {

    private static String version = "SMS_202101031";
    
    private static String currentDateAsString = MyDateAndTimeUtil.getCurrentDateAndTime_As_String();
    private static Date currentDateAsDate = MyDateAndTimeUtil.getCurrentDateAndTime();

//    private static String gatewayType = GatewayType.DONGLE.toString();
    private static String gatewayType = null;
    private static String gatewayProvider = null;

//    private static String companyCode = CompanyCodeEnum.SENOVA.toString();
    private static String companyCode = CompanyCodeEnum.HORAHENA.toString();

    // LOANS & LAND - Matale *************************************************
//    private static String companyCode = CompanyCodeEnum.LLIPL.toString();
//    private static String companyName = CompanyCodeEnum.LLIPL.toString();
    private static String companyName = null;
//          -----------------------------------------
    // N = Normal (Only receipt amount)
    // RA = Receipt amount with arrears amount
    private static String receiptMessageType = null;
//          -----------------------------------------
    // N = Normal (Only receipt amount)
    // PA = PAYMENT amount with LOAN OUTSTANDING
    private static String paymentMessageType = null;
//          -----------------------------------------
    // N = Normal (Only receipt amount)
    // JA = Jurnal amount with arrears amount
    private static String jurnalMessageType = null;
//          ------------------------------------------
    // L&L = 1
    private static Boolean customerSubOffceNoWithSMS = null;
    private static String language = null;

    private static Boolean isTest = false;
    private static String testingTele = "0776605555";
    private static String savinPrefixCode = "SBAL ";
    private static String loanPrefixCode = "LBAL";
    private static Integer msgCountForServerRestarting = 10;
    private static Boolean msgSendStart = false;
    private static String confirmTeleNo = null;
    private static Boolean isRuningBalance = true;
    /**
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
    public Company_Profile() {

        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.SIDDAMULLA.toString())) {

            gatewayType = GatewayType.GATEWAY.toString();
            gatewayProvider = GatewayProvider.DIALOG.toString();
            companyName = "\nSANASA - SIDDAMULLA";
            confirmTeleNo = "";

            language = LanguageEnum.SINHALA.toString();
//            language = "ENGLISH";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;
        }

        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.LLIPL_KANDY.toString())) {

            gatewayType = GatewayType.DONGLE.toString();

            language = LanguageEnum.ENGLISH.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = "LOANS & LAND - KATUGASTHOTA";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

        }

        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.LLIPL_MATHALE.toString())) {

            language = LanguageEnum.ENGLISH.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = "LOANS & LAND (L&L/0770037360) - MATALE";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

        }

        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.DNCS.toString())) {

            gatewayType = GatewayType.GATEWAY.toString();
            gatewayProvider = GatewayProvider.MOBITEL.toString();

//            language = LanguageEnum.ENGLISH.toString();
            language = LanguageEnum.SINHALA.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = " DNCS(025 222 72 72) - ANURADHAPURA";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

            setConfirmTeleNo("0776605555");
        }

        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.SMWM.toString())) {

            gatewayType = GatewayType.GATEWAY.toString();
            gatewayProvider = GatewayProvider.DIALOG.toString();

            language = LanguageEnum.ENGLISH.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = "\nSMWM";
            confirmTeleNo = "";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = false;

        }

        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.TEST.toString())) {

            gatewayType = GatewayType.DONGLE.toString();

            language = LanguageEnum.ENGLISH.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = " Senova Global Pvt(Ltd)";
            confirmTeleNo = "0711967867";
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.RA.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.PA.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.JA.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

        }

        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.SENOVA.toString())) {

            gatewayType = GatewayType.DONGLE.toString();

            language = LanguageEnum.SINHALA.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = "SANASA";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

        }

        
        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.SEWA_CREDIT.toString())) {

            gatewayType = GatewayType.GATEWAY.toString();
            gatewayProvider = GatewayProvider.DIALOG.toString();

//            language = LanguageEnum.ENGLISH.toString();
            language = LanguageEnum.ENGLISH.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = " Sewa Community Credit Limited.";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

            setConfirmTeleNo("0776605555");
            
            
            
            
//            ApplicationDesktop.jButton6.setVisible(false);
        }
     
        
        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.ATHUGALA_INVESTMENTS.toString())) {

            gatewayType = GatewayType.GATEWAY.toString();
            gatewayProvider = GatewayProvider.DIALOG.toString();

//            language = LanguageEnum.ENGLISH.toString();
            language = LanguageEnum.ENGLISH.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = " ATHUGALA CREDIT.";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

            setConfirmTeleNo("0776605555");
            
//            ApplicationDesktop.jButton6.setVisible(false);
        }   
        
        if (companyCode.equalsIgnoreCase(CompanyCodeEnum.VURSJ_POLONNARUWA.toString())) {

            gatewayType = GatewayType.GATEWAY.toString();
            gatewayProvider = GatewayProvider.DIALOG.toString();

//            language = LanguageEnum.ENGLISH.toString();
            language = LanguageEnum.ENGLISH.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = " -VURSJ CREDIT.";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

            setConfirmTeleNo("0776605555");
            
//            ApplicationDesktop.jButton6.setVisible(false);
        }   
        
        
        
          if (companyCode.equalsIgnoreCase(CompanyCodeEnum.HORAHENA.toString())) {

            gatewayType = GatewayType.GATEWAY.toString();
            gatewayProvider = GatewayProvider.DIALOG.toString();

//            language = LanguageEnum.ENGLISH.toString();
            language = LanguageEnum.SINHALA.toString();

            //// LOANS & LAND - Matale *************************************************
            companyName = " -HORAHENA SANASA.";

//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // RA = Receipt amount with arrears amount
            receiptMessageType = ReceiptMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // PA = PAYMENT amount with LOAN OUTSTANDING
            paymentMessageType = PaymentMessageTypeEnum.N.toString().trim();
//          -----------------------------------------
            // N = Normal (Only receipt amount)
            // JA = Jurnal amount with arrears amount
            jurnalMessageType = JurnalMessageTypeEnum.N.toString().trim();
//          ------------------------------------------
            // L&L = 1
            customerSubOffceNoWithSMS = true;

            setConfirmTeleNo("0776605555");
            
//            ApplicationDesktop.jButton6.setVisible(false);
        } 
    }

    /**
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
// ENUM --------------------------------------------------------
    public enum CompanyCodeEnum {
        LLIPL_KANDY, LLIPL_MATHALE, REGAL, DNCS, TEST, SIDDAMULLA, SMWM, SENOVA,SEWA_CREDIT, ATHUGALA_INVESTMENTS, VURSJ_POLONNARUWA, HORAHENA;

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public enum LanguageEnum {
        SINHALA, ENGLISH;

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public enum ReceiptMessageTypeEnum {
        N, RA;

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }

    }

    public enum PaymentMessageTypeEnum {
        N, PA;

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public enum JurnalMessageTypeEnum {
        N, JA;

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }
    }

// Methods ------------------------------------------------------    
    public static String getCurrentDateAsString() {
        return currentDateAsString;
    }

    public static void setCurrentDateAsString(String currentDateAsString) {
        Company_Profile.currentDateAsString = currentDateAsString;
    }

    public static Date getCurrentDateAsDate() {
        return currentDateAsDate;
    }

    public static void setCurrentDateAsDate(Date currentDateAsDate) {
        Company_Profile.currentDateAsDate = currentDateAsDate;
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setCompanyName(String companyName) {
        Company_Profile.companyName = companyName;
    }

    public static Boolean getIsTest() {
        return isTest;
    }

    public static void setIsTest(Boolean isTest) {
        Company_Profile.isTest = isTest;
    }

    public static String getTestingTele() {
        return testingTele;
    }

    public static void setTestingTele(String testingTele) {
        Company_Profile.testingTele = testingTele;
    }

    public static String getSavinPrefixCode() {
        return savinPrefixCode;
    }

    public static void setSavinPrefixCode(String savinPrefixCode) {
        Company_Profile.savinPrefixCode = savinPrefixCode;
    }

    public static String getLoanPrefixCode() {
        return loanPrefixCode;
    }

    public static void setLoanPrefixCode(String loanPrefixCode) {
        Company_Profile.loanPrefixCode = loanPrefixCode;
    }

    public static Integer getMsgCountForServerRestarting() {
        return msgCountForServerRestarting;
    }

    public static void setMsgCountForServerRestarting(Integer msgCountForServerRestarting) {
        Company_Profile.msgCountForServerRestarting = msgCountForServerRestarting;
    }

    public static Boolean getMsgSendStart() {
        return msgSendStart;
    }

    public static void setMsgSendStart(Boolean msgSendStart) {
        Company_Profile.msgSendStart = msgSendStart;
    }

    public static String getReceiptMessageType() {
        return receiptMessageType;
    }

    public static void setReceiptMessageType(String receiptMessageType) {
        Company_Profile.receiptMessageType = receiptMessageType;
    }

    public static Boolean getCustomerSubOffceNoWithSMS() {
        return customerSubOffceNoWithSMS;
    }

    public static void setCustomerSubOffceNoWithSMS(Boolean customerSubOffceNoWithSMS) {
        Company_Profile.customerSubOffceNoWithSMS = customerSubOffceNoWithSMS;
    }

    public static String getJurnalMessageType() {
        return jurnalMessageType;
    }

    public static void setJurnalMessageType(String jurnalMessageType) {
        Company_Profile.jurnalMessageType = jurnalMessageType;
    }

    public static String getPaymentMessageType() {
        return paymentMessageType;
    }

    public static void setPaymentMessageType(String paymentMessageType) {
        Company_Profile.paymentMessageType = paymentMessageType;
    }

    public static String getCompanyCode() {
        return companyCode;
    }

    public static void setCompanyCode(String companyCode) {
        Company_Profile.companyCode = companyCode;
    }

    public static String getGatewayType() {
        return gatewayType;
    }

    public static void setGatewayType(String gatewayType) {
        Company_Profile.gatewayType = gatewayType;
    }

    public static String getConfirmTeleNo() {
        return confirmTeleNo;
    }

    public static String getGatewayProvider() {
        return gatewayProvider;
    }

    public static void setGatewayProvider(String gatewayProvider) {
        Company_Profile.gatewayProvider = gatewayProvider;
    }

    public static void setConfirmTeleNo(String confirmTeleNo) {
        Company_Profile.confirmTeleNo = confirmTeleNo;
    }

    public static Boolean getIsRuningBalance() {
        return isRuningBalance;
    }

    public static void setIsRuningBalance(Boolean isRuningBalance) {
        Company_Profile.isRuningBalance = isRuningBalance;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        Company_Profile.language = language;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        Company_Profile.version = version;
    }

    
    
}
