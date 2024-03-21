/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.SendMessageSaveDao;
import iroshan.com.smsserver.dao.UpdatePaymentAsComplete;
import iroshan.com.smsserver.dao.UpdateReceiptAsComplete;
import iroshan.com.smsserver.pojo.Loan_pojo;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.com.smsserver.pojo.PaymentSMS_pojo;
import iroshan.com.smsserver.pojo.ReceiptSMS_pojo;
import iroshan.com.smsserver.view.Jurnal_if_view_2;
import iroshan.com.smsserver.view.LoanArrears_infrm_view;
import iroshan.com.smsserver.view.LoanDue_infrm_view;
import iroshan.com.smsserver.view.LoanOutstanding_infrm_view;
import iroshan.com.smsserver.view.MessageToMemebers_view;
import iroshan.com.smsserver.view.Payment_infrm_view;
import iroshan.com.smsserver.view.ReceiptJIFrame_view;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.AGateway;
import org.smslib.OutboundMessage;

/**
 *
 * @author Iroshan
 */
public class OutboundNotificationProcessing
{

    public void process (AGateway agPara, OutboundMessage omPara)
    {

        AGateway ag = agPara;
        OutboundMessage om = omPara;

        try
        {

            String recipient = om.getRecipient ().trim ();
            String msgText = om.getText ().trim ();
            String refNo = om.getRefNo ();
            System.out.println ("refNo>" + refNo);
//            String messageStatusString = MessageStatusEnum.PENDING.toString ();
            String messageStatus = om.getMessageStatus ().toString ().trim ();



            ReceiptJIFrame_view receiptJIFrame_view = ReceiptJIFrame_view.getReceiptJIFrame_view ();
            if (ReceiptJIFrame_view.getReceiptJIFrame_view () != null)
            {
                receiptJIFrame_view.loadReceiptToTables (recipient, msgText, messageStatus);
            }







            Payment_infrm_view payment_infrm_view = Payment_infrm_view.getPayment_infrm_view ();
            if (Payment_infrm_view.getPayment_infrm_view () != null)
            {

                payment_infrm_view.loadPaymentToTables (recipient, msgText, messageStatus);

            }



            LoanOutstanding_infrm_view loanOutstanding_infrm_view = LoanOutstanding_infrm_view.getArrears_infrm_view ();
            if (loanOutstanding_infrm_view != null)
            {

                loanOutstanding_infrm_view.loadArrearsToTables (recipient, msgText, messageStatus);


            }






            LoanDue_infrm_view loanDue_infrm_view = LoanDue_infrm_view.getLoanDue_infrm_view ();
            if (loanDue_infrm_view != null)
            {

                loanDue_infrm_view.loadArrearsToTables (recipient, msgText, messageStatus);

            }






            LoanArrears_infrm_view loanArrears_infrm_view = LoanArrears_infrm_view.getArrears_infrm_view ();
            if (loanArrears_infrm_view != null)
            {

                loanArrears_infrm_view.loadArrearsToTables (recipient, msgText, messageStatus);



            }


            new SendMessageSaveDao ().updateSendMessageStatus (recipient, msgText, messageStatus);



        } catch (Exception ex)
        {
            Logger.getLogger (OutboundNotification.class.getName ()).log (Level.SEVERE, null, ex);
        }


    }


    public void processWithKey (Integer keyPara, OutboundMessage omPara)
    {

        Integer key = keyPara;
        OutboundMessage om = omPara;

        try
        {

            String recipient = om.getRecipient ().trim ();
            String msgText = om.getText ().trim ();
            String refNo = om.getRefNo ();
            System.out.println ("refNo>" + refNo);
//            String messageStatusString = MessageStatusEnum.PENDING.toString ();
            String messageStatus = om.getMessageStatus ().toString ().trim ();



            Jurnal_if_view_2 jurnal_if_view = Jurnal_if_view_2.getJurnal_if_view ();
            if (jurnal_if_view != null)
            {
                jurnal_if_view.loadTablesByMapKey (key, messageStatus);
            }


            MessageToMemebers_view messageToMemebers_view = MessageToMemebers_view.getMessageToMemebers_view ();
            if (messageToMemebers_view != null)
            {
                messageToMemebers_view.loadTablesByMapKey (key, messageStatus);
            }

//            }


//            new ReceiveAndSendMonitorService ().sendMessageProcess (omPara);
            new SendMessageSaveDao ().updateSendMessageStatus (recipient, msgText, messageStatus);

        } catch (Exception ex)
        {
            Logger.getLogger (OutboundNotificationProcessing.class.getName ()).log (Level.SEVERE, null, ex);
        }
    }

}
