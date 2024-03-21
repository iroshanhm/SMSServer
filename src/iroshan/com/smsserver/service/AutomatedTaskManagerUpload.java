package iroshan.com.smsserver.service;

import iroshan.Company_Profile;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyRounding;
import iroshan.com.smsserver.dao.Payment_dao;
import iroshan.com.smsserver.dao.Receipt_dao;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.com.smsserver.pojo.PaymentSMS_pojo;
import iroshan.com.smsserver.pojo.Payment_pojo;
import iroshan.com.smsserver.pojo.ReceiptSMS_pojo;
import iroshan.com.smsserver.pojo.Receipt_pojo;
import iroshan.com.smsserver.view.ApplicationDesktop;
import iroshan.com.smsserver.view.AutomateUI;
import static iroshan.com.smsserver.view.AutomateUI.automatedTaskStarted_boolean;
import iroshan.com.smsserver.view.Jurnal_if_view_2;
import iroshan.com.smsserver.view.Payment_infrm_view_2;
import iroshan.com.smsserver.view.WaitingDialogSMSSend;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.smslib.OutboundMessage;

/**
 *
 * @author Gim
 */
public class AutomatedTaskManagerUpload {

//    private Date startingExecutionTime;
//
//    private Integer noOfHours;
//
//    Date lastExecutedTime;
//
//    Date nextExecutedTime;
    Toolkit toolkit;

    Timer timer;

    static List<Map> sendingList = new ArrayList<Map>();

    /**
     *
     */
    public AutomatedTaskManagerUpload() {
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
    }

//   UPLOAD TASK -----------------------------------------------------------------
    /**
     *
     */
    public void startUploadAutomated() {

//          MainApplicationJForm.clearAllUploading();
//        System.out.println("getStartingExecutionTime>>" + getStartingExecutionTime());
//        timer.schedule(new Send_SMS(), getStartingExecutionTime(), //initial delay
//                getNoOfHours() * 60 * 60 * 1000);  //subsequent rate
//        int period = 1 * 60 * 1000;
        int period = 30 * 1000;

        Send_SMS send_SMS = new Send_SMS();

        timer.schedule(send_SMS, 0, period);
//          MainApplicationJForm.setTextToStartExecutionTime(startingExecutionTime);
    }

    class Send_SMS extends TimerTask {

