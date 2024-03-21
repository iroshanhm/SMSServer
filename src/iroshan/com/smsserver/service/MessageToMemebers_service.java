/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyPhoneNoFormatingUtility;
import iroshan.com.smsserver.dao.Customer_dao;
import iroshan.com.smsserver.dao.SendMessageSaveDao;
import iroshan.com.smsserver.dao.UpdateJurnalAsComplete;
import iroshan.com.smsserver.dao.UpdateReceiptAsComplete;
import iroshan.com.smsserver.entity.SmsSendEntity;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.Company_Profile;
import iroshan.com.smsserver.pojo.MessageToMemeberSearch_pojo;
import iroshan.com.smsserver.pojo.NewJurnal_pojo;
import iroshan.com.smsserver.pojo.ReceiptSMS_pojo;
import iroshan.com.smsserver.pojo.SMSSend_pojo;
import static iroshan.com.smsserver.service.Jurnal_service.sendingActive;
import iroshan.com.smsserver.service.gatway.dialog.SendSMSThroughGatewayAPI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.OutboundMessage.MessageStatuses;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class MessageToMemebers_service {


    static Boolean sendingActive = true;
    SendMessage sendMessage = null;

    ReceiveAndSendMonitorService receiveAndSendMonitorService = null;
    SendMessageSaveDao sendMessageSaveDao = null;
    SendSMSThroughGatewayAPI sendSMSThroughGatewayAPI = null;

    public void search() {

    }

    public Map<Integer, MessageToMemeberSearch_pojo> search(String centerPara, String namePara, String telePara, String subACOfficeNoPara, Date birthdayPara, String sortByPara, String code_prefixPara) {
        Map<Integer, MessageToMemeberSearch_pojo> map = new TreeMap<Integer, MessageToMemeberSearch_pojo>();
        List<MessageToMemeberSearch_pojo> resList = new Customer_dao().searchCustomer(centerPara, namePara, telePara, subACOfficeNoPara, birthdayPara, sortByPara, code_prefixPara);

        Integer seq = 0;
        for (MessageToMemeberSearch_pojo messageToMemeberSearch_pojo : resList) {
            ++seq;
            map.put(seq, messageToMemeberSearch_pojo);
        }

        return map;
    }





    public void sendSms(Map<Integer, MessageToMemeberSearch_pojo> mapPara, String messagePara) {
        if (sendingActive) {
            if (MySMSServer.checkServiceAvailable() == false) {
                MyMessagesUtility.showWarning("Service Not Available.");
            } else {

                SendMessage sendMessage = new SendMessage();
//                MySMSServer.setInboundFalse ();

                String message = messagePara;
                Map<Integer, MessageToMemeberSearch_pojo> map = mapPara;
                List<MessageToMemeberSearch_pojo> smsList = new ArrayList();

                List<SMSSend_pojo> smsSendList = new ArrayList();

                for (Map.Entry<Integer, MessageToMemeberSearch_pojo> entrySet : map.entrySet()) {

                    Integer key = entrySet.getKey();
                    MessageToMemeberSearch_pojo messageToMemeberSearch_pojo = entrySet.getValue();
//                    NewJurnal_pojo newJurnalPojo = tableMap.get (keySetObj);
                    Boolean isSelected = messageToMemeberSearch_pojo.getIsSelect();


                    if (isSelected) {

                        String phnNo = messageToMemeberSearch_pojo.getTele().trim();
                        String msg = message.trim();

//                        messageToMemeberSearch_pojo.setMapKey (key);
//                        smsList.add (messageToMemeberSearch_pojo);


                        SMSSend_pojo sMSSend_pojo = new SMSSend_pojo();
                        sMSSend_pojo.setKey(key);
                        sMSSend_pojo.setIdSMSReceive(-1);
                        sMSSend_pojo.setMessage(msg);
                        sMSSend_pojo.setMessageType(MyMessageTypeEnum.CUSTOM.toString());
                        sMSSend_pojo.setPhoneNo(phnNo);
                        smsSendList.add(sMSSend_pojo);
                    }
                }

                sendMessage.send(smsSendList);

            }
        }
    }




    /**
     *
     * @param phnNoPara
     * @param msgPara
     * @param cusCodePara
     * @param jCodePara
     * @return
     */
    public MessageStatuses sendSMS_2(String phnNoPara, String msgPara) throws Exception {
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

            receiveAndSendMonitorService.sendMessageProcess_2(phnNo, msg, curentDateAndTimeAsString);
            sendMessageSaveDao.saveToDB_2(messageType, idSMSReceive, phnNo, msg, curentDateAndTimeAsDate, msgStatus);

        }
        return msgStatus;
    }








    public void reSendSms(Map<Integer, MessageToMemeberSearch_pojo> mapPara, String messagePara) {
        if (sendingActive) {
            if (MySMSServer.checkServiceAvailable() == false) {
                MyMessagesUtility.showWarning("Service Not Available.");
            } else {

                SendMessage sendMessage = new SendMessage();
//                MySMSServer.setInboundFalse ();

                String message = messagePara;
                Map<Integer, MessageToMemeberSearch_pojo> map = mapPara;
                List<MessageToMemeberSearch_pojo> smsList = new ArrayList();

                List<SMSSend_pojo> smsSendList = new ArrayList();

                for (Map.Entry<Integer, MessageToMemeberSearch_pojo> entrySet : map.entrySet()) {

                    Integer key = entrySet.getKey();
                    MessageToMemeberSearch_pojo messageToMemeberSearch_pojo = entrySet.getValue();
//                    NewJurnal_pojo newJurnalPojo = tableMap.get (keySetObj);
                    Boolean isSelected = messageToMemeberSearch_pojo.getIsSelect();
                    String unsentStr = messageToMemeberSearch_pojo.getStatus().trim();

                    if (isSelected && (unsentStr.equalsIgnoreCase(MessageStatusEnum.UNSENT.toString()) || unsentStr.equalsIgnoreCase(MessageStatusEnum.FAILED.toString()))) {

                        String phnNo = messageToMemeberSearch_pojo.getTele().trim();
                        String msg = message.trim();

//                        messageToMemeberSearch_pojo.setMapKey (key);
//                        smsList.add (messageToMemeberSearch_pojo);


                        SMSSend_pojo sMSSend_pojo = new SMSSend_pojo();
                        sMSSend_pojo.setKey(key);
                        sMSSend_pojo.setIdSMSReceive(-1);
                        sMSSend_pojo.setMessage(msg);
                        sMSSend_pojo.setMessageType(MyMessageTypeEnum.CUSTOM.toString());
                        sMSSend_pojo.setPhoneNo(phnNo);
                        smsSendList.add(sMSSend_pojo);
                    }
                }

                sendMessage.send(smsSendList);

            }
        }
    }



}
