package iroshan.com.common.config;





import iroshan.com.common.derby.pojo.Setting_pojo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DBConnection {

    private static DBConnection connect = null;
//    private static Connection conn;

    public static Connection getConnection () {

        boolean aFlag = true;

        Connection conn = null;
        String connectionUrl = null;

        if (aFlag) {

            if (Setting_pojo.getDbConnectionType ().trim ().equalsIgnoreCase (Setting_pojo.ConTypeTCPIP.ODBC.toString ())) {

                try {

                    connectionUrl = "jdbc:odbc:" + Setting_pojo.getDbODBCName ().trim ();
                    Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
                    conn = DriverManager.getConnection (connectionUrl);

                } catch (SQLException ex) {
                    aFlag = false;
                    Logger.getLogger (DBConnection.class.getName ()).log (Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog (null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (ClassNotFoundException ex) {
                    aFlag = false;
                    Logger.getLogger (DBConnection.class.getName ()).log (Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog (null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }





            if (Setting_pojo.getDbConnectionType ().trim ().equalsIgnoreCase (Setting_pojo.ConTypeTCPIP.TCPIP.toString ())) {

                try {

                    connectionUrl = "jdbc:jtds:sqlserver://" + Setting_pojo.getDbIP ().trim () + ":" + Setting_pojo.getDbPort ().trim () + ";"
                            + "databaseName=" + Setting_pojo.getDbName ().trim () + ";user=" + Setting_pojo.getDbUserName ().trim () + ";password=" + Setting_pojo.getDbPassword ().trim () + ";";
                    Class.forName ("net.sourceforge.jtds.jdbc.Driver");
                    conn = DriverManager.getConnection (connectionUrl);

                } catch (SQLException ex) {
                    aFlag = false;
                    JOptionPane.showMessageDialog (null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger (DBConnection.class.getName ()).log (Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    aFlag = false;
                    JOptionPane.showMessageDialog (null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger (DBConnection.class.getName ()).log (Level.SEVERE, null, ex);
                }

            }
        }


        if (conn == null) {
            aFlag = false;
            JOptionPane.showMessageDialog (null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
           
        }



        return conn;
    }

    
    
    
    
    
    
    
    protected static void closeConnection () {
        if (connect != null) {
            connect = null;
            System.gc ();
        }
    }

//    public static boolean loadConnection() {
// 
//        System.out.println("Load Connection Start ...");
//        
//        
//        boolean aFlag = true;
//        
//        Connection conn = null;
//        String connectionUrl = null;
//
//        if (aFlag) {
//            
//            if (SettingLoader.getConType_ODBC() == 1) {
//                
//                try {
//                
//                    connectionUrl = "jdbc:odbc:" + SettingLoader.getOdbcName();
//                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//                    conn = DriverManager.getConnection(connectionUrl);
//                    
//                } catch (SQLException ex) {
//                    aFlag = false;
//                    JFMessage.jfMessage.dispose();
//                    JOptionPane.showMessageDialog(null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    aFlag = false;
//                    JFMessage.jfMessage.dispose();
//                    JOptionPane.showMessageDialog(null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//
//            if (SettingLoader.getConType_TCP_IP() == 1) {
//                
//                try {
//                    
//                    connectionUrl = "jdbc:sqlserver://" + SettingLoader.getIp() + ":" + SettingLoader.getPort() + ";"
//                            + "databaseName=" + SettingLoader.getDbName() + ";user=" + SettingLoader.getDbUserName() + ";password=" + SettingLoader.getDbPassword() + ";";
//                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                    conn = DriverManager.getConnection(connectionUrl);
//                    
//                } catch (SQLException ex) {
//                    aFlag = false;
//                    JFMessage.jfMessage.dispose();
//                    JOptionPane.showMessageDialog(null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    aFlag = false;
//                    JFMessage.jfMessage.dispose();
//                    JOptionPane.showMessageDialog(null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        }
//
//
//        if (conn == null) {
//            aFlag = false;
//            JFMessage.jfMessage.dispose();
//            JOptionPane.showMessageDialog(null, "DB connection can not be prepared.", "Error", JOptionPane.ERROR_MESSAGE);
//        }else{
//            System.out.println("conn Success.");
//        }

//        System.out.println("Load Connection End ...");

//        return aFlag;
//    }
}
