/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.DBConnection;
import iroshan.com.common.derby.pojo.Setting_pojo;
import iroshan.com.common.derby.service.Setting_service;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iroshan
 */
public class AddNewFlagColumns {



    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {

        new Setting_service ().loadSetting ();

        try {

            String jsonString = "{'table':";

            String[] types = {"TABLE"};
            String dbFrom = Setting_pojo.getDbName ();
            Connection conFrom = DBConnection.getConnection ();
            conFrom.prepareStatement ("USE " + dbFrom).executeUpdate ();

            DatabaseMetaData metaDetaFromDB = conFrom.getMetaData ();
            ResultSet resultSet = metaDetaFromDB.getTables (null, null, "%", types);


            int startRowInt = 0;
            while (resultSet.next ()) {
                String tableName = resultSet.getString (3);
                if ((!tableName.startsWith ("z")) && (!tableName.startsWith ("Z"))) {

                    ResultSet rsetFrom = conFrom.prepareStatement ("SELECT * FROM " + tableName + "").executeQuery ();
                    ResultSetMetaData metaDetaTableFrom = rsetFrom.getMetaData ();
                    int numberOfColumns = metaDetaTableFrom.getColumnCount ();
                    String lastColumnName = metaDetaTableFrom.getColumnName (numberOfColumns);


                    System.out.println ("Executing table -> " + tableName);
                    System.out.println ("lastColumnName -> " + lastColumnName);


                    if (lastColumnName.equalsIgnoreCase ("is_sms_sent")) {
                        conFrom.prepareStatement ("UPDATE " + tableName + " SET is_sms_sent = 'Y'").execute ();
                    }

//                    String query1 = "aLTER TABLE " + tableName + " ADD is_sync VARCHAR(10) DEFAULT 'N' NOT NULL";
//                    int res = conFrom.prepareStatement (query1).executeUpdate ();
//                    System.out.println ("res ->"+res);
//                            conFrom.prepareStatement ("ALTER TABLE '"+tableName.trim ()+"' ADD (COLUMN 'is_sync' VARCHAR(10) DEFAULT 'N' NOT NULL AFTER '"+lastColumnName+"', COLUMN 'is_sync_success' VARCHAR(10) DEFAULT N NOT NULL AFTER 'is_sync')").executeQuery ();
                    //                    conFrom.prepareStatement ("ALTER TABLE " + tableName + " ADD (is_sync varchar(50), is_sync_success varchar(50))").executeQuery ();

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger (AddNewFlagColumns.class.getName ()).log (Level.SEVERE, null, ex);
        }

    }

}
