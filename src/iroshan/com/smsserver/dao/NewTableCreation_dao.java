/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyMessagesUtility;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Iroshan
 */
public class NewTableCreation_dao
{

    public void createSMSReceiveTable ()
    {

        Session sessionForMainDB;
        Transaction trForMainDB = null;

        try
        {
            sessionForMainDB = SessionFactoryUtil.getSession ();
            trForMainDB = sessionForMainDB.beginTransaction ();

            String sql = "CREATE TABLE [SMS_RECEIVED]"
                    + "([idAuto] [int] IDENTITY(1,1) NOT NULL,"
                    + "	[received_id] [int] NOT NULL,"
                    + "	[phone_no] [nchar](10) NULL,"
                    + "	[keyword] [nchar](10) NULL,"
                    + "	[account_no] [nchar](10) NULL,"
                    + "	[password] [nchar](10) NULL,"
                    + "	[received_date] [datetime] NULL, message [varchar](255) NULL)";

            SQLQuery sQLQuery = sessionForMainDB.createSQLQuery (sql);
            sQLQuery.executeUpdate ();

            trForMainDB.commit ();
            trForMainDB = null;
            MyMessagesUtility.showInformation ("Table Creation 'SMS_RECEIVED' Success.");

        } catch (Exception e)
        {
            trForMainDB.rollback ();
            trForMainDB = null;
            e.printStackTrace ();
            MyMessagesUtility.showError ("Table Creation 'SMS_RECEIVED' NOT Success.\n" + e.getMessage ());
        } finally
        {

        }

    }


    public void createSMSSendTable ()
    {

        Session sessionForMainDB;
        Transaction trForMainDB = null;

        try
        {
            sessionForMainDB = SessionFactoryUtil.getSession ();
            trForMainDB = sessionForMainDB.beginTransaction ();

            String sql = "CREATE TABLE [SMS_SENT]"
                    + "([idAuto] [int] IDENTITY(1,1) NOT NULL,"
                    + "	[msg_type] [varchar](256) NULL,"
                    + "	[received_id] [int] NULL,"
                    + "	[recipient] [varchar](256) NULL,"
                    + "	[message] [varchar](256) NULL,"
                    + "	[sent_date] [datetime] NULL,"
                    + "	[msg_status] [varchar](256) NULL)";

            SQLQuery sQLQuery = sessionForMainDB.createSQLQuery (sql);
            sQLQuery.executeUpdate ();

            trForMainDB.commit ();
            trForMainDB = null;
            MyMessagesUtility.showInformation ("Table Creation 'SMS_SENT' Success.");
        } catch (Exception e)
        {
            trForMainDB.rollback ();
            trForMainDB = null;
            e.printStackTrace ();
            MyMessagesUtility.showError ("Table Creation 'SMS_SENT' NOT Success.\n" + e.getMessage ());
        } finally
        {

        }

    }