        public void run() {

//            MyMessagesUtility.showWarning(String.valueOf(automatedTaskStarted_boolean));
            if (!automatedTaskStarted_boolean) {
                boolean cancel = this.cancel();

            }

            sendingList.clear();

            System.out.println("AUTOMATED SENDING .......");
            getNewPaymentsList();
            getNewReceiptsList();
            loadPaymentToTables();
            sendSms_2();
        }

//        PAYMENT ******************************************************
        public List<Map> getNewPaymentsList() {

            SMSMessage sMSMessage = new SMSMessage();
            Map<String, Payment_pojo> newPaymentList = new Payment_dao(false).getNewPaymentsList("");

            if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(Company_Profile.PaymentMessageTypeEnum.N.toString())) {
                for (Map.Entry<String, Payment_pojo> entrySet : newPaymentList.entrySet()) {

                    String ieCode = entrySet.getKey();
                    Payment_pojo value = entrySet.getValue();

                    String paymentNo = value.getPaymentNo();
                    String mCode = value.getCusCode();
                    String cusOfficeNo = value.getCusOfficeNo();
                    String acCode = value.getAcNumber();
                    String receiptAmount = MyRounding.roundToLastTwoDecimal(value.getAmount());
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
                    if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(Company_Profile.PaymentMessageTypeEnum.N.toString())) {
                        smsMsg = sMSMessage.msgForPayment(cusName, acCode, receiptAmount, null);
                    }

                    if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(Company_Profile.PaymentMessageTypeEnum.PA.toString())) {
                        smsMsg = sMSMessage.msgForPayment(cusName, acCode, receiptAmount, null);
                    }

//            String smsMsg = sMSMessage.msgForPayment (cusName, cusOfficeNo, receiptAmount, ieCode, loanAmount, null);
                    Map map = new HashMap();
                    map.put("TYPE", "PAYMENT");
                    map.put("TR_CODE", paymentNo);
                    map.put("MEM_CODE", mCode);
                    map.put("CUS_NAME", cusName);
                    map.put("CUS_OFFICE_NO", cusOfficeNo);
                    map.put("TELE", tele);
                    map.put("SMS", smsMsg);

//                    newPaymentSendList.add(new PaymentSMS_pojo(true, paymentNo, mCode, cusName, cusOfficeNo, tele, smsMsg));
                    sendingList.add(map);

                }
            }

            if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(Company_Profile.PaymentMessageTypeEnum.PA.toString())) {
                for (Map.Entry<String, Payment_pojo> entrySet : newPaymentList.entrySet()) {

                    String ieCode = entrySet.getKey();
                    Payment_pojo value = entrySet.getValue();

                    String paymentNo = value.getPaymentNo();
                    String mCode = value.getCusCode();
                    String cusName = value.getCusName();
                    String cusOfficeNo = value.getCusOfficeNo();
                    String acCode = value.getAcNumber();
                    String receiptAmount = MyRounding.roundToLastTwoDecimal(value.getAmount());

//                    String loanAmount = null;
//                    if (value.getLoanOrSaving() == 1) {
//                        loanAmount = MyRounding.roundToLastTwoDecimal(value.getLoanAmount());
//                    }
                    String tele = value.getTelNo();

                    if (Company_Profile.getCustomerSubOffceNoWithSMS()) {
                        if (!cusOfficeNo.isEmpty()) {
                            cusName = cusName.concat("(" + cusOfficeNo + ")");
                        }
                    } else {
                        cusName = cusName.trim();
                    }

//                    String smsMsg = null;
//                    if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(Company_Profile.PaymentMessageTypeEnum.N.toString())) {
//                        smsMsg = sMSMessage.msgForPayment(cusName, acCode, receiptAmount, null);
//                    }
                    String smsMsg = sMSMessage.msgForPayment(cusName, acCode, receiptAmount, null);

//            String smsMsg = sMSMessage.msgForPayment (cusName, cusOfficeNo, receiptAmount, ieCode, loanAmount, null);
//                    newPaymentSendList.add(new PaymentSMS_pojo(true, paymentNo, mCode, cusName, cusOfficeNo, tele, smsMsg));
                    Map map = new HashMap();
                    map.put("TYPE", "PAYMENT");
                    map.put("TR_CODE", paymentNo);
                    map.put("MEM_CODE", mCode);
                    map.put("CUS_NAME", cusName);
                    map.put("CUS_OFFICE_NO", cusOfficeNo);
                    map.put("TELE", tele);
                    map.put("SMS", smsMsg);

//                    newPaymentSendList.add(new PaymentSMS_pojo(true, paymentNo, mCode, cusName, cusOfficeNo, tele, smsMsg));
                    sendingList.add(map);
                }
            }

            return sendingList;
        }

        public List<Map> getNewReceiptsList() {

            SMSMessage sMSMessage = new SMSMessage();

            String receiptNoPreveies = "";
            String ACNoPreveies = "";
            String completedString = "";

            Map<String, Receipt_pojo> newReceiptList = new Receipt_dao(false).getNewReceiptsList();

            for (Map.Entry<String, Receipt_pojo> entrySet : newReceiptList.entrySet()) {

                String ieCode = entrySet.getKey();
                Receipt_pojo value = entrySet.getValue();

                String receiptNo = value.getReceiptNo();
                String mCode = value.getCustomerCode();

                String cusName = "";
                if (Company_Profile.getLanguage().equalsIgnoreCase(Company_Profile.LanguageEnum.SINHALA.toString())) {
                    cusName = value.getCusNameEnglish();
                } else {
                    cusName = value.getCusName();
                }

                String cusOfficeNo = value.getCusOfficeNo();
                String acCode = value.getACNumber();
                String receiptAmount = MyRounding.roundToLastTwoDecimal(value.getReceiptAmount());
                String dateRe = MyDateAndTimeUtil.getFormatedDateAsString(value.getReceiptDate());
                String tele = value.getTele();

                String smsMsg = null;
                if (Company_Profile.getReceiptMessageType().equalsIgnoreCase(Company_Profile.ReceiptMessageTypeEnum.N.toString().trim())) {
                    smsMsg = sMSMessage.msgForReceiptFormat2(cusName, cusOfficeNo, acCode, receiptAmount, dateRe);
                }

//            -----------------------------------------------------
                if (Company_Profile.getReceiptMessageType().equalsIgnoreCase(Company_Profile.ReceiptMessageTypeEnum.RA.toString().trim())) {

                    String arrearsAmount = null;
//                    arrearsAmount = MyRounding.roundToLastTwoDecimal((value.getArrearsAmount() == null ? 0.00 : value.getArrearsAmount()));

                    if (Company_Profile.getCompanyCode().equalsIgnoreCase("REGAL")) {

                        smsMsg = sMSMessage.msgForReceiptWithArrearsFormat2(cusName, cusOfficeNo, acCode, receiptAmount, arrearsAmount, dateRe);

                    } else {

                        smsMsg = sMSMessage.msgForReceiptWithArrears(cusName, cusOfficeNo, acCode, receiptAmount, arrearsAmount, dateRe);

                    }

                }

//                newReceiptSendList.add(new ReceiptSMS_pojo(true, receiptNo, mCode, cusName, cusOfficeNo, tele, smsMsg));
                Map map = new HashMap();
                map.put("TYPE", "RECEIPT");
                map.put("TR_CODE", receiptNo);
                map.put("MEM_CODE", mCode);
                map.put("CUS_NAME", cusName);
                map.put("CUS_OFFICE_NO", cusOfficeNo);
                map.put("TELE", tele);
                map.put("SMS", smsMsg);

                sendingList.add(map);
            }

            return sendingList;
        }

        public void loadPaymentToTables() {

            DefaultTableModel dfTblMdPending = (DefaultTableModel) AutomateUI.tbl_Pending.getModel();
            int rowCountPending = dfTblMdPending.getRowCount();

            for (int i = 0; i < rowCountPending; i++) {
                dfTblMdPending.removeRow(0);
            }

            int rowNo = 0;

            for (int i = 0; i < sendingList.size(); i++) {
                Map map = sendingList.get(i);
                String TYPE = (String) map.get("TYPE");
                String TR_CODE = (String) map.get("TR_CODE");
                String MEM_CODE = (String) map.get("MEM_CODE");
                String CUS_NAME = (String) map.get("CUS_NAME");
                String CUS_OFFICE_NO = (String) map.get("CUS_OFFICE_NO");
                String TELE = (String) map.get("TELE");
                String SMS = (String) map.get("SMS");

                dfTblMdPending.addRow(new Object[]{
                    TYPE, TR_CODE, MEM_CODE, CUS_NAME, CUS_OFFICE_NO, TELE, SMS, "PENDING"
                });

            }

            AutomateUI.tbl_Pending.revalidate();
        }

        private void sendSms_2() {

            if (automatedTaskStarted_boolean) {

                Thread trdSend = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int currentRow = 0;
                        SendMessage sendMessage = null;
                        Payment_service payment_service = null;

                        Automated_Services automated_Services = null;

                        String messageType = MyMessageTypeEnum.NEW_PAYMENT.toString().trim();
                        int sentSMSCount = 0;
                        int sentConfirmSMSCount = 0;

                        try {

                            if (sendMessage == null) {
                                sendMessage = new SendMessage();
                            }

//                            if (payment_service == null) {
//                                payment_service = new Payment_service();
//                            }
                            if (automated_Services == null) {
                                automated_Services = new Automated_Services();
                            }

                            // --------------------------------------------------
                            Company_Profile.setMsgSendStart(true);
                            // --------------------------------------------------

                            DefaultTableModel dfTblMd = (DefaultTableModel) AutomateUI.tbl_Pending.getModel();
                            int rowCount = dfTblMd.getRowCount();

                            //-------------------------------------
                            MySMSServer.restartService();
                            //-------------------------------------

//                            MyMessagesUtility.showWarning(String.valueOf(rowCount));
                            if (rowCount > 0 && automatedTaskStarted_boolean) {

                                OutboundMessage.MessageStatuses confirmStart = sendMessage.sendConfirm_1(Company_Profile.getConfirmTeleNo(), messageType + " sending start.");
                                if (!confirmStart.toString().trim().equals(MessageStatusEnum.SENT.toString().trim())) {
                                    MyMessagesUtility.showWarning(messageType + " " + confirmStart.toString());
                                } else if (confirmStart.toString().trim().equals(MessageStatusEnum.SENT.toString().trim())) {
                                    ++sentConfirmSMSCount;
                                }

                                outerLoop:
                                for (int i = currentRow; i < rowCount; i++) {

                                    if (!Company_Profile.getMsgSendStart() && !automatedTaskStarted_boolean) // IF MSG SENDING STOPED
                                    {
                                        break outerLoop;
                                    }

                                    int j = i;
                                    ++j;

                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(AutomatedTaskManagerUpload.class.getName()).log(Level.SEVERE, null, ex);
                                    }

//                            Boolean isSelected = Boolean.valueOf(String.valueOf(dfTblMd.getValueAt(i, 0)));
//                            if (isSelected) {
//                                if (!dfTblMd.getValueAt(i, 8).toString().trim().equalsIgnoreCase(OutboundMessage.MessageStatuses.SENT.toString())) {
                                    String payment = String.valueOf(dfTblMd.getValueAt(i, 0));
                                    String voucherNo = String.valueOf(dfTblMd.getValueAt(i, 1));
                                    String cusCode = String.valueOf(dfTblMd.getValueAt(i, 2));
                                    String phoneNo = String.valueOf(dfTblMd.getValueAt(i, 5));
                                    String message = String.valueOf(dfTblMd.getValueAt(i, 6));

                                    // SEND
                                    System.out.println("TRYING TO SEND MESSAGE...***********************************************************");

                                    String type = null;
                                    if (payment.equalsIgnoreCase("PAYMENT")) {
                                        type = "P";
                                    } else if (payment.equalsIgnoreCase("RECEIPT")) {
                                        type = "R";
                                    }

//                                    OutboundMessage.MessageStatuses msgStatus = payment_service.sendSMS_2(phoneNo, message, cusCode, voucherNo);
                                    OutboundMessage.MessageStatuses msgStatus = automated_Services.sendSMS_2(type, phoneNo, message, cusCode, voucherNo);
                                    dfTblMd.setValueAt(msgStatus.toString(), i, 7);

                                    System.out.println("TRIED TO SEND MESSAGE...*************************************************************");

                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    if (msgStatus.toString().trim().equalsIgnoreCase(OutboundMessage.MessageStatuses.SENT.toString().trim())) {
                                        ++sentSMSCount;
                                        dfTblMd.removeRow(i);
                                    }

//                                }
//                            }
                                    currentRow = i;
                                }

//                            }
//
//
//                            if (rowCount > 0) {
                                OutboundMessage.MessageStatuses confirmEnd = sendMessage.sendConfirm_1(Company_Profile.getConfirmTeleNo(), messageType + " sending end with " + sentSMSCount + " messages.");
                                if (!confirmEnd.toString().trim().equals(MessageStatusEnum.SENT.toString().trim())) {
                                    MyMessagesUtility.showWarning("Message to member 2 " + confirmEnd.toString());
                                } else if (confirmEnd.toString().trim().equals(MessageStatusEnum.SENT.toString().trim())) {
                                    ++sentConfirmSMSCount;
                                }

//                                new SMSCount_Service().saveSMSCount(messageType, sentSMSCount);
//                                new SMSCount_Service().saveSMSCount(MyMessageTypeEnum.CONFIRM.toString().trim(), sentConfirmSMSCount);
                            }

                        } catch (Exception ex) {
                            MyMessagesUtility.showError(ex.toString());
                            Logger.getLogger(AutomatedTaskManagerUpload.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
                trdSend.start();

            }
        }
    }

//    DOWNLOAD TASK ------------------------------------------------------------
    /**
     *
     */
    public void startDownloadAutomated() {

//        System.out.println("getStartingExecutionTime>>" + getStartingExecutionTime());
//        timer.schedule(new DownloadTask(), getStartingExecutionTime(), //initial delay
//                getNoOfHours() * 60 * 60 * 1000);  //subsequent rate
    }

    class DownloadTask extends TimerTask {

        int numWarningBeeps = 3;

        public void run() {

            Calendar cal = Calendar.getInstance();
            Date lastExeTime = cal.getTime();
            cal.setTime(lastExeTime);
//            cal.add(Calendar.HOUR_OF_DAY, getNoOfHours());
//            Date nextExecutionTime = cal.getTime();
//
//            new AutomatedService().upload();

//            if (numWarningBeeps > 0) {
        }
    }

    /**
     *
     * @param arg
     */
    public static void main(String[] arg) {
        new AutomatedTaskManagerUpload();
    }
}
