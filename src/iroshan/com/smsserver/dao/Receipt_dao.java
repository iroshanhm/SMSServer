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
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.smsserver.entity.MobileAccountBalances;
import iroshan.com.smsserver.pojo.Loan_pojo;
import iroshan.com.smsserver.pojo.Receipt_pojo;
import iroshan.com.smsserver.view.ApplicationDesktop;
import iroshan.com.smsserver.view.WaitingDialog;
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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

/**
 *
 * @author Iroshan
 */
public class Receipt_dao {

    boolean aFlag_Waiting_Dialog = true;

    // ***** WAITING DIALOG - START *****************
    WaitingDialog waitingDialog;
    Thread trdWaiting;
    // ***** WAITING DIALOG - END *****************

    public Receipt_dao() {

    }

    public Receipt_dao(boolean aFlag_Waiting_Dialog_Para) {
        aFlag_Waiting_Dialog = aFlag_Waiting_Dialog_Para;
    }

    public Map<String, Receipt_pojo> getNewReceiptsList(Date fromDatePara, Date toDatePara, String brCodePara) {

        Date fromDate = fromDatePara;
        Date toDate = toDatePara;
        String brCode = brCodePara;
        brCode = brCode.trim();

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

        Loan_dao loan_dao = new Loan_dao();
        Map<String, Receipt_pojo> treeMap = new TreeMap();

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        try {

            try {
                fromDate = MyDateAndTimeUtil.getFromtedDateByStringPara(MyDateAndTimeUtil.getFormatedDateAsStringAndTime(fromDate));
                toDate = MyDateAndTimeUtil.getFromtedDateByStringPara(MyDateAndTimeUtil.getFormatedDateAsStringAndTime(toDate));
            } catch (ParseException ex) {
                Logger.getLogger(HistrySendMessageDao.class.getName()).log(Level.SEVERE, null, ex);
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Date fromDate2 = cal.getTime();
            System.out.println("fromDate>" + fromDate);

            cal.setTime(toDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date toDate2 = cal.getTime();
            System.out.println("toDate>" + toDate);

            String dateFrom = MyDateAndTimeUtil.getFormatedDateAsString(fromDate2);
            String toFrom = MyDateAndTimeUtil.getFormatedDateAsString(toDate2);

            System.out.println("dateFrom-->" + dateFrom);
            System.out.println("toFrom-->" + toFrom);

            tx = session.beginTransaction();

            String sqlString = "SELECT RECEIPT.RE_CODE AS RE_CODE, RECEIPT.RE_CUSTOMER AS RE_CUSTOMER,CUSTOMER.CM_DESC AS CUS_NAME, CUSTOMER.CM_NAME_ENGLISH AS CM_NAME_ENGLISH, CUSTOMER.CM_OFFICE_NO,RECEIPT.RE_DATE, RE_DTAIL.RD_VALUE, RE_DTAIL.RD_CHEQUE, CUSTOMER.CM_DESC, RE_INCOME.RI_VALUE AS RI_VALUE, RE_INCOME.RI_TYPE AS RI_TYPE, "
                    + "INCOME_EXPENCE_SUB.IE_DESC, 'RECEIPT' AS WHAT, INCOME_EXPENCE_SUB.IE_CODE AS IE_CODE, SUBSTRING(INCOME_EXPENCE_SUB.IE_CODE, 1, 4) AS MCODE, "
                    + "INCOME_EXPENCE.IE_DESC AS DESC2, INCOME_EXPENCE.IE_MTYPE AS IE_MTYPE, RE_INCOME.RI_DESC, RECEIPT.RE_CODE AS RE_CODE2, RECEIPT.RE_CODE AS RE_CODE3, "
                    + "INCOME_EXPENCE.IE_ACCOUNT_DIVISION, RECEIPT.WHO1, RECEIPT.is_sms_sent, CUSTOMER.CM_TELE AS CM_TELE "
                    + "FROM RECEIPT AS RECEIPT INNER JOIN "
                    + "RE_DTAIL AS RE_DTAIL ON RECEIPT.RE_CODE = RE_DTAIL.RD_CODE INNER JOIN "
                    + "CUSTOMER AS CUSTOMER ON RECEIPT.RE_CUSTOMER = CUSTOMER.CM_CODE INNER JOIN "
                    + "RE_INCOME AS RE_INCOME ON RE_DTAIL.RD_CODE = RE_INCOME.RI_CODE AND RE_DTAIL.RD_CHEQUE = RE_INCOME.RI_CHEQUE INNER JOIN "
                    + "INCOME_EXPENCE_SUB AS INCOME_EXPENCE_SUB ON RE_INCOME.RI_INCOME = INCOME_EXPENCE_SUB.IE_CODE INNER JOIN "
                    + "INCOME_EXPENCE ON INCOME_EXPENCE_SUB.IE_MAIN = INCOME_EXPENCE.IE_CODE "
                    + "WHERE (RECEIPT.is_sms_sent = N'N') OR (RECEIPT.is_sms_sent IS NULL) AND RE_DATE BETWEEN '" + dateFrom + "' AND '" + toFrom + "'";

            if (brCodePara.isEmpty()) {

            } else {
                sqlString = sqlString.concat(" AND RECEIPT.RE_CODE LIKE '"+brCode+"-%'");

            }
            sqlString = sqlString.concat(" ORDER BY RECEIPT.RE_CODE,INCOME_EXPENCE_SUB.IE_CODE");

//AND RE_DATE BETWEEN '01/01/2016' AND '04/04/2016' 
            SQLQuery sqlQuery = session.createSQLQuery(sqlString);

            sqlQuery.addScalar("RE_CODE", StringType.INSTANCE);
            sqlQuery.addScalar("RE_CUSTOMER", StringType.INSTANCE);
            sqlQuery.addScalar("CUS_NAME", StringType.INSTANCE);
            sqlQuery.addScalar("CM_NAME_ENGLISH", StringType.INSTANCE);
            sqlQuery.addScalar("CM_OFFICE_NO", StringType.INSTANCE);
            sqlQuery.addScalar("RE_DATE", DateType.INSTANCE);
            sqlQuery.addScalar("IE_CODE", StringType.INSTANCE);
            sqlQuery.addScalar("CM_TELE", StringType.INSTANCE);
            sqlQuery.addScalar("RI_VALUE", DoubleType.INSTANCE);
            sqlQuery.addScalar("RI_TYPE", IntegerType.INSTANCE);
            sqlQuery.addScalar("IE_MTYPE", IntegerType.INSTANCE);

            sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = sqlQuery.list();

            List<Receipt_ent> resultList = sqlQuery.list();
            int listSize = resultList.size();

            if (Company_Profile.getReceiptMessageType().equalsIgnoreCase(Company_Profile.ReceiptMessageTypeEnum.N.toString())) {
                for (Object object : data) {
                    Map row = (Map) object;

                    Integer riType = (Integer) row.get("RI_TYPE"); // 1 = Capital / 2 = Interest / 3 = fine
                    Integer mType = (Integer) row.get("IE_MTYPE"); // 1 = Loan / 2 = Saving
                    String ieCode = (String) row.get("IE_CODE");

                    waitingDialog.setCustomMessage("Processing : " + ieCode);

                    String REcODE = (String) row.get("RE_CODE");
                    String cus_name = (String) row.get("CUS_NAME");
                    String cus_name_english = (String) row.get("CM_NAME_ENGLISH");

                    String cusOfficeNo = (String) row.get("CM_OFFICE_NO");
                    Date reDATE = (Date) row.get("RE_DATE");
                    String cmTELE = (String) row.get("CM_TELE");
                    String reCUSTOMER = (String) row.get("RE_CUSTOMER");
                    Double riValue = (Double) row.get("RI_VALUE");

//            Double sumValue = 0.00;
                    if (mType == 1) // RECEIPT FOR LOAN
                    {

                        boolean contain = treeMap.containsKey(REcODE);

                        // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        if (contain) {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;

                            Double val2 = rePojo.getReceiptAmount();

                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

                        } else {

                            Double val1 = riValue;

                            Receipt_pojo rePojo = new Receipt_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
                            Double loanOutstanding = null;
                            Double loanArrears = null;

                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                            if (mobileAccountBalances != null) {
                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                                loanArrears = arrCapital + arrInterest;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                            }

                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setACNumber(ieCode);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);

                            treeMap.put(REcODE, rePojo);

                        }

                        // RECEIPT FOR SAVING
                    } else if (mType == 2) {

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain)// FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;
                            Double val2 = rePojo.getReceiptAmount();
                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

//                                                Double val1 = riValue;
//                        Receipt_pojo rePojo = new Receipt_pojo ();
////                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setLoanOrSaving (mType);
////                rePojo.setDueAmount (loanOutstanding);
//                        treeMap.put (ieCode, rePojo);
                        } else {

                            Double loanArrears = null;
                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                            if (mobileAccountBalances != null) {
                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                                loanArrears = arrCapital + arrInterest;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                            }
//                        Double val1 = riValue;
//
//
//                        Receipt_pojo rePojo = new Receipt_pojo ();
//
//                        Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
//                        Double loanOutstanding = loanPojo.getTotalOutstanding ();
//
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setDueAmount (loanOutstanding);
//                        rePojo.setLoanOrSaving (mType);
//
//                        treeMap.put (ieCode, rePojo);
                            Double val1 = riValue;
                            Receipt_pojo rePojo = new Receipt_pojo();
//                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setACNumber(ieCode);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);
                            rePojo.setArrearsAmount(loanArrears);
//                rePojo.setDueAmount (loanOutstanding);
                            treeMap.put(REcODE, rePojo);

                        }
                    } else if (mType == 3) { // RECEIPT FOR LOAN ****************

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain) // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;

                            Double val2 = rePojo.getReceiptAmount();

                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

                        } else {

                            Double val1 = riValue;

                            Receipt_pojo rePojo = new Receipt_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
                            Double loanOutstanding = null;
                            Double loanArrears = null;

                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                            if (mobileAccountBalances != null) {
                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                                loanArrears = arrCapital + arrInterest;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                            }

                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setACNumber(ieCode);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);

                            treeMap.put(REcODE, rePojo);

                        }

                    } else if (mType == 4) { // RECEIPT FOR LOAN ****************

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain) // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;

                            Double val2 = rePojo.getReceiptAmount();

                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

                        } else {

                            Double val1 = riValue;

                            Receipt_pojo rePojo = new Receipt_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
                            Double loanOutstanding = null;
                            Double loanArrears = null;

                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                            if (mobileAccountBalances != null) {
                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                                loanArrears = arrCapital + arrInterest;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                            }

                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setACNumber(ieCode);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);

                            treeMap.put(REcODE, rePojo);

                        }

                    }
                }
            }

            if (Company_Profile.getReceiptMessageType() == "RA") {

                for (Object object : data) {
                    Map row = (Map) object;

                    Integer riType = (Integer) row.get("RI_TYPE"); // 1 = Capital / 2 = Interest / 3 = fine
                    Integer mType = (Integer) row.get("IE_MTYPE"); // 1 = Loan / 2 = Saving
                    String ieCode = (String) row.get("IE_CODE");
                    System.out.println("ieCode>>" + ieCode);

                    int numOfChar = ieCode.length();
                    String sub = ieCode.substring(5, numOfChar);
                    System.out.println("sub----------------------" + sub);

                    waitingDialog.setCustomMessage("Processing : " + sub);

                    Double loanOutstanding = null;
                    Double loanArrears = null;
                    Date fineDate = null;
                    String fineDateStr = "";

                    Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                    crqPrintInExp3.add(Restrictions.like("maCode", sub));
                    MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                    if (mobileAccountBalances != null) {
                        double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                        double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                        double arrFine = mobileAccountBalances.getMaLoanArrearsfine();
                        fineDate = mobileAccountBalances.getWhen1();
                        fineDateStr = MyDateAndTimeUtil.getFormatedDateAsString(fineDate);

                        System.out.println("arrCapital>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + arrCapital);
                        System.out.println("arrInterest>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + arrInterest);

                        loanOutstanding = mobileAccountBalances.getMaLoanOutstanding();
                        loanArrears = arrCapital + arrInterest + arrFine;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                    }

                    String REcODE = (String) row.get("RE_CODE");
                    System.out.println("REcODE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + REcODE);
                    String cus_name = (String) row.get("CUS_NAME");
                    String cus_name_english = (String) row.get("CM_NAME_ENGLISH");
                    String cusOfficeNo = (String) row.get("CM_OFFICE_NO");
                    Date reDATE = (Date) row.get("RE_DATE");
                    String cmTELE = (String) row.get("CM_TELE");
                    String reCUSTOMER = (String) row.get("RE_CUSTOMER");
                    Double riValue = (Double) row.get("RI_VALUE");

//            Double sumValue = 0.00;
                    if (mType == 1) // RECEIPT FOR LOAN
                    {

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain) // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;

                            Double val2 = rePojo.getReceiptAmount();

                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

                        } else {

                            Double val1 = riValue;

                            Receipt_pojo rePojo = new Receipt_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails(reDATE, ieCode);
//                            Double loanOutstanding = loanPojo.getTotalOutstanding();
//                            Double loanArrears = loanPojo.getTotalArears();
                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setACNumber(ieCode);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);

                            treeMap.put(REcODE, rePojo);

                        }

                    } else if (mType == 2) // RECEIPT FOR SAVING
                    {

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain)// FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;
                            Double val2 = rePojo.getReceiptAmount();
                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

//                                                Double val1 = riValue;
//                        Receipt_pojo rePojo = new Receipt_pojo ();
////                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setLoanOrSaving (mType);
////                rePojo.setDueAmount (loanOutstanding);
//                        treeMap.put (ieCode, rePojo);
                        } else {

//                            Double loanOutstanding = null;
//                            Double loanArrears = null;
//
//                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
//                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
//                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
//                            if (mobileAccountBalances != null) {
//                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
//                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
//
//                                loanOutstanding = mobileAccountBalances.getMaLoanOutstanding();
//                                loanArrears = arrCapital + arrInterest;
//
////                                mobileAccountBalances.getMaLoanArrearsCapital();
//                            }
//                        Double val1 = riValue;
//
//
//                        Receipt_pojo rePojo = new Receipt_pojo ();
//
//                        Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
//                        Double loanOutstanding = loanPojo.getTotalOutstanding ();
//
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setDueAmount (loanOutstanding);
//                        rePojo.setLoanOrSaving (mType);
//
//                        treeMap.put (ieCode, rePojo);
                            Double val1 = riValue;
                            Receipt_pojo rePojo = new Receipt_pojo();
//                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setACNumber(ieCode);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);
//                rePojo.setDueAmount (loanOutstanding);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            treeMap.put(REcODE, rePojo);

                        }
                    }
                }
            }

