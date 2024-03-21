/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.HistryReceivedMessageDao;
import iroshan.com.smsserver.dao.HistrySendMessageDao;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Iroshan
 */
public class HistrySendMessageService
{

    public Map<String, Map>  search (String phoneNoPara, String textPara, Date dateSelectedFromPara, Date dateSelectedToPara, String typePara)
    {
        String phoneNo = phoneNoPara;
        String text = textPara;
        Date dateSelectedFrom = dateSelectedFromPara;
        Date dateSelectedTo = dateSelectedToPara;
        String type = typePara;
        
        return new HistrySendMessageDao ().search (phoneNo, text, dateSelectedFrom, dateSelectedTo, type);
    }
    
}
