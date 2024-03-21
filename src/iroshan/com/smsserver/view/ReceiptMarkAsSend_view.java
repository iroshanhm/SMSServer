/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.view;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.com.smsserver.pojo.ReceiptSMS_pojo;
import iroshan.com.smsserver.service.ReceiptMarkAsSend_service;
import iroshan.com.smsserver.service.Jurnal_service;
import java.util.ArrayList;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Iroshan
 */
public class ReceiptMarkAsSend_view extends javax.swing.JInternalFrame
{

    public static ReceiptMarkAsSend_view receiptJIFrame_view = null;
    static List<ReceiptSMS_pojo> receiptList;

    /**
     * Creates new form ArrearsJIFrame_view
     */
    public ReceiptMarkAsSend_view ()
    {
        initComponents ();
        receiptJIFrame_view = this;
        jRadioButton1.doClick ();
//        tblPending.setDefaultRenderer (Object.class, new CustomTableCellRenderForSendReceiptSMS ());
//        new CustomTableCellRenderForSendReceiptSMS ().setCustomCellRender (tblSendSMS);
    }


    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings ("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        selectAllButton = new javax.swing.JButton();
        unselectAllButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPending = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("New Receipts");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/iroshan/com/smsserver/view/Messaging.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(115, 20));

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));

        searchButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        searchButton.setText("SEARCH");
        searchButton.setEnabled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                searchButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("All Receipt");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Selection");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jRadioButton2ActionPerformed(evt);
            }
        });

        selectAllButton.setText("Select All");
        selectAllButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                selectAllButtonActionPerformed(evt);
            }
        });

        unselectAllButton.setText("Unselect All");
        unselectAllButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                unselectAllButtonActionPerformed(evt);
            }
        });

        tblPending.setAutoCreateRowSorter(true);
        tblPending.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "SELECT", "RECEIPT NO", "CUS. CODE", "CUS. NAME", "ACCOUNT OFFICE NO", "TELE. NO", "MESSAGE"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean []
            {
                true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tblPending.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPending.setFillsViewportHeight(true);
        tblPending.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                tblPendingMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblPending);
        if (tblPending.getColumnModel().getColumnCount() > 0)
        {
            tblPending.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(selectAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(unselectAllButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectAllButton)
                    .addComponent(unselectAllButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Clear");

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Mark as Send");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addGap(18, 18, 18)
                        .addComponent(searchButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton1)
                        .addComponent(jRadioButton2))
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        loadSendingReceiptToList ();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        doMarkAsSend ();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblPendingMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tblPendingMouseReleased
    {//GEN-HEADEREND:event_tblPendingMouseReleased

//        tblSelectProsesing ();

    }//GEN-LAST:event_tblPendingMouseReleased

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_selectAllButtonActionPerformed
    {//GEN-HEADEREND:event_selectAllButtonActionPerformed
        selectAllProcessing ();
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void unselectAllButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_unselectAllButtonActionPerformed
    {//GEN-HEADEREND:event_unselectAllButtonActionPerformed
        unselectAllProcessing ();
    }//GEN-LAST:event_unselectAllButtonActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButton2ActionPerformed
    {//GEN-HEADEREND:event_jRadioButton2ActionPerformed
        selectionActive ();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButton1ActionPerformed
    {//GEN-HEADEREND:event_jRadioButton1ActionPerformed
        allActive ();
    }//GEN-LAST:event_jRadioButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton selectAllButton;
    private static javax.swing.JTable tblPending;
    private javax.swing.JButton unselectAllButton;
    // End of variables declaration//GEN-END:variables


    public static ReceiptMarkAsSend_view getReceiptJIFrame_view ()
    {
        return receiptJIFrame_view;
    }

    public static void setReceiptJIFrame_view (ReceiptMarkAsSend_view receiptJIFrame_view)
    {
        ReceiptMarkAsSend_view.receiptJIFrame_view = receiptJIFrame_view;
    }










    void loadSendingReceiptToList ()
    {
        new Thread (new Runnable ()
        {

            @Override
            public void run ()
            {
                receiptList = new ReceiptMarkAsSend_service ().getNewReceiptsList ();
                loadReceiptToTables ();
            }
        }).start ();

    }






    public void loadReceiptToTables (String recipientPara, String textPara, String messageStatusStringPara)
    {

        String recipient = recipientPara;
        String text = textPara;
        String messageStatusString = messageStatusStringPara;



// List<ReceiptSMS_pojo> receiptList;
//        List<ReceiptSMS_pojo> outstandingMap = receiptList;
        if (receiptList != null)
        {
            for (int i = 0 ; i < receiptList.size () ; i++)
            {
                ReceiptSMS_pojo loanPojo = receiptList.get (i);

                String telList = loanPojo.getTelNo ().trim ();
                String msgList = loanPojo.getMessage ().trim ();


                System.out.println ("**************************");
                System.out.println ("recipient>" + recipient);
                System.out.println ("telList>" + telList);
                System.out.println ("text>" + text);
                System.out.println ("msgList>" + msgList);
                System.out.println ("**************************");

                if (recipient.equalsIgnoreCase (telList) && text.equalsIgnoreCase (msgList))
                {
                    receiptList.remove (i);
                    System.out.println ("messageStatusString >>>>>>>>>>>>>" + messageStatusString);
                    loanPojo.setMsgStatus (messageStatusString);
                    receiptList.add (loanPojo);
//                    new SendMessageSaveDao ().saveToDB (MyMessageTypeEnum.OUTSTANDING.toString (), -1, telList, msgList, om.getDate ());
                }

            }
        }

        loadReceiptToTables ();


    }









    public void loadReceiptToTables ()
    {


//        PENDING
        DefaultTableModel dfTblMdPending = (DefaultTableModel) tblPending.getModel ();
        int rowCountPending = dfTblMdPending.getRowCount ();

        for (int i = 0 ; i < rowCountPending ; i++)
        {
            dfTblMdPending.removeRow (0);
        }




//        List<ReceiptSMS_pojo> receiptSMSPojo = receiptList;

        for (int i = 0 ; i < receiptList.size () ; i++)
        {
            ReceiptSMS_pojo receiptListObj = receiptList.get (i);

//        }
//
//        for (ReceiptSMS_pojo receiptListObj : getReceiptList ())
//        {

            String status = receiptListObj.getMsgStatus ();
            status = status.trim ();


            System.out.println (">>>>>>>>>>>>>>>>>>>>>>>" + status);


            if (status.equalsIgnoreCase (MessageStatusEnum.PENDING.toString ()))
            {
                dfTblMdPending.addRow (new Object[]
                {
                    receiptListObj.getIsSelected (), receiptListObj.getReceiptNo (), receiptListObj.getCusCode (), receiptListObj.getCusName (), receiptListObj.getCusOfficeNo (), receiptListObj.getTelNo (),
                    receiptListObj.getMessage ()
                });
            }



        }
        tblPending.revalidate ();
    }










    private void doMarkAsSend ()
    {

        new Thread (new Runnable ()
        {

            @Override
            public void run ()
            {

                if (MyMessagesUtility.showConfirmDoYouWantToRemove ("Confirm ?") == 0)
                {

                    if (jRadioButton1.isSelected ())
                    {
                        
                        new ReceiptMarkAsSend_service ().doMarkAsSendAll ();
                        
                    } else if (jRadioButton2.isSelected ())
                    {
                        List<ReceiptSMS_pojo> paymentList2 = new ArrayList<ReceiptSMS_pojo> ();

                        DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
                        int rowCount = dfTblMd.getRowCount ();
                        for (int i = 0 ; i < rowCount ; i++)
                        {
                            Boolean isSelected2 = Boolean.valueOf (dfTblMd.getValueAt (i, 0).toString ());
                            String receiptNo = dfTblMd.getValueAt (i, 1).toString ();
                            String phoneNo = dfTblMd.getValueAt (i, 5).toString ();
                            String message = dfTblMd.getValueAt (i, 6).toString ();

                            ReceiptSMS_pojo loanPojo = new ReceiptSMS_pojo ();
                            loanPojo.setIsSelected (isSelected2);
                            loanPojo.setReceiptNo (receiptNo);
                            loanPojo.setTelNo (phoneNo);

                            loanPojo.setMessage (message);

                            paymentList2.add (loanPojo);

//                        changeStatusToQueue (phoneNo, message);
                        }

                        if (paymentList2.size () > 0)
                        {
//            new Loan_service ().sendDueSms (loanArearsMap2);
//            new Payment_service ().sendPaymentSms (paymentList2);
                            new ReceiptMarkAsSend_service ().doMarkAsSendList (paymentList2);
                        }
                    }

                }
            }
        }).start ();


    }

    private void tblSelectProsesing ()
    {

        int rowNo = tblPending.getSelectedRow ();
        DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
        Boolean isSelected = Boolean.valueOf (dfTblMd.getValueAt (rowNo, 0).toString ().trim ());
        String cusCode = dfTblMd.getValueAt (rowNo, 1).toString ().trim ();
        String tel = dfTblMd.getValueAt (rowNo, 2).toString ().trim ();
        String msg = dfTblMd.getValueAt (rowNo, 3).toString ().trim ();


        List<ReceiptSMS_pojo> loanArearsMap = receiptList;
        for (int i = 0 ; i < loanArearsMap.size () ; i++)
        {
            ReceiptSMS_pojo loanPojo = loanArearsMap.get (i);

            String listCusCode = loanPojo.getCusCode ().trim ();
            String listTel = loanPojo.getTelNo ().trim ();
            String listMsg = loanPojo.getMessage ().trim ();

            if (listCusCode.equalsIgnoreCase (cusCode) && listTel.equalsIgnoreCase (tel) && listMsg.equalsIgnoreCase (msg))
            {
                loanArearsMap.remove (loanPojo);
            }
            loanPojo.setIsSelected (isSelected);
            loanArearsMap.add (loanPojo);

        }
    }


    private void selectAllProcessing ()
    {
        List<ReceiptSMS_pojo> loanArearsMap = receiptList;
        int size = loanArearsMap.size ();
        for (int i = 0 ; i < size ; i++)
        {
            ReceiptSMS_pojo loanPojo = loanArearsMap.get (i);

            loanArearsMap.remove (loanPojo);

            loanPojo.setIsSelected (true);
            loanArearsMap.add (i, loanPojo);

        }

        loadReceiptToTables ();
    }

    private void unselectAllProcessing ()
    {
        List<ReceiptSMS_pojo> loanArearsMap = receiptList;
        int size = loanArearsMap.size ();
        for (int i = 0 ; i < size ; i++)
        {
            ReceiptSMS_pojo loanPojo = loanArearsMap.get (i);

            loanArearsMap.remove (loanPojo);

            loanPojo.setIsSelected (false);
            loanArearsMap.add (i, loanPojo);

        }

        loadReceiptToTables ();
    }

    private void markAsSend ()
    {

        if (MyMessagesUtility.showConfirmDoYouWantToRemove ("Confirm ?") == 0)
        {
            new ReceiptMarkAsSend_service ().doMarkAsSendList (receiptList);
        }
    }




//    private void changeStatusToQueue (String teleNoPara, String MessagePara)
//    {
//
//
//        String recipient = teleNoPara;
//        String msgText = MessagePara;
//
//        List<ReceiptSMS_pojo> receiptList = receiptList;
//        if (receiptList != null)
//        {
//            for (int i = 0 ; i < receiptList.size () ; i++)
//            {
//                ReceiptSMS_pojo listObj = receiptList.get (i);
//
//                String telList = listObj.getTelNo ().trim ();
//                String msgList = listObj.getMessage ().trim ();
//
//                if (recipient.equalsIgnoreCase (telList) && msgText.equalsIgnoreCase (msgList))
//                {
//                    receiptList.remove (i);
//                    listObj.setMsgStatus ("QUEUE");
//                    receiptList.add (i, listObj);
//
//                }
//            }
//        }
//
//        loadReceiptToTables ();
//    }

    private void selectionActive ()
    {
        searchButton.setEnabled (true);
        selectAllButton.setEnabled (true);
        unselectAllButton.setEnabled (true);
        tblPending.setEnabled (true);
        jScrollPane4.setEnabled (true);
//        jPanel3.setVisible (true);
    }

    private void allActive ()
    {
        searchButton.setEnabled (false);
        selectAllButton.setEnabled (false);
        unselectAllButton.setEnabled (false);
        tblPending.setEnabled (false);
        jScrollPane4.setEnabled (false);
//        jPanel3.setVisible (false);
    }


}
