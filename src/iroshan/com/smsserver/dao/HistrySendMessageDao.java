/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.entity.SmsSendEntity;
import iroshan.com.smsserver.view.WaitingDialogView;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Iroshan
 */
public class HistrySendMessageDao
{

//    private WaitingDialogView waitDialog;

    public Map<String, Map> search (String phoneNoPara, String textPara, Date dateSelectedFromPara, Date dateSelectedToPara, String typePara)
    {



        WaitingDialogView waitingDialog = new WaitingDialogView ();
        waitingDialog.setCustomMessage ("Please Wait...");
        waitingDialog.setVisible (true);




        Map<String, Map> smsReceivedMap = new TreeMap<String, Map> ();



        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;

        try
        {

            tx = session.beginTransaction ();


            String phoneNo = phoneNoPara;
            String text = textPara;


            Date dateSelectedFrom = dateSelectedFromPara;
            Date dateSelectedTo = dateSelectedToPara;

            String type = typePara;




            Criteria criteria = session.createCriteria (SmsSendEntity.class);

            if (!phoneNo.isEmpty ())
            {
                phoneNo = "%".concat (phoneNo).concat ("%");
                criteria.add (Restrictions.like ("recipientsmssend", phoneNo));
            }

            if (!text.isEmpty ())
            {
                text = "%".concat (text).concat ("%");

                Criterion crText = Restrictions.like ("messagesmssend", text);

                criteria.add (crText);
            }




            try
            {
                dateSelectedFrom = MyDateAndTimeUtil.getFromtedDateByStringPara (MyDateAndTimeUtil.getFormatedDateAsStringAndTime (dateSelectedFrom));
                dateSelectedTo = MyDateAndTimeUtil.getFromtedDateByStringPara (MyDateAndTimeUtil.getFormatedDateAsStringAndTime (dateSelectedTo));
            } catch (ParseException ex)
            {
                Logger.getLogger (HistrySendMessageDao.class.getName ()).log (Level.SEVERE, null, ex);
            }


            System.out.println ("dateSelectedFrom.compareTo (dateSelectedTo)>>" + dateSelectedFrom.compareTo (dateSelectedTo));

            System.out.println ("dateSelectedFrom>>" + dateSelectedFrom);
            System.out.println ("dateSelectedTo>>" + dateSelectedTo);

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

            criteria.add (Restrictions.ge ("sentdatesmssend", fromDate));
            criteria.add (Restrictions.le ("sentdatesmssend", toDate));


            List<SmsSendEntity> list = criteria.list ();
            int sequnceNo = 1;
            for (SmsSendEntity smsSendEntity : list)
            {
                Map<String, String> smsReceivedMapObj = new TreeMap<String, String> ();
                smsReceivedMapObj.put ("SMSTYPE", smsSendEntity.getMsgType ());
                smsReceivedMapObj.put ("SMSRECEIVEDID", String.valueOf (smsSendEntity.getIdsmsreceive ()));
                smsReceivedMapObj.put ("SMSRECIPIENTPHONENO", smsSendEntity.getRecipientsmssend ());
                smsReceivedMapObj.put ("SMSTEXT", smsSendEntity.getMessagesmssend ());
                smsReceivedMapObj.put ("SMSDATE", MyDateAndTimeUtil.getFormatedDateAsString (smsSendEntity.getSentdatesmssend ()));
                smsReceivedMapObj.put ("STATUS", smsSendEntity.getMsgStatus ());

                smsReceivedMap.put (String.valueOf (sequnceNo), smsReceivedMapObj);
                ++sequnceNo;
            }

            tx.commit ();

            waitingDialog.dispose ();

            return smsReceivedMap;

        } catch (Exception e)
        {
            e.printStackTrace ();

            if (tx != null)
            {
                tx.rollback ();
            }

            return null;
        } finally
        {
//                        session.close ();
        }







    }


}
