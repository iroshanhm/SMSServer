/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.common.utility;

/**
 *
 * @author Iroshan
 */
public class MyPhoneNoFormatingUtility {

    public static String formatTo9Number(String phoneNoPara) {
        String phoneNo = phoneNoPara;

        int phnNoLenth = phoneNo.length();
        if (phnNoLenth > 9) {
            int t = phnNoLenth - 9;
            phoneNo = phoneNo.substring(t, (t + 9));
        }

        return phoneNo;
    }


    public static String formatToPlus94Number(String phoneNoPara) {
        String phoneNo = phoneNoPara;
        if (phoneNo.isEmpty()) {
            phoneNo = null;
        } else {

            if (phoneNo.contains("-")) {
                phoneNo = phoneNo.replace("-", "");
            }

            int phnNoLenth = phoneNo.length();
            if (phnNoLenth >= 9) {
                if (phnNoLenth > 9) {
                    if (phoneNo.startsWith("0")) {
                        int t = phnNoLenth - 9;
                        phoneNo = phoneNo.substring(t, (t + 9));
                        phoneNo = "94" + phoneNo;
                    } else {
                        int t = phnNoLenth - 9;
                        String phoneNoPrefix = phoneNo.substring(0, t);
                        if (phoneNoPrefix.equalsIgnoreCase("94")) {
                            phoneNo = phoneNo.substring(t, (t + 9));
                            phoneNo = "94" + phoneNo;
                        } else {
                            phoneNo = null;
                        }
                    }
                }
                if (phnNoLenth == 9) {
                    int t = phnNoLenth - 9;
                    phoneNo = phoneNo.substring(t, (t + 9));
                    phoneNo = "94" + phoneNo;
                }

            } else {
                phoneNo = null;
            }
        }
        return phoneNo;
    }
}
