/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.InboundMessageProcessing;
import org.smslib.AGateway;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message;
import org.smslib.OutboundMessage;

/**
 *
 * @author Iroshan
 */
public class InboundNotification implements IInboundMessageNotification
{

    @Override
    public void process (AGateway gateway, Message.MessageTypes msgType, final InboundMessage msg)
    {

        if (msgType == Message.MessageTypes.INBOUND)
        {

            new Thread (new Runnable ()
            {

                @Override
                public void run ()
                {
                    
                    System.out.println("im here.....");
                    new ReceiveAndSendMonitorService ().receiveMessageProcess (msg);
                    new InboundMessageProcessing ().processInboundMessage (msg);
                }
            }).start ();




        } else if (msgType == Message.MessageTypes.STATUSREPORT)
        {

            new Thread (new Runnable ()
            {

                @Override
                public void run ()
                {

//            new ReceiveAndSendMonitorService ().receiveMessageProcess (msg);
                    new StatusMessageProcessing ().processStatusMessage (msg);
                }
            }).start ();
        }
        
        
//        MySMSServer.restartService ();
    }


}
