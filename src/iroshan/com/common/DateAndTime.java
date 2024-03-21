package iroshan.com.common;

import iroshan.com.common.pojo.ApplicationProperties;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTime {
    
	public Date getApplicationDateTime_As_Date() throws ParseException{
		
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		
		DateFormat df = ApplicationProperties.getDateFormat();
		
		return df.parse(df.format(date));
		
		
	}
	
	
	public Timestamp getApplicationDateTime_As_Timestamp() throws ParseException{
		
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		
		DateFormat df = ApplicationProperties.getDateFormat();

		return new Timestamp(df.parse(df.format(date)).getTime());
		
	}
	
	public Date getDateFromString(String dateArg){
		
		Date returnDate = null;
		String date = dateArg;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		
		try {
			returnDate = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Date date = formatter.parse(date);
		
		return returnDate;
	}
	
public Date getDateTimeFromString(String dateArg){
		
		Date returnDate = null;
		String date = dateArg.trim();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss a");
	     
	     try {
	    	returnDate = dateFormat.parse(date);	        
	     } catch (ParseException e) {
	      	e.printStackTrace();
	     }     
		
//		Date date = formatter.parse(date);
		
		return returnDate;
	}


/**
 * 
 * @param dateArg as String
 * @return Date
 */

public Date getDateTimeFromStringByFingerPrint(String dateArg){
	
	String[] arrStr = dateArg.split(" ");
	
	String date = arrStr[0];
	String time = arrStr[1];
	
	String[] dateArray = date.split("-");
	Integer year = getIntegerForStringValue(dateArray[0].trim());
	Integer month = getIntegerForStringValue(dateArray[1].trim());
	
	Integer day = getIntegerForStringValue(dateArray[2].trim());
		
	String[] timeArray = time.split(":");
	Integer hour = getIntegerForStringValue(timeArray[0].trim());
	Integer min = getIntegerForStringValue(timeArray[1].trim());
	Integer second = getIntegerForStringValue(timeArray[2].trim());
	
	Calendar calender = Calendar.getInstance();
	calender.clear();
	calender.set(Calendar.YEAR, year);
	calender.set(Calendar.MONTH, month-1);
	calender.set(Calendar.DATE, day);
	
	calender.set(Calendar.HOUR, hour);
	calender.set(Calendar.MINUTE, min);
	calender.set(Calendar.SECOND, second);
	
//	calender.set(year, (month -1) , day, hour, min, second);
	
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	Date returnValue = null;
    try {
    	returnValue = dateFormat.parse(dateFormat.format(calender.getTime()));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	return returnValue;
}

public Integer getIntegerForStringValue(String stringArg){
	
	String str = stringArg;
	StringBuilder strBuilder = new StringBuilder();
	
	for (int i = 0; i < str.length(); i++) {
		strBuilder.append(str.substring(i, ++i));
	}
	
	return Integer.parseInt(strBuilder.toString());
}

}
