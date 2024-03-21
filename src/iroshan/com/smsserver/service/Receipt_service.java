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
import iroshan.com.smsserver.dao.NewTableCreation_dao;
import iroshan.com.smsserver.dao.Receipt_dao;
import iroshan.com.smsserver.dao.SendMessageSaveDao;
import iroshan.com.smsserver.dao.UpdateJurnalAsComplete;
import iroshan.com.smsserver.dao.UpdatePaymentAsComplete;
import iroshan.com.smsserver.dao.UpdateReceiptAsComplete;
import iroshan.com.smsserver.entity.Receipt_ent;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.Company_Profile;
import iroshan.Company_Profile.ReceiptMessageTypeEnum;
import iroshan.com.smsserver.pojo.ReceiptSMS_pojo;
import iroshan.com.smsserver.pojo.Receipt_pojo;
import iroshan.com.smsserver.service.gatway.dialog.SendSMSThroughGatewayAPI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.internal.runners.statements.RunAfters;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.OutboundMessage.MessageStatuses;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class Receipt_service {

    int smsListCurrent = 0;

    SendMessage sendMessage = null;

    UpdateReceiptAsComplete updateReceiptAsComplete = null;

    ReceiveAndSendMonitorService receiveAndSendMonitorService = null;

    SendMessageSaveDao sendMessageSaveDao = null;

    static Boolean sendingActive = true;
    SendSMSThroughGatewayAPI sendSMSThroughGatewayAPI = null;





    public List<ReceiptSMS_pojo> getNewReceiptsList(Date fromDatePara, Date toDatePara, String brCodePara) {

        Date fromDate = fromDatePara;
        Date toDate = toDatePara;        
        
        
	SMSMessage sMSMessage = new SMSMessage();
	List<ReceiptSMS_pojo> newReceiptSendList = new ArrayList<ReceiptSMS_pojo>();

	String receiptNoPreveies = "";
	String ACNoPreveies = "";
	String completedString = "";

	Map<String, Receipt_pojo> newReceiptList = new Receipt_dao().getNewReceiptsList(fromDate, toDate, brCodePara);

	for (Map.Entry<String, Receipt_pojo> entrySet : newReceiptList.entrySet()) {

	    String ieCode = entrySet.getKey();
	    Receipt_pojo value = entrySet.getValue();

	    String receiptNo = value.getReceiptNo();
	    String mCode = value.getCustomerCode();
            
            String cusName = "";
            if(Company_Profile.getLanguage().equalsIgnoreCase(Company_Profile.LanguageEnum.SINHALA.toString())){
                cusName = value.getCusNameEnglish();
            }else{
                cusName = value.getCusName();
            }
            
	    String cusOfficeNo = value.getCusOfficeNo();
	    String acCode = value.getACNumber();
	    String receiptAmount = MyRounding.roundToLastTwoDecimal(value.getReceiptAmount());
	    String dateRe = MyDateAndTimeUtil.getFormatedDateAsString(value.getReceiptDate());
	    String tele = value.getTele();

//            -----------------------------------------------------
//               if (Company_Profile.getCustomerSubOffceNoWithSMS()) {
//                    if (!cusOfficeNo.isEmpty()) {
//                         cusName = cusName.concat("(" + cusOfficeNo + ")");
//                    }
//               } else {
//                    cusName = cusName.trim();
//               }

//            ----------------------------------------------------



	    String smsMsg = null;
	    if (Company_Profile.getReceiptMessageType().equalsIgnoreCase(Company_Profile.ReceiptMessageTypeEnum.N.toString().trim())) {
		smsMsg = sMSMessage.msgForReceiptFormat2(cusName, cusOfficeNo, acCode, receiptAmount, dateRe);
	    }

//            -----------------------------------------------------
	    if (Company_Profile.getReceiptMessageType().equalsIgnoreCase(Company_Profile.ReceiptMessageTypeEnum.RA.toString().trim())) {

		String arrearsAmount = null;
		arrearsAmount = MyRounding.roundToLastTwoDecimal((value.getArrearsAmount() == null ? 0.00 : value.getArrearsAmount()));

		if (Company_Profile.getCompanyCode().equalsIgnoreCase("REGAL")) {

		    smsMsg = sMSMessage.msgForReceiptWithArrearsFormat2(cusName, cusOfficeNo, acCode, receiptAmount, arrearsAmount, dateRe);

		} else {

		    smsMsg = sMSMessage.msgForReceiptWithArrears(cusName, cusOfficeNo, acCode, receiptAmount, arrearsAmount, dateRe);

		}

	    }

	    newReceiptSendList.add(new ReceiptSMS_pojo(true, receiptNo, mCode, cusName, cusOfficeNo, tele, smsMsg));

	}

	return newReceiptSendList;
    }





    /**
     *
     * @param phnNoPara
     * @param msgPara
     * @param cusCodePara
     * @param jCodePara
     *
     * @return
     */
    public MessageStatuses sendSMS_2(String phnNoPara, String msgPara, String cusCodePara, String jCodePara) throws TimeoutException, Exception {

	if (sendMessage == null) {
	    sendMessage = new SendMessage();
	}

	if (updateReceiptAsComplete == null) {
	    updateReceiptAsComplete = new UpdateReceiptAsComplete();
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

	String cusCode = cusCodePara;
	String jCode = jCodePara;

	String messageType = MyMessageTypeEnum.NEW_RECEIPT.toString().trim();
	Integer idSMSReceive = -1;
	String phnNo = phnNoPara;
	String msg = msgPara;
	phnNo = MyPhoneNoFormatingUtility.formatToPlus94Number(phnNo);

//        MyMessagesUtility.showInformation(phnNo);

	OutboundMessage.MessageStatuses msgStatus = null;
	if (phnNo != null) {

	    msgStatus = sendMessage.send_5(phnNo, msg);

//            try {
//                msgStatus = sendMessage.send_5(phnNo, msg);
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

	    updateReceiptAsComplete.setSMSSent(jCode);
	    receiveAndSendMonitorService.sendMessageProcess_2(phnNo, msg, curentDateAndTimeAsString);
	    sendMessageSaveDao.saveToDB_2(messageType, idSMSReceive, phnNo, msg, curentDateAndTimeAsDate, msgStatus);

	}
	return msgStatus;
    }





    public void alterSMSSendInReceipt() {
	new NewTableCreation_dao().alterIsSMSSentReceiptTable();
    }





    public void markAsSend(List<String> list) {
	new Receipt_dao().markAsSend(list);
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
		List<ReceiptSMS_pojo> receiptList = receiptListPara;

		int smsCount = 0;
		outerloop:
		if (Company_Profile.getMsgSendStart()) {
		    for (ReceiptSMS_pojo receiptListObj : receiptList) {
			if (receiptListObj.getIsSelected()) {
			    String phoneNo = receiptListObj.getTelNo().trim();

			    if (phoneNo != null || !phoneNo.isEmpty()) {

				if (smsCount % Company_Profile.getMsgCountForServerRestarting() == 0) {
				    MySMSServer.restartService();
				}

				if (MySMSServer.checkServiceAvailable() == true && MySMSServer.serialModemGateway.getStatus() == AGateway.GatewayStatuses.STARTED) {
				    ++smsCount;
				    String receiptNo = receiptListObj.getReceiptNo().trim();

				    String message = receiptListObj.getMessage().trim();
				    if (Company_Profile.getMsgSendStart()) {
					Boolean isSuccess = new SendMessage().send(MyMessageTypeEnum.NEW_RECEIPT.toString(), -1, phoneNo, message);
					if (isSuccess) {
					    updateReceiptAsComplete.setSMSSent(receiptNo);
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





    public void reSendReceiptSms(List<ReceiptSMS_pojo> receiptListPara) {

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
		List<ReceiptSMS_pojo> receiptList = receiptListPara;

		int smsCount = 0;
		for (ReceiptSMS_pojo receiptListObj : receiptList) {

		    if (receiptListObj.getIsSelected()) {

			String phoneNo = receiptListObj.getTelNo().trim();

			if (phoneNo != null || !phoneNo.isEmpty()) {

			    if (smsCount % Company_Profile.getMsgCountForServerRestarting() == 0) {
				MySMSServer.restartService();
			    }

			    if (MySMSServer.checkServiceAvailable() == true && MySMSServer.serialModemGateway.getStatus() == AGateway.GatewayStatuses.STARTED) {
				++smsCount;
				String receiptNo = receiptListObj.getReceiptNo().trim();

				String message = receiptListObj.getMessage().trim();
				Boolean isSuccess = new SendMessage().send(MyMessageTypeEnum.NEW_RECEIPT.toString(), -1, phoneNo, message);
				if (isSuccess) {

				    updateReceiptAsComplete.setSMSSent(receiptNo);
				}
			    }
			}
		    }
		}

	    }
	}
    }

}
