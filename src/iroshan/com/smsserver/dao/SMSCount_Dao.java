/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.entity.SmsSendEntity;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.com.smsserver.entity.SmsCount_Entity;
import iroshan.com.smsserver.view.ApplicationDesktop;
import iroshan.com.smsserver.view.WaitingDialog;
import iroshan.com.smsserver.view.WaitingDialogView;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.smslib.OutboundMessage;

/**
 *
 * @author IROshan
 */
public class SMSCount_Dao {

    WaitingDialog waitingDialog;
    Thread trdWaiting;

    public void saveSMSCount(String SMSTypePara, int noOfSMSPara) {

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

//        try {
//
//            tx = session.beginTransaction();
//
//            String SMSType = SMSTypePara;
//            int noOfSMS = noOfSMSPara;
//
//            Date dateTime = MyDateAndTimeUtil.getCurrentDateAndTime();
//
//            Integer maxID = getMaxSMSCountId();
//            System.out.println("maxID >>>>>>>>>>>>>>>>>>>>>>>>> " + maxID);
//            if (maxID != null) {
//
//                SmsCount_Entity smsCount = new SmsCount_Entity();
//                smsCount.setId(maxID);
//                smsCount.setSmsType(SMSType);
//                smsCount.setSmsCount(noOfSMS);
//                smsCount.setSentDatetime(dateTime);
//
//                session.save(smsCount);
//
//                tx.commit();
//            }
//
//        } catch (Exception ex) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            ex.printStackTrace();
//            Logger.getLogger(SendMessageSaveDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
////            session.close ();
//        }
        try {
            tx = session.beginTransaction();

            Date dateTime = MyDateAndTimeUtil.getCurrentDateAndTime();
            Integer maxID = getMaxSMSCountId();
            String SMSType = SMSTypePara;
            int noOfSMS = noOfSMSPara;

//            String sql = "INSERT INTO SMS_COUNT(ID, SMS_TYPE, SMS_COUNT, SENT_DATETIME) VALUES(" + maxID + ", '" + SMSType + "', " + noOfSMS + ", convert(smalldatetime,'" + dateTime + "',103))";
            String sql = "INSERT INTO SMS_COUNT(ID, SMS_TYPE, SMS_COUNT, SENT_DATETIME) VALUES(" + maxID + ", '" + SMSType + "', " + noOfSMS + ", GETDATE())";

//            String sqlPaidAll = "UPDATE PAYMENT SET is_sms_sent = 'Y' WHERE PA_CODE LIKE '" + paymentNo + "'";
            System.out.println("sql: " + sql);
            SQLQuery sqlQuery = session.createSQLQuery(sql);
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

    public Integer getMaxSMSCountId() {

        Integer maxID = 0;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            Criteria cri = session.createCriteria(SmsCount_Entity.class);
            cri.setProjection(Projections.max("id"));

            Integer maxIdCri = (Integer) cri.uniqueResult();
            if (maxIdCri == null) {

            } else {
                maxID = maxIdCri;
            }
            ++maxID;

            return maxID;
//            String SMSType = SMSTypePara;
//            int noOfSMS = noOfSMSPara;            
//            Date dateTime = MyDateAndTimeUtil.getCurrentDateAndTime();
//            String messageStatusString = null;
//            SmsCount smsCount = new SmsCount();
//            smsCount.setId();
//            smsCount.setSmsType(SMSType);
//            smsCount.setSmsCount(noOfSMS);
//            smsCount.setSentDatetime(dateTime);
//            session.save(smsCount);
//            tx.commit();

        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
            Logger.getLogger(SendMessageSaveDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
//            session.close ();
        }
    }

    public List<Map> getSMSCountInDateRange(Date fromDatePara, Date toDatePara, String typePara) {

        //-------------------------------------------------------        
        trdWaiting = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingDialog = new WaitingDialog("", "Starting ...", ApplicationDesktop.appDesktop, true);
                waitingDialog.setVisible(true);
            }
        });
        trdWaiting.start();
//------------------------------------------------------- 

        List<Map> smsReceivedMap = new ArrayList<Map>();

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            Date dateSelectedFrom = fromDatePara;
            Date dateSelectedTo = toDatePara;

            String type = typePara;

            Criteria criteria = session.createCriteria(SmsCount_Entity.class);

            try {
                dateSelectedFrom = MyDateAndTimeUtil.getFromtedDateByStringPara(MyDateAndTimeUtil.getFormatedDateAsStringAndTime(dateSelectedFrom));
                dateSelectedTo = MyDateAndTimeUtil.getFromtedDateByStringPara(MyDateAndTimeUtil.getFormatedDateAsStringAndTime(dateSelectedTo));
            } catch (ParseException ex) {
                Logger.getLogger(HistrySendMessageDao.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("dateSelectedFrom.compareTo (dateSelectedTo)>>" + dateSelectedFrom.compareTo(dateSelectedTo));

            System.out.println("dateSelectedFrom>>" + dateSelectedFrom);
            System.out.println("dateSelectedTo>>" + dateSelectedTo);

            Calendar cal = Calendar.getInstance();
            cal.setTime(dateSelectedFrom);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Date fromDate = cal.getTime();
            System.out.println("fromDate>" + fromDate);

            cal.setTime(dateSelectedTo);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date toDate = cal.getTime();
            System.out.println("toDate>" + toDate);

            criteria.add(Restrictions.ge("sentDatetime", fromDate));
            criteria.add(Restrictions.le("sentDatetime", toDate));

            if (!type.equalsIgnoreCase("ALL")) {
                criteria.add(Restrictions.like("smsType", type));
            }

            List<SmsCount_Entity> list = criteria.list();
            int sequnceNo = 1;
            for (SmsCount_Entity smsSendEntity : list) {
                Map<String, Object> smsReceivedMapObj = new TreeMap<String, Object>();
                smsReceivedMapObj.put("SMSTYPE", smsSendEntity.getSmsType());
                smsReceivedMapObj.put("SMSCOUNT", smsSendEntity.getSmsCount());
                smsReceivedMapObj.put("SMSDATETIME", smsSendEntity.getSentDatetime());
                smsReceivedMap.add(smsReceivedMapObj);
                ++sequnceNo;
            }

            tx.commit();

            waitingDialog.dispose();

            return smsReceivedMap;

        } catch (Exception e) {
            e.printStackTrace();

            if (tx != null) {
                tx.rollback();
            }

            return null;
        } finally {
//                        session.close ();
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }
}
