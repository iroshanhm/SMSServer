/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.view.ReceiveAndSendMonitorView;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;

/**
 *
 * @author Iroshan
 */
public class ReceiveAndSendMonitorService
{

    public void receiveMessageProcess (InboundMessage inboundMessagePara)
    {
        String phoneNo = inboundMessagePara.getOriginator ();
        String message = inboundMessagePara.getText ();
        String date = MyDateAndTimeUtil.getFormatedDateAsString (inboundMessagePara.getDate ());

        ReceiveAndSendMonitorView receiveAndSendMonitorView = ReceiveAndSendMonitorView.getReceiveAndSendMonitorView ();
        if (receiveAndSendMonitorView != null)
        {
            receiveAndSendMonitorView.receiveMessageProcess (phoneNo, message, date);
        }
    }

    public void receiveStatusMessageProcess (InboundMessage inboundMessagePara)
    {
        String phoneNo = inboundMessagePara.getOriginator ();
        
        String message = inboundMessagePara.getText ();
        String date = MyDateAndTimeUtil.getFormatedDateAsString (inboundMessagePara.getDate ());

        ReceiveAndSendMonitorView receiveAndSendMonitorView = ReceiveAndSendMonitorView.getReceiveAndSendMonitorView ();
        if (receiveAndSendMonitorView != null)
        {
            receiveAndSendMonitorView.receiveMessageProcess (phoneNo, message, date);
        }
    }

    
    
    public void sendMessageProcess (OutboundMessage outboundMessage)
    {
        
        
        String phoneNo = outboundMessage.getRecipient ();
        String message = outboundMessage.getText ();
        String date = MyDateAndTimeUtil.getFormatedDateAsString (outboundMessage.getDate ());

        ReceiveAndSendMonitorView receiveAndSendMonitorView = ReceiveAndSendMonitorView.getReceiveAndSendMonitorView ();
        if (receiveAndSendMonitorView != null)
        {
            receiveAndSendMonitorView.sendMessageProcess (phoneNo, message, date);
        }
    }
    
    
    
    public void sendMessageProcess_2 (String phnNoPara, String messagePara, String datePara)
    {
        
        
        String phoneNo = phnNoPara;
        String message = messagePara;
//        String date = MyDateAndTimeUtil.getFormatedDateAsString (outboundMessage.getDate ());
        String date = datePara;

        ReceiveAndSendMonitorView receiveAndSendMonitorView = ReceiveAndSendMonitorView.getReceiveAndSendMonitorView ();
        if (receiveAndSendMonitorView != null)
        {
            receiveAndSendMonitorView.sendMessageProcess (phoneNo, message, date);
        }
    }
}
