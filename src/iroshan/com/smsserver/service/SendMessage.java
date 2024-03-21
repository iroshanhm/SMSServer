/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyPhoneNoFormatingUtility;
import iroshan.com.smsserver.dao.SendMessageSaveDao;
import iroshan.Company_Profile;
import iroshan.com.smsserver.enums.GatewayProvider;
import iroshan.com.smsserver.enums.GatewayType;
import iroshan.com.smsserver.pojo.SMSSend_pojo;
import iroshan.com.smsserver.service.gatway.dialog.SendSMSThroughGatewayAPI;
import iroshan.com.smsserver.service.gatway.mobitel.SendSMSThroughMobitelGatewayAPI;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.OutboundMessage.MessageStatuses;
import org.smslib.Service;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class SendMessage {

    static boolean checkService = false;
    SendSMSThroughGatewayAPI sendSMSThroughGatewayAPI = null;
    SendSMSThroughMobitelGatewayAPI sendSMSThroughMobitelGatewayAPI = null;

    /**
     *
     * @param messageTypePara
     * @param idSMSReceivePara
     * @param phoneNoPara
     * @param messagePara
     * @return
     */
    public void send(List<SMSSend_pojo> listPara) {
        Boolean aFlag = true;

        if (MySMSServer.checkServiceAvailable() == false) {
            aFlag = false;
            MyMessagesUtility.showWarning("Service Not Available.");
        }

        if (aFlag) {

            int smscount = 0;
            outerloop:
            if (Company_Profile.getMsgSendStart()) {
                for (SMSSend_pojo sMSSend_pojo : listPara) {

                    if (smscount % Company_Profile.getMsgCountForServerRestarting() == 0) {
                        MySMSServer.restartService();
                    }

                    Integer key = sMSSend_pojo.getKey();
                    String messageType = sMSSend_pojo.getMessageType();
                    Integer idSMSReceive = sMSSend_pojo.getIdSMSReceive();
                    String phoneNo = sMSSend_pojo.getPhoneNo();
                    String message = sMSSend_pojo.getMessage();

                    boolean isTest = Company_Profile.getIsTest();
                    String testTel = Company_Profile.getTestingTele();
                    phoneNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phoneNo);

                    OutboundMessage msg = new OutboundMessage(phoneNo, message);
//                msg.setRefNo ("NEW_JURNAL_".concat (String.valueOf (key)));
                    msg.setFrom(Company_Profile.getCompanyName());
                    msg.setStatusReport(true);
                    Date msgDate = msg.getDate();

//                try
//                {
                    if (!isTest) // REAL SMS
                    {
                        if (MySMSServer.checkServiceAvailable() == true && MySMSServer.serialModemGateway.getStatus() == AGateway.GatewayStatuses.STARTED) {
                            boolean returnVal = false;
                            try {
                                if (phoneNo != null) {
                                    if (Company_Profile.getMsgSendStart()) {
                                        returnVal = MySMSServer.getSmsServerService().sendMessage(msg);
                                    } else {
                                        break outerloop;
                                    }
                                } else {
                                    msg.setMessageStatus(MessageStatuses.UNSENT);
                                }
                            } catch (TimeoutException ex) {
                                Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                                MySMSServer.restartServiceWithWaiting();
                            } catch (GatewayException ex) {
                                Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                                MySMSServer.restartServiceWithWaiting();
                            } catch (IOException ex) {
                                Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                                MySMSServer.restartServiceWithWaiting();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                                MySMSServer.restartServiceWithWaiting();
                            }
                            ++smscount;
                            if (returnVal) {
                                new ReceiveAndSendMonitorService().sendMessageProcess(msg);
                                new SendMessageSaveDao().saveToDB(messageType, idSMSReceive, phoneNo, message, msgDate, msg);
                            }
                            new OutboundNotificationProcessing().processWithKey(key, msg);
                        }
                    } else // TEST SMS
                    {
                        int testTelLenth = testTel.length();

                        if (testTelLenth > 9) {
                            int t = testTelLenth - 9;
                            testTel = testTel.substring(t, (t + 9));
                        }

                        if (phoneNo.trim().equalsIgnoreCase(testTel)) {

                            if (MySMSServer.checkServiceAvailable() == true && MySMSServer.serialModemGateway.getStatus() == AGateway.GatewayStatuses.STARTED) {
                                boolean returnVal = false;
                                try {
                                    if (phoneNo != null) {
                                        if (Company_Profile.getMsgSendStart() == true) {
                                            returnVal = MySMSServer.getSmsServerService().sendMessage(msg);
                                        }
                                    } else {
                                        msg.setMessageStatus(MessageStatuses.UNSENT);
                                    }

                                } catch (TimeoutException ex) {
                                    Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                                    MySMSServer.restartServiceWithWaiting();
                                } catch (GatewayException ex) {
                                    Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                                    MySMSServer.restartServiceWithWaiting();
                                } catch (IOException ex) {
                                    Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                                    MySMSServer.restartServiceWithWaiting();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                                    MySMSServer.restartServiceWithWaiting();
                                }
//                        returnVal = MySMSServer.getSmsServerService ().queueMessage (sendMessage);
                                if (returnVal) {
                                    new ReceiveAndSendMonitorService().sendMessageProcess(msg);
                                    new OutboundNotificationProcessing().processWithKey(key, msg);
                                    new SendMessageSaveDao().saveToDB(messageType, idSMSReceive, phoneNo, message, msgDate, msg);

                                } else {
                                    new OutboundNotificationProcessing().processWithKey(key, msg);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public static Boolean send(String messageTypePara, Integer idSMSReceivePara, String phoneNoPara, String messagePara) {
        Boolean returnVal = true;

        if (MySMSServer.checkServiceAvailable() == false) {
            returnVal = false;
            if (!checkService) {
                MyMessagesUtility.showWarning("Service Not Available.");
            }
            checkService = true;
        } else {

            Boolean aFlag = true;

            String messageType = messageTypePara;
            Integer idSMSReceive = idSMSReceivePara;
            String phoneNo = phoneNoPara;
            String message = messagePara;

            boolean isTest = Company_Profile.getIsTest();
            String testTel = Company_Profile.getTestingTele();

//            int phnNoLenth = phoneNo.length ();
//            if (phnNoLenth > 9)
//            {
//                int t = phnNoLenth - 9;
//                phoneNo = phoneNo.substring (t, (t + 9));
//            } else
//            {
//                aFlag = false;
//            }
            phoneNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phoneNo);

            if (aFlag) {

                OutboundMessage msg = new OutboundMessage(phoneNo, message);
                msg.setFrom(Company_Profile.getCompanyName());
                msg.setStatusReport(true);

                Date msgDate = msg.getDate();

                if (!isTest) {

                    try {
                        if (phoneNo != null) {
                            if (Company_Profile.getMsgSendStart() == true) {
                                returnVal = MySMSServer.getSmsServerService().sendMessage(msg);
                            }
                        } else {
                            msg.setMessageStatus(MessageStatuses.UNSENT);
                        }

                    } catch (TimeoutException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (GatewayException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (IOException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    }
                    if (returnVal) {
                        new ReceiveAndSendMonitorService().sendMessageProcess(msg);
                        new SendMessageSaveDao().saveToDB(messageType, idSMSReceive, phoneNo, message, msgDate, msg);
                    }

                    new OutboundNotificationProcessing().process(null, msg);

                } else {
                    int testTelLenth = testTel.length();

                    if (testTelLenth > 9) {
                        int t = testTelLenth - 9;
                        testTel = testTel.substring(t, (t + 9));
                    }

                    if (phoneNo.trim().equalsIgnoreCase(testTel)) {

//                        MySMSServer.restartService ();
                        try {
                            if (phoneNo != null) {
                                if (Company_Profile.getMsgSendStart() == true) {
                                    returnVal = MySMSServer.getSmsServerService().sendMessage(msg);
                                }
                            } else {
                                msg.setMessageStatus(MessageStatuses.UNSENT);
                            }
                        } catch (TimeoutException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (GatewayException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (IOException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        }
//                        returnVal = MySMSServer.getSmsServerService ().queueMessage (sendMessage);
                        if (returnVal) {
                            new ReceiveAndSendMonitorService().sendMessageProcess(msg);
                            new OutboundNotificationProcessing().process(null, msg);
                            new SendMessageSaveDao().saveToDB(messageType, idSMSReceive, phoneNo, message, msgDate, msg);
                        } else {
                            new OutboundNotificationProcessing().process(null, msg);
                        }

                    }

                }

            }
        }

        return returnVal;
    }

    Boolean send(Integer keyPara, String messageTypePara, int idSMSReceivePara, String phoneNoPara, String messagePara) {
        Boolean returnVal = true;

        if (MySMSServer.checkServiceAvailable() == false) {
            returnVal = false;
            if (!checkService) {
                MyMessagesUtility.showWarning("Service Not Available.");
            }
            checkService = true;
        } else {

            Boolean aFlag = true;

            Integer key = keyPara;
            String messageType = messageTypePara;
            Integer idSMSReceive = idSMSReceivePara;
            String phoneNo = phoneNoPara;
            String message = messagePara;

            boolean isTest = Company_Profile.getIsTest();
            String testTel = Company_Profile.getTestingTele();

            phoneNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phoneNo);

            if (aFlag) {

                OutboundMessage msg = new OutboundMessage(phoneNo, message);
//                msg.setRefNo ("NEW_JURNAL_".concat (String.valueOf (key)));
                msg.setFrom(Company_Profile.getCompanyName());
                msg.setStatusReport(true);
                Date msgDate = msg.getDate();

                if (!isTest) {

                    try {
                        if (phoneNo != null) {
                            if (Company_Profile.getMsgSendStart() == true) {
                                returnVal = MySMSServer.getSmsServerService().sendMessage(msg);
                            }

                        } else {
                            msg.setMessageStatus(MessageStatuses.UNSENT);
                        }

                    } catch (TimeoutException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (GatewayException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (IOException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    }

                    if (returnVal) {
                        new ReceiveAndSendMonitorService().sendMessageProcess(msg);
                        new SendMessageSaveDao().saveToDB(messageType, idSMSReceive, phoneNo, message, msgDate, msg);
                    }

                    new OutboundNotificationProcessing().processWithKey(key, msg);

                } else {
                    int testTelLenth = testTel.length();

                    if (testTelLenth > 9) {
                        int t = testTelLenth - 9;
                        testTel = testTel.substring(t, (t + 9));
                    }

                    if (phoneNo.trim().equalsIgnoreCase(testTel)) {

//                        MySMSServer.restartService ();
                        try {
                            if (phoneNo != null) {
                                if (Company_Profile.getMsgSendStart() == true) {
                                    returnVal = MySMSServer.getSmsServerService().sendMessage(msg);
                                }
                            } else {
                                msg.setMessageStatus(MessageStatuses.UNSENT);
                            }
                        } catch (TimeoutException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (GatewayException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (IOException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        }
//                        returnVal = MySMSServer.getSmsServerService ().queueMessage (sendMessage);
                        if (returnVal) {
                            new ReceiveAndSendMonitorService().sendMessageProcess(msg);
                            new OutboundNotificationProcessing().processWithKey(key, msg);
                            new SendMessageSaveDao().saveToDB(messageType, idSMSReceive, phoneNo, message, msgDate, msg);
                        } else {
                            new OutboundNotificationProcessing().processWithKey(key, msg);
                        }

                    }

                }

            }
        }

        return returnVal;
    }

    public MessageStatuses send_2(String messageTypePara, int idSMSReceivePara, String phoneNoPara, String messagePara) {

        Boolean returnVal = true;
        MessageStatuses msgStatus = null;

        if (MySMSServer.checkServiceAvailable() == false) {

            returnVal = false;
            if (!checkService) {
                MyMessagesUtility.showWarning("Service Not Available.");
            }
            checkService = true;

        } else {

            Boolean aFlag = true;
            String messageType = messageTypePara;
            Integer idSMSReceive = idSMSReceivePara;
            String phoneNo = phoneNoPara;
            String message = messagePara;
            boolean isTest = Company_Profile.getIsTest();
            String testTel = Company_Profile.getTestingTele();

            phoneNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phoneNo);

            if (aFlag) {

                OutboundMessage msg = new OutboundMessage(phoneNo, message);
                msg.setFrom(Company_Profile.getCompanyName());
                msg.setStatusReport(true);
                Date msgDate = msg.getDate();

                if (!isTest) {
                    try {
                        if (phoneNo != null) {

                            if (Company_Profile.getMsgSendStart() == true) {
                                returnVal = MySMSServer.getSmsServerService().sendMessage(msg);
                            }

                        } else {
                            msg.setMessageStatus(MessageStatuses.UNSENT);
                        }

                    } catch (TimeoutException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (GatewayException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (IOException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                        MySMSServer.restartServiceWithWaiting();
                    }

                    if (returnVal) {
                        new ReceiveAndSendMonitorService().sendMessageProcess(msg);
                        new SendMessageSaveDao().saveToDB(messageType, idSMSReceive, phoneNo, message, msgDate, msg);
                    }

                } else {
                    int testTelLenth = testTel.length();

                    if (testTelLenth > 9) {
                        int t = testTelLenth - 9;
                        testTel = testTel.substring(t, (t + 9));
                    }

                    if (phoneNo.trim().equalsIgnoreCase(testTel)) {

//                        MySMSServer.restartService ();
                        try {
                            if (phoneNo != null) {
                                if (Company_Profile.getMsgSendStart() == true) {
                                    returnVal = MySMSServer.getSmsServerService().sendMessage(msg);
                                }
                            } else {
                                msg.setMessageStatus(MessageStatuses.UNSENT);
                            }
                        } catch (TimeoutException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (GatewayException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (IOException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                            MySMSServer.restartServiceWithWaiting();
                        }
//                        returnVal = MySMSServer.getSmsServerService ().queueMessage (sendMessage);
                        if (returnVal) {
//                            new ReceiveAndSendMonitorService ().sendMessageProcess (msg);
//                            new SendMessageSaveDao ().saveToDB (messageType, idSMSReceive, phoneNo, message, msgDate, msg);
                        }

                    }

                }
                msgStatus = msg.getMessageStatus();
            }
        }

        return msgStatus;
    }

    public MessageStatuses send_3(OutboundMessage outboundMessagePara) throws TimeoutException, GatewayException, IOException, InterruptedException {

        Boolean returnVal = true;
        MessageStatuses msgStatus = null;

        if (MySMSServer.checkServiceAvailable() == false) {

            returnVal = false;
            if (!checkService) {
                MyMessagesUtility.showWarning("Service Not Available.");
            }
            checkService = true;

        } else {

            Boolean aFlag = true;

            if (aFlag) {

                OutboundMessage obMsg = outboundMessagePara;
                boolean isTest = Company_Profile.getIsTest();
                String testTel = Company_Profile.getTestingTele();

                obMsg.setFrom(Company_Profile.getCompanyName());
                obMsg.setStatusReport(true);
                Date msgDate = obMsg.getDate();

                if (!isTest) {
//                    try
//                    {
                    if (obMsg != null) {

                        if (Company_Profile.getMsgSendStart() == true) {
                            returnVal = MySMSServer.getSmsServerService().sendMessage(obMsg);
                        }

                    } else {
                        obMsg.setMessageStatus(MessageStatuses.UNSENT);
                    }

//                    } catch (TimeoutException ex)
//                    {
//                        Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                        MySMSServer.restartServiceWithWaiting ();
//                    } catch (GatewayException ex)
//                    {
//                        Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                        MySMSServer.restartServiceWithWaiting ();
//                    } catch (IOException ex)
//                    {
//                        Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                        MySMSServer.restartServiceWithWaiting ();
//                    } catch (InterruptedException ex)
//                    {
//                        Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                        MySMSServer.restartServiceWithWaiting ();
//                    }
                } else {
                    int testTelLenth = testTel.length();

                    if (testTelLenth > 9) {
                        int t = testTelLenth - 9;
                        testTel = testTel.substring(t, (t + 9));
                    }

                    String phoneNo = obMsg.getRecipient();
                    if (phoneNo.trim().equalsIgnoreCase(testTel)) {

//                        try
//                        {
                        if (phoneNo != null) {
                            if (Company_Profile.getMsgSendStart() == true) {
                                returnVal = MySMSServer.getSmsServerService().sendMessage(obMsg);
                            }
                        } else {
                            obMsg.setMessageStatus(MessageStatuses.UNSENT);
                        }
//                        } catch (TimeoutException ex)
//                        {
//                            Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                            MySMSServer.restartServiceWithWaiting ();
//                        } catch (GatewayException ex)
//                        {
//                            Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                            MySMSServer.restartServiceWithWaiting ();
//                        } catch (IOException ex)
//                        {
//                            Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                            MySMSServer.restartServiceWithWaiting ();
//                        } catch (InterruptedException ex)
//                        {
//                            Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                            MySMSServer.restartServiceWithWaiting ();
//                        }
//                        returnVal = MySMSServer.getSmsServerService ().queueMessage (sendMessage);

                    }

                }
                msgStatus = obMsg.getMessageStatus();
            }
        }

        return msgStatus;
    }

    public MessageStatuses send4(OutboundMessage outboundMessagePara) throws TimeoutException, GatewayException, IOException, InterruptedException {

        Boolean returnVal = true;
        MessageStatuses msgStatus = null;

        if (MySMSServer.checkServiceAvailable() == false) {

            returnVal = false;
            if (!checkService) {
                MyMessagesUtility.showWarning("Service Not Available.");
            }
            checkService = true;

        } else {

            Boolean aFlag = true;

            if (aFlag) {

                OutboundMessage obMsg = outboundMessagePara;
                boolean isTest = Company_Profile.getIsTest();
                String testTel = Company_Profile.getTestingTele();

                obMsg.setFrom(Company_Profile.getCompanyName());
                obMsg.setStatusReport(true);
                Date msgDate = obMsg.getDate();

                if (!isTest) {
//                    try
//                    {
                    if (obMsg != null) {

                        if (Company_Profile.getMsgSendStart() == true) {
                            returnVal = MySMSServer.getSmsServerService().sendMessage(obMsg);
                        }

                    } else {
                        obMsg.setMessageStatus(MessageStatuses.UNSENT);
                    }

//                    } catch (TimeoutException ex)
//                    {
//                        Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                        MySMSServer.restartServiceWithWaiting ();
//                    } catch (GatewayException ex)
//                    {
//                        Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                        MySMSServer.restartServiceWithWaiting ();
//                    } catch (IOException ex)
//                    {
//                        Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                        MySMSServer.restartServiceWithWaiting ();
//                    } catch (InterruptedException ex)
//                    {
//                        Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                        MySMSServer.restartServiceWithWaiting ();
//                    }
                } else {
                    int testTelLenth = testTel.length();

                    if (testTelLenth > 9) {
                        int t = testTelLenth - 9;
                        testTel = testTel.substring(t, (t + 9));
                    }

                    String phoneNo = obMsg.getRecipient();
                    if (phoneNo.trim().equalsIgnoreCase(testTel)) {

//                        try
//                        {
                        if (phoneNo != null) {
                            if (Company_Profile.getMsgSendStart() == true) {
                                returnVal = MySMSServer.getSmsServerService().sendMessage(obMsg);
                            }
                        } else {
                            obMsg.setMessageStatus(MessageStatuses.UNSENT);
                        }
//                        } catch (TimeoutException ex)
//                        {
//                            Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                            MySMSServer.restartServiceWithWaiting ();
//                        } catch (GatewayException ex)
//                        {
//                            Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                            MySMSServer.restartServiceWithWaiting ();
//                        } catch (IOException ex)
//                        {
//                            Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                            MySMSServer.restartServiceWithWaiting ();
//                        } catch (InterruptedException ex)
//                        {
//                            Logger.getLogger (SendMessage.class.getName ()).log (Level.SEVERE, null, ex);
//                            MySMSServer.restartServiceWithWaiting ();
//                        }
//                        returnVal = MySMSServer.getSmsServerService ().queueMessage (sendMessage);

                    }

                }
                msgStatus = obMsg.getMessageStatus();
            }
        }

        return msgStatus;
    }

    /**
     *
     * @param String phoneNoPara, String msgPara
     * @return
     * @throws TimeoutException
     * @throws GatewayException
     * @throws IOException
     * @throws InterruptedException
     * @see Decide sms send via Dongle or Gateway
     */
    public MessageStatuses send_5(String phoneNoPara, String msgPara) throws Exception {

        boolean isTest = Company_Profile.getIsTest();
        Boolean returnVal = true;
        MessageStatuses msgStatus = null;
        String phoneNo = null;

        if (isTest) {
            phoneNo = Company_Profile.getTestingTele();
            String confirmTele = Company_Profile.getConfirmTeleNo();
            if (phoneNoPara.equalsIgnoreCase(confirmTele)) {
                phoneNo = confirmTele;
            }
        } else {
            phoneNo = phoneNoPara;
        }

        phoneNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phoneNo);
        String msg = msgPara;

        if (phoneNo != null) {

            if (Company_Profile.getGatewayType().trim().equalsIgnoreCase(GatewayType.GATEWAY.toString().trim())) {
                if (Company_Profile.getGatewayProvider().trim().equalsIgnoreCase(GatewayProvider.MOBITEL.toString().trim())) {

                    if (sendSMSThroughMobitelGatewayAPI == null) {
                        sendSMSThroughMobitelGatewayAPI = new SendSMSThroughMobitelGatewayAPI();
                    }
                    msgStatus = sendSMSThroughMobitelGatewayAPI.send(phoneNo, msg);

                } else if (Company_Profile.getGatewayProvider().trim().equalsIgnoreCase(GatewayProvider.DIALOG.toString().trim())) {

                    if (sendSMSThroughGatewayAPI == null) {
                        sendSMSThroughGatewayAPI = new SendSMSThroughGatewayAPI();
                    }
                    msgStatus = sendSMSThroughGatewayAPI.send(phoneNo, msg);

                }

            }

            if (Company_Profile.getGatewayType().trim().equalsIgnoreCase(GatewayType.DONGLE.toString().trim())) {

                if (MySMSServer.checkServiceAvailable() == false) {

                    returnVal = false;
                    if (!checkService) {
                        MyMessagesUtility.showWarning("Service Not Available.");
                    }
                    checkService = true;

                } else {

                    Boolean aFlag = true;

                    if (aFlag) {

                        OutboundMessage obMsg = new OutboundMessage(phoneNo, msg);

                        String testTel = Company_Profile.getTestingTele();

                        obMsg.setFrom(Company_Profile.getCompanyName());
                        obMsg.setStatusReport(true);
                        Date msgDate = obMsg.getDate();

//                    if (!isTest) {
//                    try
//                    {
                        if (obMsg != null) {

                            if (Company_Profile.getMsgSendStart() == true) {
                                returnVal = MySMSServer.getSmsServerService().sendMessage(obMsg);
                            }

                        } else {
                            obMsg.setMessageStatus(MessageStatuses.UNSENT);
                        }

//                    } else {
//                        int testTelLenth = testTel.length();
//
//                        if (testTelLenth > 9) {
//                            int t = testTelLenth - 9;
//                            testTel = testTel.substring(t, (t + 9));
//                        }
//
//                        String phoneNo2 = obMsg.getRecipient();
//                        if (phoneNo2.trim().equalsIgnoreCase(testTel)) {
//
////                        try
////                        {
//                            if (phoneNo2 != null) {
//                                if (Company_Profile.getMsgSendStart() == true) {
//                                    returnVal = MySMSServer.getSmsServerService().sendMessage(obMsg);
//                                }
//                            } else {
//                                obMsg.setMessageStatus(MessageStatuses.UNSENT);
//                            }
//                        }
//
//                    }
                        msgStatus = obMsg.getMessageStatus();
                    }
                }
            }
        }

        return msgStatus;
    }

    /**
     *
     * @param String phoneNoPara, String msgPara
     * @return
     * @throws TimeoutException
     * @throws GatewayException
     * @throws IOException
     * @throws InterruptedException
     * @see Decide sms send via Dongle or Gateway for cofirm message
     */
    public MessageStatuses sendConfirm_1(String phoneNoPara, String msgPara) throws Exception {

        boolean isTest = Company_Profile.getIsTest();
        Boolean returnVal = true;
        MessageStatuses msgStatus = null;
        String phoneNo = null;

        if (isTest) {
            phoneNo = Company_Profile.getTestingTele();
            String confirmTele = Company_Profile.getConfirmTeleNo();
            if (phoneNoPara.equalsIgnoreCase(confirmTele)) {
                phoneNo = confirmTele;
            }
        } else {
            phoneNo = phoneNoPara;
        }

        phoneNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phoneNo);
        String msg = msgPara;

        if (Company_Profile.getGatewayType().trim().equalsIgnoreCase(GatewayType.GATEWAY.toString().trim())) {

            if (Company_Profile.getGatewayProvider().trim().equalsIgnoreCase(GatewayProvider.MOBITEL.toString().trim())) {

                if (sendSMSThroughMobitelGatewayAPI == null) {
                    sendSMSThroughMobitelGatewayAPI = new SendSMSThroughMobitelGatewayAPI();
                }

                System.out.println("Confirm no >> " + phoneNo);
                msgStatus = sendSMSThroughMobitelGatewayAPI.send(phoneNo, msg);

            } else if (Company_Profile.getGatewayProvider().trim().equalsIgnoreCase(GatewayProvider.DIALOG.toString().trim())) {

                if (sendSMSThroughGatewayAPI == null) {
                    sendSMSThroughGatewayAPI = new SendSMSThroughGatewayAPI();
                }

                msgStatus = sendSMSThroughGatewayAPI.send(phoneNo, msg);

            }

        }

        if (Company_Profile.getGatewayType().trim().equalsIgnoreCase(GatewayType.DONGLE.toString().trim())) {

            if (MySMSServer.checkServiceAvailable() == false) {

                returnVal = false;
                if (!checkService) {
                    MyMessagesUtility.showWarning("Service Not Available.");
                }
                checkService = true;

            } else {

                Boolean aFlag = true;

                if (aFlag) {

                    OutboundMessage obMsg = new OutboundMessage(phoneNo, msg);

                    System.out.println("+++++++++++++++++++++++++++++++++++++");
                    System.out.println(obMsg);
                    System.out.println("+++++++++++++++++++++++++++++++++++++");

                    obMsg.setFrom(Company_Profile.getCompanyName());
                    obMsg.setStatusReport(true);
                    Date msgDate = obMsg.getDate();

//                    if (!isTest) {
//                    try
//                    {
                    if (obMsg != null) {

//                        if (Company_Profile.getMsgSendStart() == true) {
                        returnVal = MySMSServer.getSmsServerService().sendMessage(obMsg);
//                        }

                    } else {
                        obMsg.setMessageStatus(MessageStatuses.UNSENT);
                    }

//                    } else {
//                        int testTelLenth = testTel.length();
//
//                        if (testTelLenth > 9) {
//                            int t = testTelLenth - 9;
//                            testTel = testTel.substring(t, (t + 9));
//                        }
//
//                        String phoneNo2 = obMsg.getRecipient();
//                        if (phoneNo2.trim().equalsIgnoreCase(testTel)) {
//
////                        try
////                        {
//                            if (phoneNo2 != null) {
//                                if (Company_Profile.getMsgSendStart() == true) {
//                                    returnVal = MySMSServer.getSmsServerService().sendMessage(obMsg);
//                                }
//                            } else {
//                                obMsg.setMessageStatus(MessageStatuses.UNSENT);
//                            }
//                        }
//
//                    }
                    msgStatus = obMsg.getMessageStatus();
                }
            }
        }

        return msgStatus;
    }
}
