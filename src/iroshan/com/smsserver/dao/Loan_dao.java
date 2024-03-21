/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyRounding;
import iroshan.com.smsserver.entity.AccountBalanceLoanEntity;
import iroshan.com.smsserver.entity.Customer;
import iroshan.com.smsserver.entity.Customer_entity;
import iroshan.com.smsserver.entity.IncomeExpenceSubManualEntity;
import iroshan.com.smsserver.entity.MobileAccountBalances;
import iroshan.com.smsserver.entity.QprintIncomeExpence2Entity;
import iroshan.com.smsserver.entity.ZINCOME_EXPENCE_SUB_2_entity;
import iroshan.com.smsserver.entity.ZINCOME_EXPENCE_SUB_entity;
import iroshan.com.smsserver.pojo.Arrears_pojo;
import iroshan.com.smsserver.pojo.Loan_pojo;
import iroshan.com.smsserver.service.SMSMessage;
import iroshan.com.smsserver.view.ApplicationDesktop;
import iroshan.com.smsserver.view.WaitingDialog;
import iroshan.com.smsserver.view.WaitingDialogView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author Iroshan
 */
public class Loan_dao {

// ----- WAITING DIALOG - START ----------------------------
    WaitingDialog waitingDialog;

    Thread trdWaiting;

// ----- WAITING DIALOG - END ------------------------------
    public Map<String, Loan_pojo> getArrears(Date datePara) {

        WaitingDialogView waitingDialog = new WaitingDialogView();
        waitingDialog.setCustomMessage("Please Wait...");
        waitingDialog.setVisible(true);

        Date selectingDate = datePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crInExpSubManual = session.createCriteria(IncomeExpenceSubManualEntity.class);
                crInExpSubManual.add(Restrictions.like("ieCode", acCode));

                List<IncomeExpenceSubManualEntity> incExpManualList = crInExpSubManual.list();
                for (IncomeExpenceSubManualEntity incExpManualListObj : incExpManualList) {

                    Date ieDate = incExpManualListObj.getIeDate();
                    Double ieTerm = incExpManualListObj.getIeTerm();
                    Double ieInt = incExpManualListObj.getIeInt();

                    totalCapitalFullLoan = totalCapitalFullLoan + ieTerm;
                    totalInterestFullLoan = totalInterestFullLoan + ieInt;

//                  @@@@@ DUE @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(dateDue)) < 0) {
                        totalCapitalForDue = totalCapitalForDue + ieTerm;
                        totalInterestForDue = totalInterestForDue + ieInt;
                    }

//                  @@@@@ ARREARS @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(selectingDate)) < 0) {
                        totalCapitalForArrears = totalCapitalForArrears + ieTerm;
                        totalInterestForArrears = totalInterestForArrears + ieInt;
                    }
                }

                Double totalCapitalInterestFullLoan = totalCapitalFullLoan + totalInterestFullLoan;
                if (totalCapitalInterestFullLoan != 0) {

//                    Double capitalPaid = 0.00;
//                    Double interestPaid = 0.00;
//                    Double finePaid = 0.00;
                    Double totalCapitalPaid = 0.00;
                    Double totalInterestPaid = 0.00;
                    Double totalFinePaid = 0.00;

                    Criteria crqPrintInExp2 = session.createCriteria(QprintIncomeExpence2Entity.class);
                    crqPrintInExp2.add(Restrictions.like("peExpence", acCode));

                    List<QprintIncomeExpence2Entity> crqPrintInExp2List = crqPrintInExp2.list();
                    for (QprintIncomeExpence2Entity crqPrintInExp2ListObj : crqPrintInExp2List) {

                        Integer peType = crqPrintInExp2ListObj.getPeType();
                        Double q2 = crqPrintInExp2ListObj.getQ2();
                        Date paDate = crqPrintInExp2ListObj.getPaDate();
                        String reCode = crqPrintInExp2ListObj.getReCode();
                        String docType = crqPrintInExp2ListObj.getDocType();

                        if (peType == 1) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalCapitalPaid = totalCapitalPaid + q2;

                        } else if (peType == 2) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalInterestPaid = totalInterestPaid + q2;

                        } else if (peType == 3) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalFinePaid = totalFinePaid + q2;

                        }

                    }

                    String cusCode = ieMember;
                    String acNumber = acCode;
                    String custele = tele;
                    Date disburseDate = loanGrandDate;
                    Double disburseAmount = totalCapitalFullLoan;

                    Double totalCapitalAmountFullLoan = totalCapitalFullLoan;
                    Double totalInterestAmountFullLoan = totalInterestFullLoan;
                    Double totalCapitalInterstFullLoan = totalCapitalAmountFullLoan + totalInterestAmountFullLoan;

                    Double totalCapitalAmountDue = totalCapitalForDue;
                    Double totalInterestAmountDue = totalInterestForDue;

                    Double totalCapitalAmountArrears = totalCapitalForArrears;
                    Double totalInterestAmountArrears = totalInterestForArrears;

                    Double totalPaidCapitalAmount = totalCapitalPaid;
                    Double totalPaidIntersetAmount = totalInterestPaid;

                    Double outstandingCapital = totalCapitalAmountFullLoan - totalPaidCapitalAmount;
                    outstandingCapital = ((outstandingCapital < 0) ? 0.00 : outstandingCapital);
                    Double outstandingInterest = totalInterestAmountFullLoan - totalPaidIntersetAmount;
                    outstandingInterest = ((outstandingInterest < 0) ? 0.00 : outstandingInterest);
                    Double totalOutstanding = outstandingCapital + outstandingInterest;

                    Double dueCapital = totalCapitalAmountDue - totalPaidCapitalAmount;
                    dueCapital = ((dueCapital < 0) ? 0.00 : dueCapital);
                    Double dueInterest = totalInterestAmountDue - totalPaidIntersetAmount;
                    dueInterest = ((dueInterest < 0) ? 0.00 : dueInterest);
                    Double totalDue = dueCapital + dueInterest;

                    Double ArearsCapital = totalCapitalAmountArrears - totalPaidCapitalAmount;
                    ArearsCapital = ((ArearsCapital < 0) ? 0.00 : ArearsCapital);
                    Double ArearsInterst = totalInterestAmountArrears - totalPaidIntersetAmount;
                    ArearsInterst = ((ArearsInterst < 0) ? 0.00 : ArearsInterst);
                    Double totalArrears = ArearsCapital + ArearsInterst;

                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(cusCode);
                    loanPojo.setTelephoneNo(custele);
                    loanPojo.setAcNumber(acNumber);
                    loanPojo.setDisburseDate(disburseDate);
                    loanPojo.setDisburseAmount(disburseAmount);

                    loanPojo.setCapitalFullLoan(totalCapitalAmountFullLoan);
                    loanPojo.setInterestFullLoan(totalInterestAmountFullLoan);
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(outstandingCapital);
                    loanPojo.setOutstandingInterest(outstandingInterest);
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(dueCapital);
                    loanPojo.setDueInterest(dueInterest);
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(ArearsCapital);
                    loanPojo.setArearsInterst(ArearsInterst);
                    loanPojo.setTotalArears(totalArrears);

                    returnTreeMap.put(acNumber, loanPojo);

                }

            }

            tx.commit();

            waitingDialog.dispose();

            return returnTreeMap;

        } catch (HibernateException ex) {

            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
            return null;

        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());
            return null;
        } finally {

//            session.close ();
        }

    }

    public Map<String, Loan_pojo> getArrears2(Date datePara) {

        WaitingDialogView waitingDialog = new WaitingDialogView();
        waitingDialog.setCustomMessage("Please Wait...");
        waitingDialog.setVisible(true);

        Date selectingDate = datePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crInExpSubManual = session.createCriteria(IncomeExpenceSubManualEntity.class);
                crInExpSubManual.add(Restrictions.like("ieCode", acCode));

                List<IncomeExpenceSubManualEntity> incExpManualList = crInExpSubManual.list();
                for (IncomeExpenceSubManualEntity incExpManualListObj : incExpManualList) {

                    Date ieDate = incExpManualListObj.getIeDate();
                    Double ieTerm = incExpManualListObj.getIeTerm();
                    Double ieInt = incExpManualListObj.getIeInt();

                    totalCapitalFullLoan = totalCapitalFullLoan + ieTerm;
                    totalInterestFullLoan = totalInterestFullLoan + ieInt;

//                  @@@@@ DUE @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(dateDue)) < 0) {
                        totalCapitalForDue = totalCapitalForDue + ieTerm;
                        totalInterestForDue = totalInterestForDue + ieInt;
                    }

//                  @@@@@ ARREARS @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(selectingDate)) < 0) {
                        totalCapitalForArrears = totalCapitalForArrears + ieTerm;
                        totalInterestForArrears = totalInterestForArrears + ieInt;
                    }
                }

                Double totalCapitalInterestFullLoan = totalCapitalFullLoan + totalInterestFullLoan;
                if (totalCapitalInterestFullLoan != 0) {

//                    Double capitalPaid = 0.00;
//                    Double interestPaid = 0.00;
//                    Double finePaid = 0.00;
                    Double totalCapitalPaid = 0.00;
                    Double totalInterestPaid = 0.00;
                    Double totalFinePaid = 0.00;

                    Criteria crqPrintInExp2 = session.createCriteria(QprintIncomeExpence2Entity.class);
                    crqPrintInExp2.add(Restrictions.like("peExpence", acCode));

                    List<QprintIncomeExpence2Entity> crqPrintInExp2List = crqPrintInExp2.list();
                    for (QprintIncomeExpence2Entity crqPrintInExp2ListObj : crqPrintInExp2List) {

                        Integer peType = crqPrintInExp2ListObj.getPeType();
                        Double q2 = crqPrintInExp2ListObj.getQ2();
                        Date paDate = crqPrintInExp2ListObj.getPaDate();
                        String reCode = crqPrintInExp2ListObj.getReCode();
                        String docType = crqPrintInExp2ListObj.getDocType();

                        if (peType == 1) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalCapitalPaid = totalCapitalPaid + q2;

                        } else if (peType == 2) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalInterestPaid = totalInterestPaid + q2;

                        } else if (peType == 3) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalFinePaid = totalFinePaid + q2;

                        }

                    }

                    String cusCode = ieMember;
                    String acNumber = acCode;
                    String custele = tele;
                    Date disburseDate = loanGrandDate;
                    Double disburseAmount = totalCapitalFullLoan;

                    Double totalCapitalAmountFullLoan = totalCapitalFullLoan;
                    Double totalInterestAmountFullLoan = totalInterestFullLoan;
                    Double totalCapitalInterstFullLoan = totalCapitalAmountFullLoan + totalInterestAmountFullLoan;

                    Double totalCapitalAmountDue = totalCapitalForDue;
                    Double totalInterestAmountDue = totalInterestForDue;

                    Double totalCapitalAmountArrears = totalCapitalForArrears;
                    Double totalInterestAmountArrears = totalInterestForArrears;

                    Double totalPaidCapitalAmount = totalCapitalPaid;
                    Double totalPaidIntersetAmount = totalInterestPaid;

                    Double outstandingCapital = totalCapitalAmountFullLoan - totalPaidCapitalAmount;
                    outstandingCapital = ((outstandingCapital < 0) ? 0.00 : outstandingCapital);
                    Double outstandingInterest = totalInterestAmountFullLoan - totalPaidIntersetAmount;
                    outstandingInterest = ((outstandingInterest < 0) ? 0.00 : outstandingInterest);
                    Double totalOutstanding = outstandingCapital + outstandingInterest;

                    Double dueCapital = totalCapitalAmountDue - totalPaidCapitalAmount;
                    dueCapital = ((dueCapital < 0) ? 0.00 : dueCapital);
                    Double dueInterest = totalInterestAmountDue - totalPaidIntersetAmount;
                    dueInterest = ((dueInterest < 0) ? 0.00 : dueInterest);
                    Double totalDue = dueCapital + dueInterest;

                    Double ArearsCapital = totalCapitalAmountArrears - totalPaidCapitalAmount;
                    ArearsCapital = ((ArearsCapital < 0) ? 0.00 : ArearsCapital);
                    Double ArearsInterst = totalInterestAmountArrears - totalPaidIntersetAmount;
                    ArearsInterst = ((ArearsInterst < 0) ? 0.00 : ArearsInterst);
                    Double totalArrears = ArearsCapital + ArearsInterst;

                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(cusCode);
                    loanPojo.setTelephoneNo(custele);
                    loanPojo.setAcNumber(acNumber);
                    loanPojo.setDisburseDate(disburseDate);
                    loanPojo.setDisburseAmount(disburseAmount);

                    loanPojo.setCapitalFullLoan(totalCapitalAmountFullLoan);
                    loanPojo.setInterestFullLoan(totalInterestAmountFullLoan);
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(outstandingCapital);
                    loanPojo.setOutstandingInterest(outstandingInterest);
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(dueCapital);
                    loanPojo.setDueInterest(dueInterest);
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(ArearsCapital);
                    loanPojo.setArearsInterst(ArearsInterst);
                    loanPojo.setTotalArears(totalArrears);

                    if (totalArrears > 0) {
                        returnTreeMap.put(acNumber, loanPojo);
                    }

                }

            }

            tx.commit();

            waitingDialog.dispose();

            return returnTreeMap;

        } catch (HibernateException ex) {

            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
            return null;

        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());
            return null;
        } finally {

//            session.close ();
        }

    }

    public Map<String, Loan_pojo> getArrears3(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();

                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crInExpSubManual = session.createCriteria(IncomeExpenceSubManualEntity.class);
                crInExpSubManual.add(Restrictions.like("ieCode", acCode));

                Integer totalTermsForArrears = 0;

                List<Double> instalmentList = new ArrayList<Double>();

                List<IncomeExpenceSubManualEntity> incExpManualList = crInExpSubManual.list();
                for (IncomeExpenceSubManualEntity incExpManualListObj : incExpManualList) {

                    Date ieDate = incExpManualListObj.getIeDate();
                    Double ieTerm = (incExpManualListObj.getIeTerm() == null ? 0.00 : incExpManualListObj.getIeTerm());
                    Double ieInt = (incExpManualListObj.getIeInt() == null ? 0.00 : incExpManualListObj.getIeInt());

                    totalCapitalFullLoan = totalCapitalFullLoan + ieTerm;
                    totalInterestFullLoan = totalInterestFullLoan + ieInt;

//                  @@@@@ DUE @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(dateDue)) < 0) {
                        totalCapitalForDue = totalCapitalForDue + ieTerm;
                        totalInterestForDue = totalInterestForDue + ieInt;
                    }

//                  @@@@@ ARREARS @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(selectingDate)) < 0) {
                        ++totalTermsForArrears;
                        totalCapitalForArrears = totalCapitalForArrears + ieTerm;
                        totalInterestForArrears = totalInterestForArrears + ieInt;

                        Double instalment = totalCapitalForArrears + totalInterestForArrears;
                        instalmentList.add(instalment);
                    }
                }

                Double totalCapitalInterestFullLoan = totalCapitalFullLoan + totalInterestFullLoan;
                if (totalCapitalInterestFullLoan != 0) {

//                    Double capitalPaid = 0.00;
//                    Double interestPaid = 0.00;
//                    Double finePaid = 0.00;
                    Double totalCapitalPaid = 0.00;
                    Double totalInterestPaid = 0.00;
                    Double totalFinePaid = 0.00;

                    Criteria crqPrintInExp2 = session.createCriteria(QprintIncomeExpence2Entity.class);
                    crqPrintInExp2.add(Restrictions.like("peExpence", acCode));

                    List<QprintIncomeExpence2Entity> crqPrintInExp2List = crqPrintInExp2.list();
                    for (QprintIncomeExpence2Entity crqPrintInExp2ListObj : crqPrintInExp2List) {

                        Integer peType = crqPrintInExp2ListObj.getPeType();
                        Double q2 = crqPrintInExp2ListObj.getQ2();
                        Date paDate = crqPrintInExp2ListObj.getPaDate();
                        String reCode = crqPrintInExp2ListObj.getReCode();
                        String docType = crqPrintInExp2ListObj.getDocType();

                        if (peType == 1) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalCapitalPaid = totalCapitalPaid + q2;

                        } else if (peType == 2) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalInterestPaid = totalInterestPaid + q2;

                        } else if (peType == 3) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalFinePaid = totalFinePaid + q2;

                        }

                    }

                    String cusCode = ieMember;
                    String acNumber = acCode;
                    String cName = cusName;
                    String cOfficeNo = cusOfficeNo;
                    String custele = tele;
                    Date disburseDate = loanGrandDate;
                    Double disburseAmount = totalCapitalFullLoan;

                    Double totalCapitalAmountFullLoan = totalCapitalFullLoan;
                    Double totalInterestAmountFullLoan = totalInterestFullLoan;
                    Double totalCapitalInterstFullLoan = totalCapitalAmountFullLoan + totalInterestAmountFullLoan;

                    Double totalCapitalAmountDue = totalCapitalForDue;
                    Double totalInterestAmountDue = totalInterestForDue;

                    Double totalCapitalAmountArrears = totalCapitalForArrears;
                    Double totalInterestAmountArrears = totalInterestForArrears;

                    Double totalPaidCapitalAmount = totalCapitalPaid;
                    Double totalPaidIntersetAmount = totalInterestPaid;

                    Double outstandingCapital = totalCapitalAmountFullLoan - totalPaidCapitalAmount;
                    outstandingCapital = ((outstandingCapital < 0) ? 0.00 : outstandingCapital);
                    Double outstandingInterest = totalInterestAmountFullLoan - totalPaidIntersetAmount;
                    outstandingInterest = ((outstandingInterest < 0) ? 0.00 : outstandingInterest);
                    Double totalOutstanding = outstandingCapital + outstandingInterest;

                    Double dueCapital = totalCapitalAmountDue - totalPaidCapitalAmount;
                    dueCapital = ((dueCapital < 0) ? 0.00 : dueCapital);
                    Double dueInterest = totalInterestAmountDue - totalPaidIntersetAmount;
                    dueInterest = ((dueInterest < 0) ? 0.00 : dueInterest);
                    Double totalDue = dueCapital + dueInterest;

                    Double ArearsCapital = totalCapitalAmountArrears - totalPaidCapitalAmount;
                    ArearsCapital = ((ArearsCapital < 0) ? 0.00 : ArearsCapital);
                    Double ArearsInterst = totalInterestAmountArrears - totalPaidIntersetAmount;
                    ArearsInterst = ((ArearsInterst < 0) ? 0.00 : ArearsInterst);
                    Double totalArrears = ArearsCapital + ArearsInterst;

                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(cusCode);
                    loanPojo.setCusName(cName);
                    loanPojo.setCusOfficeNo(cOfficeNo);
                    loanPojo.setTelephoneNo(custele);
                    loanPojo.setAcNumber(acNumber);
                    loanPojo.setDisburseDate(disburseDate);
                    loanPojo.setDisburseAmount(disburseAmount);

                    loanPojo.setCapitalFullLoan(totalCapitalAmountFullLoan);
                    loanPojo.setInterestFullLoan(totalInterestAmountFullLoan);
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(outstandingCapital);
                    loanPojo.setOutstandingInterest(outstandingInterest);
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(dueCapital);
                    loanPojo.setDueInterest(dueInterest);
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(ArearsCapital);
                    loanPojo.setArearsInterst(ArearsInterst);
                    loanPojo.setTotalArears(totalArrears);

