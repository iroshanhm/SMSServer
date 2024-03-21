/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.Company_Profile;
import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyPhoneNoFormatingUtility;
import iroshan.com.smsserver.dao.SendMessageSaveDao;
import iroshan.com.smsserver.dao.UpdatePaymentAsComplete;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.com.smsserver.service.gatway.dialog.SendSMSThroughGatewayAPI;
import java.util.Date;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.smslib.OutboundMessage;

/**
 *
 * @author iroshan
 */
public class Automated_Services {

    SendMessage sendMessage = null;
    UpdatePaymentAsComplete updatePaymentAsComplete = null;
    ReceiveAndSendMonitorService receiveAndSendMonitorService = null;
    SendMessageSaveDao sendMessageSaveDao = null;
    SendSMSThroughGatewayAPI sendSMSThroughGatewayAPI = null;

    public OutboundMessage.MessageStatuses sendSMS_2(String type_Para, String phnNoPara, String msgPara, String cusCodePara, String jCodePara) throws Exception {
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

            setSMSSent(type_Para, paymentCode);
            receiveAndSendMonitorService.sendMessageProcess_2(phnNo, msg, curentDateAndTimeAsString);
            sendMessageSaveDao.saveToDB_2(messageType, idSMSReceive, phnNo, msg, curentDateAndTimeAsDate, msgStatus);

        }
        return msgStatus;
    }

     public void setSMSSent(String type_Para, String paymentNoPara) {

        String type = type_Para;

        if (type.equalsIgnoreCase("P")) {
            if (paymentNoPara != null) {

                String paymentNo = paymentNoPara.trim();

                Session session = SessionFactoryUtil.getSession();
                Transaction tx = null;

                try {
                    tx = session.beginTransaction();

                    String sqlPaidAll = "UPDATE PAYMENT SET is_sms_sent = 'Y' WHERE PA_CODE LIKE '" + paymentNo + "'";
                    System.out.println("UPDATE PAYMENT SET is_sms_sent: " + sqlPaidAll);
                    SQLQuery sqlQuery = session.createSQLQuery(sqlPaidAll);
                    int result = sqlQuery.executeUpdate();
                    System.out.println("result> " + result);

                    tx.commit();
                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    e.printStackTrace();

                } finally {
//            session.close ();
                }
            }
        }

        if (type.equalsIgnoreCase("R")) {
            if (paymentNoPara != null) {

                String paymentNo = paymentNoPara.trim();

                Session session = SessionFactoryUtil.getSession();
                Transaction tx = null;

                try {
                    tx = session.beginTransaction();

                    String sqlPaidAll = "UPDATE RECEIPT SET is_sms_sent = 'Y' WHERE RE_CODE LIKE '" + paymentNo + "'";
                    System.out.println("sqlPaidAll: " + sqlPaidAll);
                    SQLQuery sqlQuery = session.createSQLQuery(sqlPaidAll);
                    int result = sqlQuery.executeUpdate();
                    System.out.println("result> " + result);

                    tx.commit();
                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    e.printStackTrace();

                } finally {
//            session.close ();
                }
            }
        }

    }
}
