/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.entity.CustCategoryMain;
import iroshan.com.smsserver.entity.CustCategory_ent;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Iroshan
 */
public class SmallGroup_dao
{

    public List<CustCategoryMain> getClusterListAll ()
    {

        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;


        try
        {

            tx = session.beginTransaction ();

            List<CustCategoryMain> clusterList = null;

            if (session != null)
            {


                Criteria criteriaCluster = session.createCriteria (CustCategoryMain.class);
                criteriaCluster.add (Restrictions.like ("ccFlag", "A"));
                clusterList = criteriaCluster.list ();

            } else
            {
                clusterList = null;
            }

            tx.commit ();

            return clusterList;

        } catch (Exception e)
        {
            if (tx != null)
            {
                tx.rollback ();
            }

            MyMessagesUtility.showError (e.getMessage ());
            e.printStackTrace ();
            return null;
        } finally
        {
//            session.close ();
        }

    }

    public List<CustCategory_ent> getSmallGroupsList_byClusterCode (String clusterCodePara)
    {
        String clusterCode = clusterCodePara.trim ();

        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;

        try
        {
            tx = session.beginTransaction ();
            List<CustCategory_ent> clusterList = null;
            
            if (session != null)
            {


                Criteria criteriaCluster = session.createCriteria (CustCategory_ent.class);
                criteriaCluster.add (Restrictions.like ("ccFlag", "A"));
                criteriaCluster.add (Restrictions.eq ("ccMain", clusterCode.trim ()));
                clusterList = criteriaCluster.list ();

            } else
            {
                clusterList = null;
            }

            tx.commit ();

            return clusterList;

        } catch (Exception e)
        {
             if (tx!=null) tx.rollback();
            MyMessagesUtility.showError (e.getMessage ());
            e.printStackTrace ();
            return null;
        } finally
        {
//session.close();
        }
    }

}
