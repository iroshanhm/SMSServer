/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;



import iroshan.com.smsserver.view.ApplicationDesktop;
import org.smslib.AGateway;
import org.smslib.IGatewayStatusNotification;

/**
 *
 * @author Iroshan
 */
public class GatewayStatusNotification implements IGatewayStatusNotification {

    @Override
    public void process (AGateway ag, AGateway.GatewayStatuses gs, final AGateway.GatewayStatuses gs1) {
//        System.out.println (">>> Gateway Status change for " + ag.getGatewayId ()
//                + ", OLD: " + gs + " -> NEW: " + gs1);
//        
//        System.out.println ("ag.getStatus ()>"+ag.getStatus ());

        String status = ag.getStatus().toString ();
        
        
        
        ApplicationDesktop.setStatus (status);
        
    }

}
