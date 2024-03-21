/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;

/**
 *
 * @author Iroshan
 */
public class OutboundNotification implements IOutboundMessageNotification {

    int idSMSReceive = 0;

    @Override
    public void process (AGateway agPara, OutboundMessage omPara) {

        final AGateway ag = agPara;
        final OutboundMessage om = omPara;

        new Thread (new Runnable () {

            @Override
            public void run () {
                new OutboundNotificationProcessing ().process (ag, om);
            }
            
        }).start ();

    }

}
