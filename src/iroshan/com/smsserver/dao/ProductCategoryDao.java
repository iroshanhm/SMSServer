/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.smsserver.entity.IncomeExpenceEntity;
import iroshan.com.smsserver.entity.ZINCOME_EXPENCE_SUB_entity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Iroshan
 */
public class ProductCategoryDao {

    public List<String> getLoanList() {

        System.out.println("-------------------------------------------------");
        System.out.println("Im here....");
        System.out.println("-------------------------------------------------");

        List<String> returnList = new ArrayList<String>();

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(IncomeExpenceEntity.class);
            criteriaCluster.add(Restrictions.like("ieSubType", "LOAN"));
            criteriaCluster.add(Restrictions.like("ieFlag", "A"));

//        Criterion criOne = Restrictions.like ("IE_ACTIVE", "1");
//        Criterion criNull = Restrictions.isNull ("IE_ACTIVE");
//        LogicalExpression logExpOr = Restrictions.or (criOne, criNull);
//        criteriaCluster.add (logExpOr);
//        Criterion crsEql = Restrictions.eq ("IE_FROM", selectingDate);
//        Criterion crsLess = Restrictions.lt ("IE_FROM", selectingDate);
//        LogicalExpression leEqlLess = Restrictions.or (crsEql, crsLess);
//        criteriaCluster.add (leEqlLess);
            List<IncomeExpenceEntity> zIncExpList = criteriaCluster.list();

            for (IncomeExpenceEntity zIncExpListObj : zIncExpList) {
                String ieCode = zIncExpListObj.getIeCode();
                String ieDescription = zIncExpListObj.getIeDesc();

                String obj = ieCode + "-" + ieDescription;
                returnList.add(obj);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
//            session.close ();
        }

        return returnList;
    }

}
