/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;


import iroshan.com.common.config.DBConnection;
import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.common.utility.MyRounding;
import iroshan.com.smsserver.entity.Customer_entity;
import iroshan.com.smsserver.entity.SmsReceivedEntity;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.Company_Profile;
import iroshan.com.smsserver.pojo.Loan_pojo;
import iroshan.com.smsserver.service.DeleteInboundMessageFromGateway;
import iroshan.com.smsserver.service.Jurnal_service;
import iroshan.com.smsserver.service.MySMSServer;
import iroshan.com.smsserver.service.ReceiveAndSendMonitorService;
import iroshan.com.smsserver.service.SMSMessage;
import iroshan.com.smsserver.service.SendMessage;
import java.io.IOException;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.type.DoubleType;
import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class InboundMessageProcessing {

    private Connection conn;
    SendMessage sendMessage = null;
    SendMessageSaveDao sendMessageSaveDao = null;
    DeleteInboundMessageFromGateway deleteInboundMessageFromGateway = null;
    ReceiveAndSendMonitorService receiveAndSendMonitorService = null;

    public InboundMessageProcessing() {
        getCon();
    }

    private void getCon() {
        conn = DBConnection.getConnection();
    }

    public void processInboundMessage(InboundMessage paraMSG) {



        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;



        String msgType = null;
        String msg = null;




        InboundMessage inBoundMSG = paraMSG;

        String phoneNo = "+" + inBoundMSG.getOriginator();
        String message = inBoundMSG.getText();
        Date date = inBoundMSG.getDate();
//        Integer idSMSReceive = Integer.valueOf (inBoundMSG.getId ());
        Integer idSMSReceive = 0;
//        Long idSMSReceive = inBoundMSG.ge

        try {
            tx = session.beginTransaction();







            if ((!message.equals("")) && message.length() != 0) {
                String[] msgArr = message.split(" ");
                if (msgArr.length != 0) {
                    if (msgArr.length == 3) {

                        String keyWord = msgArr[0];
                        String accNo = msgArr[1];
                        String password = msgArr[2];


                        if (keyWord.equalsIgnoreCase(Company_Profile.getSavinPrefixCode()) || keyWord.equalsIgnoreCase(Company_Profile.getLoanPrefixCode())) {

                            int out = 0;

                            try {

                                Criteria cr = session.createCriteria(SmsReceivedEntity.class);
                                cr.setProjection(Projections.max("smsReceivedId"));
                                Integer idReceive = (Integer) cr.uniqueResult();
                                if (idReceive == null) {
                                    idReceive = 0;
                                }
                                ++idReceive;

                                SmsReceivedEntity smsReceived = new SmsReceivedEntity();
                                smsReceived.setSmsReceivedId(idReceive);
                                smsReceived.setSmsReceivedPhone(phoneNo);
                                smsReceived.setSmsReceivedKeyword(keyWord);
                                smsReceived.setSmsReceivedAcc(accNo);
                                smsReceived.setSmsReceivedPassword(password);
                                smsReceived.setSmsReceivedDate(date);

                                session.save(smsReceived);

                                int phnLenth = phoneNo.length();

                                String phnSubStr = null;

                                if (phnLenth >= 9) {
                                    phnSubStr = phoneNo.substring((phnLenth - 9), phnLenth);
                                }

                                String telDB = null;
                                String passDB = null;
                                String cmCodeDB = null;






                                SQLQuery sqlQuery = session.createSQLQuery("SELECT CUSTOMER.CM_CODE AS cmCode,CUSTOMER.CM_TELE AS cmTele,"
                                        + "CUSTOMER3.CM_DESC AS password, CUSTOMER3.CM_CODE2 AS cmCode2 "
                                        + "FROM CUSTOMER RIGHT OUTER JOIN CUSTOMER3 ON CUSTOMER.CM_CODE = CUSTOMER3.CM_CODE "
                                        + "WHERE (CUSTOMER3.CM_CODE2 = N'EMAIL3') AND (CUSTOMER.CM_TELE LIKE '%" + phnSubStr + "') AND (CUSTOMER3.CM_DESC LIKE '" + password + "')");

                                sqlQuery.addEntity(Customer_entity.class);

                                List<Customer_entity> resultList = sqlQuery.list();
                                for (Customer_entity customerEntity : resultList) {
                                    cmCodeDB = customerEntity.getCmCode().trim();
                                    System.out.println("cmCodeDB : " + cmCodeDB);

                                    telDB = customerEntity.getCmTele().trim();
                                    System.out.println("telDB : " + telDB);

                                    passDB = customerEntity.getPassword().trim();
                                    System.out.println("passDB : " + passDB);
                                }



                                if (telDB != null && passDB != null && cmCodeDB != null) {

                                    if (keyWord.equalsIgnoreCase(Company_Profile.getSavinPrefixCode())) {

                                        double bal = 0;

                                        bal = (Double) session.createSQLQuery("SELECT SUM(q2)sumBal FROM QDEPOSIT_INCOME_EXPENCE_SUB_DTAIL "
                                                + "WHERE PE_EXPENCE LIKE '" + accNo.trim() + "' AND "
                                                + "PA_SUPPLIER LIKE '" + cmCodeDB.trim() + "' AND "
                                                + "IE_SUB_TYPE LIKE 'DEPOSIT'").addScalar("sumBal", new DoubleType()).uniqueResult();

                                        boolean aFlag = true;

                                        DecimalFormat df = new DecimalFormat("####0.00");

                                        String roundedBal = df.format(bal);

                                        if (aFlag) {

                                            msgType = MyMessageTypeEnum.REPLY.toString();
                                            msg = new SMSMessage().msgForSavingDetails(accNo.trim(), roundedBal, MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(date));
//                                            sentMessageProcessing (inBoundMSG, MyMessageTypeEnum.REPLY.toString (), idReceive, phoneNo, msg);

                                        }
                                    }

                                    if (keyWord.equalsIgnoreCase(Company_Profile.getLoanPrefixCode())) {

                                        Loan_pojo loanPojo = new Loan_dao().getLoanDetails(date, accNo);

                                        Double Outstanding = loanPojo.getTotalOutstanding();
                                        String OutstandingStr = MyRounding.roundToLastTwoDecimal((Outstanding == null ? 0.00 : Outstanding));

                                        Double Due = loanPojo.getTotalDue();
                                        String DueStr = MyRounding.roundToLastTwoDecimal((Due == null ? 0.00 : Due));

                                        Double arrears = loanPojo.getTotalArears();
                                        String arrearsStr = MyRounding.roundToLastTwoDecimal((arrears == null ? 0.00 : arrears));


                                        msgType = MyMessageTypeEnum.REPLY.toString();
                                        msg = new SMSMessage().msgForLoanDetails(accNo.trim(), arrearsStr, DueStr, OutstandingStr, MyDateAndTimeUtil.getFormatedDateAsStringByDateParameter(date));

//                                        sentMessageProcessing (inBoundMSG, MyMessageTypeEnum.REPLY.toString (), idReceive, phoneNo, msg);
                                    }





                                } else {
                                    msgType = MyMessageTypeEnum.UNBOUND.toString();
                                    msg = "If Your Registered User, Please Check Your Keyword,A/C No,Password and Resend.";
//                                    sentMessageProcessing (inBoundMSG, MyMessageTypeEnum.UNBOUND.toString (), idReceive, phoneNo, msg);
                                }



                            } catch (Exception ex) {
                                msgType = MyMessageTypeEnum.UNBOUND.toString();
                                msg = "Service is not available.";

//                                sentMessageProcessing (inBoundMSG, MyMessageTypeEnum.UNBOUND.toString (), idSMSReceive, phoneNo, msg);
                                Logger.getLogger(MySMSServer.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
//                            s.closeSession ();
                            }
                        } else {
                            msgType = MyMessageTypeEnum.UNBOUND.toString();
                            msg = "11Your Message Format is Incorrect, Please Check and Resend.";
//                            sentMessageProcessing (inBoundMSG, MyMessageTypeEnum.UNBOUND.toString (), idSMSReceive, phoneNo, msg);
                        }
                    } else {
                        msgType = MyMessageTypeEnum.UNBOUND.toString();
                        msg = "2Your Message Format is Incorrect, Please Check and Resend.";
//                        sentMessageProcessing (inBoundMSG, MyMessageTypeEnum.UNBOUND.toString (), idSMSReceive, phoneNo, msg);
                    }
                } else {
                    msgType = MyMessageTypeEnum.UNBOUND.toString();
                    msg = "3Your message format is incorrect. Please Check and Resend.";
//                    sentMessageProcessing (inBoundMSG, MyMessageTypeEnum.UNBOUND.toString (), idSMSReceive, phoneNo, msg);
                }
            } else {
                msgType = MyMessageTypeEnum.UNBOUND.toString();
                msg = "4Your message format is incorrect, Please Check and Resend.";
//                sentMessageProcessing (inBoundMSG, MyMessageTypeEnum.UNBOUND.toString (), idSMSReceive, phoneNo, msg);
            }


            tx.commit();


        } catch (Exception e) {
            e.printStackTrace();

            if (tx != null) {
                tx.rollback();
            }

            msgType = MyMessageTypeEnum.UNBOUND.toString();
            msg = "Service is not available.";

        } finally {
//            session.close ();
        }

        sentMessageProcessing(inBoundMSG, msgType, idSMSReceive, phoneNo, msg);


    }


    void sentMessageProcessing(InboundMessage inBoundMSG, String msgType, Integer idSMSReceive, String phoneNo, String message) {

        if (sendMessage == null) {
            sendMessage = new SendMessage();
        }

        if (deleteInboundMessageFromGateway == null) {
            deleteInboundMessageFromGateway = new DeleteInboundMessageFromGateway();
        }

        if (sendMessageSaveDao == null) {
            sendMessageSaveDao = new SendMessageSaveDao();
        }

        if (receiveAndSendMonitorService == null) {
            receiveAndSendMonitorService = new ReceiveAndSendMonitorService();
        }

//        new SendMessage ().send (phoneNo, sendMsg);
//        sendMessage.send(msgType, idSMSReceive, phoneNo, message);
//        sendMessage.send4(new OutboundMessage(phoneNo, message));
//        new ReplyForReceivedMessage ().send (idSMSReceive, phoneNo, sendMsg);
//        new SendMessageSaveDao ().saveToDB (msgType, idSMSReceive, phoneNo, sendMsg, inBoundMSG.getDate ());





        OutboundMessage.MessageStatuses msgStatus = null;

        if (phoneNo != null) {
            try {
                Company_Profile.setMsgSendStart(true);
                msgStatus = sendMessage.send_3(new OutboundMessage(phoneNo, message));
            } catch (TimeoutException ex) {
                Logger.getLogger(Jurnal_service.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GatewayException ex) {
                Logger.getLogger(Jurnal_service.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Jurnal_service.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Jurnal_service.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            msgStatus = OutboundMessage.MessageStatuses.UNSENT;
        }

//        MessageStatuses msgStatus = sendMessage.send_2 (messageType, idSMSReceive, phnNo, msg);
        if (msgStatus == OutboundMessage.MessageStatuses.SENT) {

            String curentDateAndTimeAsString = Company_Profile.getCurrentDateAsString();
            Date curentDateAndTimeAsDate = Company_Profile.getCurrentDateAsDate();


//            updatePaymentAsComplete.setSMSSent(paymentCode);
            receiveAndSendMonitorService.sendMessageProcess_2(phoneNo, message, curentDateAndTimeAsString);
            String messageType = MyMessageTypeEnum.REPLY.toString();
            sendMessageSaveDao.saveToDB_2(messageType, idSMSReceive, phoneNo, message, curentDateAndTimeAsDate, msgStatus);

        }



        deleteInboundMessageFromGateway.deleteInboundMessage(inBoundMSG);
    }

}
