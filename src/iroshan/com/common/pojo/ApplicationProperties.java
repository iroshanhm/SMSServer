/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.common.pojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 *
 * @author Iroshan
 */
public class ApplicationProperties {

    
    
    
    
    
    
    
    private static Date systemDate;
    private static Integer logedUserId = -1;
    private static Integer sysLanguage = 1; // 1 = English / 2 = singhala    
    private static DateFormat simpleDateFormat;





    public static Integer getSysLanguage () {
        return sysLanguage;
    }

    public static void setSysLanguage (Integer sysLanguage) {
        ApplicationProperties.sysLanguage = sysLanguage;
    }

    public static String getSystemDateAndTimeAsString () {
        return getDateFormat ().format (getSystemDate());
    }

    public static Date getSystemDateAndTimeAsDate () {
        return getSystemDate ();
    }

    public static Date getSystemDate () {
        return systemDate;
    }

    public static void setSystemDate (Date systemDate) {
        ApplicationProperties.systemDate = systemDate;
    }

 
    public static Integer getLogedUserId () {
        return logedUserId;
    }

    public static void setLogedUserId (Integer logedUserId) {
        ApplicationProperties.logedUserId = logedUserId;
    }

    public static DateFormat getDateFormat () {
        simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss.SSS a");
        return simpleDateFormat;
    }






}
