/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import org.smslib.AGateway;
import org.smslib.ICallNotification;

/**
 *
 * @author Iroshan
 */
public class CallNotification implements ICallNotification{
        @Override
        public void process(AGateway ag, String callerId) {
            System.out.println(">>> New call detected from Gateway: "
                    + ag.getGatewayId() + " : " + callerId);
        }
   
}
