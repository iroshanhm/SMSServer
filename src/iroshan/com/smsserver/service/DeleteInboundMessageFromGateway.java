/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.TimeoutException;

/**
 *
 * @author Iroshan
 */
public class DeleteInboundMessageFromGateway
{

    public void deleteInboundMessage (InboundMessage inboundMSG)
    {
        try
        {
            System.out.println ("Going Delete Message...");

            int memIndex = inboundMSG.getMemIndex ();
            String memLocation = inboundMSG.getMemLocation ();
            
            System.out.println ("memIndex>>"+memIndex);
            System.out.println ("memLocation>>"+memLocation);
            
            
            MySMSServer.getSmsServerService ().deleteMessage (inboundMSG);

//            MySMSServer.serialModemGateway.getATHandler ().deleteMessage (memIndex, memLocation);

            System.out.println ("Deleted Message...");
        } catch (TimeoutException ex)
        {

            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (GatewayException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (InterruptedException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        }
    }
}
