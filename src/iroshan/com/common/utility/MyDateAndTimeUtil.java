package iroshan.com.common.utility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyDateAndTimeUtil {

    private static String currentDateAndTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS";

    public static String getDate() {

        String date;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(getCalendeDefaultDate().getTime());

        return date;

    }

    public static String getTime() {

        String time = null;
        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss.SSS");
        time = timeFormat.format(new Date());

        return time;

    }

    /**
     *
     * @return Date 'yyyy-MM-dd HH:mm:ss:SSS'
     */
    public static Date getCurrentDateAndTime() {
        try {
            DateFormat dateFormater = new SimpleDateFormat(currentDateAndTimeFormat);
            return dateFormater.parse(dateFormater.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @return String 'yyyy-MM-dd HH:mm:ss:SSS'
     */
    public static String getCurrentDateAndTime_As_String() {
        DateFormat dateFormater = new SimpleDateFormat(currentDateAndTimeFormat);
        return dateFormater.format(new Date());
    }

    /**
     *
     * @param datePara Type "Date"
     *
     * @return String | Format "yyyy-MM-dd HH:mm:ss.SSS"
     */
    public static String getDateTimeMilisecond(Date datePara) {

        try {
            String dateFormated = getFormatedDateAsString(datePara);
            String timeFormated = getTime();

            DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date dateFor = dateFormater.parse(dateFormated + " " + timeFormated);

            return String.valueOf(dateFor.getTime());

        } catch (ParseException ex) {
            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static String getDateTimeMilisecond() {

        try {
            //        String date;
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        date = dateFormat.format(datePara);        
//        date = dateFormat.format(getCalendeDefaultDate().getTime());

            DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String formatedDateStr = dateFormater.format(new Date());
            Date dateFormated = dateFormater.parse(formatedDateStr);

            return String.valueOf(dateFormated.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     *
     * @param dateDate Type "Date"
     *
     * @return String | Format "yyyy-MM-dd"
     */
    public static String getFormatedDateAsString(Date dateDate) {

        String date = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(dateDate);

        return date;

    }

    /**
     *
     * @param dateDate Type "Date"
     *
     * @return java.util.Date | Format "yyyy-MM-dd"
     */
    public static Date getFormatedDate2(Date dateDate) {
        Date date2 = null;
        try {
            String date = null;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(dateDate);

            date2 = dateFormat.parse(date);

        } catch (ParseException ex) {
            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return date2;
    }

    /**
     *
     * @param dateDate Type "Date"
     *
     * @return String | Format "yyyy-MM-dd HH:mm:ss.SSS"
     */
    public static String getFormatedDateAsStringAndTime(Date dateDate) {

        String date = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        date = dateFormat.format(dateDate);

        return date;

    }

    /**
     * Return Date type by string format "yyyy-MM-dd"
     */
    public static Date getFromtedDateByStringPara(String strDate) throws ParseException {

        Date date = null;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        date = formatter.parse(strDate);

        return date;
    }

    public static Calendar getCalendeFromString(String strDate) {

        Calendar calender;
        Date date = null;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            date = formatter.parse(strDate);

        } catch (ParseException ex) {

            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);

        }

        calender = Calendar.getInstance();
        calender.setTime(date);

        return calender;
    }

    public static Calendar getCalendeDefaultDate() {

        Calendar calender = null;

        String strDate = null;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        if (strDate == null) {
            strDate = formatter.format(new Date());
        }

        try {
            date = formatter.parse(strDate);
        } catch (ParseException ex) {
            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        calender = Calendar.getInstance();
        calender.setTime(date);

        return calender;
    }

    public static boolean dateLessThanToday(Date datePass) {

        boolean aFlag = false;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        Date today = null;
        try {
            date = formatter.parse(getFormatedDateAsString(datePass));
            today = formatter.parse(getFormatedDateAsString(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        int t = date.compareTo(today);

        if (t < 0) {
            aFlag = true;
        }

        return aFlag;
    }

    public static String getFormatedDateAsStringByStringParameter(String parStrDate) {

        String strDate = null;
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            strDate = dateFormatter.format(dateFormatter.parse(parStrDate));

        } catch (ParseException ex) {

            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);

        }

        return strDate;

    }

    /**
     *
     * @param Date
     *
     * @return Return Timestamp | Format dd/MM/yyyy
     */
    public static Timestamp getFormatedDateAsTimestampByDateParameter(Date parDate) {

        String strDate = null;
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Timestamp tmsDate = null;
        try {

            strDate = dateFormatter.format(parDate);
            java.util.Date date = dateFormatter.parse(strDate);
            tmsDate = new java.sql.Timestamp(date.getTime());

        } catch (ParseException ex) {

            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);

        }

        return tmsDate;
    }

    /**
     *
     * @param Date
     *
     * @return Return Type 'String' | Format 'dd/MM/yyyy'
     */
    public static String getFormatedDateAsStringByDateParameter(Date parDate) {

        String strDate = null;
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Timestamp tmsDate = null;
        try {

            strDate = dateFormatter.format(parDate);
//            
//            strDate = dateFormatter.format(parDate);
//            java.util.Date date = dateFormatter.parse(strDate);
//            tmsDate = new java.sql.Timestamp(date.getTime());

        } catch (Exception ex) {

            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);

        }

        return strDate;
    }

    public static Timestamp getFormatedDateAsTimestampByStringParameter(String parStrDate) {

        String strDate = null;
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Timestamp tmsDate = null;
        try {

            strDate = dateFormatter.format(dateFormatter.parse(parStrDate));
            java.util.Date date = dateFormatter.parse(strDate);
            tmsDate = new java.sql.Timestamp(date.getTime());

        } catch (ParseException ex) {

            Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);

        }

        return tmsDate;
    }

    public static Timestamp getFormatedTimestampByStringPara(String paraStrDate) {

        Timestamp tmsDate = null;

        if (paraStrDate != null && !paraStrDate.equalsIgnoreCase("")) {

            //^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$
            try {

                Pattern p = Pattern.compile("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$");
                Matcher m = p.matcher(paraStrDate);

                if ((m.matches() == true)) {

                    String newDate = paraStrDate + " " + getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    java.util.Date date = sdf.parse(newDate);
                    tmsDate = new java.sql.Timestamp(date.getTime());

                } else {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    java.util.Date date = sdf.parse(paraStrDate);
                    tmsDate = new java.sql.Timestamp(date.getTime());

                }

//             tmsDate = Timestamp.valueOf(dateFormatter.format(dateFormatter.parse(paraStrDate)));
            } catch (ParseException ex) {

                Logger.getLogger(MyDateAndTimeUtil.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        return tmsDate;
    }

    /**
     *
     * @param datePara
     *
     * @return Integere dayOfMonth
     */
    public static Integer getDayOfMonthByDate(Date datePara) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(datePara);
        Integer day = cal.get(Calendar.DAY_OF_MONTH);

        return day;
    }

    /**
     *
     * @param dayPara
     * @return String Suffix
     */
    public static String getDayOfMonthSuffix(Integer dayPara) {

        if (dayPara <= 31) {
            if (dayPara >= 11 && dayPara <= 13) {
                return "th";
            }
            switch (dayPara % 10) {
                case 1:
                    return "st";
                case 2:
                    return "nd";
                case 3:
                    return "rd";
                default:
                    return "th";
            }
        } else {
            return "Illegal day of month";
        }

    }


}
