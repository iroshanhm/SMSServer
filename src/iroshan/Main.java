/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan;

import iroshan.com.common.config.DBConnection;
import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.derby.service.Setting_service;
import iroshan.com.smsserver.view.ApplicationDesktop;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Iroshan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        Company_Profile company_Profile = new Company_Profile();
        ApplicationDesktop applicationDesktop = new ApplicationDesktop();
        applicationDesktop.setVisible(true);

        new Setting_service().loadSetting();
        new SessionFactoryUtil();

    }

}
