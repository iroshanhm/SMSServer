/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.common.derby.dao;

import iroshan.com.common.derby.pojo.Setting_pojo;
import iroshan.com.common.derby.service.DerbyConnection;
import iroshan.com.common.utility.MyMessagesUtility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iroshan
 */
public class Derby_dao {

    private Connection connForDerby;

    public Derby_dao () {
        this.connForDerby = new DerbyConnection ().getConnection ();
    }





    public void createDerbyTable () {
        try {

            connForDerby.prepareStatement ("CREATE TABLE setting"
                    + "(com_port_id VARCHAR(500),"
                    + "db_connection_type VARCHAR(500),"
                    + "db_odbc_name VARCHAR(500),"
                    + "db_ip VARCHAR(500),"
                    + "db_port VARCHAR(500),"
                    + "db_name VARCHAR(500),"
                    + "db_user_name VARCHAR(500),"
                    + "db_password VARCHAR(500))").executeUpdate ();
            System.out.println ("Create table Setting");


        } catch (SQLException ex1) {
            Logger.getLogger (Derby_dao.class.getName ()).log (Level.SEVERE, null, ex1);
        }

        try {
            // Delete Table Rows

            System.out.println ("Deleted Table Rows");
            connForDerby.prepareStatement ("Delete From Setting").executeUpdate ();

        } catch (SQLException ex) {

            Logger.getLogger (Derby_dao.class.getName ()).log (Level.SEVERE, null, ex);
        }
    }





    public void insertDBSetting () {


        createDerbyTable ();
        removeAllRowsInSettingTbl ();



        try {



            String comPortID = Setting_pojo.getComPortID ();
            System.out.println ("comPortID >> "+comPortID);
            String dbConnectionType = Setting_pojo.getDbConnectionType ();
            System.out.println ("dbConnectionType >> "+dbConnectionType);
            String dbODBCName = Setting_pojo.getDbODBCName ();
            String dbIP = Setting_pojo.getDbIP ();
            String dbPort = Setting_pojo.getDbPort ();
            String dbName = Setting_pojo.getDbName ();
            String dbUserName = Setting_pojo.getDbUserName ();
            String dbPassword = Setting_pojo.getDbPassword ();





            connForDerby.prepareStatement (
                    "INSERT INTO setting"
                    + "(com_port_id,"
                    + "db_connection_type,"
                    + "db_odbc_name,"
                    + "db_ip,"
                    + "db_port,"
                    + "db_name,"
                    + "db_user_name,"
                    + "db_password)"
                    + " VALUES('" + comPortID + "','" + dbConnectionType + "','" + dbODBCName + "','" + dbIP + "',"
                    + "'" + dbPort + "','" + dbName + "','" + dbUserName + "','" + dbPassword + "')").executeUpdate ();


            MyMessagesUtility.showInformation_insertSuccess ();

        } catch (SQLException ex) {

            MyMessagesUtility.showError_insertNOTSuccess ();
            MyMessagesUtility.showError (ex.getMessage ());
            Logger.getLogger (Derby_dao.class
                    .getName ()).log (Level.SEVERE, null, ex);

        } catch (Exception ex) {

            MyMessagesUtility.showError_insertNOTSuccess ();
            MyMessagesUtility.showError (ex.getMessage ());
            Logger.getLogger (Derby_dao.class
                    .getName ()).log (Level.SEVERE, null, ex);

        }

    }


    public void removeAllRowsInSettingTbl () {

        try {

            try {

                connForDerby.prepareStatement ("Delete From setting").executeUpdate ();

            } catch (SQLException ex) {

                Logger.getLogger (Derby_dao.class.getName ()).log (Level.SEVERE, null, ex);
            }



        } catch (Exception ex) {

            MyMessagesUtility.showError_insertNOTSuccess ();
            MyMessagesUtility.showError (ex.getMessage ());
            Logger.getLogger (Derby_dao.class
                    .getName ()).log (Level.SEVERE, null, ex);

        }

    }


    public void loadSetting () {


        try {
            System.out.println ("Loading driver...");
            ResultSet rset = connForDerby.prepareStatement ("Select * from setting").executeQuery ();

            if (rset == null) {

//                MyMessages.showError ("null");

            } else {
                
 System.out.println ("...");
                
                while (rset.next ()) {
                    System.out.println("rset.getString (\"db_name\")>> "+rset.getString ("db_name"));
                    Setting_pojo.setComPortID (rset.getString ("com_port_id"));
                    Setting_pojo.setDbConnectionType (rset.getString ("db_connection_type"));
                    Setting_pojo.setDbName (rset.getString ("db_name").trim());
                    Setting_pojo.setDbIP (rset.getString ("db_ip"));
                    Setting_pojo.setDbPort (rset.getString ("db_port"));
                    Setting_pojo.setDbODBCName (rset.getString ("db_odbc_name"));
                    Setting_pojo.setDbUserName (rset.getString ("db_user_name"));
                    Setting_pojo.setDbPassword (rset.getString ("db_password"));

                }

            }



        } catch (SQLException ex) {
            MyMessagesUtility.showError (ex.getMessage ());
            Logger.getLogger (Derby_dao.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyMessagesUtility.showError (ex.getMessage ());
            Logger.getLogger (Derby_dao.class.getName ()).log (Level.SEVERE, null, ex);
        }

    }
}
