/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.view;


import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.common.utility.MyPhoneNoFormatingUtility;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.Company_Profile;
import iroshan.com.smsserver.pojo.NewJurnal_pojo;
import iroshan.com.smsserver.service.Loan_service;
import iroshan.com.smsserver.service.Jurnal_service;
import iroshan.com.smsserver.service.MySMSServer;
import iroshan.com.smsserver.service.Payment_service;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Iroshan
 */
public class Jurnal_if_view extends javax.swing.JInternalFrame
{

    public static Jurnal_if_view jurnal_if_view = null;
//    static List<ReceiptSMS_pojo> receiptList;
    static Map<Integer, NewJurnal_pojo> tableMap;
    Thread trdSend = null;
    Jurnal_service jurnalService = null;





    /**
     * Creates new form ArrearsJIFrame_view
     */
    public Jurnal_if_view ()
    {
        initComponents ();
        jurnal_if_view = this;
        loadCenter ();
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
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbCenter = new javax.swing.JComboBox();
        rbutLoan = new javax.swing.JRadioButton();
        rbutSaving = new javax.swing.JRadioButton();
        rbutOther = new javax.swing.JRadioButton();
        rbutAll = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        cmbSortOrder = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        butSend = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPending = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        butStopSending = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSent = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUnsent = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("New Jurnal");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/iroshan/com/smsserver/view/Messaging.png"))); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("SEARCH");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Center :");

