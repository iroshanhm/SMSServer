/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.entity.Receipt_ent;
import iroshan.com.smsserver.pojo.Loan_pojo;
import iroshan.com.smsserver.pojo.ReceiptSMS_pojo;
import iroshan.com.smsserver.pojo.Receipt_pojo;
import iroshan.com.smsserver.view.WaitingDialogView;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

/**
 *
 * @author Iroshan
 */
public class ReceiptMarkAsSend_dao
{

      public Map<String, Receipt_pojo> getNewReceiptsList ()
    {

        WaitingDialogView waitingDialog = new WaitingDialogView ();
        waitingDialog.setCustomMessage ("Wait...");
        waitingDialog.setVisible (true);

        Loan_dao loan_dao = new Loan_dao ();
        Map<String, Receipt_pojo> treeMap = new TreeMap ();

        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;

        try
        {


            tx = session.beginTransaction ();





            String sqlString = "SELECT RECEIPT.RE_CODE AS RE_CODE, RECEIPT.RE_CUSTOMER AS RE_CUSTOMER,CUSTOMER.CM_DESC AS CUS_NAME, CUSTOMER.CM_OFFICE_NO,RECEIPT.RE_DATE, RE_DTAIL.RD_VALUE, RE_DTAIL.RD_CHEQUE, CUSTOMER.CM_DESC, RE_INCOME.RI_VALUE AS RI_VALUE, RE_INCOME.RI_TYPE AS RI_TYPE, "
                    + "INCOME_EXPENCE_SUB.IE_DESC, 'RECEIPT' AS WHAT, INCOME_EXPENCE_SUB.IE_CODE AS IE_CODE, SUBSTRING(INCOME_EXPENCE_SUB.IE_CODE, 1, 4) AS MCODE, "
                    + "INCOME_EXPENCE.IE_DESC AS DESC2, INCOME_EXPENCE.IE_MTYPE AS IE_MTYPE, RE_INCOME.RI_DESC, RECEIPT.RE_CODE AS RE_CODE2, RECEIPT.RE_CODE AS RE_CODE3, "
                    + "INCOME_EXPENCE.IE_ACCOUNT_DIVISION, RECEIPT.WHO1, RECEIPT.is_sms_sent, CUSTOMER.CM_TELE AS CM_TELE "
                    + "FROM RECEIPT AS RECEIPT INNER JOIN "
                    + "RE_DTAIL AS RE_DTAIL ON RECEIPT.RE_CODE = RE_DTAIL.RD_CODE INNER JOIN "
                    + "CUSTOMER AS CUSTOMER ON RECEIPT.RE_CUSTOMER = CUSTOMER.CM_CODE INNER JOIN "
                    + "RE_INCOME AS RE_INCOME ON RE_DTAIL.RD_CODE = RE_INCOME.RI_CODE AND RE_DTAIL.RD_CHEQUE = RE_INCOME.RI_CHEQUE INNER JOIN "
                    + "INCOME_EXPENCE_SUB AS INCOME_EXPENCE_SUB ON RE_INCOME.RI_INCOME = INCOME_EXPENCE_SUB.IE_CODE INNER JOIN "
                    + "INCOME_EXPENCE ON INCOME_EXPENCE_SUB.IE_MAIN = INCOME_EXPENCE.IE_CODE "
                    + "WHERE (RECEIPT.is_sms_sent = N'N') OR (RECEIPT.is_sms_sent IS NULL) ORDER BY RECEIPT.RE_CODE,INCOME_EXPENCE_SUB.IE_CODE";


            SQLQuery sqlQuery = session.createSQLQuery (sqlString);


            sqlQuery.addScalar ("RE_CODE", StringType.INSTANCE);
            sqlQuery.addScalar ("RE_CUSTOMER", StringType.INSTANCE);
            sqlQuery.addScalar ("CUS_NAME", StringType.INSTANCE);
            sqlQuery.addScalar ("CM_OFFICE_NO", StringType.INSTANCE);
            sqlQuery.addScalar ("RE_DATE", DateType.INSTANCE);
            sqlQuery.addScalar ("IE_CODE", StringType.INSTANCE);
            sqlQuery.addScalar ("CM_TELE", StringType.INSTANCE);
            sqlQuery.addScalar ("RI_VALUE", DoubleType.INSTANCE);
            sqlQuery.addScalar ("RI_TYPE", IntegerType.INSTANCE);
            sqlQuery.addScalar ("IE_MTYPE", IntegerType.INSTANCE);

            sqlQuery.setResultTransformer (Criteria.ALIAS_TO_ENTITY_MAP);
            List data = sqlQuery.list ();


            List<Receipt_ent> resultList = sqlQuery.list ();
            int listSize = resultList.size ();



            for (Object object : data)
            {
                Map row = (Map) object;

                Integer riType = (Integer) row.get ("RI_TYPE"); // 1 = Capital / 2 = Interest / 3 = fine
                Integer mType = (Integer) row.get ("IE_MTYPE"); // 1 = Loan / 2 = Saving
                String ieCode = (String) row.get ("IE_CODE");

                waitingDialog.setCustomMessage ("Loading : " + ieCode);

                String REcODE = (String) row.get ("RE_CODE");
                String cus_name = (String) row.get ("CUS_NAME");
                String cusOfficeNo = (String) row.get ("CM_OFFICE_NO");
                Date reDATE = (Date) row.get ("RE_DATE");
                String cmTELE = (String) row.get ("CM_TELE");
                String reCUSTOMER = (String) row.get ("RE_CUSTOMER");
                Double riValue = (Double) row.get ("RI_VALUE");

//            Double sumValue = 0.00;



                if (mType == 1) // RECEIPT FOR LOAN
                {

                    boolean contain = treeMap.containsKey (REcODE);
                    System.out.println ("contain>" + contain);
                    if (contain) // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                    {

                        Receipt_pojo rePojo = treeMap.get (REcODE);


                        Double val1 = riValue;
                        System.out.println ("val1>" + val1);
                        Double val2 = rePojo.getReceiptAmount ();
                        System.out.println ("val2>" + val2);
                        Double sum = val1 + val2;
                        System.out.println ("sum>" + sum);
                        rePojo.setReceiptAmount (sum);

                        treeMap.remove (ieCode);
                        treeMap.put (REcODE, rePojo);


                    } else
                    {

                        Double val1 = riValue;


                        Receipt_pojo rePojo = new Receipt_pojo ();

                        Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
                        Double loanOutstanding = loanPojo.getTotalOutstanding ();

                        rePojo.setReceiptNo (REcODE);
                        rePojo.setCustomerCode (reCUSTOMER);
                        rePojo.setACNumber (ieCode);
                        rePojo.setCusName (cus_name);
                        rePojo.setCusOfficeNo (cusOfficeNo);
                        rePojo.setTele (cmTELE);
                        rePojo.setReceiptAmount (val1);
                        rePojo.setOutstandingAmount (loanOutstanding);
                        rePojo.setLoanOrSaving (mType);

                        treeMap.put (REcODE, rePojo);

                    }


                } else if (mType == 2) // RECEIPT FOR LOAN
                {

                    boolean contain = treeMap.containsKey (REcODE);
                    System.out.println ("contain>" + contain);
                    if (contain)// FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                    {

                        Receipt_pojo rePojo = treeMap.get (REcODE);


                        Double val1 = riValue;
                        System.out.println ("val1>" + val1);
                        Double val2 = rePojo.getReceiptAmount ();
                        System.out.println ("val2>" + val2);
                        Double sum = val1 + val2;
                        System.out.println ("sum>" + sum);
                        rePojo.setReceiptAmount (sum);

                        treeMap.remove (ieCode);
                        treeMap.put (REcODE, rePojo);



//                                                Double val1 = riValue;
//                        Receipt_pojo rePojo = new Receipt_pojo ();
////                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setLoanOrSaving (mType);
////                rePojo.setDueAmount (loanOutstanding);
//                        treeMap.put (ieCode, rePojo);


                    } else
                    {

//                        Double val1 = riValue;
//
//
//                        Receipt_pojo rePojo = new Receipt_pojo ();
//
//                        Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
//                        Double loanOutstanding = loanPojo.getTotalOutstanding ();
//
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setDueAmount (loanOutstanding);
//                        rePojo.setLoanOrSaving (mType);
//
//                        treeMap.put (ieCode, rePojo);


                        Double val1 = riValue;
                        Receipt_pojo rePojo = new Receipt_pojo ();
//                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
                        rePojo.setReceiptNo (REcODE);
                        rePojo.setCustomerCode (reCUSTOMER);
                        rePojo.setCusName (cus_name);
                        rePojo.setCusOfficeNo (cusOfficeNo);
                        rePojo.setACNumber (ieCode);
                        rePojo.setTele (cmTELE);
                        rePojo.setReceiptAmount (val1);
                        rePojo.setLoanOrSaving (mType);
//                rePojo.setDueAmount (loanOutstanding);
                        treeMap.put (REcODE, rePojo);

                    }
                }
            }