//                    if (totalArrears > 0)
//                    {
                    returnTreeMap.put(acNumber, loanPojo);
//                    }

                }

            }

            tx.commit();

            return returnTreeMap;

        } catch (HibernateException ex) {

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;

        } catch (Exception ex) {

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;
        } finally {

//            session.close ();
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    /**
     * WITH ARREARS NO OF INSTALMENTS
     *
     * @param datePara
     * @param productPara
     * @param centerPara
     * @param customerCodePara
     * @return
     */
    public Map<String, Loan_pojo> getArrearsFormat4(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                class LoanInstallmentType {

                    Double capital = 0.00;

                    Double interest = 0.00;

                    Double fine = 0.00;

                }
                Map<Integer, LoanInstallmentType> treeMapInstalment = new TreeMap<Integer, LoanInstallmentType>();

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();
//                String cusOfficeNo = zIncExpListObj.getCM_OFFICE_NO();
                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crInExpSubManual = session.createCriteria(IncomeExpenceSubManualEntity.class);
                crInExpSubManual.add(Restrictions.like("ieCode", acCode));

                Integer totalTermsForArrears = 0;

                List<IncomeExpenceSubManualEntity> incExpManualList = crInExpSubManual.list();
                Integer treeMapIndex = 0;
                for (IncomeExpenceSubManualEntity incExpManualListObj : incExpManualList) {

                    ++treeMapIndex;

                    Date ieDate = incExpManualListObj.getIeDate();
                    Double ieTerm = (incExpManualListObj.getIeTerm() == null ? 0.00 : incExpManualListObj.getIeTerm());
                    Double ieInt = (incExpManualListObj.getIeInt() == null ? 0.00 : incExpManualListObj.getIeInt());

                    totalCapitalFullLoan = totalCapitalFullLoan + ieTerm;
                    totalInterestFullLoan = totalInterestFullLoan + ieInt;

//                  @@@@@ DUE @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(dateDue)) < 0) {
                        totalCapitalForDue = totalCapitalForDue + ieTerm;
                        totalInterestForDue = totalInterestForDue + ieInt;
                    }

//                  @@@@@ ARREARS @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(selectingDate)) < 0) {

                        ++totalTermsForArrears;

                        totalCapitalForArrears = totalCapitalForArrears + ieTerm;
                        totalInterestForArrears = totalInterestForArrears + ieInt;

                        Double instalment = totalCapitalForArrears + totalInterestForArrears;

                        LoanInstallmentType loanInstallmentType = new LoanInstallmentType();
                        loanInstallmentType.capital = totalCapitalForArrears;
                        loanInstallmentType.interest = totalInterestForArrears;
                        treeMapInstalment.put(treeMapIndex, loanInstallmentType);

                    }
                }

                Double totalCapitalInterestFullLoan = totalCapitalFullLoan + totalInterestFullLoan;
                if (totalCapitalInterestFullLoan != 0) {

//                    Double capitalPaid = 0.00;
//                    Double interestPaid = 0.00;
//                    Double finePaid = 0.00;
                    Double totalCapitalPaid = 0.00;
                    Double totalInterestPaid = 0.00;
                    Double totalFinePaid = 0.00;

                    Criteria crqPrintInExp2 = session.createCriteria(QprintIncomeExpence2Entity.class);
                    crqPrintInExp2.add(Restrictions.like("peExpence", acCode));

                    List<QprintIncomeExpence2Entity> crqPrintInExp2List = crqPrintInExp2.list();
                    for (QprintIncomeExpence2Entity crqPrintInExp2ListObj : crqPrintInExp2List) {

                        Integer peType = crqPrintInExp2ListObj.getPeType();
                        Double q2 = crqPrintInExp2ListObj.getQ2();
                        Date paDate = crqPrintInExp2ListObj.getPaDate();
                        String reCode = crqPrintInExp2ListObj.getReCode();
                        String docType = crqPrintInExp2ListObj.getDocType();

                        if (peType == 1) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalCapitalPaid = totalCapitalPaid + q2;

                        } else if (peType == 2) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalInterestPaid = totalInterestPaid + q2;

                        } else if (peType == 3) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalFinePaid = totalFinePaid + q2;

                        }

                    }

                    Double totalCapitalPaid2 = totalCapitalPaid;
                    Double totalInterestPaid2 = totalInterestPaid;

                    Double noOfArrearsInstalment = 0.00;

                    for (Map.Entry<Integer, LoanInstallmentType> entry : treeMapInstalment.entrySet()) {

                        Integer key = entry.getKey();
                        LoanInstallmentType value = entry.getValue();

                        totalCapitalPaid2 = totalCapitalPaid2 - value.capital;
                        totalInterestPaid2 = totalInterestPaid2 - value.interest;

                        if (totalCapitalPaid2 <= 0 || totalInterestPaid2 <= 0) {
                            ++noOfArrearsInstalment;
                        }

                    }

                    String cusCode = ieMember;
                    String acNumber = acCode;
                    String cName = cusName;
                    String cOfficeNo = cusOfficeNo;
                    String custele = tele;
                    Date disburseDate = loanGrandDate;
                    Double disburseAmount = totalCapitalFullLoan;

                    Double totalCapitalAmountFullLoan = totalCapitalFullLoan;
                    Double totalInterestAmountFullLoan = totalInterestFullLoan;
                    Double totalCapitalInterstFullLoan = totalCapitalAmountFullLoan + totalInterestAmountFullLoan;

                    Double totalCapitalAmountDue = totalCapitalForDue;
                    Double totalInterestAmountDue = totalInterestForDue;

                    Double totalCapitalAmountArrears = totalCapitalForArrears;
                    Double totalInterestAmountArrears = totalInterestForArrears;

                    Double totalPaidCapitalAmount = totalCapitalPaid;
                    Double totalPaidIntersetAmount = totalInterestPaid;

                    Double outstandingCapital = totalCapitalAmountFullLoan - totalPaidCapitalAmount;
                    outstandingCapital = ((outstandingCapital < 0) ? 0.00 : outstandingCapital);
                    Double outstandingInterest = totalInterestAmountFullLoan - totalPaidIntersetAmount;
                    outstandingInterest = ((outstandingInterest < 0) ? 0.00 : outstandingInterest);
                    Double totalOutstanding = outstandingCapital + outstandingInterest;

                    Double dueCapital = totalCapitalAmountDue - totalPaidCapitalAmount;
                    dueCapital = ((dueCapital < 0) ? 0.00 : dueCapital);
                    Double dueInterest = totalInterestAmountDue - totalPaidIntersetAmount;
                    dueInterest = ((dueInterest < 0) ? 0.00 : dueInterest);
                    Double totalDue = dueCapital + dueInterest;

                    Double ArearsCapital = totalCapitalAmountArrears - totalPaidCapitalAmount;
                    ArearsCapital = ((ArearsCapital < 0) ? 0.00 : ArearsCapital);
                    Double ArearsInterst = totalInterestAmountArrears - totalPaidIntersetAmount;
                    ArearsInterst = ((ArearsInterst < 0) ? 0.00 : ArearsInterst);
                    Double totalArrears = ArearsCapital + ArearsInterst;
                    Double noOfArrearsTerms = noOfArrearsInstalment;

                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(cusCode);
                    loanPojo.setCusName(cName);
                    loanPojo.setCusOfficeNo(cOfficeNo);
                    loanPojo.setTelephoneNo(custele);
                    loanPojo.setAcNumber(acNumber);
                    loanPojo.setDisburseDate(disburseDate);
                    loanPojo.setDisburseAmount(disburseAmount);

                    loanPojo.setCapitalFullLoan(totalCapitalAmountFullLoan);
                    loanPojo.setInterestFullLoan(totalInterestAmountFullLoan);
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(outstandingCapital);
                    loanPojo.setOutstandingInterest(outstandingInterest);
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(dueCapital);
                    loanPojo.setDueInterest(dueInterest);
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(ArearsCapital);
                    loanPojo.setArearsInterst(ArearsInterst);
                    loanPojo.setTotalArears(totalArrears);
                    loanPojo.setNoOfArrearsTerms(noOfArrearsTerms);
