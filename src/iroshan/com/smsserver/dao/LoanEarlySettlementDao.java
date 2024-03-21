/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.dao;

import iroshan.com.common.config.SessionFactoryUtil;
import iroshan.com.common.utility.MyDateAndTimeUtil;
import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.entity.Customer;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Iroshan
 */
public class LoanEarlySettlementDao {

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

//		String tele = zIncExpListObj.getCM_TELE();
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

    public Map<String, Loan_pojo> getEarlySettlement(Date datePara, String productPara, String centerPara, String customerCodePara) {

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

//            Criterion crsEql = Restrictions.eq("IE_FROM", selectingDate);
//            Criterion crsLess = Restrictions.lt("IE_FROM", selectingDate);
//            LogicalExpression leEqlLess = Restrictions.or(crsEql, crsLess);
//            criteriaCluster.add(leEqlLess);
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
//                    Double totalOutstanding = outstandingCapital;
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

//GET FINE DETAILS *************************************************************
                    System.out.println("_____________________________________________________________");
                    System.out.println("_____________________________________________________________");
                    System.out.println("_____________________________________________________________");
                    System.out.println("_____________________________________________________________");
                    System.out.println("_____________________________________________________________");

                    System.out.println("acCode>>" + acCode);
                    Criteria crqPrintInExp3 = session.createCriteria(MobileAccountBalances.class);
                    crqPrintInExp3.add(Restrictions.like("maCode", acCode));

                    double fine = 0.00;
                    MobileAccountBalances mobileAccountBalances = (MobileAccountBalances) crqPrintInExp3.uniqueResult();
                    if (mobileAccountBalances != null) {
                        fine = mobileAccountBalances.getMaLoanArrearsfine();
                    }

                    System.out.println("_____________________________________________________________");
                    System.out.println("_____________________________________________________________");
                    System.out.println("_____________________________________________________________");
                    System.out.println("_____________________________________________________________");
                    System.out.println("_____________________________________________________________");

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
                    loanPojo.setFine(fine);

                    returnTreeMap.put(acNumber, loanPojo);

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
//                String tele = zIncExpListObj.getCM_TELE();

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
}
