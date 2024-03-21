/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyPhoneNoFormatingUtility;
import iroshan.com.common.utility.MyRounding;
import iroshan.com.smsserver.dao.Cluster_dao;
import iroshan.com.smsserver.dao.Loan_dao;
import iroshan.com.smsserver.dao.ProductCategoryDao;
import iroshan.com.smsserver.dao.SendMessageSaveDao;
import iroshan.com.smsserver.dao.UpdatePaymentAsComplete;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.Company_Profile;
import iroshan.com.smsserver.dao.LoanEarlySettlementDao;
import iroshan.com.smsserver.pojo.Loan_pojo;
import static iroshan.com.smsserver.service.Jurnal_service.sendingActive;
import iroshan.com.smsserver.service.gatway.dialog.SendSMSThroughGatewayAPI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class LoanEarlySettlementService {

    static Boolean sendingActive = true;

    SendMessage sendMessage = null;

    ReceiveAndSendMonitorService receiveAndSendMonitorService = null;

    SendMessageSaveDao sendMessageSaveDao = null;

    SendSMSThroughGatewayAPI sendSMSThroughGatewayAPI = null;

    /**
     *
     * @param phnNoPara
     * @param msgPara
     * @param cusCodePara
     * @param jCodePara
     *
     * @return
     */
    public OutboundMessage.MessageStatuses sendSMS_2(String messageTypePara, String phnNoPara, String msgPara) throws Exception {
        if (sendMessage == null) {
            sendMessage = new SendMessage();
        }

        if (receiveAndSendMonitorService == null) {
            receiveAndSendMonitorService = new ReceiveAndSendMonitorService();
        }

        if (sendMessageSaveDao == null) {
            sendMessageSaveDao = new SendMessageSaveDao();
        }

        if (sendSMSThroughGatewayAPI == null) {
            sendSMSThroughGatewayAPI = new SendSMSThroughGatewayAPI();
        }

//        String cusCode = cusCodePara;
//        String paymentCode = jCodePara;
        String messageType = messageTypePara;

        Integer idSMSReceive = -1;
        String phnNo = phnNoPara;
        String msg = msgPara;
        phnNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phnNo);

        OutboundMessage.MessageStatuses msgStatus = null;

        if (phnNo != null) {

            msgStatus = sendMessage.send_5(phnNo, msg);

//            try {
//                msgStatus = sendMessage.send_3(new OutboundMessage(phnNo, msg));
//            } catch (TimeoutException ex) {
//                Logger.getLogger(Jurnal_service.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (GatewayException ex) {
//                Logger.getLogger(Jurnal_service.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(Jurnal_service.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Jurnal_service.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else {
            msgStatus = OutboundMessage.MessageStatuses.UNSENT;
        }

//        MessageStatuses msgStatus = sendMessage.send_2 (messageType, idSMSReceive, phnNo, msg);
        if (msgStatus == OutboundMessage.MessageStatuses.SENT) {

            String curentDateAndTimeAsString = Company_Profile.getCurrentDateAsString();
            Date curentDateAndTimeAsDate = Company_Profile.getCurrentDateAsDate();

            receiveAndSendMonitorService.sendMessageProcess_2(phnNo, msg, curentDateAndTimeAsString);
            sendMessageSaveDao.saveToDB_2(messageType, idSMSReceive, phnNo, msg, curentDateAndTimeAsDate, msgStatus);

        }
        return msgStatus;
    }

    public List<String> loadProduct() {
        return new ProductCategoryDao().getLoanList();
    }

    public List<String> loadCenter() {
        return new Cluster_dao().getClusterListAsStringList();
    }

    public List<Loan_pojo> getLoanOutstandingDetails(Date datePara) {

        SMSMessage smsMessage = new SMSMessage();
//        Map<Integer, Loan_pojo> newArrReturnMap = new TreeMap<Integer, Loan_pojo> ();

        List<Loan_pojo> lis = new ArrayList<Loan_pojo>();

        Map<String, Loan_pojo> arr = new Loan_dao().getArrears(datePara);

        int rowCount = 0;
        for (Map.Entry<String, Loan_pojo> entrySet : arr.entrySet()) {
            String key = entrySet.getKey();
            Loan_pojo value = entrySet.getValue();

            Double arears = value.getOutstandingCapital() + value.getOutstandingInterest();
            String arrearsStr = MyRounding.roundToLastTwoDecimal(arears);
            String dateStr = MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(datePara);
            String message = smsMessage.msgForOutstanding(value.getCusName(), value.getAcNumber(), arrearsStr, dateStr);

            value.setMessage(message);
            lis.add(value);
        }

//        LoanOutstanding_infrm_view.setLoanOutstandingMap ();Company_Profile.setOutstandingMap (newArrReturnMap);
        return lis;

    }

    public List<Loan_pojo> getLoanOutstandingDetails(Date datePara, String product, String center, String customerCode) {

        SMSMessage smsMessage = new SMSMessage();
        List<Loan_pojo> lis = new ArrayList<Loan_pojo>();

        Map<String, Loan_pojo> arr = new Loan_dao().getArrears4(datePara, product, center, customerCode);

        int rowCount = 0;
        for (Map.Entry<String, Loan_pojo> entrySet : arr.entrySet()) {
            String key = entrySet.getKey();
            Loan_pojo value = entrySet.getValue();

            Double arears = value.getOutstandingCapital() + value.getOutstandingInterest();
            String arrearsStr = MyRounding.roundToLastTwoDecimal(arears);
            String dateStr = MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(datePara);
            String message = smsMessage.msgForOutstanding(value.getCusName(), value.getAcNumber(), arrearsStr, dateStr);

            value.setMessage(message);
            lis.add(value);
        }

//        LoanOutstanding_infrm_view.setLoanOutstandingMap ();Company_Profile.setOutstandingMap (newArrReturnMap);
        return lis;

    }

    public List<Loan_pojo> getLoanEarlySettlementDetails(Date datePara, String product, String center, String customerCode) {

        SMSMessage smsMessage = new SMSMessage();
        List<Loan_pojo> lis = new ArrayList<Loan_pojo>();

//            Map<String, Loan_pojo> arr = new LoanEarlySettlementDao().getEarlySettlement(datePara, product, center, customerCode);
        Map<String, Loan_pojo> arr = null;
//        Map<String, Loan_pojo> arr = new Loan_dao ().getArrears (datePara);
        if (!Company_Profile.getIsRuningBalance()) {
            arr = new Loan_dao().getArrears3(datePara, product, center, customerCode);
        } else if (Company_Profile.getIsRuningBalance()) {
            arr = new Loan_dao().getRuingBalanceDetailsEarlySettlement(datePara, product, center, customerCode);
        }

        int rowCount = 0;
        for (Map.Entry<String, Loan_pojo> entrySet : arr.entrySet()) {
            String key = entrySet.getKey();
            Loan_pojo value = entrySet.getValue();



//            System.out.println(""+value.getOutstandingCapital());
//            System.out.println(""+value.getOutstandingInterest() );
//            System.out.println(""+value.getArearsInterst());
//            System.out.println(""+value.getFine());
//EARLY SETTLEMENT AMOUNT ******************************************************
            Double earlySettlement1 = 0.00;
            if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.REGAL.toString())) {
                earlySettlement1 = value.getOutstandingCapital() + value.getArearsInterst() + (value.getFine()== null ? 0.00 : value.getFine());
            } else {
                earlySettlement1 = value.getOutstandingCapital() + value.getOutstandingInterest() + value.getArearsInterst() + (value.getFine()== null ? 0.00 : value.getFine());
            }

            String earlySettlementStr = MyRounding.roundToLastTwoDecimal(earlySettlement1);
            String dateStr = MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(datePara);
            String message = smsMessage.msgForEarlySettlementFormat1(value.getCusName(), value.getAcNumber(), earlySettlementStr, dateStr);

            value.setMessage(message);
            lis.add(value);
        }

