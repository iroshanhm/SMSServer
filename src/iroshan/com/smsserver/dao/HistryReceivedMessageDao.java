/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.entity.SmsReceivedEntity;
import iroshan.com.smsserver.view.WaitingDialogView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Iroshan
 */
public class HistryReceivedMessageDao
{

    private WaitingDialogView waitDialog;
    private Thread trdRunner;

    public Map<String, Map> search (String phoneNoPara, String textPara, Date dateSelectedFromPara, Date dateSelectedToPara)
    {



        WaitingDialogView waitingDialog = new WaitingDialogView ();
        waitingDialog.setCustomMessage ("Please Wait...");
        waitingDialog.setVisible (true);




        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;

        Map<String, Map> smsReceivedMap = new TreeMap<String, Map> ();



        try
        {
            
             tx = session.beginTransaction ();
            
            String phoneNo = phoneNoPara;
            String text = textPara;


            Date dateSelectedFrom = dateSelectedFromPara;
            Date dateSelectedTo = dateSelectedToPara;


            Criteria criteria = session.createCriteria (SmsReceivedEntity.class);

            if (!phoneNo.isEmpty ())
            {
                phoneNo = "%".concat (phoneNo).concat ("%");
                criteria.add (Restrictions.like ("smsReceivedPhone", phoneNo));
            }

            if (!text.isEmpty ())
            {
                text = "%".concat (text).concat ("%");


                Criterion crKey = Restrictions.like ("smsReceivedKeyword", text);
                Criterion crAcc = Restrictions.like ("smsReceivedAcc", text);
                Criterion crText = Restrictions.like ("smsReceivedText", text);

                Junction junction = Restrictions.disjunction ();
                junction.add (crKey).add (crAcc).add (crText);

                criteria.add (junction);
            }


            Calendar cal = Calendar.getInstance ();
            cal.setTime (dateSelectedFrom);
            cal.set (Calendar.HOUR_OF_DAY, 0);
            cal.set (Calendar.MINUTE, 0);
            cal.set (Calendar.SECOND, 0);
            Date fromDate = cal.getTime ();
            System.out.println ("fromDate>" + fromDate);

            cal.setTime (dateSelectedTo);
            cal.set (Calendar.HOUR_OF_DAY, 23);
            cal.set (Calendar.MINUTE, 59);
            cal.set (Calendar.SECOND, 59);
            Date toDate = cal.getTime ();
            System.out.println ("toDate>" + toDate);

            criteria.add (Restrictions.ge ("smsReceivedDate", fromDate));
            criteria.add (Restrictions.le ("smsReceivedDate", toDate));



            List<SmsReceivedEntity> list = criteria.list ();
            int sequnceNo = 1;
            for (SmsReceivedEntity smsReceivedEntity : list)
            {
                Map<String, String> smsReceivedMapObj = new TreeMap<String, String> ();
                smsReceivedMapObj.put ("SMSRECEIVEDID", String.valueOf (smsReceivedEntity.getSmsReceivedId ()));
                smsReceivedMapObj.put ("SMSRECEIVEDPHONENO", smsReceivedEntity.getSmsReceivedPhone ());
                smsReceivedMapObj.put ("SMSRECEIVEDTEXT", smsReceivedEntity.getSmsReceivedText ());
                smsReceivedMapObj.put ("SMSRECEIVEDDATE", MyDateAndTimeUtil.getFormatedDateAsString (smsReceivedEntity.getSmsReceivedDate ()));

                smsReceivedMap.put (String.valueOf (sequnceNo), smsReceivedMapObj);
                ++sequnceNo;
            }


            tx.commit ();




            waitingDialog.dispose ();


        } catch (Exception e)
        {

            e.printStackTrace ();


            if (tx != null)
            {
                tx.rollback ();
            }

        } finally
        {


            if (waitDialog != null)
            {
                waitDialog.setVisible (false);
                waitDialog = null;
            }

            if (trdRunner != null)
            {
                trdRunner.interrupt ();
                trdRunner = null;
            }


            System.out.println ("Im here....");

//                        session.close ();

        }


        return smsReceivedMap;
    }
}