            tx.commit ();


            waitingDialog.dispose ();

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
//            session.close ();
        }

        return treeMap;
    }

    
    public void markAsSendAll ()
    {





        Session sessionForMainDB = SessionFactoryUtil.getSession ();
        Transaction trForMainDB = null;


        try
        {

            trForMainDB = sessionForMainDB.beginTransaction ();

            String sqlUpdate = "UPDATE RECEIPT SET IS_SMS_SENT = 'Y'";
            SQLQuery sQLQueryUpdate = sessionForMainDB.createSQLQuery (sqlUpdate);
            sQLQueryUpdate.executeUpdate ();
            MyMessagesUtility.showInformation ("UPDATE RECEIPT SET IS_SMS_SENT = 'Y' SUCCESS.");

            trForMainDB.commit ();

        } catch (Exception e)
        {

            if (trForMainDB != null)
            {
                trForMainDB.rollback ();
            }
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        } finally
        {
            sessionForMainDB.close ();
        }

    }


    public void markAsSendList (List<ReceiptSMS_pojo> listPara)
    {


        Session sessionForMainDB = SessionFactoryUtil.getSession ();
        Transaction trForMainDB = null;




        try
        {

            trForMainDB = sessionForMainDB.beginTransaction ();



            for (ReceiptSMS_pojo listPara1 : listPara)
            {

                Boolean isSelected = listPara1.getIsSelected ();

                if (isSelected)
                {
                    String receiptNo = listPara1.getReceiptNo ();
                    if (!receiptNo.isEmpty ())
                    {
                        receiptNo = receiptNo.trim ();
                        String sqlUpdate = "UPDATE RECEIPT SET IS_SMS_SENT = 'Y' WHERE RE_CODE LIKE '" + receiptNo + "'";
                        SQLQuery sQLQueryUpdate = sessionForMainDB.createSQLQuery (sqlUpdate);
                        sQLQueryUpdate.executeUpdate ();
                        trForMainDB.commit ();
                    }
                }
            }


            MyMessagesUtility.showInformation ("UPDATE RECEIPT SET IS_SMS_SENT = 'Y' SUCCESS.");



        } catch (Exception e)
        {

            if (trForMainDB != null)
            {
                trForMainDB.rollback ();
            }
            e.printStackTrace ();
            MyMessagesUtility.showError ("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage ());
        } finally
        {
            sessionForMainDB.close ();
        }
    }
}
