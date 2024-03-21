package iroshan.com.common.derby.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class SysIdDaoImpl {

    private SessionFactoryUtil sessionFactoryUtil;

    Boolean isMainDBAllow = false;
    Boolean isSubDBAllow = false;

    private Session sessionForMainDB = null;
    private Transaction transactionMainDB = null;

    /**
     *
     * @param classPara Class
     * @param branchIdPara Integer
     * @return Integer
     */
    public Integer getNextSysId (Class classPara) throws Exception {

        Class cls = classPara;

        Integer maxInt = 0;

        sessionForMainDB = SessionFactoryUtil.getSessionFactory ().openSession ();
        transactionMainDB = sessionForMainDB.beginTransaction ();


        String nameOfClass = cls.getName ();
        String hql = "SELECT MAX(C.sysId) FROM " + nameOfClass;

        Query query = sessionForMainDB.createQuery (hql);
        maxInt = (Integer) query.uniqueResult ();

        transactionMainDB.commit ();
        sessionForMainDB.close ();

        ++maxInt;

        return maxInt;
    }


    public Boolean checkCodeExist (Class classPara, String codePara) throws Exception {
        Boolean aFlag = null;

        Class cls = classPara;
        String code = codePara.trim ();

        System.out.println ("cls >>" + cls);
        System.out.println ("code >>" + code);

        sessionFactoryUtil = new SessionFactoryUtil ();
        sessionForMainDB = SessionFactoryUtil.getSessionFactory ().openSession ();
        transactionMainDB = sessionForMainDB.beginTransaction ();

        Criteria criteria = sessionForMainDB.createCriteria (cls);
        criteria.add (Restrictions.eq ("code", code));
        List list = criteria.list ();
        transactionMainDB.commit ();
        sessionForMainDB.close ();

        System.out.println ("list.size() >>" + list.size ());

        if (list != null) {
            if (list.size () > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param classPara Class
     * @param branchIdPara Integer
     * @param sysIdPara Integer
     * @return Boolean
     * @throws Exception
     */

    public Boolean checkSysIDExist (Class classPara, Integer branchIdPara, Integer sysIdPara) throws Exception {
        Boolean aFlag = null;

        Class cls = classPara;
        Integer branchId = branchIdPara;
        Integer sysID = sysIdPara;

        sessionForMainDB = SessionFactoryUtil.getSessionFactory ().openSession ();
        transactionMainDB = sessionForMainDB.beginTransaction ();

        Criteria criteria = sessionForMainDB.createCriteria (cls);
        criteria.add (Restrictions.eq ("sysId", sysID));
        List list = criteria.list ();
        transactionMainDB.commit ();
        sessionForMainDB.close ();

        if (list != null) {
            if (list.size () > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     *
     * @param classPara Class
     * @param branchIdPara Integer
     * @param sysIdPara Integer
     * @return Boolean
     * @throws Exception
     */

    public Boolean checkCodeExistForUpdate (Class classPara, Integer branchIdPara, Integer sysIdPara, String codePara) throws Exception {
//        Integer branchId = branchIdPara;

        Class cls = classPara;
        Integer branchId = branchIdPara;
        Integer sysId = sysIdPara;
        String code = codePara;

        System.out.println ("##################################");
        System.out.println ("Cls >>" + cls);
        System.out.println ("branchId >>" + branchId);
        System.out.println ("sysId >>" + sysId);
        System.out.println ("code >>" + code);

        sessionForMainDB = SessionFactoryUtil.getSessionFactory ().openSession ();
        transactionMainDB = sessionForMainDB.beginTransaction ();

        Criteria criteria = sessionForMainDB.createCriteria (cls);
        criteria.add (Restrictions.eq ("sysId", sysId)); //Restrictions.not()
        criteria.add (Restrictions.eq ("code", code));
        List resultList = criteria.list ();

        transactionMainDB.commit ();
        sessionForMainDB.close ();

        System.out.println ("resultList.size() >>" + resultList.size ());

        System.out.println ("##################################");
        if (resultList != null) {
            if (resultList.size () >= 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     *
     * @param classPara Class
     * @param branchIdPara Integer
     * @param sysIdPara Integer
     * @return Boolean
     * @throws Exception
     */
    public Boolean checkCodeExistForRemove (Class classPara, Integer branchIdPara, int sysIdPara, String codePara) throws Exception {

        Class cls = classPara;
        Integer branchId = branchIdPara;
        Integer sysId = sysIdPara;
        String code = codePara;

        sessionForMainDB = SessionFactoryUtil.getSessionFactory ().openSession ();
        transactionMainDB = sessionForMainDB.beginTransaction ();

        Criteria criteria = sessionForMainDB.createCriteria (cls);
        criteria.add (Restrictions.eq ("sysId", sysId));
        criteria.add (Restrictions.like ("code", code));
        List list = criteria.list ();

        transactionMainDB.commit ();
        sessionForMainDB.close ();

        if (list != null) {
            if (list.size () > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
