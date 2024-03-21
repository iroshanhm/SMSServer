package iroshan.com.smsserver.view;

/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the
 * template in the editor.
 */


import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyRounding;
import iroshan.com.common.utility.WindowLocation;
import iroshan.com.smsserver.pojo.Loan_pojo;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.Company_Profile;
import iroshan.com.smsserver.pojo.PaymentSMS_pojo;
import iroshan.com.smsserver.service.Loan_service;
import iroshan.com.smsserver.service.MySMSServer;
import iroshan.com.smsserver.service.Jurnal_service;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.smslib.SMSLibException;
import org.smslib.Service;

/**
 *
 * @author Iroshan
 */
public class LoanArrears_infrm_view extends javax.swing.JInternalFrame
{

    /**
     * Creates new form CustomerUploder
     */

    private static LoanArrears_infrm_view arrears_infrm_view = null;
//    private static Map<Integer, Loan_pojo> loanArrearsMap;
    private List<Loan_pojo> loanArrearsList;
//    Map<Integer, Loan_pojo> loanArearsMap = null;
//    List<Arrears_pojo> arearsList = null;
    private Thread trdWaiting;






    public LoanArrears_infrm_view ()
    {
        initComponents ();
        arrears_infrm_view = this;
        WindowLocation.setJinternalFrameToCenter (ApplicationDesktop.jDesktopPane, this);
        loadProduct ();
        loadCenter ();
    }










    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings ("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPending = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSent = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUnsent = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        dateTF = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        cmbProduct = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        tfCustomer = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbCenter = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("ARREARS");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/iroshan/com/smsserver/view/Messaging.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblPending.setAutoCreateRowSorter(true);
        tblPending.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "SELECT", "#", "CODE", "NAME", "ACCOUNT OFFICE NO", "PHONE", "MESSAGE", "A/C", "GRANTED DATE", "GRANTED AMOUNT", "CAPITAL", "INTEREST", "ARREARS CAPITAL", "ARREARS INTEREST", "TOTAL ARREARS AMOUNT", "STATUS"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean []
            {
                true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
        jScrollPane1.setViewportView(tblPending);

        jButton1.setText("SELECT ALL");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton6.setText("UNSELECT ALL");
        jButton6.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("PENDING", jPanel5);

        tblSent.setAutoCreateRowSorter(true);
        tblSent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "#", "CODE", "NAME", "ACCOUNT OFFICE NO", "PHONE", "MESSAGE", "A/C", "GRANTED DATE", "GRANTED AMOUNT", "CAPITAL", "INTEREST", "ARREARS CAPITAL", "ARREARS INTEREST", "TOTAL ARREARS AMOUNT", "STATUS"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tblSent.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblSent.setFillsViewportHeight(true);
        jScrollPane2.setViewportView(tblSent);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("SENT", jPanel4);

        tblUnsent.setAutoCreateRowSorter(true);
        tblUnsent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "#", "CODE", "NAME", "ACCOUNT OFFICE NO", "PHONE", "MESSAGE", "A/C", "GRANTED DATE", "GRANTED AMOUNT", "CAPITAL", "INTEREST", "ARREARS CAPITAL", "ARREARS INTEREST", "TOTAL ARREARS AMOUNT", "STATUS"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tblUnsent.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblUnsent.setFillsViewportHeight(true);
        jScrollPane3.setViewportView(tblUnsent);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("UNSENT", jPanel7);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("PENDING");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton2.setText("Load");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Date :");

        dateTF.setBackground(new java.awt.Color(255, 255, 255));
        dateTF.setDate(new Date());
        dateTF.setDateFormatString("yyyy-MM-dd");

        jLabel1.setText("Product :");

        cmbProduct.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));

        jLabel2.setText("Customer :");

        jLabel4.setText("Center :");

