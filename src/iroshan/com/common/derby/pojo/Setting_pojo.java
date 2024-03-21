/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.common.derby.pojo;

/**
 *
 * @author Iroshan
 */
public class Setting_pojo {

    public enum ConTypeTCPIP {

        ODBC, TCPIP;

        @Override
        public String toString () {
            String s = super.toString ();
            return s;
        }
    }

    public enum HasDBProperty {

        Y, N;

        @Override
        public String toString () {
            String s = super.toString ();
            return s;
        }
    }


    private static String comPortID = "";
    private static String dbConnectionType = "";
    private static String dbODBCName = "";
    private static String dbIP = "";
    private static String dbPort = "";
    private static String dbName = "";
    private static String dbUserName = "";
    private static String dbPassword = "";

    public static String getComPortID () {
        return comPortID;
    }

    public static void setComPortID (String comPortID) {
        Setting_pojo.comPortID = comPortID;
    }

    public static String getDbConnectionType () {
        return dbConnectionType;
    }

    public static void setDbConnectionType (String dbConnectionType) {
        Setting_pojo.dbConnectionType = dbConnectionType;
    }

    public static String getDbODBCName () {
        return dbODBCName;
    }

    public static void setDbODBCName (String dbODBCName) {
        Setting_pojo.dbODBCName = dbODBCName;
    }

    public static String getDbIP () {
        return dbIP;
    }

    public static void setDbIP (String dbIP) {
        Setting_pojo.dbIP = dbIP;
    }

    public static String getDbPort () {
        return dbPort;
    }

    public static void setDbPort (String dbPort) {
        Setting_pojo.dbPort = dbPort;
    }

    public static String getDbName () {
        return dbName;
    }

    public static void setDbName (String dbName) {
        Setting_pojo.dbName = dbName;
    }

    public static String getDbUserName () {
        return dbUserName;
    }

    public static void setDbUserName (String dbUserName) {
        Setting_pojo.dbUserName = dbUserName;
    }

    public static String getDbPassword () {
        return dbPassword;
    }

    public static void setDbPassword (String dbPassword) {
        Setting_pojo.dbPassword = dbPassword;
    }


}
