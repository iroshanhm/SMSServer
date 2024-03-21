/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.com.smsserver.service.gatway.dialog.SendSMSThroughGatewayAPI;
import iroshan.com.smsserver.view.WaitingDialogView;
import org.smslib.OutboundMessage;

/**
 *
 * @author Iroshan
 */
public class CustomMessage_service {

    SendMessage sendMessage = null;

    public OutboundMessage.MessageStatuses sendCustomMessage(String phoneNoPara, String messagePara) throws Exception {

        if (sendMessage == null) {
            sendMessage = new SendMessage();
        }


        String phoneNo = phoneNoPara;
        String message = messagePara;
//
//
//        WaitingDialogView waitingDialog = new WaitingDialogView ();
//        waitingDialog.setCustomMessage ("Sending...");
//        waitingDialog.setVisible (true);
//
////        MySMSServer.setInboundFalse ();
////        MySMSServer.restartService ();
//        
////        boolean returnVal = new SendMessage ().send (MyMessageTypeEnum.CUSTOM.toString (), -1, phoneNo, message);
//        new SendSMSThroughGatewayAPI().send(phoneNo, message);
//
////        MySMSServer.setInboundTrue ();
//
//        waitingDialog.dispose ();
//
////        return returnVal;



        return sendMessage.send_5(phoneNo, message);

    }
}
