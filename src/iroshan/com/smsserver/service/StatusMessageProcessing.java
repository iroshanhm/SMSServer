/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.SendMessageSaveDao;
import org.smslib.InboundMessage;

/**
 *
 * @author Iroshan
 */
public class StatusMessageProcessing {

    void processStatusMessage (InboundMessage msgPara) {

        InboundMessage inBoundMSG = msgPara;

        System.out.println (inBoundMSG);
        System.out.println ("----------------------------------");
        String phoneNo = "+" + inBoundMSG.getOriginator ();
        String message = inBoundMSG.getText ();
//        inBoundMSG.get

//        new SendMessageSaveDao ().updateSendMessageStatus (phoneNo, message, message);

        new DeleteInboundMessageFromGateway ().deleteInboundMessage (inBoundMSG);

    }

}
