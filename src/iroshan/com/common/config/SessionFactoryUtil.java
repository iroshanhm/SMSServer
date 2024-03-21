package iroshan.com.common.config;

import iroshan.com.common.derby.pojo.Setting_pojo;
import iroshan.com.common.pojo.CustomerCategoryMainCategory_Deleted;
import iroshan.com.smsserver.entity.AccountBalanceLoanEntity;
import iroshan.com.smsserver.entity.CustCategoryMain;
import iroshan.com.smsserver.entity.CustCategory_ent;
import iroshan.com.smsserver.entity.Customer;
import iroshan.com.smsserver.entity.Customer_entity;
import iroshan.com.smsserver.entity.IncomeExpenceEntity;
import iroshan.com.smsserver.entity.IncomeExpenceSubManualEntity;
import iroshan.com.smsserver.entity.MobileAccountBalances;
import iroshan.com.smsserver.entity.Payment_ent;
import iroshan.com.smsserver.entity.QDailyPayment2;
import iroshan.com.smsserver.entity.QDailyReceipt2;
import iroshan.com.smsserver.entity.QprintIncomeExpence2Entity;
import iroshan.com.smsserver.entity.Receipt_ent;
import iroshan.com.smsserver.entity.SmsCount_Entity;
import iroshan.com.smsserver.entity.SmsReceivedEntity;
import iroshan.com.smsserver.entity.SmsSendEntity;
import iroshan.com.smsserver.entity.VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity;
import iroshan.com.smsserver.entity.ViewSMSJurnal_entity;
import iroshan.com.smsserver.entity.ZINCOME_EXPENCE_SUB_2_entity;
import iroshan.com.smsserver.entity.ZINCOME_EXPENCE_SUB_entity;
import iroshan.com.smsserver.pojo.MessageToMemeberSearch_pojo;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    private static Logger logger = Logger.getLogger(SessionFactoryUtil.class);

    private Boolean subDBAllow = false;
    private static SessionFactory sessionFactory = null;
    private static Session session = null;

    public SessionFactoryUtil() {
        buildSessionFactory();
    }

    public static Session getSession() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }

//        if (session == null)
//        {
//            session = sessionFactory.openSession ();
//        }
//        if (!session.isOpen ())
//        {
//            session = sessionFactory.openSession ();
//        }
        session = sessionFactory.openSession();
        return session;
    }

    public static SessionFactory getSessionFactory() {

//        if (sessionFactory == null)
//        {
//            buildSessionFactory ();
//        }
//        if (sessionFactory.isClosed ())
//        {
//            buildSessionFactory ();
//        }
        buildSessionFactory();

        return sessionFactory;
    }

    public Boolean getTest() {
        return true;
    }

    public static void buildSessionFactory() {

        try {

            Properties properties = getHibernatePropertyForMainDB();
            Configuration configuration = getHibernateConfigarationForMainDB();
            configuration.setProperties(properties);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Sorry.\nDatabase Connection Error.\n" + e, "DB Error.", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void closeSession() {

        if (session != null && session.isOpen()) {
            session.flush();
            session.close();
        }

    }

    private static Properties getHibernatePropertyForMainDB() {

        String dbName = Setting_pojo.getDbName().trim();
        String password = Setting_pojo.getDbPassword().trim();
        String userName = Setting_pojo.getDbUserName().trim();
        String ip = Setting_pojo.getDbIP().trim();
        String port = Setting_pojo.getDbPort().trim();

//        String dbName = "SANASA";
//        String password = "abc";
//        String userName = "sa";
//        String ip = "127.0.0.1";
//        String port = "1449";

        
        
        
        
        
        
        
//COMMENT ON 2019-03-27 - END *************************************************************************************************
//    INSERT ON 2019-03-27 - START ***********************************************************************************************
        Properties property = new Properties();
        property.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2008Dialect");
        property.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        property.setProperty("hibernate.connection.driver_class", "net.sourceforge.jtds.jdbc.Driver");
//        property.setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        property.setProperty ("hibernate.connection.url", "jdbc:jtds:sqlserver://" + ip + ";databaseName=" + dbName);
        property.setProperty("hibernate.connection.url", "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databaseName=" + dbName);
//        property.setProperty("hibernate.connection.url", "jdbc:sqlserver://" + ip + ":" + port + ";databaseName=" + dbName);
        property.setProperty("hibernate.connection.username", userName);
        property.setProperty("hibernate.connection.password", password);
//        property.setProperty("hibernate.show_sql", "true");
        property.setProperty("hibernate.show_sql", "true");
        property.setProperty("hibernate.current_session_context_class", "thread");
//    INSERT ON 2019-03-27 - END *************************************************************************************************

        return property;
    }

    private static Configuration getHibernateConfigarationForMainDB() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(CustCategoryMain.class);
        configuration.addAnnotatedClass(IncomeExpenceEntity.class);
        configuration.addAnnotatedClass(CustCategoryMain.class);
//        configuration.addAnnotatedClass (ZIncomeExpenceSubEntity.class);
        configuration.addAnnotatedClass(QprintIncomeExpence2Entity.class);
        configuration.addAnnotatedClass(IncomeExpenceSubManualEntity.class);
        configuration.addAnnotatedClass(CustomerCategoryMainCategory_Deleted.class);
        configuration.addAnnotatedClass(CustCategory_ent.class);
        configuration.addAnnotatedClass(Payment_ent.class);
        configuration.addAnnotatedClass(QDailyPayment2.class);
        configuration.addAnnotatedClass(QDailyReceipt2.class);
        configuration.addAnnotatedClass(Receipt_ent.class);
        configuration.addAnnotatedClass(SmsReceivedEntity.class);
        configuration.addAnnotatedClass(SmsSendEntity.class);
        configuration.addAnnotatedClass(Customer_entity.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(ZINCOME_EXPENCE_SUB_entity.class);
        configuration.addAnnotatedClass(AccountBalanceLoanEntity.class);
        configuration.addAnnotatedClass(ViewSMSJurnal_entity.class);
        configuration.addAnnotatedClass(VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.class);
        configuration.addAnnotatedClass(SmsCount_Entity.class);
//        configuration.addAnnotatedClass(IncomeExpenceEntity.class);

        return configuration;
    }

}
