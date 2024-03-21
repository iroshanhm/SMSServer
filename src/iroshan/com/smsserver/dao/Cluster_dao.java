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
public class Cluster_dao
{

    public List<CustCategoryMain> getClusterListAll ()
    {

        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;

        try
        {

            if (session != null)
            {

                tx = session.beginTransaction ();
                Criteria criteriaCluster = session.createCriteria (CustCategoryMain.class);
                criteriaCluster.add (Restrictions.like ("ccFlag", "A"));
                List<CustCategoryMain> clusterList = criteriaCluster.list ();

                tx.commit ();


                return clusterList;
            } else
            {
                return null;
            }
        } catch (Exception e)
        {
            MyMessagesUtility.showError (e.getMessage ());
            e.printStackTrace ();


            if (tx != null)
            {
                tx.rollback ();
            }

            return null;
        } finally
        {
//            session.close ();
        }

    }










    public List<String> getClusterListAsStringList ()
    {

        Session session = SessionFactoryUtil.getSessionFactory ().openSession ();
        Transaction tx = null;


        try
        {

            if (session != null)
            {

                List<String> list = new ArrayList<String> ();

                tx = session.beginTransaction ();
                Criteria criteriaCluster = session.createCriteria (CustCategoryMain.class);
                criteriaCluster.add (Restrictions.like ("ccFlag", "A"));
                List<CustCategoryMain> clusterList = criteriaCluster.list ();

                for (CustCategoryMain clusterListObj : clusterList)
                {
                    String code = clusterListObj.getCcCode ();
                    String name = clusterListObj.getCcName ();

                    String obj = code + "-" + name;
                    list.add (obj);
                }



                tx.commit ();

                return list;

            } else
            {
                return null;
            }
        } catch (Exception e)
        {
            MyMessagesUtility.showError (e.getMessage ());
            e.printStackTrace ();


            if (tx != null)
            {
                tx.rollback ();
            }

            return null;
        } finally
        {
//            session.close ();
        }

    }




}
