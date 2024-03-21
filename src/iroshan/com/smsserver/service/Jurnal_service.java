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
import iroshan.com.smsserver.dao.Jurnal_dao;
import iroshan.com.smsserver.dao.SendMessageSaveDao;
import iroshan.com.smsserver.dao.UpdateJurnalAsComplete;
import iroshan.com.smsserver.dao.UpdateReceiptAsComplete;
import iroshan.com.smsserver.entity.ViewSMSJurnal_entity;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.Company_Profile;
import iroshan.com.smsserver.enums.GatewayType;
import iroshan.com.smsserver.pojo.NewJurnal_pojo;
import iroshan.com.smsserver.pojo.ReceiptSMS_pojo;
import iroshan.com.smsserver.pojo.Receipt_pojo;
import iroshan.com.smsserver.service.gatway.dialog.SendSMSThroughGatewayAPI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.OutboundMessage.MessageStatuses;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class Jurnal_service {

    static Boolean sendingActive = true;
    List<NewJurnal_pojo> smsList = null;
    int smsListCurrent = 0;
    SendMessage sendMessage = null;
    UpdateJurnalAsComplete updateJurnalAsComplete = null;
    SendSMSThroughGatewayAPI sendSMSThroughGatewayAPI = null;

    public List<ReceiptSMS_pojo> search() {

        SMSMessage sMSMessage = new SMSMessage();
        List<ReceiptSMS_pojo> newReceiptSendList = new ArrayList<ReceiptSMS_pojo>();

        String receiptNoPreveies = "";
        String ACNoPreveies = "";
        String completedString = "";

        Map<String, Receipt_pojo> newReceiptList = new Jurnal_dao().search();

        for (Map.Entry<String, Receipt_pojo> entrySet : newReceiptList.entrySet()) {

            String ieCode = entrySet.getKey();
            Receipt_pojo value = entrySet.getValue();

            String receiptNo = value.getReceiptNo();
            String mCode = value.getCustomerCode();
            String cusName = value.getCusName();
            String cusOfficeNo = value.getCusOfficeNo();
            String acCode = value.getCusOfficeNo();
            String receiptAmount = MyRounding.roundToLastTwoDecimal(value.getReceiptAmount());

            String dueAmount = null;
            if (value.getLoanOrSaving() == 1) {
                dueAmount = MyRounding.roundToLastTwoDecimal((value.getOutstandingAmount() == null ? 0.00 : value.getOutstandingAmount()));
            }

            String tele = value.getTele();

            String smsMsg = sMSMessage.msgForReceipt(cusName, acCode, receiptAmount, ieCode, dueAmount, null);

            newReceiptSendList.add(new ReceiptSMS_pojo(true, receiptNo, mCode, cusName, cusOfficeNo, tele, smsMsg));

        }

        return newReceiptSendList;
    }

    public void sendReceiptSms(final List<ReceiptSMS_pojo> receiptListPara) {

        final UpdateReceiptAsComplete updateReceiptAsComplete = new UpdateReceiptAsComplete();

        if (sendingActive) {
            if (MySMSServer.checkServiceAvailable() == false) {
                MyMessagesUtility.showWarning("Service Not Available.");
            } else {

//                if (MySMSServer.getSmsServerService ().getServiceStatus () == Service.ServiceStatus.STARTED)
//                {
//                    try
//                    {
//                        MySMSServer.getSmsServerService ().stopService ();
//                        MySMSServer.getSmsServerService ().startService ();
//                    } catch (SMSLibException ex)
//                    {
//                        Logger.getLogger (Receipt_service.class.getName ()).log (Level.SEVERE, null, ex);
//                    } catch (IOException ex)
//                    {
//                        Logger.getLogger (Receipt_service.class.getName ()).log (Level.SEVERE, null, ex);
//                    } catch (InterruptedException ex)
//                    {
//                        Logger.getLogger (Receipt_service.class.getName ()).log (Level.SEVERE, null, ex);
//                    }
//                }
                SendMessage sendMessage = new SendMessage();
//                MySMSServer.setInboundFalse ();

                List<ReceiptSMS_pojo> receiptList = receiptListPara;
                for (ReceiptSMS_pojo receiptListObj : receiptList) {

                    if (receiptListObj.getIsSelected()) {

                        String phoneNo = receiptListObj.getTelNo().trim();

                        if (phoneNo != null || !phoneNo.isEmpty()) {

                            String receiptNo = receiptListObj.getReceiptNo().trim();

                            String message = receiptListObj.getMessage().trim();
                            Boolean isSuccess = sendMessage.send(MyMessageTypeEnum.NEW_RECEIPT.toString(), -1, phoneNo, message);
                            if (isSuccess) {

                                updateReceiptAsComplete.setSMSSent(receiptNo);
                            }

                        }
                    }
                }

//                MySMSServer.setInboundTrue ();
            }
        }

    }

    public Map<Integer, NewJurnal_pojo> search(String br_Code, String center_str, String type_str, String sortOrderPara) {
        System.out.println("Here 02..........................................");
        SMSMessage sMSMessage = new SMSMessage();
        Map mapNewJurnalPojo = new TreeMap<Integer, NewJurnal_pojo>();

//                "SELECT", "RECEIPT NO", "CUS. CODE", "CUS. NAME", "ACCOUNT OFFICE NO", "TELE. NO", "MESSAGE", "STATUS"
        MultiKeyMap returnList = new Jurnal_dao().search(br_Code, center_str, type_str, sortOrderPara);
        MapIterator it = returnList.mapIterator();

        int rowCount = 0;
        while (it.hasNext()) {
            it.next();

            MultiKey mk = (MultiKey) it.getKey();

//            String keyFDCode = (String) mk.getKey (0);
//            String keyFDMemeber = (String) mk.getKey (1);
            ViewSMSJurnal_entity obj1 = (ViewSMSJurnal_entity) it.getValue();

            ++rowCount;

            String fdCode = obj1.getFD_CODE();
            System.out.println("fdCode: " + fdCode);

            if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.SEWA_CREDIT.toString())) {

//                if (!Pattern.matches(".*[a-zA-Z]+.*", fdCode) || fdCode.startsWith("BTCR")) {
                String memSubOffACNo = obj1.getCM_OFFICE_NO();

                String memName = "";
                if (Company_Profile.getLanguage().equalsIgnoreCase(Company_Profile.LanguageEnum.SINHALA.toString())) {
                    memName = obj1.getCM_NAME_ENGLISH();
                } else {
                    memName = obj1.getCM_DESC();
                }

                String receipt_No = obj1.getFD_CODE();
                String memAC = obj1.getFD_TOEXPENCE();
                String amount = MyRounding.roundToLastTwoDecimal(obj1.getFD_VALUE());
                Date dateDate = obj1.getFT_DATE();
                String dateJr = MyDateAndTimeUtil.getFormatedDateAsString(dateDate);
//            Double arrearsDouble = obj1.getArrearsAmount();

                System.out.println("memAC: " + memAC);
                System.out.println("amount: " + amount);
                System.out.println("dateDate: " + dateDate);
                System.out.println("dateJr: " + dateJr);

//            ----------------------------------------------------
                if (Company_Profile.getCustomerSubOffceNoWithSMS()) {
                    if (memSubOffACNo != null) {
                        if (!memSubOffACNo.isEmpty()) {
                            memName = memName.concat("(" + memSubOffACNo + ")");
                        }
                    }

                } else {
                    memName = memName.trim();
                }

//            ------------------------------------------------------------
                String smsMsg = null;

                if (Company_Profile.getJurnalMessageType().trim().equalsIgnoreCase(Company_Profile.JurnalMessageTypeEnum.N.toString().trim())) {
                    smsMsg = sMSMessage.msgForJurnalFormat1_SewaCredit(receipt_No, memName, memAC, amount, dateJr);
                }

                if (Company_Profile.getJurnalMessageType().trim().equalsIgnoreCase(Company_Profile.JurnalMessageTypeEnum.JA.toString().trim())) {
                    String arrearsAmount = null;
                    arrearsAmount = MyRounding.roundToLastTwoDecimal((obj1.getArrearsAmount() == null ? 0.00 : obj1.getArrearsAmount()));

                    if (Company_Profile.getCompanyCode().trim().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.REGAL.toString().trim())) {
                        smsMsg = sMSMessage.msgForJurnalWithArrears(memName, memAC, amount, arrearsAmount, dateJr);
                    }
                }

//            if (Company_Profile.getJurnalMessageType () == "L&L")
//            {
//                smsMsg = sMSMessage.msgForReceipt (memName, memAC, amount, dateJr);
//            }
//
//            if (Company_Profile.getJurnalMessageType () == "JA")
//            {
//                String arrearsAmount = MyRounding.roundToLastTwoDecimal ((obj1.getArrearsAmount () == null ? 0.00 : obj1.getArrearsAmount ()));
//                smsMsg = sMSMessage.msgForJurnalWithArrears (memName, memAC, amount, arrearsAmount, dateJr);
//            }
                NewJurnal_pojo newJurnal_pojo = new NewJurnal_pojo();
                newJurnal_pojo.setIsSelect(true);
                newJurnal_pojo.setJurnalNo(obj1.getFD_CODE());
                newJurnal_pojo.setMemCode(obj1.getFD_TOMEMBER());
                newJurnal_pojo.setMemName(memName);
                newJurnal_pojo.setAcOfficeNo(obj1.getCM_OFFICE_NO());
                newJurnal_pojo.setMemTeleNo(obj1.getCM_TELE());
                newJurnal_pojo.setMessage(smsMsg);
                newJurnal_pojo.setStatus(MessageStatusEnum.PENDING.toString());

                mapNewJurnalPojo.put(rowCount, newJurnal_pojo);

            } else {

                if (!Pattern.matches(".*[a-zA-Z]+.*", fdCode) || fdCode.startsWith("BTCR")) {
                    String memSubOffACNo = obj1.getCM_OFFICE_NO();

                    String memName = "";
                    if (Company_Profile.getLanguage().equalsIgnoreCase(Company_Profile.LanguageEnum.SINHALA.toString())) {
                        memName = obj1.getCM_NAME_ENGLISH();
                    } else {
                        memName = obj1.getCM_DESC();
                    }

                    String memAC = obj1.getFD_TOEXPENCE();
                    String amount = MyRounding.roundToLastTwoDecimal(obj1.getFD_VALUE());
                    Date dateDate = obj1.getFT_DATE();
                    String dateJr = MyDateAndTimeUtil.getFormatedDateAsString(dateDate);
//            Double arrearsDouble = obj1.getArrearsAmount();

                    System.out.println("memAC: " + memAC);
                    System.out.println("amount: " + amount);
                    System.out.println("dateDate: " + dateDate);
                    System.out.println("dateJr: " + dateJr);

//            ----------------------------------------------------
                    if (Company_Profile.getCustomerSubOffceNoWithSMS()) {
                        if (memSubOffACNo != null) {
                            if (!memSubOffACNo.isEmpty()) {
                                memName = memName.concat("(" + memSubOffACNo + ")");
                            }
                        }

                    } else {
                        memName = memName.trim();
                    }

//            ------------------------------------------------------------
                    String smsMsg = null;

                    if (Company_Profile.getJurnalMessageType().trim().equalsIgnoreCase(Company_Profile.JurnalMessageTypeEnum.N.toString().trim())) {
                        smsMsg = sMSMessage.msgForJurnalFormat1(memName, memAC, amount, dateJr);
                    }

                    if (Company_Profile.getJurnalMessageType().trim().equalsIgnoreCase(Company_Profile.JurnalMessageTypeEnum.JA.toString().trim())) {
                        String arrearsAmount = null;
                        arrearsAmount = MyRounding.roundToLastTwoDecimal((obj1.getArrearsAmount() == null ? 0.00 : obj1.getArrearsAmount()));

                        if (Company_Profile.getCompanyCode().trim().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.REGAL.toString().trim())) {
                            smsMsg = sMSMessage.msgForJurnalWithArrears(memName, memAC, amount, arrearsAmount, dateJr);
                        }
                    }

//            if (Company_Profile.getJurnalMessageType () == "L&L")
//            {
//                smsMsg = sMSMessage.msgForReceipt (memName, memAC, amount, dateJr);
//            }
//
//            if (Company_Profile.getJurnalMessageType () == "JA")
//            {
//                String arrearsAmount = MyRounding.roundToLastTwoDecimal ((obj1.getArrearsAmount () == null ? 0.00 : obj1.getArrearsAmount ()));
//                smsMsg = sMSMessage.msgForJurnalWithArrears (memName, memAC, amount, arrearsAmount, dateJr);
//            }
                    NewJurnal_pojo newJurnal_pojo = new NewJurnal_pojo();
                    newJurnal_pojo.setIsSelect(true);
                    newJurnal_pojo.setJurnalNo(obj1.getFD_CODE());
                    newJurnal_pojo.setMemCode(obj1.getFD_TOMEMBER());
                    newJurnal_pojo.setMemName(memName);
                    newJurnal_pojo.setAcOfficeNo(obj1.getCM_OFFICE_NO());
                    newJurnal_pojo.setMemTeleNo(obj1.getCM_TELE());
                    newJurnal_pojo.setMessage(smsMsg);
                    newJurnal_pojo.setStatus(MessageStatusEnum.PENDING.toString());

                    mapNewJurnalPojo.put(rowCount, newJurnal_pojo);

                }
            }

        }

