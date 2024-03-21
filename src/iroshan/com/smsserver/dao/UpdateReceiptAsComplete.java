/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Iroshan
 */
public class UpdateReceiptAsComplete {

    public void setSMSSent(String receiptNoPara) {





        if (receiptNoPara != null) {

            String receiptNo = receiptNoPara.trim();

            Session session = SessionFactoryUtil.getSession();
            Transaction tx = null;


            try {
                tx = session.beginTransaction();




                System.out.println("receiptNo >>" + receiptNo);

                String sqlPaidAll = "UPDATE RECEIPT SET is_sms_sent = 'Y' WHERE RE_CODE LIKE '" + receiptNo + "'";
                SQLQuery sqlQuery = session.createSQLQuery(sqlPaidAll);
                int result = sqlQuery.executeUpdate();
                System.out.println("result> " + result);

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();

            } finally {
//            session.close ();
            }
        }





    }
}