//                    if (totalArrears > 0)
//                    {
                    returnTreeMap.put(acNumber, loanPojo);
//                    }

                }

            }

            tx.commit();

            return returnTreeMap;

        } catch (HibernateException ex) {

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;

        } catch (Exception ex) {

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;
        } finally {

//            session.close ();
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    /*
    
    
    
    
    
     */
//    GET LOAN ARREARS BY MOBILE_ACCOUNT_BALANCES TABLE
    public Map<String, Loan_pojo> getArrearsFormat5(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {
                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();
//                String cusOfficeNo = zIncExpListObj.getCM_OFFICE_NO();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

// MOBILE_ACCOUNT_BALANCES TABLE ***********************************************                
                Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                crqPrintInExp3.add(Restrictions.like("maCode", acCode));

                Criterion crsGt1 = Restrictions.gt("maLoanArrearsCapital", 0.00);
                Criterion crsGt2 = Restrictions.gt("maLoanArrearsInterest", 0.00);
                Criterion crsGt3 = Restrictions.gt("maLoanArrearsInterest", 0.00);
                LogicalExpression logicExp = Restrictions.or(crsGt1, crsGt2);

                Disjunction orDisjunction = Restrictions.disjunction();
                orDisjunction.add(Restrictions.gt("maLoanArrearsCapital", 0.00));
                orDisjunction.add(Restrictions.gt("maLoanArrearsInterest", 0.00));
                orDisjunction.add(Restrictions.gt("maLoanArrearsfine", 0.00));

                crqPrintInExp3.add(orDisjunction);

                double fine = 0.00;
                double disbursedAmount = 0.00;
                Date loanGrandDate;
                double outstanding = 0.00;
                double dueCapital = 0.00;
                double dueInterest = 0.00;
                double totalDue = 0.00;
                double arearsCapital = 0.00;
                double arearsInterest = 0.00;
                double totalArears = 0.00;
                double noOfArrearsTerms = 0;
//                System.out.println("crqPrintInExp3.list()>>"+crqPrintInExp3.list());

                double totalArrers = getTotalArrears(acCode);
                System.out.println("totalArrers>> " + totalArrers);

                MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                if (mobileAccountBalances != null) {

                    disbursedAmount = mobileAccountBalances.getMaLoanDisbursedAmount();
                    loanGrandDate = mobileAccountBalances.getMaLoanDisbursedDate();

                    outstanding = mobileAccountBalances.getMaLoanOutstanding();

                    dueCapital = mobileAccountBalances.getMaLoanDueCapital();
                    dueInterest = mobileAccountBalances.getMaLoanDueInterest();
                    totalDue = dueCapital + dueInterest;

                    arearsCapital = mobileAccountBalances.getMaLoanArrearsCapital();
                    arearsInterest = mobileAccountBalances.getMaLoanArrearsInterest();
                    fine = mobileAccountBalances.getMaLoanArrearsfine();
                    totalArears = arearsCapital + arearsInterest + fine;
                    noOfArrearsTerms = mobileAccountBalances.getMaLoanArrearsNoOfTerms();

                    String cusCode = ieMember;
                    String acNumber = acCode;
                    String cName = cusName;
                    String cOfficeNo = cusOfficeNo;
                    String custele = tele;
                    Date disburseDate = loanGrandDate;
                    Double disburseAmount = disbursedAmount;

                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(cusCode);
                    loanPojo.setCusName(cName);
                    loanPojo.setCusOfficeNo(cOfficeNo);
                    loanPojo.setTelephoneNo(custele);
                    loanPojo.setAcNumber(acNumber);
                    loanPojo.setDisburseDate(disburseDate);
                    loanPojo.setDisburseAmount(disburseAmount);

                    loanPojo.setCapitalFullLoan(disbursedAmount);
                    loanPojo.setInterestFullLoan(0.00);
                    loanPojo.setTotalFullLoan(0.00);

                    loanPojo.setOutstandingCapital(0.00);
                    loanPojo.setOutstandingInterest(0.00);
                    loanPojo.setTotalOutstanding(outstanding);

                    loanPojo.setDueCapital(dueCapital);
                    loanPojo.setDueInterest(dueInterest);
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(arearsCapital);
                    loanPojo.setArearsInterst(arearsInterest);
//                    loanPojo.setTotalArears(totalArears);
                    loanPojo.setTotalArears(totalArrers);
                    loanPojo.setNoOfArrearsTerms(noOfArrearsTerms);
                    loanPojo.setFine(fine);

                    returnTreeMap.put(acNumber, loanPojo);
                }

            }

            tx.commit();

            return returnTreeMap;

        } catch (HibernateException ex) {

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;

        } catch (Exception ex) {

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;
        } finally {

//            session.close ();
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    private double getTotalArrears(String accountNo) {
        System.out.println("accountNo>> " + accountNo);
        double returnValue = 0.00;
        Session session = SessionFactoryUtil.getSession();
        Connection connection = ((SessionImpl) session).connection();
        try {
            String sqlSelectRow = "SELECT AB_CAP_DUE_OUTSTANDING, "
                    + "AB_INT_DUE_OUTSTANDING, "
                    + "AB_OTHER1_DUE_OUTSTANDING, "
                    + "AB_OTHER2_DUE_OUTSTANDING, "
                    + "AB_OTHER3_DUE_OUTSTANDING, "
                    + "AB_FINE_DUE_OUTSTANDING "
                    + "FROM ACCOUNT_BALANCE_LOAN WHERE AB_ACCOUNT LIKE '" + accountNo + "'";
            PreparedStatement pstm = connection.prepareStatement(sqlSelectRow);
            ResultSet rset = pstm.executeQuery();
            while (rset.next()) {
                

                Double AB_CAP_DUE_OUTSTANDING = rset.getDouble("AB_CAP_DUE_OUTSTANDING");
                Double AB_INT_DUE_OUTSTANDING = rset.getDouble("AB_INT_DUE_OUTSTANDING");
                Double AB_OTHER1_DUE_OUTSTANDING = rset.getDouble("AB_OTHER1_DUE_OUTSTANDING");
                Double AB_OTHER2_DUE_OUTSTANDING = rset.getDouble("AB_OTHER2_DUE_OUTSTANDING");
                Double AB_OTHER3_DUE_OUTSTANDING = rset.getDouble("AB_OTHER3_DUE_OUTSTANDING");
                Double AB_FINE_DUE_OUTSTANDING = rset.getDouble("AB_FINE_DUE_OUTSTANDING");


                returnValue = ((AB_CAP_DUE_OUTSTANDING != null) ? AB_CAP_DUE_OUTSTANDING : 0.00)
                        + ((AB_INT_DUE_OUTSTANDING != null) ? AB_INT_DUE_OUTSTANDING : 0.00) + ((AB_FINE_DUE_OUTSTANDING != null) ? AB_FINE_DUE_OUTSTANDING : 0.00)
                        + ((AB_OTHER1_DUE_OUTSTANDING != null) ? AB_OTHER1_DUE_OUTSTANDING : 0.00) + ((AB_OTHER2_DUE_OUTSTANDING != null) ? AB_OTHER2_DUE_OUTSTANDING : 0.00)
                        + ((AB_OTHER3_DUE_OUTSTANDING != null) ? AB_OTHER3_DUE_OUTSTANDING : 0.00);

                
                
//                returnValue = ((AB_BALANCE_OF_ALL_CAP_TERMS != null) ? AB_BALANCE_OF_ALL_CAP_TERMS : 0.00)
//                        + ((AB_BALANCE_OF_ALL_INT_TERMS != null) ? AB_BALANCE_OF_ALL_INT_TERMS : 0.00)
//                        + ((AB_BALANCE_OF_ALL_OTHER1_TERMS != null) ? AB_BALANCE_OF_ALL_OTHER1_TERMS : 0.00)
//                        + ((AB_BALANCE_OF_ALL_OTHER2_TERMS != null) ? AB_BALANCE_OF_ALL_OTHER2_TERMS : 0.00)
//                        + ((AB_BALANCE_OF_ALL_OTHER3_TERMS != null) ? AB_BALANCE_OF_ALL_OTHER3_TERMS : 0.00)
//                        + ((AB_FINE_DUE_OUTSTANDING != null) ? AB_FINE_DUE_OUTSTANDING : 0.00)
//                        - ((AB_OTHER4_OUTSTANDING != null) ? AB_OTHER4_OUTSTANDING : 0.00);

            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Loan_dao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Loan_dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("returnValue>> " + returnValue);
        return returnValue;
    }

    public Map<String, Loan_pojo> getArrears4(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();
//                String cusOfficeNo = zIncExpListObj.getCM_OFFICE_NO();

                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crInExpSubManual = session.createCriteria(IncomeExpenceSubManualEntity.class);
                crInExpSubManual.add(Restrictions.like("ieCode", acCode));

                List<IncomeExpenceSubManualEntity> incExpManualList = crInExpSubManual.list();
                for (IncomeExpenceSubManualEntity incExpManualListObj : incExpManualList) {

                    Date ieDate = incExpManualListObj.getIeDate();
                    Double ieTerm = incExpManualListObj.getIeTerm();
                    Double ieInt = incExpManualListObj.getIeInt();

                    totalCapitalFullLoan = totalCapitalFullLoan + ieTerm;
                    totalInterestFullLoan = totalInterestFullLoan + ieInt;

//                  @@@@@ DUE @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(dateDue)) < 0) {
                        totalCapitalForDue = totalCapitalForDue + ieTerm;
                        totalInterestForDue = totalInterestForDue + ieInt;
                    }

//                  @@@@@ ARREARS @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(selectingDate)) < 0) {
                        totalCapitalForArrears = totalCapitalForArrears + ieTerm;
                        totalInterestForArrears = totalInterestForArrears + ieInt;
                    }
                }

                Double totalCapitalInterestFullLoan = totalCapitalFullLoan + totalInterestFullLoan;
                if (totalCapitalInterestFullLoan != 0) {

//                    Double capitalPaid = 0.00;
//                    Double interestPaid = 0.00;
//                    Double finePaid = 0.00;
                    Double totalCapitalPaid = 0.00;
                    Double totalInterestPaid = 0.00;
                    Double totalFinePaid = 0.00;

                    Criteria crqPrintInExp2 = session.createCriteria(QprintIncomeExpence2Entity.class);
                    crqPrintInExp2.add(Restrictions.like("peExpence", acCode));

                    List<QprintIncomeExpence2Entity> crqPrintInExp2List = crqPrintInExp2.list();
                    for (QprintIncomeExpence2Entity crqPrintInExp2ListObj : crqPrintInExp2List) {

                        Integer peType = crqPrintInExp2ListObj.getPeType();
                        Double q2 = crqPrintInExp2ListObj.getQ2();
                        Date paDate = crqPrintInExp2ListObj.getPaDate();
                        String reCode = crqPrintInExp2ListObj.getReCode();
                        String docType = crqPrintInExp2ListObj.getDocType();

                        if (peType == 1) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalCapitalPaid = totalCapitalPaid + q2;

                        } else if (peType == 2) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalInterestPaid = totalInterestPaid + q2;

                        } else if (peType == 3) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalFinePaid = totalFinePaid + q2;

                        }

                    }

                    String cusCode = ieMember;
                    String acNumber = acCode;
                    String cName = cusName;
                    String cOfficeNo = cusOfficeNo;
                    String custele = tele;
                    Date disburseDate = loanGrandDate;
                    Double disburseAmount = totalCapitalFullLoan;

                    Double totalCapitalAmountFullLoan = totalCapitalFullLoan;
                    Double totalInterestAmountFullLoan = totalInterestFullLoan;
                    Double totalCapitalInterstFullLoan = totalCapitalAmountFullLoan + totalInterestAmountFullLoan;

                    Double totalCapitalAmountDue = totalCapitalForDue;
                    Double totalInterestAmountDue = totalInterestForDue;

                    Double totalCapitalAmountArrears = totalCapitalForArrears;
                    Double totalInterestAmountArrears = totalInterestForArrears;

                    Double totalPaidCapitalAmount = totalCapitalPaid;
                    Double totalPaidIntersetAmount = totalInterestPaid;

                    Double outstandingCapital = totalCapitalAmountFullLoan - totalPaidCapitalAmount;
                    outstandingCapital = ((outstandingCapital < 0) ? 0.00 : outstandingCapital);
                    Double outstandingInterest = totalInterestAmountFullLoan - totalPaidIntersetAmount;
//                    outstandingInterest = ((outstandingInterest < 0) ? 0.00 : outstandingInterest);
                    Double totalOutstanding = outstandingCapital + outstandingInterest;
                    totalOutstanding = ((totalOutstanding < 0) ? 0.00 : totalOutstanding);

                    Double dueCapital = totalCapitalAmountDue - totalPaidCapitalAmount;
                    dueCapital = ((dueCapital < 0) ? 0.00 : dueCapital);
                    Double dueInterest = totalInterestAmountDue - totalPaidIntersetAmount;
                    dueInterest = ((dueInterest < 0) ? 0.00 : dueInterest);
                    Double totalDue = dueCapital + dueInterest;

                    Double ArearsCapital = totalCapitalAmountArrears - totalPaidCapitalAmount;
                    ArearsCapital = ((ArearsCapital < 0) ? 0.00 : ArearsCapital);
                    Double ArearsInterst = totalInterestAmountArrears - totalPaidIntersetAmount;
                    ArearsInterst = ((ArearsInterst < 0) ? 0.00 : ArearsInterst);
                    Double totalArrears = ArearsCapital + ArearsInterst;

                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(cusCode);
                    loanPojo.setCusName(cName);
                    loanPojo.setCusOfficeNo(cOfficeNo);
                    loanPojo.setTelephoneNo(custele);
                    loanPojo.setAcNumber(acNumber);
                    loanPojo.setDisburseDate(disburseDate);
                    loanPojo.setDisburseAmount(disburseAmount);

                    loanPojo.setCapitalFullLoan(totalCapitalAmountFullLoan);
                    loanPojo.setInterestFullLoan(totalInterestAmountFullLoan);
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(outstandingCapital);
                    loanPojo.setOutstandingInterest(outstandingInterest);
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(dueCapital);
                    loanPojo.setDueInterest(dueInterest);
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(ArearsCapital);
                    loanPojo.setArearsInterst(ArearsInterst);
                    loanPojo.setTotalArears(totalArrears);

                    if (totalArrears > 0) {
                        returnTreeMap.put(acNumber, loanPojo);
                    }

                }

            }

            tx.commit();

            return returnTreeMap;

        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
            return null;

        } catch (Exception ex) {

            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());
            return null;

        } finally {
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    public Map<String, Loan_pojo> getRuingBalanceDetailsOutstanding(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();
//                String cusOfficeNo = zIncExpListObj.getCM_OFFICE_NO();

                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crAccountBalanceLoanEntity = session.createCriteria(AccountBalanceLoanEntity.class);
                crAccountBalanceLoanEntity.add(Restrictions.like("abAccount", acCode));

                AccountBalanceLoanEntity bpAccountBalanceLoanEntity = (AccountBalanceLoanEntity) crAccountBalanceLoanEntity.uniqueResult();

                if (bpAccountBalanceLoanEntity != null) {

//                        Map keyValMap = new HashMap();
//                        Double totalCapitalMadeDue = bpAccountBalanceLoanEntity.getAbCapTotalDue();
//                        String totalCapitalMadeDue_Str = MyRounding.roundToLastTwoDecimal(totalCapitalMadeDue);
//                        keyValMap.put("totalCapitalMadeDue_Str", totalCapitalMadeDue_Str);
//                        Double totalInterestMadeDue = bpAccountBalanceLoanEntity.getAbIntTotalDue();
//                        String totalInterestMadeDue_str = MyRounding.roundToLastTwoDecimal(totalInterestMadeDue);
//                        keyValMap.put("totalInterestMadeDue_str", totalInterestMadeDue_str);
//                        Date firstArearsDate = bpAccountBalanceLoanEntity.getAbFirstArrearsTermDate();
//                        String firstArearsDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(firstArearsDate);
//                        keyValMap.put("firstArearsDate_Str", firstArearsDate_Str);
                    Double captalArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbCapDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbCapDueOutstanding());
//                        String captalArreas_str = MyRounding.roundToLastTwoDecimal(captalArreas_Dbl);
//                        keyValMap.put("captalArreas_str", captalArreas_str);
//                        Double interestArreas_Dbl = bpAccountBalanceLoanEntity.getAbIntDueOutstanding();
                    Double interestArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbIntDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbIntDueOutstanding());
//                        String interestArreas_str = MyRounding.roundToLastTwoDecimal(interestArreas_Dbl);
//                        keyValMap.put("interestArreas_str", interestArreas_str);
//                        Double fineArreas_Dbl = bpAccountBalanceLoanEntity.getAbFineDueOutstanding();
                    Double fineArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbFineDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbFineDueOutstanding());
