package iroshan.com.common.utility;

import java.text.DecimalFormat;

/**
 *
 * @author IroSHAN
 */
public class MyRounding {

    public static String round_Up(double f) {

        double f2 = f + 0.005f;
        DecimalFormat dfm = new DecimalFormat("#0.00");
        return dfm.format(f2);
    }

    public static String round_Zero(double f) {
        DecimalFormat dfm = new DecimalFormat("#0.00");
        return dfm.format(f);
    }

    public static String round_Down(double f) {
        double f2 = f - 0.005f;
        DecimalFormat dfm = new DecimalFormat("#0.00");
        return dfm.format(f2);
    }

    public static String round_Up_toZero(double f) {
        double f2 = f + 0.5f;
        DecimalFormat dfm1 = new DecimalFormat("#0");
        DecimalFormat dfm2 = new DecimalFormat("#0.00");
        return dfm2.format(Double.parseDouble(dfm1.format(f2)));
    }

//    public static String roundToLastTwoDecimal(double f) {
//        double f2 = f;
////        DecimalFormat dfm1 = new DecimalFormat("#0");
//        DecimalFormat dfm2 = new DecimalFormat("#0.00");
//        return dfm2.format(f2);
//    }

    public static String roundToLastTwoDecimal(double f) {
        double f2 = f;
//        DecimalFormat dfm1 = new DecimalFormat("#0");
        DecimalFormat twoDForm = new DecimalFormat("#.00##");
        return twoDForm.format(f2);
    }

    public static String roundToWithoutDecimal(double f) {
        double f2 = f;
//        DecimalFormat dfm1 = new DecimalFormat("#0");
        DecimalFormat twoDForm = new DecimalFormat("#");
        return twoDForm.format(f2);
    }

}