//        LoanOutstanding_infrm_view.setLoanOutstandingMap ();Company_Profile.setOutstandingMap (newArrReturnMap);
        return lis;

    }

    public List<Loan_pojo> getLoanDueDetails(Date datePara) {

        SMSMessage smsMessage = new SMSMessage();

        List<Loan_pojo> newArrReturnMap = new ArrayList<Loan_pojo>();

        Map<String, Loan_pojo> arr = new Loan_dao().getArrears(datePara);

        int rowCount = 0;
        for (Map.Entry<String, Loan_pojo> entrySet : arr.entrySet()) {

            String key = entrySet.getKey();
            Loan_pojo value = entrySet.getValue();

            Double arears = value.getDueCapital() + value.getDueInterest();
            String arrearsStr = MyRounding.roundToLastTwoDecimal(arears);
            String dateStr = MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(datePara);
            String message = smsMessage.msgForDueLoans(value.getCusName(), value.getAcNumber(), arrearsStr, dateStr);

            value.setMessage(message);
            newArrReturnMap.add(value);
        }

        return newArrReturnMap;

    }

    public List<Loan_pojo> getLoanDueDetails(Date datePara, String product, String center, String customerCode) {

        SMSMessage smsMessage = new SMSMessage();

        List<Loan_pojo> newArrReturnMap = new ArrayList<Loan_pojo>();

//        Map<String, Loan_pojo> arr = new Loan_dao ().getArrears (datePara);
        Map<String, Loan_pojo> arr = new Loan_dao().getArrears3(datePara, product, center, customerCode);

        int rowCount = 0;
        for (Map.Entry<String, Loan_pojo> entrySet : arr.entrySet()) {

            String key = entrySet.getKey();
            Loan_pojo value = entrySet.getValue();

            Double arears = value.getDueCapital() + value.getDueInterest();
            String arrearsStr = MyRounding.roundToLastTwoDecimal(arears);
            String dateStr = MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(datePara);
            String message = smsMessage.msgForDueLoans(value.getCusName(), value.getAcNumber(), arrearsStr, dateStr);

            value.setMessage(message);
            newArrReturnMap.add(value);
        }

        return newArrReturnMap;

    }

    public List<Loan_pojo> getLoanArrearsDetails(Date datePara) {

        SMSMessage smsMessage = new SMSMessage();

        List<Loan_pojo> newArrReturnMap = new ArrayList<Loan_pojo>();

        Map<String, Loan_pojo> arr = new Loan_dao().getArrears2(datePara);

        int rowCount = 0;
        for (Map.Entry<String, Loan_pojo> entrySet : arr.entrySet()) {

            String key = entrySet.getKey();
            Loan_pojo value = entrySet.getValue();

            Double arears = value.getArearsCapital() + value.getArearsInterst();
            String arrearsStr = MyRounding.roundToLastTwoDecimal(arears);
            String dateStr = MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(datePara);
            String message = smsMessage.msgForArrears(value.getCusName(), value.getAcNumber(), arrearsStr, dateStr);

            value.setMessage(message);
            newArrReturnMap.add(value);
        }

        return newArrReturnMap;

    }

    public List<Loan_pojo> getLoanArrearsDetails(Date datePara, String product, String center, String customerCode) {

        SMSMessage smsMessage = new SMSMessage();

        List<Loan_pojo> newArrReturnMap = new ArrayList<Loan_pojo>();

        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.REGAL.toString())) {
            Map<String, Loan_pojo> arr = new Loan_dao().getArrearsFormat4(datePara, product, center, customerCode);
            int rowCount = 0;
            for (Map.Entry<String, Loan_pojo> entrySet : arr.entrySet()) {

                String key = entrySet.getKey();
                Loan_pojo value = entrySet.getValue();

                Double arears = value.getArearsCapital() + value.getArearsInterst();
                Double arrTerms = value.getNoOfArrearsTerms();
                String arrTermsStr = "0";
                try {
                    if (arrTerms != null) {
                        arrTermsStr = String.valueOf(arrTerms);
                    }

                } catch (Exception e) {
                }
                String arrearsStr = MyRounding.roundToLastTwoDecimal(arears);
                String dateStr = MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(datePara);
                String message = smsMessage.msgForArrearsFormat2(value.getCusName(), value.getCusOfficeNo(), value.getAcNumber(), arrTerms, arrearsStr, dateStr);

                value.setMessage(message);
                newArrReturnMap.add(value);
            }

        } else {
            Map<String, Loan_pojo> arr = new Loan_dao().getArrears3(datePara, product, center, customerCode);
            int rowCount = 0;
            for (Map.Entry<String, Loan_pojo> entrySet : arr.entrySet()) {

                String key = entrySet.getKey();
                Loan_pojo value = entrySet.getValue();

                Double arears = value.getArearsCapital() + value.getArearsInterst();
                String arrearsStr = MyRounding.roundToLastTwoDecimal(arears);
                String dateStr = MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(datePara);
                String message = smsMessage.msgForArrears(value.getCusName(), value.getAcNumber(), arrearsStr, dateStr);

                value.setMessage(message);
                newArrReturnMap.add(value);
            }

        }

        return newArrReturnMap;

    }

    public void sendOutstandingSms(Map<Integer, Loan_pojo> arrearsPojoMapPara) {

        Map<Integer, Loan_pojo> arrearsPojoMap = arrearsPojoMapPara;
        for (Map.Entry<Integer, Loan_pojo> entrySet : arrearsPojoMap.entrySet()) {
            Integer key = entrySet.getKey();
            Loan_pojo loanPojo = entrySet.getValue();

            if (loanPojo.getIsSelected()) {

                String phoneNo = loanPojo.getTelephoneNo().trim();

                String arearsAmount = MyRounding.roundToLastTwoDecimal(loanPojo.getTotalOutstanding());
                String acNo = loanPojo.getAcNumber();
                String message = loanPojo.getMessage();

                if ((!phoneNo.isEmpty()) && (phoneNo != null)) {

                    new SendMessage().send(MyMessageTypeEnum.OUTSTANDING.toString(), -1, phoneNo, message);

                }
            }
        }
    }

    public void sendOutstandingSms(List<Loan_pojo> arrearsPojoMapPara) {

        if (MySMSServer.checkServiceAvailable() == false) {
            MyMessagesUtility.showWarning("Service Not Available.");
        } else {

//            MySMSServer.setInboundFalse ();
//             MyMessagesUtility.showInformation (MySMSServer.serialModemGateway.getStatus ().toString ());
            SendMessage sendMessage = new SendMessage();

            List<Loan_pojo> arrearsPojoMap = arrearsPojoMapPara;

            MySMSServer.restartService();

            for (int i = 0; i < arrearsPojoMap.size(); i++) {

                Loan_pojo loanPojo = arrearsPojoMap.get(i);

                if (loanPojo.getIsSelected()) {

                    String phoneNo = loanPojo.getTelephoneNo().trim();

//                String arearsAmount = MyRounding.roundToLastTwoDecimal (loanPojo.getTotalOutstanding ());
//                String acNo = loanPojo.getAcNumber ();
                    String message = loanPojo.getMessage();

                    if ((!phoneNo.isEmpty()) && (phoneNo != null)) {

                        sendMessage.send(MyMessageTypeEnum.OUTSTANDING.toString(), -1, phoneNo, message);

                    }
                }
            }

//            MySMSServer.setInboundTrue ();
        }
    }

    public void sendDueSms(Map<Integer, Loan_pojo> arrearsPojoMapPara) {

        Map<Integer, Loan_pojo> arrearsPojoMap = arrearsPojoMapPara;
        for (Map.Entry<Integer, Loan_pojo> entrySet : arrearsPojoMap.entrySet()) {
            Integer key = entrySet.getKey();
            Loan_pojo loanPojo = entrySet.getValue();

            if (loanPojo.getIsSelected()) {

                String phoneNo = loanPojo.getTelephoneNo().trim();

                String arearsAmount = MyRounding.roundToLastTwoDecimal(loanPojo.getTotalDue());
                String acNo = loanPojo.getAcNumber();
                String message = loanPojo.getMessage();

                if ((!phoneNo.isEmpty()) && (phoneNo != null)) {

                    new SendMessage().send(MyMessageTypeEnum.DUE.toString(), -1, phoneNo, message);

                }
            }
        }
    }

    public void sendDueSms(List<Loan_pojo> arrearsPojoMapPara) {

        if (MySMSServer.checkServiceAvailable() == false) {
            MyMessagesUtility.showWarning("Service Not Available.");
        } else {

            List<Loan_pojo> arrearsPojoMap = arrearsPojoMapPara;

            for (int i = 0; i < arrearsPojoMap.size(); i++) {

                Loan_pojo loanPojo = arrearsPojoMap.get(i);

                if (loanPojo.getIsSelected()) {

                    String phoneNo = loanPojo.getTelephoneNo().trim();

//                String arearsAmount = MyRounding.roundToLastTwoDecimal (loanPojo.getTotalOutstanding ());
//                String acNo = loanPojo.getAcNumber ();
                    String message = loanPojo.getMessage();

                    if ((!phoneNo.isEmpty()) && (phoneNo != null)) {

                        new SendMessage().send(MyMessageTypeEnum.OUTSTANDING.toString(), -1, phoneNo, message);

                    }
                }
            }
        }
    }

    public void sendArrearsSms(Map<Integer, Loan_pojo> arrearsPojoMapPara) {

        Map<Integer, Loan_pojo> arrearsPojoMap = arrearsPojoMapPara;
        for (Map.Entry<Integer, Loan_pojo> entrySet : arrearsPojoMap.entrySet()) {
            Integer key = entrySet.getKey();
            Loan_pojo loanPojo = entrySet.getValue();

            if (loanPojo.getIsSelected()) {

                String phoneNo = loanPojo.getTelephoneNo().trim();

                String arearsAmount = MyRounding.roundToLastTwoDecimal(loanPojo.getTotalArears());
                String acNo = loanPojo.getAcNumber();
                String message = loanPojo.getMessage();
//                String message = "You have a arrears amount " + arearsAmount + " for " + acNo;

                if ((!phoneNo.isEmpty()) && (phoneNo != null)) {

                    new SendMessage().send(MyMessageTypeEnum.ARREARS.toString(), -1, phoneNo, message);

                }

            }

        }

    }

    public void sendArrearsSms(final List<Loan_pojo> arrearsPojoMapPara) {

//        new Thread (new Runnable ()
//        {
//
//            @Override
//            public void run ()
//            {
        if (sendingActive) {
            if (MySMSServer.checkServiceAvailable() == false) {
                MyMessagesUtility.showWarning("Service Not Available.");
            } else {

                List<Loan_pojo> arrearsPojoMap = arrearsPojoMapPara;

//                        if (MySMSServer.getSmsServerService ().getServiceStatus () == Service.ServiceStatus.STARTED)
//                        {
//
//                            try
//                            {
//                                MySMSServer.getSmsServerService ().stopService ();
//                                MySMSServer.getSmsServerService ().startService ();
//                                
//                            } catch (SMSLibException ex)
//                            {
//                                java.util.logging.Logger.getLogger (Loan_service.class.getName ()).log (Level.SEVERE, null, ex);
//                            } catch (IOException ex)
//                            {
//                                java.util.logging.Logger.getLogger (Loan_service.class.getName ()).log (Level.SEVERE, null, ex);
//                            } catch (InterruptedException ex)
//                            {
//                                java.util.logging.Logger.getLogger (Loan_service.class.getName ()).log (Level.SEVERE, null, ex);
//                            }
//
//
//                        }
                for (int i = 0; i < arrearsPojoMap.size(); i++) {

                    Loan_pojo loanPojo = arrearsPojoMap.get(i);

                    if (loanPojo.getIsSelected()) {

                        String phoneNo = loanPojo.getTelephoneNo().trim();

//                String arearsAmount = MyRounding.roundToLastTwoDecimal (loanPojo.getTotalArears ());
//                String acNo = loanPojo.getAcNumber ();
                        String message = loanPojo.getMessage();

                        if ((!phoneNo.isEmpty()) && (phoneNo != null)) {

                            new SendMessage().send(MyMessageTypeEnum.OUTSTANDING.toString(), -1, phoneNo, message);

                        }
                    }
                }

            }
        }
//            }
//        }).start ();

    }

    public List<Loan_pojo> getLoanDisbursementDetails(Date datePara, String product, String center, String customerCode) {

        String companyCode = Company_Profile.getCompanyCode();
        SMSMessage smsMessage = new SMSMessage();

        List<Loan_pojo> newArrReturnMap = new ArrayList<Loan_pojo>();
        List<Loan_pojo> arr = null;

        if (companyCode.equalsIgnoreCase(Company_Profile.CompanyCodeEnum.REGAL.toString())) {
            arr = new Loan_dao().getLoanDisbursedDetails(datePara, product, center, customerCode);
        } else {
            arr = new Loan_dao().getLoanDisbursedDetails(datePara, product, center, customerCode);
        }

        int rowCount = 0;
        for (Loan_pojo entrySet : arr) {

            Loan_pojo value = entrySet;

            String acCode = value.getAcNumber();
            String ieMember = value.getCusCode();
            String cusName = value.getCusName();
            String cusOfficeNo = value.getCusOfficeNo();
            Double loanVlue = value.getDisburseAmount();
            String loanVlueStr = MyRounding.roundToLastTwoDecimal(loanVlue);
            Double termAmount = value.getTermAmount();
            String termAmountStr = MyRounding.roundToLastTwoDecimal(termAmount);
            Double noOfTerms = value.getNoOfTerms();
            String noOfTermsStr = MyRounding.roundToWithoutDecimal(noOfTerms);
            Date loanGrandDate = value.getDisburseDate();
            Integer dayOfMonthInt = MyDateAndTimeUtil.getDayOfMonthByDate(loanGrandDate);
            String dayOfMOnth = String.valueOf(dayOfMonthInt);
            String suffix = MyDateAndTimeUtil.getDayOfMonthSuffix(dayOfMonthInt);
            String tele = value.getTelephoneNo();

            String message = null;

//            if (companyCode.equalsIgnoreCase (Company_Profile.CompanyCodeEnum.REGAL.toString ()))
//            {
////                                      String cusNamePara, String cusSubOfficeNoPara, String ACNoPara, String noOfArrearsTermsPara, 
////                                      String loanValuePara, String dayPara
//                message = smsMessage.msgForLoanDisbursementFormat1 (cusName, cusOfficeNo, acCode, noOfTermsStr, termAmountStr, dayOfMOnth, suffix);
//            } else
//            {
//                message = smsMessage.msgForLoanDisbursementFormat1 (cusName, cusOfficeNo, acCode, noOfTermsStr, termAmountStr, dayOfMOnth, suffix);
//            }
            message = smsMessage.msgForLoanDisbursementFormat1(cusName, cusOfficeNo, acCode, noOfTermsStr, termAmountStr, dayOfMOnth, suffix);
            value.setMessage(message);
            newArrReturnMap.add(value);

        }

        return newArrReturnMap;

    }
}