    public void alterIsSMSSentColumnToReceiptTable ()
    {

        Session sessionForMainDB = null;
        Transaction trForMainDB = null;



        try
        {
            sessionForMainDB = SessionFactoryUtil.getSession ();
            trForMainDB = sessionForMainDB.beginTransaction ();
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        }




        try
        {
            String sqlDrop = "ALTER TABLE RECEIPT DROP IS_SMS_SENT";
            SQLQuery sQLQueryAlterDrop = sessionForMainDB.createSQLQuery (sqlDrop);
            sQLQueryAlterDrop.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE RECEIPT DROP COLUMN IS_SMS_SENT SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        }



        try
        {
            String sql = "ALTER TABLE RECEIPT ADD IS_SMS_SENT VARCHAR(10)";
            SQLQuery sQLQueryAlter = sessionForMainDB.createSQLQuery (sql);
            sQLQueryAlter.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE RECEIPT ADD IS_SMS_SENT VARCHAR(10) SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        }



        try
        {
            String sqlUpdate = "UPDATE RECEIPT SET IS_SMS_SENT = 'Y'";
            SQLQuery sQLQueryUpdate = sessionForMainDB.createSQLQuery (sqlUpdate);
            sQLQueryUpdate.executeUpdate ();
            MyMessagesUtility.showInformation ("UPDATE RECEIPT SET IS_SMS_SENT = 'Y' SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        }




        trForMainDB.commit ();
        trForMainDB = null;
    }

    public void alterIsSMSSentColumnToPaymentTable ()
    {

        Session sessionForMainDB = null;
        Transaction trForMainDB = null;

        try
        {
            sessionForMainDB = SessionFactoryUtil.getSession ();
            trForMainDB = sessionForMainDB.beginTransaction ();
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE PAYMENT NOT SUCCESS.\n" + e.getMessage ());
        }



        try
        {
            String sqlDrop = "ALTER TABLE PAYMENT DROP IS_SMS_SENT";
            SQLQuery sQLQueryAlterDrop = sessionForMainDB.createSQLQuery (sqlDrop);
            sQLQueryAlterDrop.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE PAYMENT DROP COLUMN IS_SMS_SENT SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE PAYMENT NOT SUCCESS.\n" + e.getMessage ());
        }

        try
        {
            String sql = "ALTER TABLE PAYMENT ADD IS_SMS_SENT VARCHAR(10)";
            SQLQuery sQLQueryAlter = sessionForMainDB.createSQLQuery (sql);
            sQLQueryAlter.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE PAYMENT ADD IS_SMS_SENT VARCHAR(10) SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE PAYMENT NOT SUCCESS.\n" + e.getMessage ());
        }

        try
        {
            String sqlUpdate = "UPDATE PAYMENT SET IS_SMS_SENT = 'Y'";
            SQLQuery sQLQueryUpdate = sessionForMainDB.createSQLQuery (sqlUpdate);
            sQLQueryUpdate.executeUpdate ();
            MyMessagesUtility.showInformation ("UPDATE PAYMENT SET IS_SMS_SENT = 'Y' SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE PAYMENT NOT SUCCESS.\n" + e.getMessage ());
        }

        trForMainDB.commit ();
        trForMainDB = null;
    }






    public void alterZIncomeExpencesSub ()
    {
        Session sessionForMainDB = null;
        Transaction trForMainDB = null;

        try
        {
            sessionForMainDB = SessionFactoryUtil.getSession ();
            trForMainDB = sessionForMainDB.beginTransaction ();
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER ZINCOME_EXPENCE_SUB NOT SUCCESS.\n" + e.getMessage ());
        }



        try
        {
            String sqlUpdate = "ALTER VIEW ZINCOME_EXPENCE_SUB AS SELECT INCOME_EXPENCE.IE_SUB_TYPE, INCOME_EXPENCE.IE_CARD_TYPE, INCOME_EXPENCE_SUB.IE_CODE, INCOME_EXPENCE_SUB.IE_FLAG, "
                    + "INCOME_EXPENCE_SUB.IE_DESC, INCOME_EXPENCE_SUB.WHO1, INCOME_EXPENCE_SUB.WHEN1, INCOME_EXPENCE_SUB.IE_MAIN, "
                    + "INCOME_EXPENCE_SUB.IE_MEMBER, INCOME_EXPENCE_SUB.IE_INT_TYPE, INCOME_EXPENCE_SUB.IE_INT_VAL, INCOME_EXPENCE_SUB.IE_INT_TYPE2, "
                    + "INCOME_EXPENCE_SUB.IE_DATES, INCOME_EXPENCE_SUB.IE_TAX, INCOME_EXPENCE_SUB.IE_TAX_TYPE, INCOME_EXPENCE_SUB.IE_VALUE, "
                    + "INCOME_EXPENCE_SUB.IE_TERM, INCOME_EXPENCE_SUB.IE_NO_TERM, INCOME_EXPENCE_SUB.IE_FROM, INCOME_EXPENCE_SUB.IE_TO, "
                    + "INCOME_EXPENCE_SUB.IE_INT_METHOD, INCOME_EXPENCE_SUB.IE_MINIMUM, INCOME_EXPENCE_SUB.IE_PAYDATE, INCOME_EXPENCE_SUB.IE_TOTINTEREST, "
                    + "INCOME_EXPENCE_SUB.IE_TOTVALUE, INCOME_EXPENCE_SUB.IE_RMAX, INCOME_EXPENCE_SUB.IE_PMAX, INCOME_EXPENCE_SUB.IE_INT_TOTAL, "
                    + "INCOME_EXPENCE_SUB.IE_INT_TERM, INCOME_EXPENCE_SUB.IE_TOTAL_TERM, INCOME_EXPENCE_SUB.IE_NARATION, INCOME_EXPENCE_SUB.IE_GIVE_VALUE, "
                    + "INCOME_EXPENCE_SUB.IE_GIVE_DATE, INCOME_EXPENCE_SUB.IE_ACTIVE, INCOME_EXPENCE_SUB.IE_OPEN1, INCOME_EXPENCE_SUB.IE_OPEN2, "
                    + "INCOME_EXPENCE_SUB.IE_OPEN3, INCOME_EXPENCE_SUB.IE_GRACE, INCOME_EXPENCE_SUB.IE_CLOSE_DATE, INCOME_EXPENCE_SUB.IE_OPEN_DATE, "
                    + "INCOME_EXPENCE_SUB.IE_GRACE2, INCOME_EXPENCE.IE_INT_PAYMENT, INCOME_EXPENCE.IE_DESC AS IE_DESC2, INCOME_EXPENCE_SUB.IE_IO, "
                    + "INCOME_EXPENCE_SUB.IE_CON_PAY, INCOME_EXPENCE_SUB.IE_INT_INCREASE, INCOME_EXPENCE_SUB.IE_MAIN_NUMBER_CODE, "
                    + "INCOME_EXPENCE_SUB.IE_OPEN_LOAN, INCOME_EXPENCE_SUB.RN, INCOME_EXPENCE_SUB.IE_OPEN22, INCOME_EXPENCE_SUB.IE_OPEN32, "
                    + "INCOME_EXPENCE.IE_ACCOUNT_TYPE, INCOME_EXPENCE.IE_ACCOUNT_DIVISION, INCOME_EXPENCE.IE_INT_AFTER, INCOME_EXPENCE_SUB.IE_PAYMENT, "
                    + "INCOME_EXPENCE_SUB.IE_PAY_WAY, INCOME_EXPENCE_SUB.IE_PAY_TERM, INCOME_EXPENCE_SUB.IE_PAY_START, INCOME_EXPENCE_SUB.IE_LOAN_TYPE, "
                    + "INCOME_EXPENCE.IE_INCOME_TYPE, INCOME_EXPENCE.IE_TRTYPE11, INCOME_EXPENCE.IE_TRTYPE21, INCOME_EXPENCE.IE_TRTYPE12, "
                    + "INCOME_EXPENCE.IE_TRTYPE13, INCOME_EXPENCE.IE_TRTYPE22, INCOME_EXPENCE.IE_TRTYPE23, INCOME_EXPENCE.IE_TRTYPE31, INCOME_EXPENCE.IE_TRTYPE41,"
                    + " INCOME_EXPENCE.IE_TRTYPE32, INCOME_EXPENCE.IE_TRTYPE42, INCOME_EXPENCE.IE_CHQ_TRTYPE11, INCOME_EXPENCE.IE_CHQ_TRTYPE12, "
                    + "INCOME_EXPENCE.IE_CHQ_TRTYPE13, INCOME_EXPENCE.IE_CHQ_TRTYPE21, INCOME_EXPENCE.IE_CHQ_TRTYPE22, INCOME_EXPENCE.IE_CHQ_TRTYPE23, "
                    + "INCOME_EXPENCE.IE_OTHER_TRTYPE11, INCOME_EXPENCE.IE_OTHER_TRTYPE12, INCOME_EXPENCE.IE_OTHER_TRTYPE13, INCOME_EXPENCE.IE_OTHER_TRTYPE21, "
                    + "INCOME_EXPENCE.IE_OTHER_TRTYPE22, INCOME_EXPENCE.IE_OTHER_TRTYPE23, INCOME_EXPENCE.IE_INCOME_TYPE2, "
                    + "INCOME_EXPENCE_SUB.IE_HOW_STARTING_POLY, INCOME_EXPENCE_SUB.IE_HOW3, INCOME_EXPENCE.IE_IN_OUT, INCOME_EXPENCE_SUB.IE_NON_INT_VALUE, "
                    + "INCOME_EXPENCE.IE_MIN_INT_DAYS, INCOME_EXPENCE_SUB.IE_OPEN12, INCOME_EXPENCE_SUB.IE_VALUE_PAID, INCOME_EXPENCE_SUB.IE_CRDID_IE, "
                    + "INCOME_EXPENCE_SUB.IE_DEBIT_IE, INCOME_EXPENCE_SUB.IE_DEBIT_IE_VALUE, INCOME_EXPENCE_SUB.IE_DEBIT_IE_DAY, INCOME_EXPENCE.IE_INT_TYPE2_MULTY, "
                    + "INCOME_EXPENCE_SUB.IE_INTEREST_TERMS, INCOME_EXPENCE.IE_PAWN, INCOME_EXPENCE.IE_GRACE_TO_PAYMENT, INCOME_EXPENCE.IE_MTYPE, "
                    + "CUST_CATEGORY.CC_MAIN AS CC_MAIN_2, CUSTOMER.CM_CODE AS CM_CODE_2, CUSTOMER.CM_DESC AS CM_DESC_2, CUSTOMER.CM_TELE, CUSTOMER.CM_OFFICE_NO, "
                    + "INCOME_EXPENCE.IE_GRACE_TYPE, INCOME_EXPENCE_SUB.IE_FUND, INCOME_EXPENCE.IE_REPAYMENT_DIS, INCOME_EXPENCE.IE_NON_INT_RATIO, "
                    + "INCOME_EXPENCE.IE_NON_INT_RATIO2, INCOME_EXPENCE_SUB.IE_BANK_ACCOUNT, INCOME_EXPENCE_SUB.IE_BRANCH, INCOME_EXPENCE_SUB.IE_MANUAL_TERM, "
                    + "INCOME_EXPENCE_SUB.IE_OFFICER, INCOME_EXPENCE_SUB.IE_HOLDER, INCOME_EXPENCE_SUB.IE_RECEIPT, INCOME_EXPENCE_SUB.IE_PRINT, "
                    + "INCOME_EXPENCE.IE_MONTH_END, INCOME_EXPENCE_SUB.IE_ORGINAL, INCOME_EXPENCE_SUB.IE_N_L_G_D, INCOME_EXPENCE_SUB.IE_DOWN_PAYMENT, "
                    + "INCOME_EXPENCE.IE_MANUAL_TERMS_ESSENTIAL, INCOME_EXPENCE.IE_TYPE_OF_EQUAL_TERMS, INCOME_EXPENCE_SUB.IE_PRE_DEFINITION_CODE, "
                    + "INCOME_EXPENCE_SUB.IE_APPROVED, INCOME_EXPENCE_SUB.IE_SPECIAL_TERM, INCOME_EXPENCE.IE_STAMP_DUTY, INCOME_EXPENCE.IE_WHEN_DOUBLE_ENTRY, "
                    + "CUST_CATEGORY_MAIN.CC_OFFICER, INCOME_EXPENCE_SUB.IE_LOAN_DEDUCT_CODE, INCOME_EXPENCE_SUB.IE_LAST_DAY_END_DATE, "
                    + "INCOME_EXPENCE_SUB.IE_PRINT2, INCOME_EXPENCE_SUB.IE_PRINT3, INCOME_EXPENCE_SUB.IE_PRINT2_DONE, INCOME_EXPENCE_SUB.IE_LOAN_STATUS, "
                    + "INCOME_EXPENCE_SUB.IE_LOAN_STATUS_DATE "
                    + "FROM CUSTOMER LEFT OUTER JOIN "
                    + "CUST_CATEGORY LEFT OUTER JOIN "
                    + "CUST_CATEGORY_MAIN ON CUST_CATEGORY.CC_MAIN = CUST_CATEGORY_MAIN.CC_CODE ON CUSTOMER.CM_CATEGORY = CUST_CATEGORY.CC_CODE RIGHT OUTER JOIN INCOME_EXPENCE_SUB INNER JOIN INCOME_EXPENCE ON INCOME_EXPENCE_SUB.IE_MAIN = INCOME_EXPENCE.IE_CODE ON CUSTOMER.CM_CODE = INCOME_EXPENCE_SUB.IE_MEMBER";


            SQLQuery sQLQueryUpdate = sessionForMainDB.createSQLQuery (sqlUpdate);
            sQLQueryUpdate.executeUpdate ();
            MyMessagesUtility.showInformation ("UPDATE ZINCOME_EXPENCE_SUB SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER ZINCOME_EXPENCE_SUB NOT SUCCESS.\n" + e.getMessage ());
        }

        trForMainDB.commit ();
        trForMainDB = null;
    }






    public void alterIsSMSSentReceiptTable ()
    {

        Session sessionForMainDB = null;
        Transaction trForMainDB = null;



        try
        {
            sessionForMainDB = SessionFactoryUtil.getSession ();
            trForMainDB = sessionForMainDB.beginTransaction ();
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        }




        try
        {
            String sqlDrop = "ALTER TABLE RECEIPT DROP IS_SMS_SENT";
            SQLQuery sQLQueryAlterDrop = sessionForMainDB.createSQLQuery (sqlDrop);
            sQLQueryAlterDrop.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE RECEIPT DROP COLUMN IS_SMS_SENT SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        }



        try
        {
            String sql = "ALTER TABLE RECEIPT ADD IS_SMS_SENT VARCHAR(10)";
            SQLQuery sQLQueryAlter = sessionForMainDB.createSQLQuery (sql);
            sQLQueryAlter.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE RECEIPT ADD IS_SMS_SENT VARCHAR(10) SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        }



        try
        {
            String sqlUpdate = "UPDATE RECEIPT SET IS_SMS_SENT = 'Y'";
            SQLQuery sQLQueryUpdate = sessionForMainDB.createSQLQuery (sqlUpdate);
            sQLQueryUpdate.executeUpdate ();
            MyMessagesUtility.showInformation ("UPDATE RECEIPT SET IS_SMS_SENT = 'Y' SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        }




        trForMainDB.commit ();
        trForMainDB = null;
    }

    public void alterIsSMSSentPaymentTable ()
    {

        Session sessionForMainDB = null;
        Transaction trForMainDB = null;

        try
        {
            sessionForMainDB = SessionFactoryUtil.getSession ();
            trForMainDB = sessionForMainDB.beginTransaction ();
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE PAYMENT NOT SUCCESS.\n" + e.getMessage ());
        }



        try
        {
            String sqlDrop = "ALTER TABLE PAYMENT DROP IS_SMS_SENT";
            SQLQuery sQLQueryAlterDrop = sessionForMainDB.createSQLQuery (sqlDrop);
            sQLQueryAlterDrop.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE PAYMENT DROP COLUMN IS_SMS_SENT SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE PAYMENT NOT SUCCESS.\n" + e.getMessage ());
        }

        try
        {
            String sql = "ALTER TABLE PAYMENT ADD IS_SMS_SENT VARCHAR(10)";
            SQLQuery sQLQueryAlter = sessionForMainDB.createSQLQuery (sql);
            sQLQueryAlter.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE PAYMENT ADD IS_SMS_SENT VARCHAR(10) SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE PAYMENT NOT SUCCESS.\n" + e.getMessage ());
        }

        try
        {
            String sqlUpdate = "UPDATE PAYMENT SET IS_SMS_SENT = 'Y'";
            SQLQuery sQLQueryUpdate = sessionForMainDB.createSQLQuery (sqlUpdate);
            sQLQueryUpdate.executeUpdate ();
            MyMessagesUtility.showInformation ("UPDATE PAYMENT SET IS_SMS_SENT = 'Y' SUCCESS.");
        } catch (Exception e)
        {
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE PAYMENT NOT SUCCESS.\n" + e.getMessage ());
        }

        trForMainDB.commit ();
        trForMainDB = null;
    }






    public void alterFT_DTAIL ()
    {
        Session session = null;
        Transaction tx = null;

        try
        {
            session = SessionFactoryUtil.getSession ();
            tx = session.beginTransaction ();


            String sqlDrop = "ALTER TABLE FT_DTAIL ADD IS_SMS_SENT VARCHAR(10)";

            SQLQuery sQLQueryAlterDrop = session.createSQLQuery (sqlDrop);
            sQLQueryAlterDrop.executeUpdate ();
            MyMessagesUtility.showInformation ("ALTER TABLE FT_DTAIL is Success.");

            tx.commit ();

        } catch (Exception ex)
        {
            if (tx != null)
            {
                tx.rollback ();
            }

            ex.printStackTrace ();
            MyMessagesUtility.showError (ex.toString ());

        } finally
        {
            if (session != null)
            {
//                session.close ();
            }
        }


    }





    public void createVIEW_SMS_JURNAL ()
    {
        Session session = null;
        Transaction tx = null;

        try
        {
            session = SessionFactoryUtil.getSession ();
            tx = session.beginTransaction ();

            
            
//***** DROP VIEW **********************
            String sqlDrop = "DROP VIEW VIEW_SMS_JURNAL";

            SQLQuery sQLQueryAlterDrop = session.createSQLQuery (sqlDrop);
            sQLQueryAlterDrop.executeUpdate ();

            
            
//***** CREAT VIEW *********************
            String sqlCreate = "CREATE VIEW VIEW_SMS_JURNAL AS SELECT FT_DTAIL.FD_CODE, FT_DTAIL.FD_VALUE, FT_DTAIL.FD_RN, FT_DTAIL.FD_TYPE, FT_DTAIL.FD_TOMEMBER, FT_DTAIL.FD_TOEXPENCE, INCOME_EXPENCE.IE_SUB_TYPE, INCOME_EXPENCE.IE_CODE, FT_DTAIL.WHEN1, FT_DTAIL.IS_SMS_SENT, CUSTOMER.CM_DESC, CUSTOMER.CM_TELE, CUSTOMER.CM_OFFICE_NO, CUST_CATEGORY.CC_CODE, CUST_CATEGORY.CC_NAME, CUST_CATEGORY_MAIN.CC_CODE AS CENTER_CODE, CUST_CATEGORY_MAIN.CC_NAME AS CENTER_NAME, FFROM_TTO.FT_DATE FROM FT_DTAIL INNER JOIN INCOME_EXPENCE_SUB ON FT_DTAIL.FD_TOEXPENCE = INCOME_EXPENCE_SUB.IE_CODE INNER JOIN INCOME_EXPENCE ON INCOME_EXPENCE_SUB.IE_MAIN = INCOME_EXPENCE.IE_CODE INNER JOIN CUST_CATEGORY_MAIN INNER JOIN CUST_CATEGORY ON CUST_CATEGORY_MAIN.CC_CODE = CUST_CATEGORY.CC_MAIN INNER JOIN CUSTOMER ON CUST_CATEGORY.CC_CODE = CUSTOMER.CM_CATEGORY ON FT_DTAIL.FD_TOMEMBER = CUSTOMER.CM_CODE INNER JOIN FFROM_TTO ON FT_DTAIL.FD_CODE = FFROM_TTO.FT_CODE WHERE(FT_DTAIL.IS_SMS_SENT IS NULL) OR (FT_DTAIL.IS_SMS_SENT = 'N')";

            SQLQuery sQLQueryAlterCreate = session.createSQLQuery (sqlCreate);
            sQLQueryAlterCreate.executeUpdate ();
            MyMessagesUtility.showInformation ("VIEW_SMS_JURNAL Create is Success.");

            tx.commit ();

        } catch (Exception ex)
        {
            if (tx != null)
            {
                tx.rollback ();
            }

            ex.printStackTrace ();
            MyMessagesUtility.showError (ex.toString ());

        } finally
        {
            if (session != null)
            {
//                session.close ();
            }
        }


    }


    public void createVIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER ()
    {

        Session session = null;
        Transaction tx = null;

        try
        {
            session = SessionFactoryUtil.getSession ();
            tx = session.beginTransaction ();


            String sqlDrop = "CREATE VIEW VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER AS SELECT CUST_CATEGORY_MAIN.CC_CODE AS cluster_code, CUST_CATEGORY_MAIN.CC_NAME AS cluster_name, CUST_CATEGORY_MAIN.CC_OFFICER AS mfo, CUST_CATEGORY.CC_CODE AS small_group_code, CUST_CATEGORY.CC_NAME AS small_group_name, CUSTOMER.CM_CODE, CUSTOMER.CM_DESC, CUSTOMER.CM_ID,CUSTOMER.CM_BDATE, CUSTOMER.CM_ADD1, CUSTOMER.CM_ADD2, CUSTOMER.CM_ADD3, CUSTOMER.CM_ADD4, CUSTOMER.CM_TELE, CUSTOMER.CM_FLAG FROM CUSTOMER LEFT OUTER JOIN CUST_CATEGORY ON CUSTOMER.CM_CATEGORY = CUST_CATEGORY.CC_CODE LEFT OUTER JOIN CUST_CATEGORY_MAIN ON  CUST_CATEGORY.CC_MAIN = CUST_CATEGORY_MAIN.CC_CODE";

            SQLQuery sQLQueryAlterDrop = session.createSQLQuery (sqlDrop);
            sQLQueryAlterDrop.executeUpdate ();
            MyMessagesUtility.showInformation ("VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER Create is Success.");

            tx.commit ();

        } catch (Exception ex)
        {
            if (tx != null)
            {
                tx.rollback ();
            }

            ex.printStackTrace ();
            MyMessagesUtility.showError (ex.toString ());

        } finally
        {
            if (session != null)
            {
//                session.close ();
            }
        }
    }
}