//        for (ViewSMSJurnal_entity returnListObj : returnList)
//        {
//
//        }
//        int rowCount = 0;
//        for (Map.Entry<String, ViewSMSJurnal_entity> entrySet : returnList.entrySet ())
//        {
//
//        }
        return mapNewJurnalPojo;
    }

    public void markAsSend(List<String> list) {
        new Jurnal_dao().markAsSend(list);
    }

    public void sendJurnalSMS(Map<Integer, NewJurnal_pojo> tableMapPara) {
        if (Company_Profile.getMsgSendStart()) {
            if (MySMSServer.checkServiceAvailable() == false) {
                MyMessagesUtility.showWarning("Service Not Available.");
            } else {

//                MySMSServer.setInboundFalse ();
                Map<Integer, NewJurnal_pojo> tableMap = tableMapPara;
                smsList = new ArrayList();

//                MySMSServer.restartService ();
                for (Map.Entry<Integer, NewJurnal_pojo> entrySet : tableMap.entrySet()) {
                    Integer key = entrySet.getKey();
                    NewJurnal_pojo newJurnalPojo = entrySet.getValue();

//                    NewJurnal_pojo newJurnalPojo = tableMap.get (keySetObj);
                    Boolean isSelected = newJurnalPojo.getIsSelect();
                    String status = newJurnalPojo.getStatus().trim();
                    if (isSelected && (!status.equalsIgnoreCase(MessageStatusEnum.SENT.toString().trim()))) {
//                        System.out.println ("********************************" + key);
                        newJurnalPojo.setMapKey(key);
                        smsList.add(newJurnalPojo);
                    }
                }
            }
        }

        sendSMS();
    }

    public void sendSMS() {
        int smsCount = 0;
        outerloop:
        if (Company_Profile.getMsgSendStart()) {

            SendMessage sendMessage = new SendMessage();
            UpdateJurnalAsComplete updateJurnalAsComplete = new UpdateJurnalAsComplete();

            for (int i = smsListCurrent; i < smsList.size(); i++) {
                ++smsListCurrent;

                NewJurnal_pojo newJurnalPojoObj = smsList.get(i);

                if (!Company_Profile.getMsgSendStart()) {
                    break outerloop;
                }

                Boolean isSelected = newJurnalPojoObj.getIsSelect();
                String phnNo = newJurnalPojoObj.getMemTeleNo().trim();
                String msg = newJurnalPojoObj.getMessage().trim();

//                        System.out.println ("msg>" + newJurnalPojoObj.getMessage ());
                if (isSelected) {
                    if (smsCount % Company_Profile.getMsgCountForServerRestarting() == 0) {
                        MySMSServer.restartService();
                    }
                    ++smsCount;
                    Integer mapKey = newJurnalPojoObj.getMapKey();
                    if (MySMSServer.checkServiceAvailable() == true && MySMSServer.serialModemGateway.getStatus() == AGateway.GatewayStatuses.STARTED) {
                        Boolean isSuccess = sendMessage.send(mapKey, MyMessageTypeEnum.NEW_JURNAL.toString(), -1, phnNo, msg);
                        if (isSuccess) {
                            String fdCode = newJurnalPojoObj.getJurnalNo().trim();
                            String fdMemCode = newJurnalPojoObj.getMemCode().trim();
                            updateJurnalAsComplete.setSMSSent(fdCode, fdMemCode);
                        }
                    }
                }
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
    public MessageStatuses sendSMS_2(String phnNoPara, String msgPara, String cusCodePara, String jCodePara) throws Exception {
        if (sendMessage == null) {
            sendMessage = new SendMessage();
        }

        if (updateJurnalAsComplete == null) {
            updateJurnalAsComplete = new UpdateJurnalAsComplete();
        }

        if (sendSMSThroughGatewayAPI == null) {
            sendSMSThroughGatewayAPI = new SendSMSThroughGatewayAPI();
        }

        String cusCode = cusCodePara;
        String jCode = jCodePara;

        String messageType = MyMessageTypeEnum.NEW_JURNAL.toString();
        Integer idSMSReceive = -1;
        String phnNo = phnNoPara;
        String msg = msgPara;
        phnNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phnNo);

        MessageStatuses msgStatus = null;
        if (phnNo != null) {

            msgStatus = sendMessage.send_5(phnNo, msg);

        } else {
            msgStatus = MessageStatuses.UNSENT;
        }
//        MessageStatuses msgStatus = sendMessage.send_2 (messageType, idSMSReceive, phnNo, msg);
        if (msgStatus == MessageStatuses.SENT) {

            String curentDateAndTimeAsString = Company_Profile.getCurrentDateAsString();
            Date curentDateAndTimeAsDate = Company_Profile.getCurrentDateAsDate();

            updateJurnalAsComplete.setSMSSent(jCode, cusCode);
            new ReceiveAndSendMonitorService().sendMessageProcess_2(phnNo, msg, curentDateAndTimeAsString);
            new SendMessageSaveDao().saveToDB_2(messageType, idSMSReceive, phnNo, msg, curentDateAndTimeAsDate, msgStatus);

        }
        return msgStatus;
    }

    public void reSendJurnalSMS(Map<Integer, NewJurnal_pojo> mapPara) {
        UpdateJurnalAsComplete updateJurnalAsComplete = new UpdateJurnalAsComplete();

        if (sendingActive) {
            if (MySMSServer.checkServiceAvailable() == false) {
                MyMessagesUtility.showWarning("Service Not Available.");
            } else {

                SendMessage sendMessage = new SendMessage();

                Map<Integer, NewJurnal_pojo> tableMap = mapPara;
                List<NewJurnal_pojo> smsList = new ArrayList();

//                MySMSServer.restartService ();
                for (Map.Entry<Integer, NewJurnal_pojo> entrySet : tableMap.entrySet()) {

                    Integer key = entrySet.getKey();
                    NewJurnal_pojo newJurnalPojo = entrySet.getValue();

//                    NewJurnal_pojo newJurnalPojo = tableMap.get (keySetObj);
                    Boolean isSelected = newJurnalPojo.getIsSelect();
                    String msgStatus = newJurnalPojo.getStatus().trim();

                    if (isSelected && (msgStatus.equalsIgnoreCase(MessageStatusEnum.UNSENT.toString()) || msgStatus.equalsIgnoreCase(MessageStatusEnum.FAILED.toString()))) {
//                        System.out.println ("********************************" + key);
                        newJurnalPojo.setMapKey(key);
                        smsList.add(newJurnalPojo);
                    }
                }

                int smsCount = 0;
                for (NewJurnal_pojo newJurnalPojoObj : smsList) {

                    Boolean isSelected = newJurnalPojoObj.getIsSelect();
                    String phnNo = newJurnalPojoObj.getMemTeleNo().trim();
                    String msg = newJurnalPojoObj.getMessage().trim();

                    System.out.println("msg>" + newJurnalPojoObj.getMessage());
                    if (isSelected) {
                        if (smsCount % Company_Profile.getMsgCountForServerRestarting() == 0) {
                            MySMSServer.restartService();
                        }
                        ++smsCount;
                        Integer mapKey = newJurnalPojoObj.getMapKey();
                        if (MySMSServer.checkServiceAvailable() == true && MySMSServer.serialModemGateway.getStatus() != AGateway.GatewayStatuses.STARTED) {
                            Boolean isSuccess = sendMessage.send(mapKey, MyMessageTypeEnum.NEW_JURNAL.toString(), -1, phnNo, msg);
                            if (isSuccess) {
                                String fdCode = newJurnalPojoObj.getJurnalNo().trim();
                                String fdMemCode = newJurnalPojoObj.getMemCode().trim();
                                updateJurnalAsComplete.setSMSSent(fdCode, fdMemCode);
                            }
                        }
                    }
                }
            }
        }
    }
}
