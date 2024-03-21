/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.Company_Profile;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class ReplyForReceivedMessage
{

    public void send (int idSMSReceive, String phoneNo, String message)
    {



//        if (MySMSServer.getSmsServerService ().getServiceStatus () == Service.ServiceStatus.STARTED)
//        {
//            try
//            {
//                MySMSServer.getSmsServerService ().stopService ();
//                MySMSServer.getSmsServerService ().startService ();
//            } catch (SMSLibException ex)
//            {
//                Logger.getLogger (ReplyForReceivedMessage.class.getName ()).log (Level.SEVERE, null, ex);
//            } catch (IOException ex)
//            {
//                Logger.getLogger (ReplyForReceivedMessage.class.getName ()).log (Level.SEVERE, null, ex);
//            } catch (InterruptedException ex)
//            {
//                Logger.getLogger (ReplyForReceivedMessage.class.getName ()).log (Level.SEVERE, null, ex);
//            }
//        }



        try
        {
            OutboundMessage sendMessage = new OutboundMessage (phoneNo, message);
            sendMessage.setStatusReport (true);
            sendMessage.setFrom (Company_Profile.getCompanyName ());
            MySMSServer.smsServerService.queueMessage (sendMessage);

            System.out.println ("\n");
            System.out.println ("\n");
            System.out.println ("SMS SENT =======================");
            System.out.println (sendMessage);
            System.out.println ("\n");
            System.out.println ("\n");
//            Home.setConsolText("Message Send ...");



        } catch (Exception ex)
        {
            ex.printStackTrace ();
            Logger.getLogger (ReplyForReceivedMessage.class.getName ()).log (Level.SEVERE, null, ex);
        }
    }
}
