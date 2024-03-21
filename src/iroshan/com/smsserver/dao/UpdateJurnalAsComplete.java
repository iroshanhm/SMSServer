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
public class UpdateJurnalAsComplete {

    public void setSMSSent(String fdCodePara, String fdMemberPara) {



        if (fdCodePara != null && fdMemberPara != null) {

            String fdCode = fdCodePara.trim();
            String fdMember = fdMemberPara.trim();

            Session session = SessionFactoryUtil.getSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();


                System.out.println("fdCode >>" + fdCode);
                System.out.println("fdMember >>" + fdMember);

                String sqlPaidAll = "UPDATE FT_DTAIL SET is_sms_sent = 'Y' WHERE FD_CODE LIKE '" + fdCode + "' AND FD_TOMEMBER LIKE '" + fdMember + "'";
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
