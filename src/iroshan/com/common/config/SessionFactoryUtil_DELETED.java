package iroshan.com.common.config;


import iroshan.com.common.derby.pojo.Setting_pojo;
import iroshan.com.common.pojo.CustomerCategoryMainCategory_Deleted;
import iroshan.com.smsserver.entity.CustCategoryMain;
import iroshan.com.smsserver.entity.CustCategory_ent;
import iroshan.com.smsserver.entity.Customer_entity;
import iroshan.com.smsserver.entity.IncomeExpenceEntity;
import iroshan.com.smsserver.entity.IncomeExpenceSubManualEntity;
import iroshan.com.smsserver.entity.Payment_ent;
import iroshan.com.smsserver.entity.QDailyPayment2;
import iroshan.com.smsserver.entity.QDailyReceipt2;
import iroshan.com.smsserver.entity.QprintIncomeExpence2Entity;
import iroshan.com.smsserver.entity.Receipt_ent;
import iroshan.com.smsserver.entity.SmsReceivedEntity;
import iroshan.com.smsserver.entity.SmsSendEntity;
import iroshan.com.smsserver.entity.ZINCOME_EXPENCE_SUB_entity;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil_DELETED
{


    private static Logger logger = Logger.getLogger (SessionFactoryUtil.class);


    private Boolean subDBAllow = false;
    private static SessionFactory sessionFactory = null;
    private static Session session = null;





    public SessionFactoryUtil_DELETED ()
    {
        buildSessionFactory ();
    }


    public static Session getSession ()
    {

        if (session == null)
        {
            session = getSessionFactory ().openSession ();
        }

        if (!session.isOpen ())
        {
            session = getSessionFactory ().openSession ();
        }


        if (!session.isConnected ())
        {
            session = getSessionFactory ().openSession ();
        }
        return session;
    }


    public static SessionFactory getSessionFactory ()
    {

        if (sessionFactory == null)
        {
            buildSessionFactory ();
        }
        if (sessionFactory.isClosed ())
        {
            buildSessionFactory ();
        }

        
        return sessionFactory;
    }



    public Boolean getTest ()
    {
        return true;
    }



    public static void buildSessionFactory ()
    {


        try
        {

            Properties properties = getHibernatePropertyForMainDB ();
            Configuration configuration = getHibernateConfigarationForMainDB ();
            configuration.setProperties (properties);


            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder ().applySettings (configuration.getProperties ());
            sessionFactory = configuration.buildSessionFactory (builder.build ());


        } catch (Exception e)
        {
            logger.error (e);
            e.printStackTrace ();
            JOptionPane.showMessageDialog (null, "Sorry.\nDatabase Connection Error.\n" + e, "DB Error.", JOptionPane.ERROR_MESSAGE);

        }


    }






    public void closeSession ()
    {

        if (session != null && session.isOpen ())
        {
            session.flush ();
            session.close ();
        }

    }



    private static Properties getHibernatePropertyForMainDB ()
    {


//        DBConnectionProperty.getConnectionType ();
        String dbName = Setting_pojo.getDbName ().trim ();
        String password = Setting_pojo.getDbPassword ().trim ();
        String userName = Setting_pojo.getDbUserName ().trim ();
//        DBConnectionProperty.getHasDBProperty ();
        String ip = Setting_pojo.getDbIP ().trim ();
//        String oDBCName = Setting_pojo.getDbODBCName ().trim ();
        String port = Setting_pojo.getDbPort ().trim ();


        Properties property = new Properties ();
        property.setProperty ("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        property.setProperty ("hibernate.connection.driver_class", "net.sourceforge.jtds.jdbc.Driver");
        property.setProperty ("hibernate.connection.url", "jdbc:jtds:sqlserver://" + ip + ";databaseName=" + dbName);
        property.setProperty ("hibernate.connection.username", userName);
        property.setProperty ("hibernate.connection.password", password);
        property.setProperty ("hibernate.show_sql", "false");
        property.setProperty ("hibernate.current_session_context_class", "thread");

//        Properties property = new Properties ();
//        property.setProperty ("hibernate.connection.url", "jdbc:sqlserver://127.0.0.1;databaseName=DB_MAIN");
//        property.setProperty ("hibernate.connection.username", "sa");
//        property.setProperty ("hibernate.connection.password", "abc");
//        property.setProperty ("hibernate.show_sql", "true");

        return property;
    }





    private static Configuration getHibernateConfigarationForMainDB ()
    {

        Configuration configuration = new Configuration ();
        configuration.addAnnotatedClass (CustCategoryMain.class);
//        configuration.addAnnotatedClass (ZIncomeExpenceSubEntity.class);
        configuration.addAnnotatedClass (QprintIncomeExpence2Entity.class);
        configuration.addAnnotatedClass (IncomeExpenceSubManualEntity.class);
        configuration.addAnnotatedClass (CustomerCategoryMainCategory_Deleted.class);
        configuration.addAnnotatedClass (CustCategory_ent.class);
        configuration.addAnnotatedClass (Payment_ent.class);
        configuration.addAnnotatedClass (QDailyPayment2.class);
        configuration.addAnnotatedClass (QDailyReceipt2.class);
        configuration.addAnnotatedClass (Receipt_ent.class);
        configuration.addAnnotatedClass (SmsReceivedEntity.class);
        configuration.addAnnotatedClass (SmsSendEntity.class);
        configuration.addAnnotatedClass (Customer_entity.class);
        configuration.addAnnotatedClass (ZINCOME_EXPENCE_SUB_entity.class);
        configuration.addAnnotatedClass (IncomeExpenceEntity.class);



        return configuration;
    }

}