//                        String fineArreas_str = MyRounding.roundToLastTwoDecimal(fineArreas_Dbl);
//                        keyValMap.put("fineArreas_str", fineArreas_str);

//                        Double rental = bpAccountBalanceLoanEntity.getAbRental();
//                        String rental_str = MyRounding.roundToLastTwoDecimal(rental);
//                        keyValMap.put("rental_str", rental_str);
//                        Double rentalCapital = bpAccountBalanceLoanEntity.getAbLastAddedCapTerm();
                    Double rentalCapital = ((bpAccountBalanceLoanEntity.getAbLastAddedCapTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedCapTerm());
//                        String rentalCapital_str = MyRounding.roundToLastTwoDecimal(rentalCapital);
//                        keyValMap.put("rentalCapital_str", rentalCapital_str);

//                        Double rentalInterest = bpAccountBalanceLoanEntity.getAbLastAddedIntTerm();
                    Double rentalInterest = ((bpAccountBalanceLoanEntity.getAbLastAddedIntTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedIntTerm());
//                        String rentalInterest_str = MyRounding.roundToLastTwoDecimal(rentalInterest);
//                        keyValMap.put("rentalInterest_str", rentalInterest_str);

//                        Double rentalFine = bpAccountBalanceLoanEntity.getAbLastAddedFineTerm();
                    Double rentalFine = ((bpAccountBalanceLoanEntity.getAbLastAddedFineTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedFineTerm());
//                        String rentalFine_str = MyRounding.roundToLastTwoDecimal(rentalFine);
//                        keyValMap.put("rentalFine_str", rentalFine_str);

                    Date lastDisburcementDate = bpAccountBalanceLoanEntity.getAbLastDisbursmentDate();
//                        Double lastDisburcementDate = ((bpAccountBalanceLoanEntity.getAbLastDisbursmentDate() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastDisbursmentDate());
//                        String lastDisburcementDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(lastDisburcementDate);
//                        keyValMap.put("lastDisburcementDate_Str", lastDisburcementDate_Str);

//                        Double totalDisbursmentDouble = bpAccountBalanceLoanEntity.getAbTotalDisbursment();
                    Double totalDisbursmentDouble = ((bpAccountBalanceLoanEntity.getAbTotalDisbursment() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalDisbursment());
//                        String totalDisbursmentString = MyRounding.roundToLastTwoDecimal(totalDisbursmentDouble);

//                        Double totalTermsDue = bpAccountBalanceLoanEntity.getAbTotalTermsDue();
//                        String totalTermsDue_str = MyRounding.roundToLastTwoDecimal(totalTermsDue);
//                        keyValMap.put("totalTermsDue_str", totalTermsDue_str);
//                        Double totalCapitalRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentCap();
//                        String totalCapitalRepayment_str = MyRounding.roundToLastTwoDecimal(totalCapitalRepayment);
//                        keyValMap.put("totalCapitalRepayment_str", totalCapitalRepayment_str);
//                        Double totalInterestRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentInt();
//                        String totalInterestRepayment_str = MyRounding.roundToLastTwoDecimal(totalInterestRepayment);
//                        keyValMap.put("totalInterestRepayment_str", totalInterestRepayment_str);
//                        Double totalFineRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentFine();
//                        String totalFineRepayment_str = MyRounding.roundToLastTwoDecimal(totalFineRepayment);
//                        keyValMap.put("totalFineRepayment_str", totalFineRepayment_str);
//                        Double totalCapitalValue = bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms();
                    Double totalCapitalValue = ((bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms());
//                        String totalCapitalValue_str = MyRounding.roundToLastTwoDecimal(totalCapitalValue);
//                        keyValMap.put("totalCapitalValue_str", totalCapitalValue_str);

//                        Double totalInterstValue = bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms();
                    Double totalInterstValue = ((bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms());
//                        String totalInterstValue_str = MyRounding.roundToLastTwoDecimal(totalInterstValue);
//                        keyValMap.put("totalInterstValue_str", totalInterstValue_str);

//                        Date recordUpdateLastDate = bpAccountBalanceLoanEntity.getAbRecordUpdatedLastDate();
//                        String recordUpdateLastDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(recordUpdateLastDate);
//                        keyValMap.put("recordUpdateLastDate_Str", recordUpdateLastDate_Str);
//                        Double capitalOutstanding = bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms();
                    Double capitalOutstanding = ((bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms());
//                        String capitalOutstanding_str = MyRounding.roundToLastTwoDecimal(capitalOutstanding);
//                        keyValMap.put("capitalOutstanding_str", capitalOutstanding_str);

//                        Double interstOutstanding = bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms();
                    Double interstOutstanding = ((bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms());
//                        String interstOutstanding_str = MyRounding.roundToLastTwoDecimal(interstOutstanding);
//                        keyValMap.put("interstOutstanding_str", interstOutstanding_str);

//                        Date lastRepaymentDate = bpAccountBalanceLoanEntity.getAbLastRepaymentDate();
//                        String lastRepaymentDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(lastRepaymentDate);
//                        keyValMap.put("lastRepaymentDate_Str", lastRepaymentDate_Str);
//                        Double lastRepaymentCap = bpAccountBalanceLoanEntity.getAbLastRepaymentCap();
//                        String lastRepaymentCap_str = MyRounding.roundToLastTwoDecimal(lastRepaymentCap);
//                        keyValMap.put("lastRepaymentCap", lastRepaymentCap_str);
//                        Double lastRepaymentInt = bpAccountBalanceLoanEntity.getAbLastRepaymentInt();
//                        String lastRepaymentInt_str = MyRounding.roundToLastTwoDecimal(lastRepaymentInt);
//                        keyValMap.put("lastRepaymentInt", lastRepaymentInt_str);
//                        Double lastRepaymentFine = bpAccountBalanceLoanEntity.getAbLastRepaymentFine();
//                        String lastRepaymentFine_str = MyRounding.roundToLastTwoDecimal(lastRepaymentFine);
//                        keyValMap.put("lastRepaymentFine", lastRepaymentFine_str);
                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(ieMember);
                    loanPojo.setCusName(cusName);
                    loanPojo.setCusOfficeNo(cusOfficeNo);
                    loanPojo.setTelephoneNo(tele);
                    loanPojo.setAcNumber(acCode);
                    loanPojo.setDisburseDate(lastDisburcementDate);
                    loanPojo.setDisburseAmount(totalDisbursmentDouble);

                    loanPojo.setCapitalFullLoan(totalCapitalValue);
                    loanPojo.setInterestFullLoan(totalInterstValue);
                    Double totalCapitalInterstFullLoan = totalCapitalValue + totalInterstValue;
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(capitalOutstanding);
                    loanPojo.setOutstandingInterest(interstOutstanding);
                    Double totalOutstanding = capitalOutstanding + interstOutstanding;
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(rentalCapital);
                    loanPojo.setDueInterest(rentalInterest);
                    loanPojo.setDueFine(rentalFine);
                    Double totalDue = rentalCapital + rentalInterest + rentalFine;
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(captalArreas_Dbl);
                    loanPojo.setArearsInterst(interestArreas_Dbl);
                    loanPojo.setArearsFine(fineArreas_Dbl);
                    Double totalArrears = captalArreas_Dbl + interestArreas_Dbl + fineArreas_Dbl;
                    loanPojo.setTotalArears(totalArrears);

//                        if (totalArrears > 0) {
                    returnTreeMap.put(acCode, loanPojo);
//                        }

                }

            }

            tx.commit();

            return returnTreeMap;

        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
            return null;

        } catch (Exception ex) {

            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());
            return null;

        } finally {
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    public Map<String, Loan_pojo> getRuingBalanceDetailsDue(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();
//                String cusOfficeNo = zIncExpListObj.getCM_OFFICE_NO();

                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crAccountBalanceLoanEntity = session.createCriteria(AccountBalanceLoanEntity.class);
                crAccountBalanceLoanEntity.add(Restrictions.like("abAccount", acCode));

                AccountBalanceLoanEntity bpAccountBalanceLoanEntity = (AccountBalanceLoanEntity) crAccountBalanceLoanEntity.uniqueResult();

                if (bpAccountBalanceLoanEntity != null) {

//                        Map keyValMap = new HashMap();
//                        Double totalCapitalMadeDue = bpAccountBalanceLoanEntity.getAbCapTotalDue();
//                        String totalCapitalMadeDue_Str = MyRounding.roundToLastTwoDecimal(totalCapitalMadeDue);
//                        keyValMap.put("totalCapitalMadeDue_Str", totalCapitalMadeDue_Str);
//                        Double totalInterestMadeDue = bpAccountBalanceLoanEntity.getAbIntTotalDue();
//                        String totalInterestMadeDue_str = MyRounding.roundToLastTwoDecimal(totalInterestMadeDue);
//                        keyValMap.put("totalInterestMadeDue_str", totalInterestMadeDue_str);
//                        Date firstArearsDate = bpAccountBalanceLoanEntity.getAbFirstArrearsTermDate();
//                        String firstArearsDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(firstArearsDate);
//                        keyValMap.put("firstArearsDate_Str", firstArearsDate_Str);
                    Double captalArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbCapDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbCapDueOutstanding());
//                        String captalArreas_str = MyRounding.roundToLastTwoDecimal(captalArreas_Dbl);
//                        keyValMap.put("captalArreas_str", captalArreas_str);
//                        Double interestArreas_Dbl = bpAccountBalanceLoanEntity.getAbIntDueOutstanding();
                    Double interestArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbIntDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbIntDueOutstanding());
//                        String interestArreas_str = MyRounding.roundToLastTwoDecimal(interestArreas_Dbl);
//                        keyValMap.put("interestArreas_str", interestArreas_str);
//                        Double fineArreas_Dbl = bpAccountBalanceLoanEntity.getAbFineDueOutstanding();
                    Double fineArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbFineDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbFineDueOutstanding());
//                        String fineArreas_str = MyRounding.roundToLastTwoDecimal(fineArreas_Dbl);
//                        keyValMap.put("fineArreas_str", fineArreas_str);

//                        Double rental = bpAccountBalanceLoanEntity.getAbRental();
//                        String rental_str = MyRounding.roundToLastTwoDecimal(rental);
//                        keyValMap.put("rental_str", rental_str);
//                        Double rentalCapital = bpAccountBalanceLoanEntity.getAbLastAddedCapTerm();
                    Double rentalCapital = ((bpAccountBalanceLoanEntity.getAbLastAddedCapTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedCapTerm());
//                        String rentalCapital_str = MyRounding.roundToLastTwoDecimal(rentalCapital);
//                        keyValMap.put("rentalCapital_str", rentalCapital_str);

//                        Double rentalInterest = bpAccountBalanceLoanEntity.getAbLastAddedIntTerm();
                    Double rentalInterest = ((bpAccountBalanceLoanEntity.getAbLastAddedIntTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedIntTerm());
//                        String rentalInterest_str = MyRounding.roundToLastTwoDecimal(rentalInterest);
//                        keyValMap.put("rentalInterest_str", rentalInterest_str);

//                        Double rentalFine = bpAccountBalanceLoanEntity.getAbLastAddedFineTerm();
                    Double rentalFine = ((bpAccountBalanceLoanEntity.getAbLastAddedFineTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedFineTerm());
//                        String rentalFine_str = MyRounding.roundToLastTwoDecimal(rentalFine);
//                        keyValMap.put("rentalFine_str", rentalFine_str);

                    Date lastDisburcementDate = bpAccountBalanceLoanEntity.getAbLastDisbursmentDate();
//                        Double lastDisburcementDate = ((bpAccountBalanceLoanEntity.getAbLastDisbursmentDate() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastDisbursmentDate());
//                        String lastDisburcementDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(lastDisburcementDate);
//                        keyValMap.put("lastDisburcementDate_Str", lastDisburcementDate_Str);

//                        Double totalDisbursmentDouble = bpAccountBalanceLoanEntity.getAbTotalDisbursment();
                    Double totalDisbursmentDouble = ((bpAccountBalanceLoanEntity.getAbTotalDisbursment() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalDisbursment());
//                        String totalDisbursmentString = MyRounding.roundToLastTwoDecimal(totalDisbursmentDouble);

//                        Double totalTermsDue = bpAccountBalanceLoanEntity.getAbTotalTermsDue();
//                        String totalTermsDue_str = MyRounding.roundToLastTwoDecimal(totalTermsDue);
//                        keyValMap.put("totalTermsDue_str", totalTermsDue_str);
//                        Double totalCapitalRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentCap();
//                        String totalCapitalRepayment_str = MyRounding.roundToLastTwoDecimal(totalCapitalRepayment);
//                        keyValMap.put("totalCapitalRepayment_str", totalCapitalRepayment_str);
//                        Double totalInterestRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentInt();
//                        String totalInterestRepayment_str = MyRounding.roundToLastTwoDecimal(totalInterestRepayment);
//                        keyValMap.put("totalInterestRepayment_str", totalInterestRepayment_str);
//                        Double totalFineRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentFine();
//                        String totalFineRepayment_str = MyRounding.roundToLastTwoDecimal(totalFineRepayment);
//                        keyValMap.put("totalFineRepayment_str", totalFineRepayment_str);
//                        Double totalCapitalValue = bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms();
                    Double totalCapitalValue = ((bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms());
//                        String totalCapitalValue_str = MyRounding.roundToLastTwoDecimal(totalCapitalValue);
//                        keyValMap.put("totalCapitalValue_str", totalCapitalValue_str);

//                        Double totalInterstValue = bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms();
                    Double totalInterstValue = ((bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms());
//                        String totalInterstValue_str = MyRounding.roundToLastTwoDecimal(totalInterstValue);
//                        keyValMap.put("totalInterstValue_str", totalInterstValue_str);

//                        Date recordUpdateLastDate = bpAccountBalanceLoanEntity.getAbRecordUpdatedLastDate();
//                        String recordUpdateLastDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(recordUpdateLastDate);
//                        keyValMap.put("recordUpdateLastDate_Str", recordUpdateLastDate_Str);
//                        Double capitalOutstanding = bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms();
                    Double capitalOutstanding = ((bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms());
//                        String capitalOutstanding_str = MyRounding.roundToLastTwoDecimal(capitalOutstanding);
//                        keyValMap.put("capitalOutstanding_str", capitalOutstanding_str);

//                        Double interstOutstanding = bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms();
                    Double interstOutstanding = ((bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms());
//                        String interstOutstanding_str = MyRounding.roundToLastTwoDecimal(interstOutstanding);
//                        keyValMap.put("interstOutstanding_str", interstOutstanding_str);

//                        Date lastRepaymentDate = bpAccountBalanceLoanEntity.getAbLastRepaymentDate();
//                        String lastRepaymentDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(lastRepaymentDate);
//                        keyValMap.put("lastRepaymentDate_Str", lastRepaymentDate_Str);
//                        Double lastRepaymentCap = bpAccountBalanceLoanEntity.getAbLastRepaymentCap();
//                        String lastRepaymentCap_str = MyRounding.roundToLastTwoDecimal(lastRepaymentCap);
//                        keyValMap.put("lastRepaymentCap", lastRepaymentCap_str);
//                        Double lastRepaymentInt = bpAccountBalanceLoanEntity.getAbLastRepaymentInt();
//                        String lastRepaymentInt_str = MyRounding.roundToLastTwoDecimal(lastRepaymentInt);
//                        keyValMap.put("lastRepaymentInt", lastRepaymentInt_str);
//                        Double lastRepaymentFine = bpAccountBalanceLoanEntity.getAbLastRepaymentFine();
//                        String lastRepaymentFine_str = MyRounding.roundToLastTwoDecimal(lastRepaymentFine);
//                        keyValMap.put("lastRepaymentFine", lastRepaymentFine_str);
                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(ieMember);
                    loanPojo.setCusName(cusName);
                    loanPojo.setCusOfficeNo(cusOfficeNo);
                    loanPojo.setTelephoneNo(tele);
                    loanPojo.setAcNumber(acCode);
                    loanPojo.setDisburseDate(lastDisburcementDate);
                    loanPojo.setDisburseAmount(totalDisbursmentDouble);

                    loanPojo.setCapitalFullLoan(totalCapitalValue);
                    loanPojo.setInterestFullLoan(totalInterstValue);
                    Double totalCapitalInterstFullLoan = totalCapitalValue + totalInterstValue;
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(capitalOutstanding);
                    loanPojo.setOutstandingInterest(interstOutstanding);
                    Double totalOutstanding = capitalOutstanding + interstOutstanding;
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(rentalCapital);
                    loanPojo.setDueInterest(rentalInterest);
                    loanPojo.setDueFine(rentalFine);
                    Double totalDue = rentalCapital + rentalInterest + rentalFine;
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(captalArreas_Dbl);
                    loanPojo.setArearsInterst(interestArreas_Dbl);
                    loanPojo.setArearsFine(fineArreas_Dbl);
                    Double totalArrears = captalArreas_Dbl + interestArreas_Dbl + fineArreas_Dbl;
                    loanPojo.setTotalArears(totalArrears);

//                        if (totalArrears > 0) {
                    returnTreeMap.put(acCode, loanPojo);
//                        }

                }

            }

            tx.commit();

            return returnTreeMap;

        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
            return null;

        } catch (Exception ex) {

            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());
            return null;

        } finally {
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    public Map<String, Loan_pojo> getRuingBalanceDetailsArrears(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();
//                String cusOfficeNo = zIncExpListObj.getCM_OFFICE_NO();

                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                double totalArrers = getTotalArrears(acCode);
                System.out.println("totalArrers>> " + totalArrers);

                Criteria crAccountBalanceLoanEntity = session.createCriteria(AccountBalanceLoanEntity.class);
                crAccountBalanceLoanEntity.add(Restrictions.like("abAccount", acCode));

                AccountBalanceLoanEntity bpAccountBalanceLoanEntity = (AccountBalanceLoanEntity) crAccountBalanceLoanEntity.uniqueResult();

                if (bpAccountBalanceLoanEntity != null) {

//                        Map keyValMap = new HashMap();
//                        Double totalCapitalMadeDue = bpAccountBalanceLoanEntity.getAbCapTotalDue();
//                        String totalCapitalMadeDue_Str = MyRounding.roundToLastTwoDecimal(totalCapitalMadeDue);
//                        keyValMap.put("totalCapitalMadeDue_Str", totalCapitalMadeDue_Str);
//                        Double totalInterestMadeDue = bpAccountBalanceLoanEntity.getAbIntTotalDue();
//                        String totalInterestMadeDue_str = MyRounding.roundToLastTwoDecimal(totalInterestMadeDue);
//                        keyValMap.put("totalInterestMadeDue_str", totalInterestMadeDue_str);
//                        Date firstArearsDate = bpAccountBalanceLoanEntity.getAbFirstArrearsTermDate();
//                        String firstArearsDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(firstArearsDate);
//                        keyValMap.put("firstArearsDate_Str", firstArearsDate_Str);
                    Double captalArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbCapDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbCapDueOutstanding());
//                        String captalArreas_str = MyRounding.roundToLastTwoDecimal(captalArreas_Dbl);
//                        keyValMap.put("captalArreas_str", captalArreas_str);
//                        Double interestArreas_Dbl = bpAccountBalanceLoanEntity.getAbIntDueOutstanding();
                    Double interestArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbIntDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbIntDueOutstanding());
//                        String interestArreas_str = MyRounding.roundToLastTwoDecimal(interestArreas_Dbl);
//                        keyValMap.put("interestArreas_str", interestArreas_str);
//                        Double fineArreas_Dbl = bpAccountBalanceLoanEntity.getAbFineDueOutstanding();
                    Double fineArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbFineDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbFineDueOutstanding());
//                        String fineArreas_str = MyRounding.roundToLastTwoDecimal(fineArreas_Dbl);
//                        keyValMap.put("fineArreas_str", fineArreas_str);

//                        Double rental = bpAccountBalanceLoanEntity.getAbRental();
//                        String rental_str = MyRounding.roundToLastTwoDecimal(rental);
//                        keyValMap.put("rental_str", rental_str);
//                        Double rentalCapital = bpAccountBalanceLoanEntity.getAbLastAddedCapTerm();
                    Double rentalCapital = ((bpAccountBalanceLoanEntity.getAbLastAddedCapTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedCapTerm());
//                        String rentalCapital_str = MyRounding.roundToLastTwoDecimal(rentalCapital);
//                        keyValMap.put("rentalCapital_str", rentalCapital_str);

//                        Double rentalInterest = bpAccountBalanceLoanEntity.getAbLastAddedIntTerm();
                    Double rentalInterest = ((bpAccountBalanceLoanEntity.getAbLastAddedIntTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedIntTerm());
//                        String rentalInterest_str = MyRounding.roundToLastTwoDecimal(rentalInterest);
//                        keyValMap.put("rentalInterest_str", rentalInterest_str);

//                        Double rentalFine = bpAccountBalanceLoanEntity.getAbLastAddedFineTerm();
                    Double rentalFine = ((bpAccountBalanceLoanEntity.getAbLastAddedFineTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedFineTerm());
//                        String rentalFine_str = MyRounding.roundToLastTwoDecimal(rentalFine);
//                        keyValMap.put("rentalFine_str", rentalFine_str);

                    Date lastDisburcementDate = bpAccountBalanceLoanEntity.getAbLastDisbursmentDate();
//                        Double lastDisburcementDate = ((bpAccountBalanceLoanEntity.getAbLastDisbursmentDate() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastDisbursmentDate());
//                        String lastDisburcementDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(lastDisburcementDate);
//                        keyValMap.put("lastDisburcementDate_Str", lastDisburcementDate_Str);

//                        Double totalDisbursmentDouble = bpAccountBalanceLoanEntity.getAbTotalDisbursment();
                    Double totalDisbursmentDouble = ((bpAccountBalanceLoanEntity.getAbTotalDisbursment() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalDisbursment());
//                        String totalDisbursmentString = MyRounding.roundToLastTwoDecimal(totalDisbursmentDouble);

//                        Double totalTermsDue = bpAccountBalanceLoanEntity.getAbTotalTermsDue();
//                        String totalTermsDue_str = MyRounding.roundToLastTwoDecimal(totalTermsDue);
//                        keyValMap.put("totalTermsDue_str", totalTermsDue_str);
//                        Double totalCapitalRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentCap();
//                        String totalCapitalRepayment_str = MyRounding.roundToLastTwoDecimal(totalCapitalRepayment);
//                        keyValMap.put("totalCapitalRepayment_str", totalCapitalRepayment_str);
//                        Double totalInterestRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentInt();
//                        String totalInterestRepayment_str = MyRounding.roundToLastTwoDecimal(totalInterestRepayment);
//                        keyValMap.put("totalInterestRepayment_str", totalInterestRepayment_str);
//                        Double totalFineRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentFine();
//                        String totalFineRepayment_str = MyRounding.roundToLastTwoDecimal(totalFineRepayment);
//                        keyValMap.put("totalFineRepayment_str", totalFineRepayment_str);
//                        Double totalCapitalValue = bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms();
                    Double totalCapitalValue = ((bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms());
//                        String totalCapitalValue_str = MyRounding.roundToLastTwoDecimal(totalCapitalValue);
//                        keyValMap.put("totalCapitalValue_str", totalCapitalValue_str);

//                        Double totalInterstValue = bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms();
                    Double totalInterstValue = ((bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms());
//                        String totalInterstValue_str = MyRounding.roundToLastTwoDecimal(totalInterstValue);
//                        keyValMap.put("totalInterstValue_str", totalInterstValue_str);

//                        Date recordUpdateLastDate = bpAccountBalanceLoanEntity.getAbRecordUpdatedLastDate();
//                        String recordUpdateLastDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(recordUpdateLastDate);
//                        keyValMap.put("recordUpdateLastDate_Str", recordUpdateLastDate_Str);
//                        Double capitalOutstanding = bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms();
                    Double capitalOutstanding = ((bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms());
//                        String capitalOutstanding_str = MyRounding.roundToLastTwoDecimal(capitalOutstanding);
//                        keyValMap.put("capitalOutstanding_str", capitalOutstanding_str);

//                        Double interstOutstanding = bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms();
                    Double interstOutstanding = ((bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms());
//                        String interstOutstanding_str = MyRounding.roundToLastTwoDecimal(interstOutstanding);
//                        keyValMap.put("interstOutstanding_str", interstOutstanding_str);

//                        Date lastRepaymentDate = bpAccountBalanceLoanEntity.getAbLastRepaymentDate();
//                        String lastRepaymentDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(lastRepaymentDate);
//                        keyValMap.put("lastRepaymentDate_Str", lastRepaymentDate_Str);
//                        Double lastRepaymentCap = bpAccountBalanceLoanEntity.getAbLastRepaymentCap();
//                        String lastRepaymentCap_str = MyRounding.roundToLastTwoDecimal(lastRepaymentCap);
//                        keyValMap.put("lastRepaymentCap", lastRepaymentCap_str);
//                        Double lastRepaymentInt = bpAccountBalanceLoanEntity.getAbLastRepaymentInt();
//                        String lastRepaymentInt_str = MyRounding.roundToLastTwoDecimal(lastRepaymentInt);
//                        keyValMap.put("lastRepaymentInt", lastRepaymentInt_str);
//                        Double lastRepaymentFine = bpAccountBalanceLoanEntity.getAbLastRepaymentFine();
//                        String lastRepaymentFine_str = MyRounding.roundToLastTwoDecimal(lastRepaymentFine);
//                        keyValMap.put("lastRepaymentFine", lastRepaymentFine_str);
                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(ieMember);
                    loanPojo.setCusName(cusName);
                    loanPojo.setCusOfficeNo(cusOfficeNo);
                    loanPojo.setTelephoneNo(tele);
                    loanPojo.setAcNumber(acCode);
                    loanPojo.setDisburseDate(lastDisburcementDate);
                    loanPojo.setDisburseAmount(totalDisbursmentDouble);

                    loanPojo.setCapitalFullLoan(totalCapitalValue);
                    loanPojo.setInterestFullLoan(totalInterstValue);
                    Double totalCapitalInterstFullLoan = totalCapitalValue + totalInterstValue;
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(capitalOutstanding);
                    loanPojo.setOutstandingInterest(interstOutstanding);
                    Double totalOutstanding = capitalOutstanding + interstOutstanding;
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(rentalCapital);
                    loanPojo.setDueInterest(rentalInterest);
                    loanPojo.setDueFine(rentalFine);
                    Double totalDue = rentalCapital + rentalInterest + rentalFine;
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(captalArreas_Dbl);
                    loanPojo.setArearsInterst(interestArreas_Dbl);
                    loanPojo.setArearsFine(fineArreas_Dbl);
//                    Double totalArrears = captalArreas_Dbl + interestArreas_Dbl + fineArreas_Dbl;
//                    loanPojo.setTotalArears(totalArrears);
                    loanPojo.setTotalArears(totalArrers);

//                        if (totalArrears > 0) {
                    returnTreeMap.put(acCode, loanPojo);
//                        }

                }

            }

            tx.commit();

            return returnTreeMap;

        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
            return null;

        } catch (Exception ex) {

            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());
            return null;

        } finally {
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    public Map<String, Loan_pojo> getRuingBalanceDetailsEarlySettlement(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();
//                String cusOfficeNo = zIncExpListObj.getCM_OFFICE_NO();

                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crAccountBalanceLoanEntity = session.createCriteria(AccountBalanceLoanEntity.class);
                crAccountBalanceLoanEntity.add(Restrictions.like("abAccount", acCode));

                AccountBalanceLoanEntity bpAccountBalanceLoanEntity = (AccountBalanceLoanEntity) crAccountBalanceLoanEntity.uniqueResult();

                if (bpAccountBalanceLoanEntity != null) {

//                        Map keyValMap = new HashMap();
//                        Double totalCapitalMadeDue = bpAccountBalanceLoanEntity.getAbCapTotalDue();
//                        String totalCapitalMadeDue_Str = MyRounding.roundToLastTwoDecimal(totalCapitalMadeDue);
//                        keyValMap.put("totalCapitalMadeDue_Str", totalCapitalMadeDue_Str);
//                        Double totalInterestMadeDue = bpAccountBalanceLoanEntity.getAbIntTotalDue();
//                        String totalInterestMadeDue_str = MyRounding.roundToLastTwoDecimal(totalInterestMadeDue);
//                        keyValMap.put("totalInterestMadeDue_str", totalInterestMadeDue_str);
//                        Date firstArearsDate = bpAccountBalanceLoanEntity.getAbFirstArrearsTermDate();
//                        String firstArearsDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(firstArearsDate);
//                        keyValMap.put("firstArearsDate_Str", firstArearsDate_Str);
                    Double captalArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbCapDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbCapDueOutstanding());
//                        String captalArreas_str = MyRounding.roundToLastTwoDecimal(captalArreas_Dbl);
//                        keyValMap.put("captalArreas_str", captalArreas_str);
//                        Double interestArreas_Dbl = bpAccountBalanceLoanEntity.getAbIntDueOutstanding();
                    Double interestArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbIntDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbIntDueOutstanding());
//                        String interestArreas_str = MyRounding.roundToLastTwoDecimal(interestArreas_Dbl);
//                        keyValMap.put("interestArreas_str", interestArreas_str);
//                        Double fineArreas_Dbl = bpAccountBalanceLoanEntity.getAbFineDueOutstanding();
                    Double fineArreas_Dbl = ((bpAccountBalanceLoanEntity.getAbFineDueOutstanding() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbFineDueOutstanding());
//                        String fineArreas_str = MyRounding.roundToLastTwoDecimal(fineArreas_Dbl);
//                        keyValMap.put("fineArreas_str", fineArreas_str);

//                        Double rental = bpAccountBalanceLoanEntity.getAbRental();
//                        String rental_str = MyRounding.roundToLastTwoDecimal(rental);
//                        keyValMap.put("rental_str", rental_str);
//                        Double rentalCapital = bpAccountBalanceLoanEntity.getAbLastAddedCapTerm();
                    Double rentalCapital = ((bpAccountBalanceLoanEntity.getAbLastAddedCapTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedCapTerm());
//                        String rentalCapital_str = MyRounding.roundToLastTwoDecimal(rentalCapital);
//                        keyValMap.put("rentalCapital_str", rentalCapital_str);

//                        Double rentalInterest = bpAccountBalanceLoanEntity.getAbLastAddedIntTerm();
                    Double rentalInterest = ((bpAccountBalanceLoanEntity.getAbLastAddedIntTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedIntTerm());
//                        String rentalInterest_str = MyRounding.roundToLastTwoDecimal(rentalInterest);
//                        keyValMap.put("rentalInterest_str", rentalInterest_str);

//                        Double rentalFine = bpAccountBalanceLoanEntity.getAbLastAddedFineTerm();
                    Double rentalFine = ((bpAccountBalanceLoanEntity.getAbLastAddedFineTerm() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastAddedFineTerm());
//                        String rentalFine_str = MyRounding.roundToLastTwoDecimal(rentalFine);
//                        keyValMap.put("rentalFine_str", rentalFine_str);

                    Date lastDisburcementDate = bpAccountBalanceLoanEntity.getAbLastDisbursmentDate();
//                        Double lastDisburcementDate = ((bpAccountBalanceLoanEntity.getAbLastDisbursmentDate() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbLastDisbursmentDate());
//                        String lastDisburcementDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(lastDisburcementDate);
//                        keyValMap.put("lastDisburcementDate_Str", lastDisburcementDate_Str);

//                        Double totalDisbursmentDouble = bpAccountBalanceLoanEntity.getAbTotalDisbursment();
                    Double totalDisbursmentDouble = ((bpAccountBalanceLoanEntity.getAbTotalDisbursment() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalDisbursment());
//                        String totalDisbursmentString = MyRounding.roundToLastTwoDecimal(totalDisbursmentDouble);

//                        Double totalTermsDue = bpAccountBalanceLoanEntity.getAbTotalTermsDue();
//                        String totalTermsDue_str = MyRounding.roundToLastTwoDecimal(totalTermsDue);
//                        keyValMap.put("totalTermsDue_str", totalTermsDue_str);
//                        Double totalCapitalRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentCap();
//                        String totalCapitalRepayment_str = MyRounding.roundToLastTwoDecimal(totalCapitalRepayment);
//                        keyValMap.put("totalCapitalRepayment_str", totalCapitalRepayment_str);
//                        Double totalInterestRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentInt();
//                        String totalInterestRepayment_str = MyRounding.roundToLastTwoDecimal(totalInterestRepayment);
//                        keyValMap.put("totalInterestRepayment_str", totalInterestRepayment_str);
//                        Double totalFineRepayment = bpAccountBalanceLoanEntity.getAbTotalRepaymentFine();
//                        String totalFineRepayment_str = MyRounding.roundToLastTwoDecimal(totalFineRepayment);
//                        keyValMap.put("totalFineRepayment_str", totalFineRepayment_str);
//                        Double totalCapitalValue = bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms();
                    Double totalCapitalValue = ((bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalOfAllCapTerms());
//                        String totalCapitalValue_str = MyRounding.roundToLastTwoDecimal(totalCapitalValue);
//                        keyValMap.put("totalCapitalValue_str", totalCapitalValue_str);

//                        Double totalInterstValue = bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms();
                    Double totalInterstValue = ((bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbTotalOfAllIntTerms());
//                        String totalInterstValue_str = MyRounding.roundToLastTwoDecimal(totalInterstValue);
//                        keyValMap.put("totalInterstValue_str", totalInterstValue_str);

//                        Date recordUpdateLastDate = bpAccountBalanceLoanEntity.getAbRecordUpdatedLastDate();
//                        String recordUpdateLastDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(recordUpdateLastDate);
//                        keyValMap.put("recordUpdateLastDate_Str", recordUpdateLastDate_Str);
//                        Double capitalOutstanding = bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms();
                    Double capitalOutstanding = ((bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbBalanceOfAllCapTerms());
//                        String capitalOutstanding_str = MyRounding.roundToLastTwoDecimal(capitalOutstanding);
//                        keyValMap.put("capitalOutstanding_str", capitalOutstanding_str);

//                        Double interstOutstanding = bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms();
                    Double interstOutstanding = ((bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms() == null) ? 0.00 : bpAccountBalanceLoanEntity.getAbBalanceOfAllIntTerms());
//                        String interstOutstanding_str = MyRounding.roundToLastTwoDecimal(interstOutstanding);
//                        keyValMap.put("interstOutstanding_str", interstOutstanding_str);

//                        Date lastRepaymentDate = bpAccountBalanceLoanEntity.getAbLastRepaymentDate();
//                        String lastRepaymentDate_Str = MyDateAndTimeUtil.getFormatedDateAsString(lastRepaymentDate);
//                        keyValMap.put("lastRepaymentDate_Str", lastRepaymentDate_Str);
//                        Double lastRepaymentCap = bpAccountBalanceLoanEntity.getAbLastRepaymentCap();
//                        String lastRepaymentCap_str = MyRounding.roundToLastTwoDecimal(lastRepaymentCap);
//                        keyValMap.put("lastRepaymentCap", lastRepaymentCap_str);
//                        Double lastRepaymentInt = bpAccountBalanceLoanEntity.getAbLastRepaymentInt();
//                        String lastRepaymentInt_str = MyRounding.roundToLastTwoDecimal(lastRepaymentInt);
//                        keyValMap.put("lastRepaymentInt", lastRepaymentInt_str);
//                        Double lastRepaymentFine = bpAccountBalanceLoanEntity.getAbLastRepaymentFine();
//                        String lastRepaymentFine_str = MyRounding.roundToLastTwoDecimal(lastRepaymentFine);
//                        keyValMap.put("lastRepaymentFine", lastRepaymentFine_str);
                    Loan_pojo loanPojo = new Loan_pojo();
                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(ieMember);
                    loanPojo.setCusName(cusName);
                    loanPojo.setCusOfficeNo(cusOfficeNo);
                    loanPojo.setTelephoneNo(tele);
                    loanPojo.setAcNumber(acCode);
                    loanPojo.setDisburseDate(lastDisburcementDate);
                    loanPojo.setDisburseAmount(totalDisbursmentDouble);

                    loanPojo.setCapitalFullLoan(totalCapitalValue);
                    loanPojo.setInterestFullLoan(totalInterstValue);
                    Double totalCapitalInterstFullLoan = totalCapitalValue + totalInterstValue;
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(capitalOutstanding);
                    loanPojo.setOutstandingInterest(interstOutstanding);
                    Double totalOutstanding = capitalOutstanding + interstOutstanding;
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(rentalCapital);
                    loanPojo.setDueInterest(rentalInterest);
                    loanPojo.setDueFine(rentalFine);
                    Double totalDue = rentalCapital + rentalInterest + rentalFine;
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(captalArreas_Dbl);
                    loanPojo.setArearsInterst(interestArreas_Dbl);
                    loanPojo.setArearsFine(fineArreas_Dbl);
                    Double totalArrears = captalArreas_Dbl + interestArreas_Dbl + fineArreas_Dbl;
                    loanPojo.setTotalArears(totalArrears);

//                        if (totalArrears > 0) {
                    returnTreeMap.put(acCode, loanPojo);
//                        }

                }

            }

            tx.commit();

            return returnTreeMap;

        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());
            return null;

        } catch (Exception ex) {

            if (tx != null) {
                tx.rollback();
            }

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());
            return null;

        } finally {
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    public Loan_pojo getLoanDetails(Date datePara, String accountCode) {

        Date selectingDate = datePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        Loan_pojo loanPojo = new Loan_pojo();
        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_CODE", accountCode));
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();
//                String tele = zIncExpListObj.getCM_TELE();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();

                Double totalCapitalFullLoan = 0.00;
                Double totalInterestFullLoan = 0.00;

                Double totalCapitalForDate = 0.00;
                Double totalInterestForDate = 0.00;

                Double totalCapitalForDue = 0.00;
                Double totalInterestForDue = 0.00;

                Double totalCapitalForArrears = 0.00;
                Double totalInterestForArrears = 0.00;

                Criteria crInExpSubManual = session.createCriteria(IncomeExpenceSubManualEntity.class);
                crInExpSubManual.add(Restrictions.like("ieCode", acCode));

                List<IncomeExpenceSubManualEntity> incExpManualList = crInExpSubManual.list();
                for (IncomeExpenceSubManualEntity incExpManualListObj : incExpManualList) {

                    Date ieDate = incExpManualListObj.getIeDate();
                    Double ieTerm = incExpManualListObj.getIeTerm();
                    Double ieInt = incExpManualListObj.getIeInt();

                    totalCapitalFullLoan = totalCapitalFullLoan + ieTerm;
                    totalInterestFullLoan = totalInterestFullLoan + ieInt;

//                  @@@@@ DUE @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(dateDue)) < 0) {
                        totalCapitalForDue = totalCapitalForDue + ieTerm;
                        totalInterestForDue = totalInterestForDue + ieInt;
                    }

//                  @@@@@ ARREARS @@@@@@@@@@@@@@@@@
                    if ((ieDate.compareTo(selectingDate)) < 0) {
                        totalCapitalForArrears = totalCapitalForArrears + ieTerm;
                        totalInterestForArrears = totalInterestForArrears + ieInt;
                    }
                }

                Double totalCapitalInterestFullLoan = totalCapitalFullLoan + totalInterestFullLoan;
                if (totalCapitalInterestFullLoan != 0) {

//                    Double capitalPaid = 0.00;
//                    Double interestPaid = 0.00;
//                    Double finePaid = 0.00;
                    Double totalCapitalPaid = 0.00;
                    Double totalInterestPaid = 0.00;
                    Double totalFinePaid = 0.00;

                    Criteria crqPrintInExp2 = session.createCriteria(QprintIncomeExpence2Entity.class);
                    crqPrintInExp2.add(Restrictions.like("peExpence", acCode));

                    List<QprintIncomeExpence2Entity> crqPrintInExp2List = crqPrintInExp2.list();
                    for (QprintIncomeExpence2Entity crqPrintInExp2ListObj : crqPrintInExp2List) {

                        Integer peType = crqPrintInExp2ListObj.getPeType();
                        Double q2 = crqPrintInExp2ListObj.getQ2();
                        Date paDate = crqPrintInExp2ListObj.getPaDate();
                        String reCode = crqPrintInExp2ListObj.getReCode();
                        String docType = crqPrintInExp2ListObj.getDocType();

                        if (peType == 1) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalCapitalPaid = totalCapitalPaid + q2;

                        } else if (peType == 2) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalInterestPaid = totalInterestPaid + q2;

                        } else if (peType == 3) {

                            q2 = ((q2 == null) ? 0.00 : q2);
                            totalFinePaid = totalFinePaid + q2;

                        }

                    }

                    String cusCode = ieMember;
                    String acNumber = acCode;
                    String custele = tele;
                    Date disburseDate = loanGrandDate;
                    Double disburseAmount = totalCapitalFullLoan;

                    Double totalCapitalAmountFullLoan = totalCapitalFullLoan;
                    Double totalInterestAmountFullLoan = totalInterestFullLoan;
                    Double totalCapitalInterstFullLoan = totalCapitalAmountFullLoan + totalInterestAmountFullLoan;

                    Double totalCapitalAmountDue = totalCapitalForDue;
                    Double totalInterestAmountDue = totalInterestForDue;

                    Double totalCapitalAmountArrears = totalCapitalForArrears;
                    Double totalInterestAmountArrears = totalInterestForArrears;

                    Double totalPaidCapitalAmount = totalCapitalPaid;
                    Double totalPaidIntersetAmount = totalInterestPaid;

                    Double outstandingCapital = totalCapitalAmountFullLoan - totalPaidCapitalAmount;
                    outstandingCapital = ((outstandingCapital < 0) ? 0.00 : outstandingCapital);
                    Double outstandingInterest = totalInterestAmountFullLoan - totalPaidIntersetAmount;
                    outstandingInterest = ((outstandingInterest < 0) ? 0.00 : outstandingInterest);
                    Double totalOutstanding = outstandingCapital + outstandingInterest;

                    Double dueCapital = totalCapitalAmountDue - totalPaidCapitalAmount;
                    dueCapital = ((dueCapital < 0) ? 0.00 : dueCapital);
                    Double dueInterest = totalInterestAmountDue - totalPaidIntersetAmount;
                    dueInterest = ((dueInterest < 0) ? 0.00 : dueInterest);
                    Double totalDue = dueCapital + dueInterest;

                    Double ArearsCapital = totalCapitalAmountArrears - totalPaidCapitalAmount;
                    ArearsCapital = ((ArearsCapital < 0) ? 0.00 : ArearsCapital);
                    Double ArearsInterst = totalInterestAmountArrears - totalPaidIntersetAmount;
                    ArearsInterst = ((ArearsInterst < 0) ? 0.00 : ArearsInterst);
                    Double totalArrears = ArearsCapital + ArearsInterst;

                    loanPojo.setIsSelected(true);
                    loanPojo.setCusCode(cusCode);
                    loanPojo.setTelephoneNo(custele);
                    loanPojo.setAcNumber(acNumber);
                    loanPojo.setDisburseDate(disburseDate);
                    loanPojo.setDisburseAmount(disburseAmount);

                    loanPojo.setCapitalFullLoan(totalCapitalAmountFullLoan);
                    loanPojo.setInterestFullLoan(totalInterestAmountFullLoan);
                    loanPojo.setTotalFullLoan(totalCapitalInterstFullLoan);

                    loanPojo.setOutstandingCapital(outstandingCapital);
                    loanPojo.setOutstandingInterest(outstandingInterest);
                    loanPojo.setTotalOutstanding(totalOutstanding);

                    loanPojo.setDueCapital(dueCapital);
                    loanPojo.setDueInterest(dueInterest);
                    loanPojo.setTotalDue(totalDue);

                    loanPojo.setArearsCapital(ArearsCapital);
                    loanPojo.setArearsInterst(ArearsInterst);
                    loanPojo.setTotalArears(totalArrears);

                    returnTreeMap.put(acNumber, loanPojo);

                }

            }

            tx.commit();

            return loanPojo;

        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();

            return null;

        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();

            return null;
        } finally {
//            session.close ();

        }

    }

//LOAN DISBURSED DETAILS========================================================
    public List<Loan_pojo> getLoanDisbursedDetails(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(selectingDate);
//                    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
//                    cal.set(Calendar.DAY_OF_MONTH, 1);
//                    Date dateDue = cal.getTime();
        System.out.println("selectingDate>>" + selectingDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date fromDate = cal.getTime();
        System.out.println("fromDate>" + fromDate);

        cal.setTime(selectingDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date toDate = cal.getTime();
        System.out.println("toDate>" + toDate);

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Loan_pojo> returnList = new ArrayList<Loan_pojo>();

        try {

            System.out.println("product>>" + product);
            System.out.println("center>>" + center);
            System.out.println("customerCode>>" + customerCode);

            tx = session.beginTransaction();
//                              System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

//                              Criterion criIsSMSSentN = Restrictions.like("IS_SMS_SENT", "N");
//                              Criterion criIsSMSSentNull = Restrictions.isNull("IS_SMS_SENT");
//                              LogicalExpression logExpOrIsSMSSent = Restrictions.or(criIsSMSSentN, criIsSMSSentNull);
//                              criteriaCluster.add(logExpOrIsSMSSent);
            Criterion crsEql = Restrictions.between("IE_FROM", fromDate, toDate);
//                              Criterion crsEql = Restrictions.gt("IE_FROM", fromDate);
//                              Criterion crsLess = Restrictions.lt("IE_FROM", toDate);
//                              LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(crsEql);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();
//                String cusOfficeNo = zIncExpListObj.getCM_OFFICE_NO();
                Double loanVlue = zIncExpListObj.getIE_VALUE();
                Double noOfTerms = zIncExpListObj.getIE_NO_TERM();
                Date loanGrandDate = zIncExpListObj.getIE_FROM();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusOfficeNo = customer.getCmOfficeNo();

                waitingDialog.setCustomMessage("Processing : " + acCode);

                Criteria crInExpSubManual = session.createCriteria(IncomeExpenceSubManualEntity.class);
                crInExpSubManual.add(Restrictions.like("ieCode", acCode));
                List<IncomeExpenceSubManualEntity> incExpManualList = crInExpSubManual.list();
                IncomeExpenceSubManualEntity incExpManualListObj = incExpManualList.get(1);
//                                        for (IncomeExpenceSubManualEntity incExpManualListObj : incExpManualList) {
//
//                                        }

                Double ieTerm = (incExpManualListObj.getIeTerm() == null ? 0.00 : incExpManualListObj.getIeTerm());
                Double ieInt = (incExpManualListObj.getIeInt() == null ? 0.00 : incExpManualListObj.getIeInt());
                Double termAmount = ieTerm + ieInt;

                Loan_pojo loanPojo = new Loan_pojo();
                loanPojo.setIsSelected(true);
                loanPojo.setCusCode(ieMember);
                loanPojo.setCusName(cusName);
                loanPojo.setCusOfficeNo(cusOfficeNo);
                loanPojo.setTelephoneNo(tele);
                loanPojo.setAcNumber(acCode);
                loanPojo.setDisburseDate(loanGrandDate);
                loanPojo.setDisburseAmount(loanVlue);
                loanPojo.setNoOfTerms(noOfTerms);
                loanPojo.setTermAmount(termAmount);

                returnList.add(loanPojo);
//                    }

            }

            tx.commit();

            return returnList;

        } catch (HibernateException ex) {

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;

        } catch (Exception ex) {

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;
        } finally {

//            session.close ();
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }

    public Map<String, Loan_pojo> getLoanOutstanding(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

        Date selectingDate = datePara;
        String product = productPara;
        String center = centerPara;
        String customerCode = customerCodePara;

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectingDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDue = cal.getTime();

        SMSMessage messageCreater = new SMSMessage();

        Double fullPaidDiscountRate = 0.00;

        Session session = SessionFactoryUtil.getSession();
        Connection connection = ((SessionImpl) session).connection();
        Transaction tx = null;

        String selectingDateStr = MyDateAndTimeUtil.getFormatedDateAsString(selectingDate);

        List<Arrears_pojo> transactionUploadListObjectJSONList = new ArrayList<Arrears_pojo>();
        Map<String, Loan_pojo> returnTreeMap = new TreeMap<String, Loan_pojo>();

        try {

            tx = session.beginTransaction();

            Criteria criteriaCluster = session.createCriteria(ZINCOME_EXPENCE_SUB_entity.class);
            criteriaCluster.add(Restrictions.like("IE_SUB_TYPE", "LOAN"));

            if (!product.equalsIgnoreCase("-1")) {
                Criterion criOne2 = Restrictions.like("IE_MAIN", product);
                Criterion criNull2 = Restrictions.isNull("IE_MAIN");
                LogicalExpression logExpOr2 = Restrictions.or(criOne2, criNull2);
                criteriaCluster.add(logExpOr2);
            }

            if (!center.equalsIgnoreCase("-1")) {
                Criterion criOne3 = Restrictions.like("CC_MAIN_2", center);
                Criterion criNull3 = Restrictions.isNull("CC_MAIN_2");
                LogicalExpression logExpOr3 = Restrictions.or(criOne3, criNull3);
                criteriaCluster.add(logExpOr3);
            }

            if (!customerCode.equalsIgnoreCase("-1")) {
                Criterion criOne4 = Restrictions.like("IE_MEMBER", customerCode);
                Criterion criNull4 = Restrictions.isNull("IE_MEMBER");
                LogicalExpression logExpOr4 = Restrictions.or(criOne4, criNull4);
                criteriaCluster.add(logExpOr4);
            }

            Criterion criOne = Restrictions.like("IE_ACTIVE", "1");
            Criterion criNull = Restrictions.isNull("IE_ACTIVE");
            LogicalExpression logExpOr = Restrictions.or(criOne, criNull);
            criteriaCluster.add(logExpOr);

            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);

            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
            criteriaCluster.add(leEqlLess);

            List<ZINCOME_EXPENCE_SUB_entity> zIncExpList = criteriaCluster.list();

            for (ZINCOME_EXPENCE_SUB_entity zIncExpListObj : zIncExpList) {

                String acCode = zIncExpListObj.getIE_CODE();
                String ieMember = zIncExpListObj.getIE_MEMBER();
                String cusName = zIncExpListObj.getCM_DESC_2();

                Criteria crCustomerEntity = session.createCriteria(Customer.class);
                crCustomerEntity.add(Restrictions.like("cmCode", ieMember));
                Customer customer = (Customer) crCustomerEntity.uniqueResult();

                String tele = customer.getCmTele();
                String cusName_English = customer.getCmNameEnglish();

                Loan_pojo loanPojo = new Loan_pojo();
                loanPojo.setIsSelected(true);
                loanPojo.setCusCode(ieMember);
                loanPojo.setCusName(cusName_English);
//                loanPojo.setCusOfficeNo(cusOfficeNo);
                loanPojo.setAcNumber(acCode);
                loanPojo.setTelephoneNo(tele);

                String accBalanceString = "0.00";
                String sqlString = "SELECT SUM(q2) AS SUMQ2 FROM QDEPOSIT_INCOME_EXPENCE_SUB_ALL WHERE PE_EXPENCE = '" + acCode.trim() + "'";
                PreparedStatement pstm = connection.prepareStatement(sqlString);
                ResultSet rset = pstm.executeQuery();
                double subTotal = 0.00;
                while (rset.next()) {
                    subTotal = subTotal + rset.getDouble("SUMQ2");
                }
                accBalanceString = MyRounding.roundToLastTwoDecimal(subTotal);

                loanPojo.setOutstandingCapital(subTotal);

                if (subTotal > 0) {
                    returnTreeMap.put(acCode, loanPojo);
                }

//                    }
            }

            tx.commit();
            return returnTreeMap;

        } catch (HibernateException ex) {

            ex.printStackTrace();
            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;

        } catch (Exception ex) {

            ex.printStackTrace();

            MyMessagesUtility.showError(ex.toString());

            if (tx != null) {
                tx.rollback();
            }

            return null;
        } finally {

//            session.close ();
            waitingDialog.dispose();
            trdWaiting.interrupt();
        }

    }
}
