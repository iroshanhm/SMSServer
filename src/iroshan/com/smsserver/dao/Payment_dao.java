/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.entity.Receipt_ent;
import iroshan.Company_Profile;
import iroshan.Company_Profile.PaymentMessageTypeEnum;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.pojo.Loan_pojo;
import iroshan.com.smsserver.pojo.Payment_pojo;
import iroshan.com.smsserver.view.ApplicationDesktop;
import iroshan.com.smsserver.view.WaitingDialog;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

/**
 *
 * @author Iroshan
 */
public class Payment_dao {

    // ***** WAITING DIALOG - START *****************
    WaitingDialog waitingDialog;
    Thread trdWaiting;
    // ***** WAITING DIALOG - END *****************

    boolean aFlag_Waiting_Dialog = true;

    public Payment_dao() {

    }

    public Payment_dao(boolean aFlag) {
        aFlag_Waiting_Dialog = aFlag;
    }

    public Map<String, Payment_pojo> getNewPaymentsList(String brCodePara) {

//-------------------------------------------------------        
        if (aFlag_Waiting_Dialog) {
            trdWaiting = new Thread(new Runnable() {
                @Override
                public void run() {
                    waitingDialog = new WaitingDialog("", "Starting ...", ApplicationDesktop.appDesktop, true);
                    waitingDialog.setVisible(true);
                }
            });
            trdWaiting.start();
        }

//------------------------------------------------------- 
        Loan_dao loan_dao = new Loan_dao();
        Map<String, Payment_pojo> treeMap = new TreeMap();

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String brCode = brCodePara;

        try {

            tx = session.beginTransaction();

            String sqlString = "SELECT PAYMENT.PA_CODE, PAYMENT.PA_DATE, PA_DTAIL.PD_VALUE, PA_DTAIL.PD_CHEQUE, CUSTOMER.CM_CODE,CUSTOMER.CM_DESC AS CUS_NAME,CUSTOMER.CM_NAME_ENGLISH AS CM_NAME_ENGLISH,CUSTOMER.CM_OFFICE_NO, CUSTOMER.CM_DESC, PA_EXPENCE.PE_VALUE, "
                    + "INCOME_EXPENCE_SUB.IE_DESC, INCOME_EXPENCE.IE_MTYPE, INCOME_EXPENCE.IE_DESC AS MDESC, PA_EXPENCE.PE_TYPE, INCOME_EXPENCE.IE_CODE, "
                    + "INCOME_EXPENCE_SUB.IE_CODE AS CODE2, PAYMENT.WHO1, PA_EXPENCE.PE_DESC, INCOME_EXPENCE_SUB.IE_DESC AS IE_DESC_ENGLISH, '' AS DEPO_ACCOUNT, "
                    + "INCOME_EXPENCE.IE_ACCOUNT_DIVISION, PAYMENT.is_sms_sent, CUSTOMER.CM_TELE "
                    + "FROM PAYMENT AS PAYMENT INNER JOIN "
                    + "PA_DTAIL AS PA_DTAIL ON PAYMENT.PA_CODE = PA_DTAIL.PD_CODE INNER JOIN "
                    + "CUSTOMER AS CUSTOMER ON PAYMENT.PA_SUPPLIER = CUSTOMER.CM_CODE INNER JOIN "
                    + "PA_EXPENCE AS PA_EXPENCE ON PA_DTAIL.PD_CODE = PA_EXPENCE.PE_CODE AND PA_DTAIL.PD_CHEQUE = PA_EXPENCE.PE_CHEQUE INNER JOIN "
                    + "INCOME_EXPENCE_SUB AS INCOME_EXPENCE_SUB ON PA_EXPENCE.PE_EXPENCE = INCOME_EXPENCE_SUB.IE_CODE INNER JOIN "
                    + "INCOME_EXPENCE ON INCOME_EXPENCE_SUB.IE_MAIN = INCOME_EXPENCE.IE_CODE "
                    + "WHERE (PAYMENT.is_sms_sent = N'N') OR (PAYMENT.is_sms_sent IS NULL)";

            if (brCodePara.isEmpty()) {

            } else {
                sqlString = sqlString.concat(" AND CUSTOMER.CM_CODE LIKE '%-" + brCode + "-%'");

            }
            sqlString = sqlString.concat(" ORDER BY PAYMENT.PA_DATE, PAYMENT.PA_CODE, CODE2");

            System.out.println("sqlString: " + sqlString);

            SQLQuery sqlQuery = session.createSQLQuery(sqlString);

            sqlQuery.addScalar("PA_CODE", StringType.INSTANCE);
            sqlQuery.addScalar("PA_DATE", DateType.INSTANCE);
            sqlQuery.addScalar("CM_CODE", StringType.INSTANCE);
            sqlQuery.addScalar("CUS_NAME", StringType.INSTANCE);
            sqlQuery.addScalar("CM_NAME_ENGLISH", StringType.INSTANCE);
            sqlQuery.addScalar("CM_OFFICE_NO", StringType.INSTANCE);
            sqlQuery.addScalar("CODE2", StringType.INSTANCE);
            sqlQuery.addScalar("CM_TELE", StringType.INSTANCE);
            sqlQuery.addScalar("PE_VALUE", DoubleType.INSTANCE);
            sqlQuery.addScalar("PE_TYPE", IntegerType.INSTANCE);
            sqlQuery.addScalar("IE_MTYPE", IntegerType.INSTANCE);

            sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = sqlQuery.list();

//        sqlQuery.addEntity (Receipt_ent.class);
            List<Receipt_ent> resultList = sqlQuery.list();
            int listSize = resultList.size();

            if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(PaymentMessageTypeEnum.N.toString())) {
                for (Object object : data) {
                    Map row = (Map) object;

                    Integer instalmentType = (Integer) row.get("PE_TYPE"); // 1 = Capital / 2 = Interest / 3 = fine
                    Date paDate = (Date) row.get("PA_DATE");
                    Integer loanOrSaving = (Integer) row.get("IE_MTYPE"); // 1 = Loan / 2 = Saving
                    String acNumber = (String) row.get("CODE2");
                    String paymentCode = (String) row.get("PA_CODE");
                    String cusTele = (String) row.get("CM_TELE");
                    String cusCode = (String) row.get("CM_CODE");
                    String cusName = (String) row.get("CUS_NAME");
                    String cusOfficeNo = (String) row.get("CM_OFFICE_NO");
                    Double amount = (Double) row.get("PE_VALUE");
                    String cus_name_english = (String) row.get("CM_NAME_ENGLISH");

//            Double sumValue = 0.00;
                    if (loanOrSaving == 1) { // loan

                        boolean contain = treeMap.containsKey(acNumber);
                        System.out.println("contain>" + contain);
//                        if (contain) {
//
//                            Payment_pojo paPojo = treeMap.get(acNumber);
//
//                            Double val1 = amount;
//                            Double val2 = paPojo.getAmount();
//                            Double sum = val1 + val2;
//                            paPojo.setAmount(sum);
//                            
//
//                            treeMap.remove(acNumber);
//                            treeMap.put(acNumber, paPojo);
//
//                        } else {

                        Double val1 = amount;

                        Payment_pojo paPojo = new Payment_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails (paDate, acNumber);
                        Double loanOutstanding = null;

                        paPojo.setPaymentNo(paymentCode);
                        paPojo.setCusCode(cusCode);
                        paPojo.setCusOfficeNo(cusOfficeNo);
                        paPojo.setAcNumber(acNumber);
                        paPojo.setTelNo(cusTele);
                        paPojo.setCusName(cusName);
                        paPojo.setCusNameEnglish(cus_name_english);
                        paPojo.setAmount(val1);
                        paPojo.setLoanAmount(loanOutstanding);
                        paPojo.setLoanOrSaving(loanOrSaving);
                        paPojo.setPay_date(paDate);

                        treeMap.put(acNumber, paPojo);

//                        }
                    } else if (loanOrSaving == 2) {

                        boolean contain = treeMap.containsKey(acNumber);
                        System.out.println("contain>" + contain);
//                        if (contain) {
//
//                            Payment_pojo paPojo = treeMap.get(acNumber);
//
//                            Double val1 = amount;
//                            System.out.println("val1>" + val1);
//                            Double val2 = paPojo.getAmount();
//                            System.out.println("val2>" + val2);
//                            Double sum = val1 + val2;
//                            System.out.println("sum>" + sum);
//                            paPojo.setAmount(sum);
//
//                            treeMap.remove(acNumber);
//                            treeMap.put(acNumber, paPojo);
//
//                        } else {

                            Double val1 = amount;

                            Payment_pojo paPojo = new Payment_pojo();

                            paPojo.setPaymentNo(paymentCode);
                            paPojo.setCusCode(cusCode);
                            paPojo.setCusOfficeNo(cusOfficeNo);
                            paPojo.setCusName(cusName);
                            paPojo.setAcNumber(acNumber);
                            paPojo.setTelNo(cusTele);
                            paPojo.setAmount(val1);
                            paPojo.setLoanOrSaving(loanOrSaving);
                            paPojo.setPay_date(paDate);

                            treeMap.put(acNumber, paPojo);
//                        }

                    }
                }
            }

            if (Company_Profile.getPaymentMessageType().equalsIgnoreCase(PaymentMessageTypeEnum.PA.toString())) {
                for (Object object : data) {
                    Map row = (Map) object;

                    Integer instalmentType = (Integer) row.get("PE_TYPE"); // 1 = Capital / 2 = Interest / 3 = fine
                    Date paDate = (Date) row.get("PA_DATE");
                    Integer loanOrSaving = (Integer) row.get("IE_MTYPE"); // 1 = Loan / 2 = Saving
                    String acNumber = (String) row.get("CODE2");
                    String paymentCode = (String) row.get("PA_CODE");
                    String cusTele = (String) row.get("CM_TELE");
                    String cusCode = (String) row.get("CM_CODE");
                    String cusName = (String) row.get("CUS_NAME");
                    String cusOfficeNo = (String) row.get("CM_OFFICE_NO");
                    Double amount = (Double) row.get("PE_VALUE");

//            Double sumValue = 0.00;
                    if (loanOrSaving == 1) { // loan

                        boolean contain = treeMap.containsKey(acNumber);
                        System.out.println("contain>" + contain);
                        if (contain) {

                            Payment_pojo paPojo = treeMap.get(acNumber);

                            Double val1 = amount;
                            System.out.println("val1>" + val1);
                            Double val2 = paPojo.getAmount();
                            System.out.println("val2>" + val2);
                            Double sum = val1 + val2;
                            System.out.println("sum>" + sum);
                            paPojo.setAmount(sum);

                            treeMap.remove(acNumber);
                            treeMap.put(acNumber, paPojo);

                        } else {

                            Double val1 = amount;

                            Payment_pojo paPojo = new Payment_pojo();

                            Loan_pojo loanPojo = loan_dao.getLoanDetails(paDate, acNumber);
                            Double loanOutstanding = loanPojo.getTotalOutstanding();

                            paPojo.setPaymentNo(paymentCode);
                            paPojo.setCusCode(cusCode);
                            paPojo.setCusOfficeNo(cusOfficeNo);
                            paPojo.setAcNumber(acNumber);
                            paPojo.setTelNo(cusTele);
                            paPojo.setCusName(cusName);
                            paPojo.setAmount(val1);
                            paPojo.setLoanAmount(loanOutstanding);
                            paPojo.setLoanOrSaving(loanOrSaving);

                            treeMap.put(acNumber, paPojo);

                        }

                    } else if (loanOrSaving == 2) {

                        boolean contain = treeMap.containsKey(acNumber);
                        System.out.println("contain>" + contain);
                        if (contain) {

                            Payment_pojo paPojo = treeMap.get(acNumber);

                            Double val1 = amount;
                            System.out.println("val1>" + val1);
                            Double val2 = paPojo.getAmount();
                            System.out.println("val2>" + val2);
                            Double sum = val1 + val2;
                            System.out.println("sum>" + sum);
                            paPojo.setAmount(sum);

                            treeMap.remove(acNumber);
                            treeMap.put(acNumber, paPojo);

                        } else {

//                        Double val1 = amount;
//                        Payment_pojo paPojo = new Payment_pojo ();
//
//                        Loan_pojo loanPojo = loan_dao.getLoanDetails (paDate, acNumber);
//                        Double loanOutstanding = loanPojo.getTotalOutstanding ();
//
//                        paPojo.setPaymentNo (paymentCode);
//                        paPojo.setCusCode (cusCode);
//                        paPojo.setAcNumber (acNumber);
//                        paPojo.setTelNo (cusTele);
//                        paPojo.setAmount (val1);
//                        paPojo.setLoanAmount (loanOutstanding);
//                        paPojo.setLoanOrSaving (loanOrSaving);
//
//                        treeMap.put (acNumber, paPojo);
                            Double val1 = amount;

                            Payment_pojo paPojo = new Payment_pojo();

                            paPojo.setPaymentNo(paymentCode);
                            paPojo.setCusCode(cusCode);
                            paPojo.setCusOfficeNo(cusOfficeNo);
                            paPojo.setCusName(cusName);
                            paPojo.setAcNumber(acNumber);
                            paPojo.setTelNo(cusTele);
                            paPojo.setAmount(val1);
                            paPojo.setLoanOrSaving(loanOrSaving);

                            treeMap.put(acNumber, paPojo);
                        }

                    }
                }
            }

            tx.commit();

            if (aFlag_Waiting_Dialog) {
                waitingDialog.dispose();
            }
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
        } finally {
            if (aFlag_Waiting_Dialog) {
                waitingDialog.dispose();
                trdWaiting.interrupt();
            }
        }

        return treeMap;
    }

    public void markAsSend(List<String> list) {
        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            List<String> list2 = list;

            for (String string : list2) {
                String sqlUpdate = "UPDATE PAYMENT SET IS_SMS_SENT = 'Y' WHERE PA_CODE LIKE '" + string + "'";
                SQLQuery sQLQueryUpdate = session.createSQLQuery(sqlUpdate);
                sQLQueryUpdate.executeUpdate();

            }

            tx.commit();

            MyMessagesUtility.showInformation("Mark success !.");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }

            e.printStackTrace();
            MyMessagesUtility.showError("ALTER TABLE Payment NOT SUCCESS.\n" + e.getMessage());
        } finally {
//            session.close ();
        }
    }
}
