/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.common.derby.service;

import iroshan.com.common.utility.MyMessagesUtility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iroshan
 */
public class DerbyConnection {

    private Connection derbyConnection;





    public DerbyConnection () {
        setConnection ();
    }





    /**
     * Get derby connection
     *
     * @return Connection
     *
     */
    public Connection getConnection () {

        return derbyConnection;

    }





    private void setConnection () {

        try {

            String dbURL = "jdbc:derby:derbysetting;create=true";

            Class.forName ("org.apache.derby.jdbc.EmbeddedDriver").newInstance ();
            System.out.println ("Loaded the appropriate driver");
            try {
                derbyConnection = DriverManager.getConnection (dbURL);
            } catch (SQLException ex) {
                Logger.getLogger (DerbyConnection.class.getName ()).log (Level.SEVERE, null, ex);
            }


        } catch (ClassNotFoundException ex) {
            MyMessagesUtility.showError (ex.getMessage ());
            Logger.getLogger (DerbyConnection.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            MyMessagesUtility.showError (ex.getMessage ());
            Logger.getLogger (DerbyConnection.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            MyMessagesUtility.showError (ex.getMessage ());
            Logger.getLogger (DerbyConnection.class.getName ()).log (Level.SEVERE, null, ex);
        }

    }
}
