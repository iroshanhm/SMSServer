package iroshan.com.common.utility;

import javax.swing.JOptionPane;

public class MyMessagesUtility {


    /**
     * *************************************************************************
     */



    /**
     * Show information with custom message
     *
     * @param message String
     */
    public static void showInformation (String message) {
        JOptionPane.showMessageDialog (null, message, "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show information for insert success
     */
    public static void showInformation_insertSuccess () {
        JOptionPane.showMessageDialog (null, "Insert Success", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show information for update success
     */
    public static void showInformation_updateSuccess () {
        JOptionPane.showMessageDialog (null, "Insert Success", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show information for remove success
     */
    public static void showInformation_removeSuccess () {
        JOptionPane.showMessageDialog (null, "Insert Success", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }








    /**
     * *************************************************************************
     */

    /**
     * Show Warining with custom message
     *
     * @param message String
     */
    public static void showWarning (String message) {
        JOptionPane.showMessageDialog (null, message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }





    
    
    
    /**
     * *************************************************************************
     */



    /**
     * Show Error with custome message
     *
     * @param message String
     */
    public static void showError (String message) {
        JOptionPane.showMessageDialog (null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Show Error for Insert NOT success
     *
     */
    public static void showError_insertNOTSuccess () {
        JOptionPane.showMessageDialog (null, "Insert NOT Success", "ERROR", JOptionPane.ERROR_MESSAGE);
    }



    /**
     * Show Error for Update NOT success
     *
     */
    public static void showError_updateNOTSuccess () {
        JOptionPane.showMessageDialog (null, "Update NOT Success", "ERROR", JOptionPane.ERROR_MESSAGE);
    }



    /**
     * Show Error for Remove NOT success
     *
     */
    public static void showError_removeNOTSuccess () {
        JOptionPane.showMessageDialog (null, "Remove NOT Success", "ERROR", JOptionPane.ERROR_MESSAGE);
    }



    /**
     * *************************************************************************
     */


    public static Integer showConfirmDoYouWantToRemove (String message) {
        return JOptionPane.showConfirmDialog (null, message, "CONFIRM ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

}
