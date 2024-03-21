/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.HistryReceivedMessageDao;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Iroshan
 */
public class HistryReceivedMessageService
{

    public Map<String, Map>  search (String phoneNoPara, String textPara, Date dateSelectedFromPara, Date dateSelectedToPara)
    {
        String phoneNo = phoneNoPara;
        String text = textPara;
        Date dateSelectedFrom = dateSelectedFromPara;
        Date dateSelectedTo = dateSelectedToPara;
        
        return new HistryReceivedMessageDao ().search (phoneNo, text, dateSelectedFrom, dateSelectedTo);
    }
    
}
