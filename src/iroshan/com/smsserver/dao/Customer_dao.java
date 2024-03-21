/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.Company_Profile;
import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.entity.SmsSendEntity;
import iroshan.com.smsserver.entity.VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity;
import iroshan.com.smsserver.pojo.MessageToMemeberSearch_pojo;
import iroshan.com.smsserver.view.ApplicationDesktop;
import iroshan.com.smsserver.view.WaitingDialogSMSSend;
import iroshan.com.smsserver.view.WaitingDialogView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Iroshan
 */
public class Customer_dao {

    public List<MessageToMemeberSearch_pojo> searchCustomer(String centerPara, String namePara, String telePara, String subACOfficeNoPara, Date birthdayPara, String sortByPara, String code_prefixPara) {

        WaitingDialogView waitingDialog = new WaitingDialogView();
        waitingDialog.setCustomMessage("Wait...");
        waitingDialog.setVisible(true);

        List<MessageToMemeberSearch_pojo> messageToMemeberSearchPojoList = new ArrayList<>();

        String center = centerPara;
        String name = namePara;
        String tele = telePara;
        String subACOfficeNo = subACOfficeNoPara;
        Date birthday = birthdayPara;
        String sortOrder = sortByPara;
        String code_prefix = code_prefixPara;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.class);

            if (!center.isEmpty()) {
                if (!center.equalsIgnoreCase("ALL")) {
                    center = "%".concat(center).concat("%");
                    criteria.add(Restrictions.like("cluster_code", center.trim()));
                }
            }

            if (!code_prefix.isEmpty()) {
                code_prefix = code_prefix + "%";
                criteria.add(Restrictions.like("CM_CODE", code_prefix.trim()));
            } else {
                code_prefix = "%";
                criteria.add(Restrictions.like("CM_CODE", code_prefix.trim()));
            }

            if (!name.isEmpty()) {
                if (!name.equalsIgnoreCase("ALL")) {
                    name = "%".concat(name).concat("%");
                    criteria.add(Restrictions.like("CM_DESC", name.trim()));
                }
            }

            if (!tele.isEmpty()) {
                if (!tele.equalsIgnoreCase("ALL")) {
                    tele = "%".concat(tele).concat("%");
                    criteria.add(Restrictions.like("CM_TELE", tele.trim()));
                }
            }

            if (!subACOfficeNo.isEmpty()) {
                if (!subACOfficeNo.equalsIgnoreCase("ALL")) {
                    subACOfficeNo = "%".concat(subACOfficeNo).concat("%");
                    criteria.add(Restrictions.like("CM_OFFICE_NO", subACOfficeNo.trim()));
                }
            }

            if (!subACOfficeNo.isEmpty()) {
                if (!subACOfficeNo.equalsIgnoreCase("ALL")) {
                    subACOfficeNo = "%".concat(subACOfficeNo).concat("%");
                    criteria.add(Restrictions.like("CM_OFFICE_NO", subACOfficeNo.trim()));
                }
            }

            if (!sortOrder.isEmpty()) {
                if (sortOrder.equalsIgnoreCase("DEFAULT")) {

                } else if (sortOrder.equalsIgnoreCase("CODE")) {
                    criteria.addOrder(Order.asc("CM_CODE"));
                } else if (sortOrder.equalsIgnoreCase("NAME")) {
                    criteria.addOrder(Order.asc("CM_DESC"));
                } else if (sortOrder.equalsIgnoreCase("SUB A/C OFFICE NO")) {
                    criteria.addOrder(Order.asc("CM_OFFICE_NO"));
                } else if (sortOrder.equalsIgnoreCase("TELE.")) {
                    criteria.addOrder(Order.asc("CM_TELE"));
                } else if (sortOrder.equalsIgnoreCase("BIRTHDAY")) {
                    criteria.addOrder(Order.asc("CM_BDATE"));
                }
            }

            List<VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity> list = criteria.list();

            if (birthday == null) {
                for (VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity : list) {
//                    "SELECT", "#", "CODE", "NAME", "SUB A/C OFFICE NO", "TELE.", "BIRTHDAY"
                    String code = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_CODE();
                    String cmDescript = "";
                    if (Company_Profile.getLanguage().equalsIgnoreCase(Company_Profile.LanguageEnum.SINHALA.toString())) {
                        cmDescript = "Customer";
                    } else {
                        cmDescript = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_DESC();
                    }

                    String cmOfficeNo = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_OFFICE_NO();
                    String cmTele = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_TELE();
                    Date cmBirthday = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_BDATE();

                    MessageToMemeberSearch_pojo messageToMemeberSearch_pojo = new MessageToMemeberSearch_pojo();
                    messageToMemeberSearch_pojo.setIsSelect(true);
                    messageToMemeberSearch_pojo.setCode(code);
                    messageToMemeberSearch_pojo.setName(cmDescript);
                    messageToMemeberSearch_pojo.setSubACOfficeNo(cmOfficeNo);
                    messageToMemeberSearch_pojo.setTele(cmTele);
                    messageToMemeberSearch_pojo.setBirthday(cmBirthday);

                    messageToMemeberSearchPojoList.add(messageToMemeberSearch_pojo);
                }
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(birthday);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                Integer selectedYear = cal.get(Calendar.YEAR);
                birthday = cal.getTime();
                birthday = MyDateAndTimeUtil.getFormatedDate2(birthday);

                for (VIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity : list) {
//               "SELECT", "#", "CODE", "NAME", "SUB A/C OFFICE NO", "TELE.", "BIRTHDAY"
                    Date cmBirthdayOriginal = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_BDATE();

                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(cmBirthdayOriginal);
                    cal2.set(Calendar.YEAR, selectedYear);
                    cal2.set(Calendar.HOUR_OF_DAY, 0);
                    cal2.set(Calendar.MINUTE, 0);
                    cal2.set(Calendar.SECOND, 0);
                    Date cmBirthday = cal2.getTime();

                    cmBirthday = MyDateAndTimeUtil.getFormatedDate2(cmBirthday);

                    System.out.println(cmBirthday + " / " + birthday);
                    System.out.println(cmBirthday.equals(birthday));

                    if (cmBirthday.equals(birthday)) {

                        System.out.println("lllllllllllllllllllllllllll");

                        String code = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_CODE();
                        String cmDescript = "";
                        if (Company_Profile.getLanguage().equalsIgnoreCase(Company_Profile.LanguageEnum.SINHALA.toString())) {
                            cmDescript = "Customer";
                        } else {
                            cmDescript = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_DESC();
                        }

//                        String cmDescript = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_DESC();
                        String cmOfficeNo = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_OFFICE_NO();
                        String cmTele = vIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER_entity.getCM_TELE();

                        MessageToMemeberSearch_pojo messageToMemeberSearch_pojo = new MessageToMemeberSearch_pojo();
                        messageToMemeberSearch_pojo.setIsSelect(true);
                        messageToMemeberSearch_pojo.setCode(code);
                        messageToMemeberSearch_pojo.setName(cmDescript);
                        messageToMemeberSearch_pojo.setSubACOfficeNo(cmOfficeNo);
                        messageToMemeberSearch_pojo.setTele(cmTele);
                        messageToMemeberSearch_pojo.setBirthday(cmBirthdayOriginal);

                        messageToMemeberSearchPojoList.add(messageToMemeberSearch_pojo);
                    }
                }

            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();

            if (tx != null) {
                tx.rollback();
            }
        } finally {
//            session.close ();
        }

        waitingDialog.dispose();

        return messageToMemeberSearchPojoList;
    }

}