//            tx.commit ();
            waitingDialog.dispose();

        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
        } finally {
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

        return treeMap;
    }

    public Map<String, Receipt_pojo> getNewReceiptsList() {

        if (aFlag_Waiting_Dialog) {
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
        }

        Loan_dao loan_dao = new Loan_dao();
        Map<String, Receipt_pojo> treeMap = new TreeMap();

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            String sqlString = "SELECT RECEIPT.RE_CODE AS RE_CODE, RECEIPT.RE_CUSTOMER AS RE_CUSTOMER,CUSTOMER.CM_DESC AS CUS_NAME, CUSTOMER.CM_NAME_ENGLISH AS CM_NAME_ENGLISH, CUSTOMER.CM_OFFICE_NO,RECEIPT.RE_DATE, RE_DTAIL.RD_VALUE, RE_DTAIL.RD_CHEQUE, CUSTOMER.CM_DESC, RE_INCOME.RI_VALUE AS RI_VALUE, RE_INCOME.RI_TYPE AS RI_TYPE, "
                    + "INCOME_EXPENCE_SUB.IE_DESC, 'RECEIPT' AS WHAT, INCOME_EXPENCE_SUB.IE_CODE AS IE_CODE, SUBSTRING(INCOME_EXPENCE_SUB.IE_CODE, 1, 4) AS MCODE, "
                    + "INCOME_EXPENCE.IE_DESC AS DESC2, INCOME_EXPENCE.IE_MTYPE AS IE_MTYPE, RE_INCOME.RI_DESC, RECEIPT.RE_CODE AS RE_CODE2, RECEIPT.RE_CODE AS RE_CODE3, "
                    + "INCOME_EXPENCE.IE_ACCOUNT_DIVISION, RECEIPT.WHO1, RECEIPT.is_sms_sent, CUSTOMER.CM_TELE AS CM_TELE "
                    + "FROM RECEIPT AS RECEIPT INNER JOIN "
                    + "RE_DTAIL AS RE_DTAIL ON RECEIPT.RE_CODE = RE_DTAIL.RD_CODE INNER JOIN "
                    + "CUSTOMER AS CUSTOMER ON RECEIPT.RE_CUSTOMER = CUSTOMER.CM_CODE INNER JOIN "
                    + "RE_INCOME AS RE_INCOME ON RE_DTAIL.RD_CODE = RE_INCOME.RI_CODE AND RE_DTAIL.RD_CHEQUE = RE_INCOME.RI_CHEQUE INNER JOIN "
                    + "INCOME_EXPENCE_SUB AS INCOME_EXPENCE_SUB ON RE_INCOME.RI_INCOME = INCOME_EXPENCE_SUB.IE_CODE INNER JOIN "
                    + "INCOME_EXPENCE ON INCOME_EXPENCE_SUB.IE_MAIN = INCOME_EXPENCE.IE_CODE "
                    + "WHERE (RECEIPT.is_sms_sent = N'N') OR (RECEIPT.is_sms_sent IS NULL) ORDER BY RECEIPT.RE_CODE,INCOME_EXPENCE_SUB.IE_CODE";

//AND RE_DATE BETWEEN '01/01/2016' AND '04/04/2016' 
            SQLQuery sqlQuery = session.createSQLQuery(sqlString);

            sqlQuery.addScalar("RE_CODE", StringType.INSTANCE);
            sqlQuery.addScalar("RE_CUSTOMER", StringType.INSTANCE);
            sqlQuery.addScalar("CUS_NAME", StringType.INSTANCE);
            sqlQuery.addScalar("CM_NAME_ENGLISH", StringType.INSTANCE);
            sqlQuery.addScalar("CM_OFFICE_NO", StringType.INSTANCE);
            sqlQuery.addScalar("RE_DATE", DateType.INSTANCE);
            sqlQuery.addScalar("IE_CODE", StringType.INSTANCE);
            sqlQuery.addScalar("CM_TELE", StringType.INSTANCE);
            sqlQuery.addScalar("RI_VALUE", DoubleType.INSTANCE);
            sqlQuery.addScalar("RI_TYPE", IntegerType.INSTANCE);
            sqlQuery.addScalar("IE_MTYPE", IntegerType.INSTANCE);

            sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = sqlQuery.list();

            List<Receipt_ent> resultList = sqlQuery.list();
            int listSize = resultList.size();

            if (Company_Profile.getReceiptMessageType().equalsIgnoreCase(Company_Profile.ReceiptMessageTypeEnum.N.toString())) {
                for (Object object : data) {
                    Map row = (Map) object;

                    Integer riType = (Integer) row.get("RI_TYPE"); // 1 = Capital / 2 = Interest / 3 = fine
                    Integer mType = (Integer) row.get("IE_MTYPE"); // 1 = Loan / 2 = Saving
                    String ieCode = (String) row.get("IE_CODE");

                    if (aFlag_Waiting_Dialog) {
                        waitingDialog.setCustomMessage("Processing : " + ieCode);
                    }

                    String REcODE = (String) row.get("RE_CODE");
                    String cus_name = (String) row.get("CUS_NAME");
                    String cus_name_english = (String) row.get("CM_NAME_ENGLISH");

                    String cusOfficeNo = (String) row.get("CM_OFFICE_NO");
                    Date reDATE = (Date) row.get("RE_DATE");
                    String cmTELE = (String) row.get("CM_TELE");
                    String reCUSTOMER = (String) row.get("RE_CUSTOMER");
                    Double riValue = (Double) row.get("RI_VALUE");

//            Double sumValue = 0.00;
                    if (mType == 1) // RECEIPT FOR LOAN
                    {

                        boolean contain = treeMap.containsKey(REcODE);

                        // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        if (contain) {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;

                            Double val2 = rePojo.getReceiptAmount();

                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

                        } else {

                            Double val1 = riValue;

                            Receipt_pojo rePojo = new Receipt_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
                            Double loanOutstanding = null;
                            Double loanArrears = null;

                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                            if (mobileAccountBalances != null) {
                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                                loanArrears = arrCapital + arrInterest;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                            }

                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setACNumber(ieCode);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);

                            treeMap.put(REcODE, rePojo);

                        }

                        // RECEIPT FOR SAVING
                    } else if (mType == 2) {

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain)// FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;
                            Double val2 = rePojo.getReceiptAmount();
                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

//                                                Double val1 = riValue;
//                        Receipt_pojo rePojo = new Receipt_pojo ();
////                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setLoanOrSaving (mType);
////                rePojo.setDueAmount (loanOutstanding);
//                        treeMap.put (ieCode, rePojo);
                        } else {

                            Double loanArrears = null;
                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                            if (mobileAccountBalances != null) {
                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                                loanArrears = arrCapital + arrInterest;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                            }
//                        Double val1 = riValue;
//
//
//                        Receipt_pojo rePojo = new Receipt_pojo ();
//
//                        Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
//                        Double loanOutstanding = loanPojo.getTotalOutstanding ();
//
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setDueAmount (loanOutstanding);
//                        rePojo.setLoanOrSaving (mType);
//
//                        treeMap.put (ieCode, rePojo);
                            Double val1 = riValue;
                            Receipt_pojo rePojo = new Receipt_pojo();
//                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setACNumber(ieCode);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);
                            rePojo.setArrearsAmount(loanArrears);
//                rePojo.setDueAmount (loanOutstanding);
                            treeMap.put(REcODE, rePojo);

                        }
                    } else if (mType == 3) { // RECEIPT FOR LOAN ****************

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain) // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;

                            Double val2 = rePojo.getReceiptAmount();

                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

                        } else {

                            Double val1 = riValue;

                            Receipt_pojo rePojo = new Receipt_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
                            Double loanOutstanding = null;
                            Double loanArrears = null;

                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                            if (mobileAccountBalances != null) {
                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                                loanArrears = arrCapital + arrInterest;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                            }

                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setACNumber(ieCode);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);

                            treeMap.put(REcODE, rePojo);

                        }

                    } else if (mType == 4) { // RECEIPT FOR LOAN ****************

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain) // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;

                            Double val2 = rePojo.getReceiptAmount();

                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

                        } else {

                            Double val1 = riValue;

                            Receipt_pojo rePojo = new Receipt_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
                            Double loanOutstanding = null;
                            Double loanArrears = null;

                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                            if (mobileAccountBalances != null) {
                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                                loanArrears = arrCapital + arrInterest;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                            }

                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setACNumber(ieCode);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);

                            treeMap.put(REcODE, rePojo);

                        }

                    }
                }
            }

            if (Company_Profile.getReceiptMessageType() == "RA") {

                for (Object object : data) {
                    Map row = (Map) object;

                    Integer riType = (Integer) row.get("RI_TYPE"); // 1 = Capital / 2 = Interest / 3 = fine
                    Integer mType = (Integer) row.get("IE_MTYPE"); // 1 = Loan / 2 = Saving
                    String ieCode = (String) row.get("IE_CODE");
                    System.out.println("ieCode>>" + ieCode);

                    int numOfChar = ieCode.length();
                    String sub = ieCode.substring(5, numOfChar);
                    System.out.println("sub----------------------" + sub);

                    if (aFlag_Waiting_Dialog) {
                        waitingDialog.setCustomMessage("Processing : " + sub);
                    }

                    Double loanOutstanding = null;
                    Double loanArrears = null;
                    Date fineDate = null;
                    String fineDateStr = "";

                    Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                    crqPrintInExp3.add(Restrictions.like("maCode", sub));
                    MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                    if (mobileAccountBalances != null) {
                        double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                        double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                        double arrFine = mobileAccountBalances.getMaLoanArrearsfine();
                        fineDate = mobileAccountBalances.getWhen1();
                        fineDateStr = MyDateAndTimeUtil.getFormatedDateAsString(fineDate);

                        System.out.println("arrCapital>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + arrCapital);
                        System.out.println("arrInterest>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + arrInterest);

                        loanOutstanding = mobileAccountBalances.getMaLoanOutstanding();
                        loanArrears = arrCapital + arrInterest + arrFine;

//                                mobileAccountBalances.getMaLoanArrearsCapital();
                    }

                    String REcODE = (String) row.get("RE_CODE");
                    System.out.println("REcODE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + REcODE);
                    String cus_name = (String) row.get("CUS_NAME");
                    String cus_name_english = (String) row.get("CM_NAME_ENGLISH");
                    String cusOfficeNo = (String) row.get("CM_OFFICE_NO");
                    Date reDATE = (Date) row.get("RE_DATE");
                    String cmTELE = (String) row.get("CM_TELE");
                    String reCUSTOMER = (String) row.get("RE_CUSTOMER");
                    Double riValue = (Double) row.get("RI_VALUE");

//            Double sumValue = 0.00;
                    if (mType == 1) // RECEIPT FOR LOAN
                    {

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain) // FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;

                            Double val2 = rePojo.getReceiptAmount();

                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

                        } else {

                            Double val1 = riValue;

                            Receipt_pojo rePojo = new Receipt_pojo();

//                            Loan_pojo loanPojo = loan_dao.getLoanDetails(reDATE, ieCode);
//                            Double loanOutstanding = loanPojo.getTotalOutstanding();
//                            Double loanArrears = loanPojo.getTotalArears();
                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setACNumber(ieCode);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);

                            treeMap.put(REcODE, rePojo);

                        }

                    } else if (mType == 2) // RECEIPT FOR SAVING
                    {

                        boolean contain = treeMap.containsKey(REcODE);

                        if (contain)// FOR MANY ACCOUNT FOR SAME RECEIPT NUMBER
                        {

                            Receipt_pojo rePojo = treeMap.get(REcODE);

                            Double val1 = riValue;
                            Double val2 = rePojo.getReceiptAmount();
                            Double sum = val1 + val2;

                            rePojo.setReceiptAmount(sum);

                            treeMap.remove(ieCode);
                            treeMap.put(REcODE, rePojo);

//                                                Double val1 = riValue;
//                        Receipt_pojo rePojo = new Receipt_pojo ();
////                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setLoanOrSaving (mType);
////                rePojo.setDueAmount (loanOutstanding);
//                        treeMap.put (ieCode, rePojo);
                        } else {

//                            Double loanOutstanding = null;
//                            Double loanArrears = null;
//
//                            Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
//                            crqPrintInExp3.add(Restrictions.like("maCode", ieCode));
//                            MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
//                            if (mobileAccountBalances != null) {
//                                double arrCapital = mobileAccountBalances.getMaLoanArrearsCapital();
//                                double arrInterest = mobileAccountBalances.getMaLoanArrearsInterest();
//
//                                loanOutstanding = mobileAccountBalances.getMaLoanOutstanding();
//                                loanArrears = arrCapital + arrInterest;
//
////                                mobileAccountBalances.getMaLoanArrearsCapital();
//                            }
//                        Double val1 = riValue;
//
//
//                        Receipt_pojo rePojo = new Receipt_pojo ();
//
//                        Loan_pojo loanPojo = loan_dao.getLoanDetails (reDATE, ieCode);
//                        Double loanOutstanding = loanPojo.getTotalOutstanding ();
//
//                        rePojo.setReceiptNo (REcODE);
//                        rePojo.setCustomerCode (reCUSTOMER);
//                        rePojo.setACNumber (ieCode);
//                        rePojo.setTele (cmTELE);
//                        rePojo.setReceiptAmount (val1);
//                        rePojo.setDueAmount (loanOutstanding);
//                        rePojo.setLoanOrSaving (mType);
//
//                        treeMap.put (ieCode, rePojo);
                            Double val1 = riValue;
                            Receipt_pojo rePojo = new Receipt_pojo();
//                Double loanOutstanding = arrears_dao.getDueForLoan (ieCode);
                            rePojo.setReceiptNo(REcODE);
                            rePojo.setCustomerCode(reCUSTOMER);
                            rePojo.setCusName(cus_name);
                            rePojo.setCusNameEnglish(cus_name_english);
                            rePojo.setCusOfficeNo(cusOfficeNo);
                            rePojo.setACNumber(ieCode);
                            rePojo.setTele(cmTELE);
                            rePojo.setReceiptAmount(val1);
                            rePojo.setLoanOrSaving(mType);
                            rePojo.setReceiptDate(reDATE);
//                rePojo.setDueAmount (loanOutstanding);
                            rePojo.setOutstandingAmount(loanOutstanding);
                            rePojo.setArrearsAmount(loanArrears);
                            treeMap.put(REcODE, rePojo);

                        }
                    }
                }
            }

//            tx.commit ();
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

                System.out.println("string>>" + string);

                String sqlUpdate = "UPDATE RECEIPT SET IS_SMS_SENT = 'Y' WHERE RE_CODE LIKE '" + string + "'";
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
            MyMessagesUtility.showError("ALTER TABLE RECEIPT NOT SUCCESS.\n" + e.getMessage());
        } finally {
//            session.close ();
        }
    }

}
