/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.view;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.Company_Profile;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;
import iroshan.com.smsserver.service.CustomMessage_service;
import iroshan.com.smsserver.service.SMSCount_Service;
import iroshan.com.smsserver.service.SendMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.OutboundMessage;

/**
 *
 * @author Iroshan
 */
public class CustomMessage_view extends javax.swing.JInternalFrame {

    SendMessage sendMessage = null;

    /**
     * Creates new form CustomMessage_view
     */
    public CustomMessage_view() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tf_phoneNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tf_message = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Custom Message");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/iroshan/com/smsserver/view/Messaging.png"))); // NOI18N

        jLabel1.setText("Phone No :");

        jLabel2.setText("Message :");

        tf_message.setColumns(20);
        tf_message.setRows(5);
        jScrollPane1.setViewportView(tf_message);

        jButton1.setText("CLEAR");

        jButton2.setText("SEND");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_phoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 185, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tf_phoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        sendCustomMessage();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea tf_message;
    private javax.swing.JTextField tf_phoneNo;
    // End of variables declaration//GEN-END:variables

    private void sendCustomMessage() {


        new Thread(new Runnable() {

            @Override
            public void run() {

                if (MyMessagesUtility.showConfirmDoYouWantToRemove("Confirm ?") == 0) {


                    try {

//                    --------------------------------------
                        Company_Profile.setMsgSendStart(true);
//                    --------------------------------------

                        if (sendMessage == null) {
                            sendMessage = new SendMessage();
                        }


                        int sentSMSCount = 0;
                        int sentConfirmSMSCount = 0;

                        OutboundMessage.MessageStatuses confirmStart = sendMessage.sendConfirm_1(Company_Profile.getConfirmTeleNo(), "Message to member 2 sending start.");
                        if (!confirmStart.toString().trim().equals(MessageStatusEnum.SENT.toString().trim())) {
                            MyMessagesUtility.showWarning("Message to member 2 " + confirmStart.toString());
                        } else if (confirmStart.toString().trim().equals(MessageStatusEnum.SENT.toString().trim())) {
                            ++sentConfirmSMSCount;
                        }


                        String phoneNo = tf_phoneNo.getText().trim();
                        String messgae = tf_message.getText().trim();
                        messgae = messgae.concat(Company_Profile.getCompanyName());

                        OutboundMessage.MessageStatuses msgStatus = new CustomMessage_service().sendCustomMessage(phoneNo, messgae);
                        if (msgStatus.toString().trim().equalsIgnoreCase(OutboundMessage.MessageStatuses.SENT.toString().trim())) {
                            ++sentSMSCount;
                        }



                        OutboundMessage.MessageStatuses confirmEnd = sendMessage.sendConfirm_1(Company_Profile.getConfirmTeleNo(), "Message to member 2 sending end with " + sentSMSCount + " messages.");
                        if (!confirmEnd.toString().trim().equals(MessageStatusEnum.SENT.toString().trim())) {
                            MyMessagesUtility.showWarning("Message to member 2 " + confirmEnd.toString());
                        } else if (confirmEnd.toString().trim().equals(MessageStatusEnum.SENT.toString().trim())) {
                            ++sentConfirmSMSCount;
                        }


                        new SMSCount_Service().saveSMSCount(MyMessageTypeEnum.CUSTOM.toString().trim(), sentSMSCount);
                        new SMSCount_Service().saveSMSCount(MyMessageTypeEnum.CONFIRM.toString().trim(), sentConfirmSMSCount);

                        tf_phoneNo.setText("");
                        tf_message.setText("");




                    } catch (Exception ex) {
                        MyMessagesUtility.showError(ex.toString());
                        Logger.getLogger(CustomMessage_view.class.getName()).log(Level.SEVERE, null, ex);
                    }






                }

            }
        }).start();

    }

}