        cmbCenter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(8, 8, 8)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfCustomer)
                    .addComponent(dateTF, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbCenter, 0, 250, Short.MAX_VALUE)
                    .addComponent(cmbProduct, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(356, 356, 356))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateTF, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setText("Send SMS");
        jButton5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("Loan Arrears");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadFieldOfficerTransactionTBL ();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        sendSms ();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose ();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblPendingMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tblPendingMouseReleased
    {//GEN-HEADEREND:event_tblPendingMouseReleased
//        tblSelectProsesing ();
    }//GEN-LAST:event_tblPendingMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        selectAllProcessing ();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton6ActionPerformed
    {//GEN-HEADEREND:event_jButton6ActionPerformed
        unselectAllProcessing ();
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbCenter;
    private javax.swing.JComboBox cmbProduct;
    private com.toedter.calendar.JDateChooser dateTF;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblPending;
    private javax.swing.JTable tblSent;
    private javax.swing.JTable tblUnsent;
    private javax.swing.JTextField tfCustomer;
    // End of variables declaration//GEN-END:variables





    private void loadProduct ()
    {
        cmbProduct.removeAllItems ();
        cmbProduct.addItem ("Select");

        List<String> list = new Loan_service ().loadProduct ();
        for (String listObj : list)
        {
            cmbProduct.addItem (listObj);
        }
    }



    private void loadCenter ()
    {
        cmbCenter.removeAllItems ();
        cmbCenter.addItem ("Select");

        List<String> list = new Loan_service ().loadCenter ();
        for (String listObj : list)
        {
            cmbCenter.addItem (listObj);
        }
    }








    public static LoanArrears_infrm_view getArrears_infrm_view ()
    {
        return arrears_infrm_view;
    }

    public static void setArrears_infrm_view (LoanArrears_infrm_view arrears_infrm_view)
    {
        LoanArrears_infrm_view.arrears_infrm_view = arrears_infrm_view;
    }

    public List<Loan_pojo> getLoanArrearsList ()
    {
        return loanArrearsList;
    }

    public void setLoanArrearsList (List<Loan_pojo> loanArrearsList)
    {
        this.loanArrearsList = loanArrearsList;
    }







    private void loadFieldOfficerTransactionTBL ()
    {

        Runnable runBle = new Runnable ()
        {

            @Override
            public void run ()
            {

                DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
                int sizeTbl = dfTblMd.getRowCount ();
                for (int i = 0 ; i < sizeTbl ; i++)
                {
                    dfTblMd.removeRow (0);
                }


                String productCodeAndName = "-1";
                if (cmbProduct.getSelectedIndex () == 0)
                {
                    productCodeAndName = "-1";
                } else
                {

                    productCodeAndName = cmbProduct.getSelectedItem ().toString ().trim ();
                    String[] productCodeAndNameArr = productCodeAndName.split ("-");
                    productCodeAndName = productCodeAndNameArr[ 0 ].trim ();

                }


                String centerCodeAndName = "-1";
                if (cmbCenter.getSelectedIndex () == 0)
                {
                    centerCodeAndName = "-1";
                } else
                {
                    centerCodeAndName = cmbCenter.getSelectedItem ().toString ().trim ();
                    String[] centerCodeAndNameArr = centerCodeAndName.split ("-");
                    centerCodeAndName = centerCodeAndNameArr[ 0 ].trim ();

                }


                String customer = tfCustomer.getText ();
                if (customer.isEmpty ())
                {
                    customer = "-1";
                } else
                {
                    customer = customer.trim ();
                }




                Date dateSelected = dateTF.getDate ();
                if (dateSelected == null)
                {
                    MyMessagesUtility.showWarning ("Set the Date");
                } else
                {
                    loanArrearsList = new Loan_service ().getLoanArrearsDetails (dateSelected, productCodeAndName, centerCodeAndName, customer);
                    loadArrearsToTables ();
                }

            }
        };

        new Thread (runBle).start ();
    }


    
    
    



    public void loadArrearsToTables ()
    {

        Date dateSelected = dateTF.getDate ();

//        PENDING
        DefaultTableModel dfTblMdPending = (DefaultTableModel) tblPending.getModel ();
        int rowCountPending = dfTblMdPending.getRowCount ();

        for (int i = 0 ; i < rowCountPending ; i++)
        {
            dfTblMdPending.removeRow (0);

        }

//        SENT
        DefaultTableModel dfTblMdSent = (DefaultTableModel) tblSent.getModel ();
        int rowCountSent = dfTblMdSent.getRowCount ();

        for (int i = 0 ; i < rowCountSent ; i++)
        {
            dfTblMdSent.removeRow (0);
        }

//        UNSENT
        DefaultTableModel dfTblMdUnsent = (DefaultTableModel) tblUnsent.getModel ();
        int rowCountUnsent = dfTblMdUnsent.getRowCount ();

        for (int i = 0 ; i < rowCountUnsent ; i++)
        {
            dfTblMdUnsent.removeRow (0);
        }


        int pendingSeqId = 0;
        int sentSeqId = 0;
        int unsentSeqId = 0;

//        List<Loan_pojo> loanArearsMap = getLoanArrearsList ();
//        for (int i = 0 ; i < loanArearsMap.size () ; i++)
//        {

        for (Loan_pojo loanArrearsList1 : loanArrearsList)
        {


//        }
//        for (Map.Entry<Integer, Loan_pojo> entrySet : loanArearsMap.entrySet ())
//        {

//            Integer key = entrySet.getKey ();
//            Loan_pojo value = entrySet.getValue ();

            Loan_pojo value = loanArrearsList1;

            Boolean isSele = value.getIsSelected ();
            String cusCode = value.getCusCode ();
                         String cusName = value.getCusName ();
            String cusOfficeNo = value.getCusOfficeNo ();
            String acNumber = value.getAcNumber ();
            String phoneNo = value.getTelephoneNo ().trim ();
            String message = value.getMessage ().trim ();
            Date disburseDate = value.getDisburseDate ();

            String loanAmount = ((value.getDisburseAmount () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getDisburseAmount ()));

            String capitalAmount = ((value.getCapitalFullLoan () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getCapitalFullLoan ()));
            String interestAmount = ((value.getInterestFullLoan () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getInterestFullLoan ()));
            String outstandingCapital = ((value.getOutstandingCapital () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getOutstandingCapital ()));
            String outstandingInterest = ((value.getOutstandingInterest () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getOutstandingInterest ()));
            String totalOutstanding = ((value.getTotalOutstanding () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getTotalOutstanding ()));
//            String dueCapital = ((value.getDueCapital () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getDueCapital ()));
//            String dueInterest = ((value.getDueInterest () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getDueInterest ()));
            String arearsCapital = ((value.getArearsCapital () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getArearsCapital ()));
            String arearsInterst = ((value.getArearsInterst () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getArearsInterst ()));
            String totalArears = ((value.getTotalArears () == null) ? "0.00" : MyRounding.roundToLastTwoDecimal (value.getTotalArears ()));
            String msgStatus = value.getMsgStatus ();

            if (msgStatus.equalsIgnoreCase (MessageStatusEnum.PENDING.toString ()))
            {

                ++pendingSeqId;

                dfTblMdPending.addRow (new Object[]
                {
                    isSele, pendingSeqId, cusCode,cusName,cusOfficeNo, phoneNo, message, acNumber, disburseDate, loanAmount, capitalAmount,
                    interestAmount, arearsCapital, arearsInterst, totalArears, msgStatus
                });

            } else if (msgStatus.equalsIgnoreCase (MessageStatusEnum.SENT.toString ()))
            {

                ++sentSeqId;
                dfTblMdSent.addRow (new Object[]
                {
                    sentSeqId, cusCode,cusName,cusOfficeNo, phoneNo, message, acNumber, disburseDate, loanAmount, capitalAmount,
                    interestAmount, arearsCapital, arearsInterst, totalArears, msgStatus
                });

            } else if (msgStatus.equalsIgnoreCase (MessageStatusEnum.UNSENT.toString ()))
            {

                ++unsentSeqId;
//"#", "CODE", "PHONE", "MESSAGE", "STATUS", "MFO", "A/C", "DUE AMOUNT", "TO DATE", "TOTAL ARREARS CAPITAL", "TOTAL ARREARS INTEREST", "TOTAL ARREARS AMOUNT", "GRANTED AMOUNT", "GRANTED DATE", "GRANTED DETAILS"
                dfTblMdUnsent.addRow (new Object[]
                {
                    unsentSeqId, cusCode,cusName,cusOfficeNo, phoneNo, message, acNumber, disburseDate, loanAmount, capitalAmount,
                    interestAmount, arearsCapital, arearsInterst, totalArears, msgStatus
                });

            } else if (msgStatus.equalsIgnoreCase (MessageStatusEnum.FAILED.toString ()))
            {

                ++unsentSeqId;
//"#", "CODE", "PHONE", "MESSAGE", "STATUS", "MFO", "A/C", "DUE AMOUNT", "TO DATE", "TOTAL ARREARS CAPITAL", "TOTAL ARREARS INTEREST", "TOTAL ARREARS AMOUNT", "GRANTED AMOUNT", "GRANTED DATE", "GRANTED DETAILS"
                dfTblMdUnsent.addRow (new Object[]
                {
                    unsentSeqId, cusCode,cusName,cusOfficeNo, phoneNo, message, acNumber, disburseDate, loanAmount, capitalAmount,
                    interestAmount, arearsCapital, arearsInterst, totalArears, msgStatus
                });

            }



        }


        tblPending.revalidate ();
    }


    
    
    
    
    
    


    public void loadArrearsToTables (String recipientPara, String textPara, String messageStatusStringPara)
    {

        String recipient = recipientPara;
        String text = textPara;
        String messageStatusString = messageStatusStringPara;



        List<Loan_pojo> outstandingMap = getLoanArrearsList ();
        if (outstandingMap != null)
        {

            for (int i = 0 ; i < outstandingMap.size () ; i++)
            {
                Loan_pojo loanPojo = outstandingMap.get (i);

                String telList = loanPojo.getTelephoneNo ().trim ();
                String msgList = loanPojo.getMessage ().trim ();


           

                if (recipient.equalsIgnoreCase (telList) && text.equalsIgnoreCase (msgList))
                {
                    outstandingMap.remove (i);
                    loanPojo.setMsgStatus (messageStatusString);
                    outstandingMap.add (loanPojo);
//                    new SendMessageSaveDao ().saveToDB (MyMessageTypeEnum.OUTSTANDING.toString (), -1, telList, msgList, om.getDate ());
                }

            }

        }
        loadArrearsToTables ();
    }





    private void sendSms ()
    {
        new Thread (new Runnable ()
        {

            @Override
            public void run ()
            {
                if (MyMessagesUtility.showConfirmDoYouWantToRemove ("Confirm ?") == 0)
                {

                    //                    --------------------------------------
                    Company_Profile.setMsgSendStart (true);
//                    --------------------------------------
                    
                    List<Loan_pojo> loanArearsMap2 = new ArrayList<Loan_pojo> ();

                    DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
                    int rowCount = dfTblMd.getRowCount ();
                                       
                    
                    for (int i = 0 ; i < rowCount ; i++)
                    {
                        Boolean isSelected2 = Boolean.valueOf (dfTblMd.getValueAt (i, 0).toString ());
                        String phoneNo = dfTblMd.getValueAt (i, 5).toString ();
                        String message = dfTblMd.getValueAt (i, 6).toString ();

                        Loan_pojo loanPojo = new Loan_pojo ();
                        loanPojo.setIsSelected (isSelected2);
                        loanPojo.setTelephoneNo (phoneNo);
                        loanPojo.setMessage (message);

                        loanArearsMap2.add (loanPojo);
                    }

                    if (loanArearsMap2.size () > 0)
                    {
                        new Loan_service ().sendArrearsSms (loanArearsMap2);
                    }
                }
            }
        }).start ();

    }

    
    
    
    
    private void tblSelectProsesing ()
    {

        int rowNo = tblPending.getSelectedRow ();
        DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
        String cusCode = dfTblMd.getValueAt (rowNo, 2).toString ().trim ();
        String ac = dfTblMd.getValueAt (rowNo, 5).toString ().trim ();


        List<Loan_pojo> loanArearsMap = getLoanArrearsList ();
        for (int i = 0 ; i < loanArearsMap.size () ; i++)
        {
            Loan_pojo loanPojo = loanArearsMap.get (i);

            String listCusCode = loanPojo.getCusCode ().trim ();
            String listAC = loanPojo.getAcNumber ().trim ();

            if (listCusCode.equalsIgnoreCase (cusCode) && listAC.equalsIgnoreCase (ac))
            {
                loanArearsMap.remove (loanPojo);
            }
            loanPojo.setIsSelected (false);
            loanArearsMap.add (loanPojo);

        }
    }



    private void selectAllProcessing ()
    {

        int listSize = loanArrearsList.size ();

        for (int i = 0 ; i < listSize ; i++)
        {
            Loan_pojo loanPojo = loanArrearsList.get (i);

            loanArrearsList.remove (i);

            loanPojo.setIsSelected (true);
            loanArrearsList.add (i, loanPojo);

        }

        loadArrearsToTables ();
    }

    private void unselectAllProcessing ()
    {

        int listSize = loanArrearsList.size ();
        for (int i = 0 ; i < listSize ; i++)
        {
            Loan_pojo loanPojo = loanArrearsList.get (i);

            loanArrearsList.remove (i);

            loanPojo.setIsSelected (false);
            loanArrearsList.add (i, loanPojo);

        }

        loadArrearsToTables ();
    }
}
