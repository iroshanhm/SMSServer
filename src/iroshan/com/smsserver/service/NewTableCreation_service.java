/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.NewTableCreation_dao;

/**
 *
 * @author Iroshan
 */
public class NewTableCreation_service
{

    public void createSMSReceiveTable ()
    {
        new NewTableCreation_dao ().createSMSReceiveTable ();

    }


    public void createSMSSendTable ()
    {
        new NewTableCreation_dao ().createSMSSendTable ();

    }


    public void alterIsSMSSentColumnToReceiptTable ()
    {
        new NewTableCreation_dao ().alterIsSMSSentColumnToReceiptTable ();

    }


    public void alterIsSMSSentColumnToPaymentTable ()
    {
        new NewTableCreation_dao ().alterIsSMSSentColumnToPaymentTable ();

    }


    public void alterZIncomeExpencesSub ()
    {
        new NewTableCreation_dao ().alterZIncomeExpencesSub ();
    }


    public void alterFT_DTAIL ()
    {
        new NewTableCreation_dao ().alterFT_DTAIL ();
    }


    public void createVIEW_SMS_JURNAL ()
    {
        new NewTableCreation_dao ().createVIEW_SMS_JURNAL ();
    }


    public void createVIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER ()
    {
        new NewTableCreation_dao ().createVIEW_CATEGORYMAIN_CATEGORYSUB_CUSTOMER ();
    }
}
