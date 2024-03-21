package iroshan.com.common.utility;

import iroshan.com.common.pojo.ApplicationProperties;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class CurrencyValidater {

    public static boolean validateCurrency(String str_para) {

        boolean aFlag = true;

        if (!str_para.isEmpty()) {
            if (str_para.endsWith("F") || str_para.endsWith("f") || str_para.endsWith("D") || str_para.endsWith("d")) {
                aFlag = false;
//		 JOptionPane.showMessageDialog(null, "Check format.", "", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Double.parseDouble(str_para);
                } catch (NumberFormatException e) {
                    aFlag = false;
//		    JOptionPane.showMessageDialog(null, "Check format.", "", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        /*else{
         aFlag = false;
         JOptionPane.showMessageDialog(null, "Empty.", "", JOptionPane.WARNING_MESSAGE);
         }*/

        return aFlag;
    }

    public static boolean validateInteger(String str_para) {

        boolean aFlag = true;

        if (!str_para.isEmpty()) {
            if (str_para.endsWith("F") || str_para.endsWith("f") || str_para.endsWith("D") || str_para.endsWith("d")) {
                aFlag = false;
//		 JOptionPane.showMessageDialog(null, "Check format.", "", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Double.parseDouble(str_para);
                } catch (NumberFormatException e) {
                    aFlag = false;
//		    JOptionPane.showMessageDialog(null, "Check format.", "", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        /*else{
         aFlag = false;
         JOptionPane.showMessageDialog(null, "Empty.", "", JOptionPane.WARNING_MESSAGE);
         }*/

        return aFlag;
    }

    public static boolean validateDateIsPast(Date date_para) throws ParseException {

        boolean aFlag = true;

        if (date_para != null) {

//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            DateFormat dateFormat = ApplicationProperties.getDateFormat();

            Calendar calCurentAppDate = Calendar.getInstance();
            calCurentAppDate.setTime(dateFormat.parse(dateFormat.format(ApplicationProperties.getSystemDateAndTimeAsDate())));

            Calendar calParaDate = Calendar.getInstance();
            calParaDate.setTime(dateFormat.parse(dateFormat.format(date_para)));

            if (calCurentAppDate.compareTo(calParaDate) > 0) {
                aFlag = true;
            } else {
                aFlag = false;
            }

        }
        /*else{
         aFlag = false;
         JOptionPane.showMessageDialog(null, "Empty.", "", JOptionPane.WARNING_MESSAGE);
         }*/

        return aFlag;
    }

}
