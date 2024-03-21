/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.view;

import iroshan.com.common.utility.WindowLocation;
import iroshan.Company_Profile;


/**
 *
 * @author Iroshan
 */
public class WaitingDialogSMSSend extends javax.swing.JDialog
{

    /**
     * Creates new form WaitingDialog
     */
    public WaitingDialogSMSSend (String customTitlePara, String customMessagePara, java.awt.Frame parent, boolean modal)
    {
        super (parent, modal);
        initComponents ();
        this.setTitle (customTitlePara);
        this.lblCustomeMessage.setText (customMessagePara);
        WindowLocation.setJDialogToCenter (ApplicationDesktop.appDesktop, this);
    }





    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings ("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jProgressBar1 = new javax.swing.JProgressBar();
        lblCustomeMessage = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jProgressBar1.setIndeterminate(true);

        jButton1.setText("Stop Sending");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCustomeMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCustomeMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        cancelSending ();
    }//GEN-LAST:event_jButton1ActionPerformed





    /**
     * @param args the command line arguments
     */
//    public static void main (String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels ()) {
//                if ("Nimbus".equals (info.getName ())) {
//                    javax.swing.UIManager.setLookAndFeel (info.getClassName ());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger (WaitingDialog.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger (WaitingDialog.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger (WaitingDialog.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger (WaitingDialog.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater (new Runnable () {
//            public void run () {
//                WaitingDialog dialog = new WaitingDialog (new javax.swing.JFrame (), true);
//                dialog.addWindowListener (new java.awt.event.WindowAdapter () {
//                    @Override
//                    public void windowClosing (java.awt.event.WindowEvent e) {
//                        System.exit (0);
//                    }
//                });
//                dialog.setVisible (true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblCustomeMessage;
    // End of variables declaration//GEN-END:variables





    public void setCustomMessage (String string)
    {
        this.lblCustomeMessage.setText (string);
    }

    private void cancelSending ()
    {
        Company_Profile.setMsgSendStart (false);
        this.dispose ();
    }

}
