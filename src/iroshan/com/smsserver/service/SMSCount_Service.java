/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.SMSCount_Dao;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author IROshan
 */
public class SMSCount_Service {

    public void saveSMSCount(String SMSTypePara, int noOfSMSPara) {
        new SMSCount_Dao().saveSMSCount(SMSTypePara, noOfSMSPara);
    }


    public List<Map> getSMSCountInDateRange(Date fromDatePara, Date toDatePara, String typePara) {
        return new SMSCount_Dao().getSMSCountInDateRange(fromDatePara, toDatePara, typePara);
    }
}
