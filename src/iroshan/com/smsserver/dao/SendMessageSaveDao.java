/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;


import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.smsserver.entity.SmsSendEntity;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.smslib.OutboundMessage;
import org.smslib.OutboundMessage.MessageStatuses;

/**
 *
 * @author Iroshan
 */
public class SendMessageSaveDao
{

    /**
     *
     * @param smsTypePara
     * @param idSMSReceive
     * @param phoneNo
     * @param messagePara
     * @param datePara
     * @param outBoundMessagePara
     */
    public void saveToDB (String smsTypePara, int idSMSReceive, String phoneNo, String messagePara, Date datePara, OutboundMessage outBoundMessagePara)
    {

        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;

        try
        {

            tx = session.beginTransaction ();

            String smsType = smsTypePara;
            String recipient = phoneNo;
            String message = messagePara;
            Date date = datePara;
            OutboundMessage outBoundMessage = outBoundMessagePara;
            String messageStatusString = MessageStatusEnum.QUEUE.toString ();



            OutboundMessage.MessageStatuses messageStatus = outBoundMessage.getMessageStatus ();

            if (OutboundMessage.MessageStatuses.SENT == messageStatus)
            {
                messageStatusString = MessageStatusEnum.SENT.toString ();
            } else if (OutboundMessage.MessageStatuses.UNSENT.toString ().trim ().equalsIgnoreCase (messageStatus.toString ().trim ()))
            {
                messageStatusString = MessageStatusEnum.UNSENT.toString ();
            } else if (OutboundMessage.MessageStatuses.FAILED.toString ().trim ().equalsIgnoreCase (messageStatus.toString ().trim ()))
            {
                messageStatusString = MessageStatusEnum.FAILED.toString ();
            }

            SmsSendEntity smsSent = new SmsSendEntity ();
            smsSent.setMsgType (smsType);
            smsSent.setIdsmsreceive (idSMSReceive);
            smsSent.setRecipientsmssend (recipient);
            smsSent.setMessagesmssend (message);
            smsSent.setSentdatesmssend (date);
            smsSent.setMsgStatus (messageStatusString);

            session.save (smsSent);

            tx.commit ();


        } catch (Exception ex)
        {
            if (tx != null)
            {
                tx.rollback ();
            }
            ex.printStackTrace ();
            Logger.getLogger (SendMessageSaveDao.class.getName ()).log (Level.SEVERE, null, ex);
        } finally
        {
//            session.close ();
        }



    }



    /**
     *
     * @param smsTypePara
     * @param idSMSReceive
     * @param phoneNo
     * @param messagePara
     * @param datePara
     * @param messageStatusPara
     */
    public void saveToDB_2 (String smsTypePara, int idSMSReceive, String phoneNo, String messagePara, Date datePara, MessageStatuses messageStatusPara)
    {

        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;

        try
        {

            tx = session.beginTransaction ();

            String smsType = smsTypePara;
            String recipient = phoneNo;
            String message = messagePara;
            Date date = datePara;
//            OutboundMessage outBoundMessage = outBoundMessagePara;
            String messageStatusString = null;



            MessageStatuses messageStatus = messageStatusPara;

            if (OutboundMessage.MessageStatuses.SENT == messageStatus)
            {
                messageStatusString = MessageStatusEnum.SENT.toString ();
            } else if (OutboundMessage.MessageStatuses.UNSENT == messageStatus)
            {
                messageStatusString = MessageStatusEnum.UNSENT.toString ();
            } else if (OutboundMessage.MessageStatuses.FAILED == messageStatus)
            {
                messageStatusString = MessageStatusEnum.FAILED.toString ();
            }

            SmsSendEntity smsSent = new SmsSendEntity ();
            smsSent.setMsgType (smsType);
            smsSent.setIdsmsreceive (idSMSReceive);
            smsSent.setRecipientsmssend (recipient);
            smsSent.setMessagesmssend (message);
            smsSent.setSentdatesmssend (date);
            smsSent.setMsgStatus (messageStatusString);

            session.save (smsSent);

            tx.commit ();


        } catch (Exception ex)
        {
            if (tx != null)
            {
                tx.rollback ();
            }
            ex.printStackTrace ();
            Logger.getLogger (SendMessageSaveDao.class.getName ()).log (Level.SEVERE, null, ex);
        } finally
        {
//            session.close ();
        }



    }





    public void updateSendMessageStatus (String phoneNoPara, String messagePara, String msgStatusPara)
    {

        Session session = SessionFactoryUtil.getSession ();
        Transaction tx = null;


        String phoneNo = phoneNoPara;
        String message = messagePara;
        String msgStatus = msgStatusPara;


        try
        {

            tx = session.beginTransaction ();

            phoneNo = phoneNo.trim ();
            message = message.trim ();

            System.out.println ("phoneNo>" + phoneNo.trim ());
            System.out.println ("message>" + message.trim ());

            Criteria criteria = session.createCriteria (SmsSendEntity.class);
            criteria.add (Restrictions.eq ("recipientsmssend", phoneNo));
            criteria.add (Restrictions.eq ("messagesmssend", message));
            List<SmsSendEntity> smsSendEntityList = criteria.list ();

            for (SmsSendEntity smsSendEntityList1 : smsSendEntityList)
            {
                smsSendEntityList1.setMsgStatus (msgStatus);
                session.update (smsSendEntityList1);
            }


            tx.commit ();
            session.flush ();
            session.clear ();

        } catch (Exception ex)
        {
            if (tx != null)
            {
                tx.rollback ();
            }
            ex.printStackTrace ();
            Logger.getLogger (SendMessageSaveDao.class.getName ()).log (Level.SEVERE, null, ex);
        } finally
        {
//            session.close ();
        }

    }



}
