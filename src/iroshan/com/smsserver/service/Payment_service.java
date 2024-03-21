/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyPhoneNoFormatingUtility;
import iroshan.com.common.utility.MyRounding;
import iroshan.com.smsserver.dao.Payment_dao;
import iroshan.com.smsserver.dao.SendMessageSaveDao;
import iroshan.com.smsserver.dao.UpdatePaymentAsComplete;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.Company_Profile;
import iroshan.Company_Profile.PaymentMessageTypeEnum;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.pojo.PaymentSMS_pojo;
import iroshan.com.smsserver.pojo.Payment_pojo;
import iroshan.com.smsserver.service.gatway.dialog.SendSMSThroughGatewayAPI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.OutboundMessage.MessageStatuses;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class Payment_service {

    static Boolean sendingActive = true;
    SendMessage sendMessage = null;

    UpdatePaymentAsComplete updatePaymentAsComplete = null;
    ReceiveAndSendMonitorService receiveAndSendMonitorService = null;
    SendMessageSaveDao sendMessageSaveDao = null;
    SendSMSThroughGatewayAPI sendSMSThroughGatewayAPI = null;

    public List<PaymentSMS_pojo> getNewPaymentsList(String brCodePara) {

        SMSMessage sMSMessage = new SMSMessage();
        List<PaymentSMS_pojo> newPaymentSendList = new ArrayList<PaymentSMS_pojo>();

        Map<String, Payment_pojo> newPaymentList = new Payment_dao().getNewPaymentsList(brCodePara);

        if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(PaymentMessageTypeEnum.N.toString())) {
            for (Map.Entry<String, Payment_pojo> entrySet : newPaymentList.entrySet()) {

                String ieCode = entrySet.getKey();
                Payment_pojo value = entrySet.getValue();

                String paymentNo = value.getPaymentNo();
                String mCode = value.getCusCode();
//                String cusName = value.getCusName();
                String cusOfficeNo = value.getCusOfficeNo();
                String acCode = value.getAcNumber();
                String receiptAmount = MyRounding.roundToLastTwoDecimal(value.getAmount());
                String payDate = MyDateAndTimeUtil.getFormatedDateAsString(value.getPay_date());
//                String loanAmount = null;
//                if (value.getLoanOrSaving () == 1)
//                {
//                    loanAmount = MyRounding.roundToLastTwoDecimal (value.getLoanAmount ());
//                }
                String tele = value.getTelNo();

                String cusName = "";
                if (Company_Profile.getLanguage().equalsIgnoreCase(Company_Profile.LanguageEnum.SINHALA.toString())) {
                    cusName = value.getCusNameEnglish();
                } else {
                    if (Company_Profile.getCustomerSubOffceNoWithSMS()) {
                        if (cusOfficeNo != null) {
                            if (!cusOfficeNo.isEmpty()) {
                                cusName = value.getCusName().trim().concat("(" + cusOfficeNo + ")");
                            }
                        }
                    } else {
                        cusName = value.getCusName().trim();
                    }
//                    cusName = value.getCusName();
                }

                String smsMsg = null;

                if (Company_Profile.getCompanyCode() == Company_Profile.CompanyCodeEnum.SEWA_CREDIT.toString()) {
                    smsMsg = sMSMessage.msgForPayment_SewaCredit(paymentNo, cusName, acCode, receiptAmount, null);
                } else {
                    if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(PaymentMessageTypeEnum.N.toString())) {
                        smsMsg = sMSMessage.msgForPayment(cusName, acCode, receiptAmount, payDate);
                    }

                    if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(PaymentMessageTypeEnum.PA.toString())) {
                        smsMsg = sMSMessage.msgForPayment(cusName, acCode, receiptAmount, null);
                    }
                }

//            String smsMsg = sMSMessage.msgForPayment (cusName, cusOfficeNo, receiptAmount, ieCode, loanAmount, null);
                newPaymentSendList.add(new PaymentSMS_pojo(true, paymentNo, mCode, cusName, cusOfficeNo, tele, smsMsg));

            }
        }

        if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(PaymentMessageTypeEnum.PA.toString())) {
            for (Map.Entry<String, Payment_pojo> entrySet : newPaymentList.entrySet()) {

                String ieCode = entrySet.getKey();
                Payment_pojo value = entrySet.getValue();

                String paymentNo = value.getPaymentNo();
                String mCode = value.getCusCode();
                String cusName = value.getCusName();
                String cusOfficeNo = value.getCusOfficeNo();
                String acCode = value.getAcNumber();
                String receiptAmount = MyRounding.roundToLastTwoDecimal(value.getAmount());

                String loanAmount = null;
                if (value.getLoanOrSaving() == 1) {
                    loanAmount = MyRounding.roundToLastTwoDecimal(value.getLoanAmount());
                }

                String tele = value.getTelNo();

                if (Company_Profile.getCustomerSubOffceNoWithSMS()) {
                    if (!cusOfficeNo.isEmpty()) {
                        cusName = cusName.concat("(" + cusOfficeNo + ")");
                    }
                } else {
                    cusName = cusName.trim();
                }

                String smsMsg = null;
                if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(PaymentMessageTypeEnum.N.toString())) {
                    smsMsg = sMSMessage.msgForPayment(cusName, acCode, receiptAmount, null);
                }

//            String smsMsg = sMSMessage.msgForPayment (cusName, cusOfficeNo, receiptAmount, ieCode, loanAmount, null);
                newPaymentSendList.add(new PaymentSMS_pojo(true, paymentNo, mCode, cusName, cusOfficeNo, tele, smsMsg));

            }
        }

        return newPaymentSendList;
    }

    /**
     *
     * @param phnNoPara
     * @param msgPara
     * @param cusCodePara
     * @param jCodePara
     * @return
     */
    public MessageStatuses sendSMS_2(String phnNoPara, String msgPara, String cusCodePara, String jCodePara) throws Exception {
        if (sendMessage == null) {
            sendMessage = new SendMessage();
        }

        if (updatePaymentAsComplete == null) {
            updatePaymentAsComplete = new UpdatePaymentAsComplete();
        }

        if (receiveAndSendMonitorService == null) {
            receiveAndSendMonitorService = new ReceiveAndSendMonitorService();
        }

        if (sendMessageSaveDao == null) {
            sendMessageSaveDao = new SendMessageSaveDao();
        }

//        if (sendSMSThroughGatewayAPI == null) {
        sendSMSThroughGatewayAPI = new SendSMSThroughGatewayAPI();
//        }

        String cusCode = cusCodePara;
        String paymentCode = jCodePara;

        String messageType = MyMessageTypeEnum.NEW_JURNAL.toString();
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

            updatePaymentAsComplete.setSMSSent(paymentCode);
            receiveAndSendMonitorService.sendMessageProcess_2(phnNo, msg, curentDateAndTimeAsString);
            sendMessageSaveDao.saveToDB_2(messageType, idSMSReceive, phnNo, msg, curentDateAndTimeAsDate, msgStatus);

        }
        return msgStatus;
    }

    public void sendPaymentSms(final List<PaymentSMS_pojo> paymentListPara) {
        final UpdatePaymentAsComplete updatePaymentAsComplete = new UpdatePaymentAsComplete();
        if (sendingActive) {
            if (MySMSServer.checkServiceAvailable() == false) {
                MyMessagesUtility.showWarning("Service Not Available.");
            } else {
                List<PaymentSMS_pojo> paymentList = paymentListPara;

                int smsCount = 0;
                outerloop:
                if (Company_Profile.getMsgSendStart()) {
                    for (PaymentSMS_pojo paymentListObj : paymentList) {
                        if (paymentListObj.getIsSelected()) {
                            String phoneNo = paymentListObj.getTelNo().trim();
                            if (phoneNo != null || !phoneNo.isEmpty()) {
                                if (smsCount % Company_Profile.getMsgCountForServerRestarting() == 0) {
                                    MySMSServer.restartService();
                                }
                                if (MySMSServer.checkServiceAvailable() == true && MySMSServer.serialModemGateway.getStatus() == AGateway.GatewayStatuses.STARTED) {
                                    ++smsCount;
                                    String paymentNo = paymentListObj.getPaymentNo().trim();
                                    String message = paymentListObj.getMessage().trim();
//                new SendMessage ().send (tele, description);
                                    if (Company_Profile.getMsgSendStart()) {
                                        Boolean isSuccess = new SendMessage().send(MyMessageTypeEnum.NEW_PAYMENT.toString(), -1, phoneNo, message);
                                        if (isSuccess) {
                                            updatePaymentAsComplete.setSMSSent(paymentNo);
                                        }
                                    } else {
                                        break outerloop;
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void markAsSend(List<String> list) {
        new Payment_dao().markAsSend(list);
    }

    public void reSendPaymentSms(List<PaymentSMS_pojo> paymentListPara) {
        final UpdatePaymentAsComplete updatePaymentAsComplete = new UpdatePaymentAsComplete();
        if (sendingActive) {
            if (MySMSServer.checkServiceAvailable() == false) {
                MyMessagesUtility.showWarning("Service Not Available.");
            } else {
                List<PaymentSMS_pojo> paymentList = paymentListPara;
                int smsCount = 0;
                for (PaymentSMS_pojo paymentListObj : paymentList) {
                    if (paymentListObj.getIsSelected()) {
                        String phoneNo = paymentListObj.getTelNo().trim();
                        if (phoneNo != null || !phoneNo.isEmpty()) {
                            if (smsCount % Company_Profile.getMsgCountForServerRestarting() == 0) {
                                MySMSServer.restartService();
                            }
                            if (MySMSServer.checkServiceAvailable() == true && MySMSServer.serialModemGateway.getStatus() == AGateway.GatewayStatuses.STARTED) {
                                ++smsCount;
                                String paymentNo = paymentListObj.getPaymentNo().trim();
                                String message = paymentListObj.getMessage().trim();
//                new SendMessage ().send (tele, description);
                                Boolean isSuccess = new SendMessage().send(MyMessageTypeEnum.NEW_PAYMENT.toString(), -1, phoneNo, message);
                                if (isSuccess) {
                                    updatePaymentAsComplete.setSMSSent(paymentNo);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
