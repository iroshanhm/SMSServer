/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;


import iroshan.com.smsserver.view.WaitingDialogView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class CustomMessageSender {

    Thread trdPleaseWait = null;
    WaitingDialogView waitDialog = null;

    public void sendCustomMessage(final String mobileNo, final String messageString) {

        OutboundMessage sendMessage = new OutboundMessage(mobileNo, messageString);
//            msg = new OutboundMessage(phoneNo, msg);
//            MySMSServer.smsServerService.sendMessage(sendMessage);
        MySMSServer.smsServerService.queueMessage(sendMessage);

    }
}
