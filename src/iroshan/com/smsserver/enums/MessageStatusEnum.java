/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.enums;

/**
 *
 * @author Iroshan
 */
public enum MessageStatusEnum {


    PENDING,QUEUE, SENDING, SENT, DELEVERED,UNSENT, FAILED;

    @Override
    public String toString () {
        return super.toString (); //To change body of generated methods, choose Tools | Templates.
    }

}