        cmbCenter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));

        buttonGroup1.add(rbutLoan);
        rbutLoan.setText("Loan");

        buttonGroup1.add(rbutSaving);
        rbutSaving.setText("Saving");

        buttonGroup1.add(rbutOther);
        rbutOther.setText("Other");

        buttonGroup1.add(rbutAll);
        rbutAll.setSelected(true);
        rbutAll.setText("All");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        cmbSortOrder.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DEFAULT", "JURNAL NO", "CUS. CODE", "CUS. NAME", "ACCOUNT SUB OFFICE NO", "TELE. NO" }));

        jLabel2.setText("Sort by :");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbutAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbutLoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbutSaving)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbutOther)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSortOrder, 0, 1, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cmbCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbutLoan)
                        .addComponent(rbutSaving)
                        .addComponent(rbutOther)
                        .addComponent(rbutAll)
                        .addComponent(cmbSortOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );

        butSend.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        butSend.setText("SEND");
        butSend.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                butSendActionPerformed(evt);
            }
        });

        tblPending.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "SELECT", "#", "JURNAL NO", "CUS. CODE", "CUS. NAME", "ACCOUNT OFFICE NO", "TELE. NO", "MESSAGE", "STATUS"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean []
            {
                true, false, false, false, false, false, false, false, false
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
        tblPending.getTableHeader().setReorderingAllowed(false);
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
            tblPending.getColumnModel().getColumn(6).setPreferredWidth(100);
            tblPending.getColumnModel().getColumn(7).setMinWidth(250);
        }

        jButton4.setText("Select All");
        jButton4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Unselect All");
        jButton5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setText("MARK AS SEND");
        jButton6.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton6ActionPerformed(evt);
            }
        });

        butStopSending.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        butStopSending.setText("STOP SENDING");
        butStopSending.setEnabled(false);
        butStopSending.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                butStopSendingActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setText("CLOSE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(butStopSending)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butSend, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butSend, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6)
                    .addComponent(butStopSending, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addContainerGap())
        );

        jTabbedPane1.addTab("PENDING", jPanel2);

        tblSent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "#", "JURNAL NO", "CUS. CODE", "CUS. NAME", "ACCOUNT OFFICE NO", "TELE. NO", "MESSAGE", "STATUS"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("SENT", jPanel4);

        tblUnsent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "SELECT", "#", "JURNAL NO", "CUS. CODE", "CUS. NAME", "ACCOUNT OFFICE NO", "TELE. NO", "MESSAGE", "STATUS"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false, false
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
        tblUnsent.setFillsViewportHeight(true);
        jScrollPane3.setViewportView(tblUnsent);

        jButton7.setText("RE-SEND");
        jButton7.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap())
        );

        jTabbedPane1.addTab("UNSENT", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        search ();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void butSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSendActionPerformed

        sendSms ();

    }//GEN-LAST:event_butSendActionPerformed

    private void tblPendingMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tblPendingMouseReleased
    {//GEN-HEADEREND:event_tblPendingMouseReleased

        tblSelectProsesing ();

    }//GEN-LAST:event_tblPendingMouseReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton4ActionPerformed
    {//GEN-HEADEREND:event_jButton4ActionPerformed
        selectAllProcessing ();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton5ActionPerformed
    {//GEN-HEADEREND:event_jButton5ActionPerformed
        unselectAllProcessing ();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton6ActionPerformed
    {//GEN-HEADEREND:event_jButton6ActionPerformed
        markAsSend ();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton7ActionPerformed
    {//GEN-HEADEREND:event_jButton7ActionPerformed
        reSendSms ();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void butStopSendingActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butStopSendingActionPerformed
    {//GEN-HEADEREND:event_butStopSendingActionPerformed
        stopSending ();
    }//GEN-LAST:event_butStopSendingActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butSend;
    private javax.swing.JButton butStopSending;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbCenter;
    private javax.swing.JComboBox cmbSortOrder;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbutAll;
    private javax.swing.JRadioButton rbutLoan;
    private javax.swing.JRadioButton rbutOther;
    private javax.swing.JRadioButton rbutSaving;
    private static javax.swing.JTable tblPending;
    private static javax.swing.JTable tblSent;
    private static javax.swing.JTable tblUnsent;
    // End of variables declaration//GEN-END:variables

    public static Jurnal_if_view getJurnal_if_view ()
    {
        return jurnal_if_view;
    }

    public static void setJurnal_if_view (Jurnal_if_view jurnal_if_view)
    {
        Jurnal_if_view.jurnal_if_view = jurnal_if_view;
    }










    void search ()
    {
        new Thread (new Runnable ()
        {

            @Override
            public void run ()
            {

                boolean aFlag = true;

                String center_str = null;
                String type_str = "ALL";
                String sortOrder = "DEFAULT";



                String sortOrder2 = cmbSortOrder.getSelectedItem ().toString ().trim ();
                sortOrder = sortOrder2.trim ();



                if (cmbCenter.getSelectedIndex () == 0)
                {
                    aFlag = false;
                } else if (cmbCenter.getSelectedIndex () > 0)
                {
                    String center = cmbCenter.getSelectedItem ().toString ().trim ();
                    if (center.equalsIgnoreCase ("ALL"))
                    {
                        center_str = center;
                    } else
                    {
                        String[] centerArr = center.split ("-");
                        center_str = centerArr[ 0 ].trim ();
                    }
                }


                if (rbutAll.isSelected ())
                {
                    type_str = "ALL";
                } else if (rbutLoan.isSelected ())
                {
                    type_str = "LOAN";
                } else if (rbutSaving.isSelected ())
                {
                    type_str = "SAVING";
                } else if (rbutOther.isSelected ())
                {
                    type_str = "OTHER";
                }




                if (aFlag)
                {
//                receiptList = new Jurnal_service ().search (center_str,type_str);
//                    Map<Integer, NewJurnal_pojo> receiveMap = new Jurnal_service ().search (center_str, type_str);
                    tableMap = new Jurnal_service ().search ("", center_str, type_str, sortOrder);

                    loadTables ();
//                    loadReceiptToTables ();
                }
            }
        }).start ();

    }






//    public void loadTables (String recipientPara, String textPara, String messageStatusStringPara)
//    {
//
//        String recipient = recipientPara.trim ();
//        recipient = MyPhoneNoFormatingUtility.formatTo9Number (recipient);
//
//        String text = textPara.trim ();
//        String messageStatusString = messageStatusStringPara.trim ();
//
//
//
//        DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
//        int rowCount = dfTblMd.getRowCount ();
//        for (int i = 0 ; i < rowCount ; i++)
//        {
////            Boolean isSelected = Boolean.valueOf (dfTblMd.getValueAt (i, 0).toString ().trim ());
//            Integer keyNo = Integer.valueOf (dfTblMd.getValueAt (i, 1).toString ().trim ());
//
//            String tel = dfTblMd.getValueAt (i, 6).toString ().trim ();
//            tel = MyPhoneNoFormatingUtility.formatTo9Number (tel);
//            String msg = dfTblMd.getValueAt (i, 7).toString ().trim ();
//
//            if (recipient.equalsIgnoreCase (tel) && text.equalsIgnoreCase (msg))
//            {
//                NewJurnal_pojo newJRPojo = tableMap.get (keyNo);
//                newJRPojo.setStatus (messageStatusString);
//                tableMap.remove (keyNo);
//                tableMap.put (keyNo, newJRPojo);
//            }
//
//        }
//
//
//        loadTables ();
//
//    }




    public void loadTablesByMapKey (Integer keyPara, String messageStatusStringPara)
    {

        Integer key = keyPara;
        String messageStatusString = messageStatusStringPara.trim ();


        NewJurnal_pojo value = tableMap.get (key);

        value.setStatus (messageStatusString);
        tableMap.remove (key);
        tableMap.put (key, value);



        loadTables ();

    }








    public void loadTables ()
    {

//        tblPending.setAutoCreateRowSorter (false);
//        tblSent.setAutoCreateRowSorter (false);
//        tblUnsent.setAutoCreateRowSorter (false);


//        PENDING
        final DefaultTableModel dfTblMdPending = (DefaultTableModel) tblPending.getModel ();
        int rowCountPending = dfTblMdPending.getRowCount ();

        for (int i = 0 ; i < rowCountPending ; i++)
        {
            dfTblMdPending.removeRow (0);
        }
//        dfTblMdPending.setRowCount (0);




//        SENT
        final DefaultTableModel dfTblMdSent = (DefaultTableModel) tblSent.getModel ();
        int rowCountSent = dfTblMdSent.getRowCount ();

        for (int j = 0 ; j < rowCountSent ; j++)
        {
            dfTblMdSent.removeRow (0);
        }
//dfTblMdSent.setRowCount (0);





//        UNSENT
        final DefaultTableModel dfTblMdUnsent = (DefaultTableModel) tblUnsent.getModel ();
        int rowCountUnsent = dfTblMdUnsent.getRowCount ();

        for (int k = 0 ; k < rowCountUnsent ; k++)
        {
            dfTblMdUnsent.removeRow (0);
        }
//        dfTblMdUnsent.setRowCount (0);




        for (Map.Entry<Integer, NewJurnal_pojo> entrySet : tableMap.entrySet ())
        {
            Integer keyNo = entrySet.getKey ();
            NewJurnal_pojo value = entrySet.getValue ();


            Boolean isSelect = value.getIsSelect ();
            String JurnalNo = value.getJurnalNo ();
            String memCode = value.getMemCode ();
            String memName = value.getMemName ();
            String acOfficeNo = value.getAcOfficeNo ();
            String memTeleNo = value.getMemTeleNo ();
            String message = value.getMessage ();
            String status = value.getStatus ().trim ();


            if (status.equalsIgnoreCase (MessageStatusEnum.PENDING.toString ()))
            {

                dfTblMdPending.addRow (new Object[]
                {
                    isSelect, keyNo, JurnalNo, memCode, memName, acOfficeNo, memTeleNo,
                    message, status
                });
            } else if (status.equalsIgnoreCase (MessageStatusEnum.QUEUE.toString ()))
            {
                dfTblMdPending.addRow (new Object[]
                {
                    isSelect, keyNo, JurnalNo, memCode, memName, acOfficeNo, memTeleNo, message, status
                });
            } else if (status.equalsIgnoreCase (MessageStatusEnum.SENT.toString ()))
            {

                dfTblMdSent.addRow (new Object[]
                {
                    keyNo, JurnalNo, memCode, memName, acOfficeNo, memTeleNo, message, status
                });
            } else if (status.equalsIgnoreCase (MessageStatusEnum.UNSENT.toString ()))
            {

                dfTblMdUnsent.addRow (new Object[]
                {
                    isSelect, keyNo, JurnalNo, memCode, memName, acOfficeNo, memTeleNo, message, status
                });
            } else if (status.equalsIgnoreCase (MessageStatusEnum.FAILED.toString ().trim ()))
            {

                dfTblMdUnsent.addRow (new Object[]
                {
                    isSelect, keyNo, JurnalNo, memCode, memName, acOfficeNo, memTeleNo, message, status
                });
            }

        }

    }





    private void sendSms ()
    {

        trdSend = new Thread (new Runnable ()
        {
            @Override
            public void run ()
            {
                if (MyMessagesUtility.showConfirmDoYouWantToRemove ("Confirm ?") == 0)
                {

                    enableStopSending ();
                    disableSendBut ();

                    if (jurnalService == null)
                    {

//                    --------------------------------------
                        Company_Profile.setMsgSendStart (true);
//                    --------------------------------------

                        Map<Integer, NewJurnal_pojo> map = tableMap;
                        jurnalService = new Jurnal_service ();
                        jurnalService.sendJurnalSMS (map);

                    } else
                    {
                        jurnalService.sendSMS ();
                    }
                }
            }
        });

        trdSend.start ();
    }


    private void stopSending ()
    {
        if (MyMessagesUtility.showConfirmDoYouWantToRemove ("Confirm ?") == 0)
        {
            
            enableSendBut ();
            disableStopSending ();
            
            Company_Profile.setMsgSendStart (false);
            trdSend = null;
        }

    }




    private void reSendSms ()
    {
        trdSend = new Thread (new Runnable ()
        {

            @Override
            public void run ()
            {

                if (MyMessagesUtility.showConfirmDoYouWantToRemove ("Confirm ?") == 0)
                {

                    //                    --------------------------------------
                    Company_Profile.setMsgSendStart (true);
//                    --------------------------------------

                    Map<Integer, NewJurnal_pojo> map = tableMap;
//                    Map<Integer, NewJurnal_pojo> tableMap2 = tableMap;
                    new Jurnal_service ().reSendJurnalSMS (map);

                }
            }
        });
        trdSend.start ();
    }





    private void tblSelectProsesing ()
    {



        int selectedColumn = tblPending.getSelectedColumn ();
        if (selectedColumn == 0)
        {
            int rowNo = tblPending.getSelectedRow ();
            DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
            Boolean isSelected = Boolean.valueOf (dfTblMd.getValueAt (rowNo, 0).toString ().trim ());


//        Integer keyNo = dfTblMd.getValueAt (rowNo, 1).toString ().trim ();
            Integer keyNo = Integer.valueOf (dfTblMd.getValueAt (rowNo, 1).toString ().trim ());
            String tel = dfTblMd.getValueAt (rowNo, 2).toString ().trim ();
            String msg = dfTblMd.getValueAt (rowNo, 3).toString ().trim ();


            NewJurnal_pojo newJRPojo = tableMap.get (keyNo);

            newJRPojo.setIsSelect (isSelected);
            tableMap.remove (keyNo);
            tableMap.put (keyNo, newJRPojo);

        }


    }





    private void selectAllProcessing ()
    {
        int rowNo = tblPending.getSelectedRow ();
        DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
        int rowCount = dfTblMd.getRowCount ();
        for (int i = 0 ; i < rowCount ; i++)
        {
            Boolean isSelected = Boolean.valueOf (dfTblMd.getValueAt (i, 0).toString ().trim ());
            Integer keyNo = Integer.valueOf (dfTblMd.getValueAt (i, 1).toString ().trim ());
//            String tel = dfTblMd.getValueAt (i, 2).toString ().trim ();
//            String msg = dfTblMd.getValueAt (i, 3).toString ().trim ();

            NewJurnal_pojo newJRPojo = tableMap.get (keyNo);
            newJRPojo.setIsSelect (true);
            tableMap.remove (keyNo);
            tableMap.put (keyNo, newJRPojo);
        }


        loadTables ();
    }





    private void unselectAllProcessing ()
    {

//        int rowNo = tblPending.getSelectedRow ();
        DefaultTableModel dfTblMd = (DefaultTableModel) tblPending.getModel ();
        int rowCount = dfTblMd.getRowCount ();
        for (int i = 0 ; i < rowCount ; i++)
        {
            Boolean isSelected = Boolean.valueOf (dfTblMd.getValueAt (i, 0).toString ().trim ());
            Integer keyNo = Integer.valueOf (dfTblMd.getValueAt (i, 1).toString ().trim ());
//            String tel = dfTblMd.getValueAt (i, 2).toString ().trim ();
//            String msg = dfTblMd.getValueAt (i, 3).toString ().trim ();

            NewJurnal_pojo newJRPojo = tableMap.get (keyNo);
            newJRPojo.setIsSelect (false);
            tableMap.remove (keyNo);
            tableMap.put (keyNo, newJRPojo);
        }





        loadTables ();
    }





    private void markAsSend ()
    {
        if (MyMessagesUtility.showConfirmDoYouWantToRemove ("Confirm ?") == 0)
        {
            List<String> list = new ArrayList<String> ();

            Map<Integer, NewJurnal_pojo> tableMap2 = tableMap;
            for (Map.Entry<Integer, NewJurnal_pojo> entry : tableMap2.entrySet ())
            {
                Integer key = entry.getKey ();
                NewJurnal_pojo value = entry.getValue ();

                if (value.getIsSelect ())
                {
                    String paymentNo = value.getJurnalNo ();
                    list.add (paymentNo);
                }
            }

            if (list.size () > 0)
            {
                new Jurnal_service ().markAsSend (list);
                search ();
            } else
            {
                MyMessagesUtility.showWarning ("Mark at least one record.");
            }
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





    private void loadCenter ()
    {
        cmbCenter.removeAllItems ();
        cmbCenter.addItem ("Select");
        cmbCenter.addItem ("ALL");

        List<String> list = new Loan_service ().loadCenter ();
        for (String listObj : list)
        {
            cmbCenter.addItem (listObj);
        }
    }


    public void sortTblPending ()
    {

        TableRowSorter<TableModel> sorter = new TableRowSorter<> (tblPending.getModel ());
        tblPending.setRowSorter (sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<> ();

        int columnIndexToSort = 3;
        sortKeys.add (new RowSorter.SortKey (columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys (sortKeys);
        sorter.sort ();

    }





    private void enableSendBut ()
    {
        butSend.setEnabled (true);
    }

    private void disableSendBut ()
    {
        butSend.setEnabled (false);
    }





    private void enableStopSending ()
    {
        butStopSending.setEnabled (true);
    }

    private void disableStopSending ()
    {
        butStopSending.setEnabled (false);
    }

}
