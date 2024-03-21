/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyRounding;
import iroshan.com.smsserver.dao.NewTableCreation_dao;
import iroshan.com.smsserver.dao.ReceiptMarkAsSend_dao;
import iroshan.com.smsserver.dao.Receipt_dao;
import iroshan.com.smsserver.dao.UpdateReceiptAsComplete;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.com.smsserver.pojo.ReceiptSMS_pojo;
import iroshan.com.smsserver.pojo.Receipt_pojo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iroshan
 */
public class ReceiptMarkAsSend_service
{


    static Boolean sendingActive = true;


    public List<ReceiptSMS_pojo> getNewReceiptsList ()
    {

        SMSMessage sMSMessage = new SMSMessage ();
        List<ReceiptSMS_pojo> newReceiptSendList = new ArrayList<ReceiptSMS_pojo> ();

        String receiptNoPreveies = "";
        String ACNoPreveies = "";
        String completedString = "";


        Map<String, Receipt_pojo> newReceiptList = new ReceiptMarkAsSend_dao ().getNewReceiptsList ();


        for (Map.Entry<String, Receipt_pojo> entrySet : newReceiptList.entrySet ())
        {

            String ieCode = entrySet.getKey ();
            Receipt_pojo value = entrySet.getValue ();

            String receiptNo = value.getReceiptNo ();
            String mCode = value.getCustomerCode ();
            String cusName = value.getCusName ();
            String cusOfficeNo = value.getCusOfficeNo ();
            String acCode = value.getACNumber ();
            String receiptAmount = MyRounding.roundToLastTwoDecimal (value.getReceiptAmount ());

            String dueAmount = null;
            if (value.getLoanOrSaving () == 1)
            {
                dueAmount = MyRounding.roundToLastTwoDecimal ((value.getOutstandingAmount () == null ? 0.00 : value.getOutstandingAmount ()));
            }


            String tele = value.getTele ();

            String smsMsg = sMSMessage.msgForReceipt (cusName, acCode, receiptAmount, ieCode, dueAmount, null);

            newReceiptSendList.add (new ReceiptSMS_pojo (true, receiptNo, mCode, cusName, cusOfficeNo, tele, smsMsg));

        }

        return newReceiptSendList;
    }

    public void doMarkAsSendList (List<ReceiptSMS_pojo> listPara)
    {
        List<ReceiptSMS_pojo> list = listPara;
        
        new ReceiptMarkAsSend_dao ().markAsSendList (list);
    }
    
    public void doMarkAsSendAll ()
    {
          new ReceiptMarkAsSend_dao ().markAsSendAll ();
    }

}
