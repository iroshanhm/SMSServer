package iroshan.com.smsserver.service;

import iroshan.com.common.derby.pojo.Setting_pojo;
import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.view.ApplicationDesktop;
import iroshan.com.smsserver.view.WaitingDialog;
import iroshan.com.smsserver.view.WaitingDialogSMSSend;
import iroshan.com.smsserver.view.WaitingDialogView;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.Phonebook;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

public class MySMSServer
{

    // ***** WAITING DIALOG - START *****************
    WaitingDialog waitingDialog;
    Thread trdWaiting;
    // ***** WAITING DIALOG - END *****************

    
    
    
//    private WaitingDialogView waitDialog;
    static String s;

    private Connection conn;
    public static Service smsServerService;
    Phonebook phonebook;
    List<InboundMessage> msgList;
    InboundNotification inboundNotification;
    CallNotification callNotification;
    GatewayStatusNotification statusNotification;
    OutboundNotification outboundNotification;
    static SerialModemGateway serialModemGateway;
    OutboundMessage msg;
    private String comPortID = null;

//    Thread trdPleaseWait = null;
//    WaitDialogView waitDialog = null;
    public MySMSServer ()
    {
        comPortID = Setting_pojo.getComPortID ();
//        startService ();
    }

    public static Boolean checkServiceAvailable ()
    {
        Boolean returnValue = false;

        if (smsServerService != null)
        {
            returnValue = true;
        }

        return returnValue;
    }

    public static Service getSmsServerService ()
    {

        if (smsServerService == null)
        {
            MyMessagesUtility.showInformation ("Service NOT started.");
        }

        return smsServerService;
    }

    public static void setSmsServerService (Service smsServerService)
    {
        MySMSServer.smsServerService = smsServerService;
    }

//    public void startService ()
//    {
//
//
//
//        WaitingDialogView waitingDialog = new WaitingDialogView ();
//        waitingDialog.setCustomMessage ("Connecting...");
//        waitingDialog.setVisible (true);
//
//        System.out.println ("Im here...");
//
//
//
//        boolean aFlag = true;
//        if (comPortID == null)
//        {
//            aFlag = false;
//        }
//
//        if (aFlag)
//        {
//
//            smsServerService = Service.getInstance ();
//            System.out.println ("this.srv.getServiceStatus() >> " + smsServerService.getServiceStatus ());
//
//
//            if (smsServerService.getServiceStatus () == ServiceStatus.STARTED)
//            {
//
//                ApplicationDesktop.setStatusAsStart ();
//
////                MyMessagesUtility.showInformation ("Server Started ...");
//
//            } else
//            {
//
//                System.out.println ("comPortID >> " + comPortID);
//                try
//                {
//                    inboundNotification = new InboundNotification ();
//                    callNotification = new CallNotification ();
//                    statusNotification = new GatewayStatusNotification ();
//                    outboundNotification = new OutboundNotification ();
////        orphanedMessageNotification = new OrphanedMessageNotification();
//
////            lblMsg.setText("Starting with COM Port ID : " + comPortID); 115600
//
//                    MySMSServer.serialModemGateway = new SerialModemGateway ("SMSServer", comPortID.trim (), 9600, "", "");
//                    MySMSServer.serialModemGateway.getATHandler ().setStorageLocations ("SM");
//                    MySMSServer.serialModemGateway.setProtocol (AGateway.Protocols.PDU);
//                    MySMSServer.serialModemGateway.setInbound (true);
//                    MySMSServer.serialModemGateway.setOutbound (true);
//
//                    MySMSServer.smsServerService.setInboundMessageNotification (inboundNotification);
//                    MySMSServer.smsServerService.setOutboundMessageNotification (outboundNotification);
//                    MySMSServer.smsServerService.setCallNotification (callNotification);
//                    MySMSServer.smsServerService.setGatewayStatusNotification (statusNotification);
//
//
//                    MySMSServer.smsServerService.addGateway (serialModemGateway);
//
////                    MySMSServer.smsServerService.startService (true);
////                    MySMSServer.smsServerService.startService ();
//
//
//
////                    System.out.println ("getSignalLevel ()>" + MySMSServer.serialModemGateway.getSignalLevel ());
//
//                    System.out.println ("Modem Information:");
//
////                    Home.setConsolText("Modem Information");
////                    Home.setConsolText("=================");
////
////                    System.out.println("  Manufacturer: " + serialModemGateway.get);
////                    Home.setConsolText("  Manufacturer: " + serialModemGateway.getManufacturer());
////                    Home.lblManufacturer.setText(serialModemGateway.getManufacturer());
////
////                    System.out.println("  Model: " + serialModemGateway.getModel());
////                    Home.setConsolText("  Model: " + serialModemGateway.getModel());
////                    Home.lblModel.setText(serialModemGateway.getModel());
////
////                    System.out.println("  Serial No: " + serialModemGateway.getSerialNo());
////                    Home.setConsolText("  Serial No: " + serialModemGateway.getSerialNo());
////                    Home.lblSerial.setText(serialModemGateway.getSerialNo());
////
////                    System.out.println("  SIM IMSI: " + serialModemGateway.getImsi());
////                    Home.setConsolText("  SIM IMSI: " + serialModemGateway.getImsi());
////
////                    System.out.println("  Signal Level: " + serialModemGateway.getSignalLevel() + "%");
////                    Home.setConsolText("  Signal Level: " + serialModemGateway.getSignalLevel() + "%");
////
////                    Home.lblSignalLevel.setText(String.valueOf(serialModemGateway.getSignalLevel()));
////
////                    System.out.println("  Battery Level: " + serialModemGateway.getBatteryLevel() + "%");
////                    Home.setConsolText("  Battery Level: " + serialModemGateway.getBatteryLevel() + "%");
////
////                    System.out.println("  SMsCenter number: " + serialModemGateway.getSmscNumber() + "%");
////                    Home.setConsolText("  SMsCenter number: " + serialModemGateway.getSmscNumber() + "%");
////                    MyMessagesUtility.showInformation ("Server Started ...");
//
//                    ApplicationDesktop.setStatusAsStart ();
//
//
//
//                } catch (GatewayException ex)
//                {
//                    System.out.println ("1");
//
//                    if (ex.getMessage ().trim ().equalsIgnoreCase ("Comm library exception: java.lang.RuntimeException: gnu.io.NoSuchPortException"))
//                    {
//                        MyMessagesUtility.showWarning ("Check COM Port(No Such a Port).");
//                    } else if (ex.getMessage ().trim ().equalsIgnoreCase ("Comm library exception: java.lang.RuntimeException: gnu.io.PortInUseException: org.smslib"))
//                    {
//                        MyMessagesUtility.showWarning ("Port In Use, Please Restart Application.");
//                    } else if (ex.getMessage ().trim ().equalsIgnoreCase ("Comm library exception: java.lang.RuntimeException: gnu.io.PortInUseException: Unknown Application"))
//                    {
//                        MySMSServer.serialModemGateway.getGatewayId ();
//                        MyMessagesUtility.showWarning ("Port In Use, Please Check Mobile Partner is Running.");
//                    } else
//                    {
//                        MyMessagesUtility.showError (ex.getMessage ());
//                    }
//
//                    ApplicationDesktop.setStatusAsStop ();
//                    ex.printStackTrace ();
//                    Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//                } //                catch (SMSLibException ex)
//                //                {
//                //                    System.out.println ("2");
//                //                    ApplicationDesktop.setStatusAsStop ();
//                //                    MyMessagesUtility.showError (ex.getMessage ());
//                //                    ex.printStackTrace ();
//                //                    Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//                //                } 
//                //                catch (IOException ex)
//                //                {
//                //                    System.out.println ("3");
//                //                    ApplicationDesktop.setStatusAsStop ();
//                //                    MyMessagesUtility.showError (ex.getMessage ());
//                //                    ex.printStackTrace ();
//                //                    Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//                //                }
//                //                catch (InterruptedException ex)
//                //                {
//                //                    System.out.println ("4");
//                //                    ApplicationDesktop.setStatusAsStop ();
//                //                    MyMessagesUtility.showError (ex.getMessage ());
//                //                    ex.printStackTrace ();
//                //                    Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//                //                } 
//                catch (Exception ex)
//                {
//                    System.out.println ("5");
//                    ApplicationDesktop.setStatusAsStop ();
//                    MyMessagesUtility.showError ("Error: " + ex.getMessage ());
//                    ex.printStackTrace ();
//                    Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//                } finally
//                {
////                    trd.interrupt ();
//                    waitingDialog.dispose ();
//                }
//
//            }
//
////            trdPleaseWait.interrupt();
////            waitDialog.dispose();
//        }
//
//        waitingDialog.dispose ();
//
//    }
    public void stopService ()
    {

        WaitingDialogView waitingDialog = new WaitingDialogView ();
        waitingDialog.setCustomMessage ("Disconnecting...");
        waitingDialog.setVisible (true);

        try
        {
            MySMSServer.smsServerService.getInstance ().stopService ();
//            MySMSServer.smsServerService.stopService ();

            MySMSServer.smsServerService.removeGateway (serialModemGateway);
            ApplicationDesktop.setStatusAsStop ();

//            Home.setConsolText("Server Stopped.");
        } catch (TimeoutException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (GatewayException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (InterruptedException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (SMSLibException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } finally
        {
            waitingDialog.dispose ();
        }

//        } catch (SMSLibException e)
//        {
//            MyMessagesUtility.showError (e.getMessage ());
//            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, e);
//            Home.setConsolText("Error : " + e.getMessage());
//            Home.setConsolText(e.toString());
//        }
//        catch (IOException ex)
//        {
//            MyMessagesUtility.showError (ex.getMessage ());
//            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//        } catch (InterruptedException ex)
//        {
//            MyMessagesUtility.showError (ex.getMessage ());
//            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//        }
    }

    public static void restartService ()
    {
//        if (MySMSServer.getSmsServerService ().getServiceStatus () == Service.ServiceStatus.STARTED)
//        {
//            try
//            {
//                MySMSServer.getSmsServerService ().getInstance ().stopService ();
//            } catch (SMSLibException ex)
//            {
//                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//            } catch (IOException ex)
//            {
//                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//            } catch (InterruptedException ex)
//            {
//                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//            }
//
//        }
//
//        try
//        {
//            MySMSServer.getSmsServerService ().getInstance ().startService ();
//        } catch (SMSLibException ex)
//        {
//            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//        } catch (IOException ex)
//        {
//            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//        } catch (InterruptedException ex)
//        {
//            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//        }

    }


    public void assignGateway ()
    {

//        WaitingDialogView waitingDialog = new WaitingDialogView();
//        waitingDialog.setCustomMessage("Connecting...");
//        waitingDialog.setVisible(true);

//-------------------------------------------------------        
        trdWaiting = new Thread (new Runnable ()
        {
            @Override
            public void run ()
            {
                waitingDialog = new WaitingDialog ("", "Connecting ...", ApplicationDesktop.appDesktop, true);
                waitingDialog.setVisible (true);
            }
        });
        trdWaiting.start ();
//-------------------------------------------------------



        if (MySMSServer.serialModemGateway != null)
        {

            try
            {
                MySMSServer.smsServerService.startService ();
            } catch (SMSLibException ex)
            {

                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);

            } catch (IOException ex)
            {
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);

            } catch (InterruptedException ex)
            {
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);

            } catch (Exception ex)
            {
                System.out.println ("" + ex.getMessage ());
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);

            } finally
            {
                waitingDialog.dispose ();
                trdWaiting.interrupt ();
            }
        }


//---------------------------------------------------------------------------------------------------------------


        if (MySMSServer.serialModemGateway == null)
        {
            try
            {
                inboundNotification = new InboundNotification ();
                callNotification = new CallNotification ();
                statusNotification = new GatewayStatusNotification ();
                outboundNotification = new OutboundNotification ();

//        orphanedMessageNotification = new OrphanedMessageNotification();
//            lblMsg.setText("Starting with COM Port ID : " + comPortID); 115600
                MySMSServer.serialModemGateway = new SerialModemGateway ("MobileBanker", comPortID.trim (), 9600, "", "");
//        MySMSServer.serialModemGateway.getATHandler ().setStorageLocations ("SM");
//        MySMSServer.serialModemGateway.setProtocol (AGateway.Protocols.PDU);
                MySMSServer.serialModemGateway.getATHandler ().setStorageLocations ("SMME");
// Set the modem protocol to PDU (alternative is TEXT). PDU is the default, anyway…

//            MySMSServer.serialModemGateway.setProtocol (Protocols.PDU);
                MySMSServer.serialModemGateway.setSimPin ("0000");
                MySMSServer.serialModemGateway.setSmscNumber ("");
//                MySMSServer.serialModemGateway.setSimPin2 ("0000");
                MySMSServer.serialModemGateway.setInbound (true);
                MySMSServer.serialModemGateway.setOutbound (true);

                smsServerService = Service.getInstance ();

//                1000 //1000 milliseconds is one second.
//                int minutes = 1 / 2;
//                int milliseconds = minutes * 60000;
//                smsServerService.S.AT_WAIT_CGMS = 1000 * 2;
                smsServerService.S.OUTBOUND_RETRIES = 0;
                smsServerService.S.SERIAL_RTSCTS_OUT = true;

                //                smsServerService.S.SERIAL_NOFLUSH = true;
                //                smsServerService.S.SERIAL_POLLING = true;
                //                smsServerService.S.SERIAL_POLLING_INTERVAL = 200;
                MySMSServer.smsServerService.setInboundMessageNotification (inboundNotification);
                MySMSServer.smsServerService.setOutboundMessageNotification (outboundNotification);
                MySMSServer.smsServerService.setCallNotification (callNotification);
                MySMSServer.smsServerService.setGatewayStatusNotification (statusNotification);

                MySMSServer.smsServerService.addGateway (serialModemGateway);
                MySMSServer.smsServerService.startService ();

                System.out.println ("AT_WAIT>>" + smsServerService.S.AT_WAIT);
                System.out.println ("AT_WAIT_CGMS>>" + smsServerService.S.AT_WAIT_CGMS);

            } catch (GatewayException ex)
            {

                ex.printStackTrace ();
                System.out.println ("ex.getMessage ()>>" + ex.getMessage ());
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);

                if (ex.getMessage ().equalsIgnoreCase ("Comm library exception: java.lang.RuntimeException: javax.comm.PortInUseException: Port currently owned by Unknown Windows Application"))
                {
                    MyMessagesUtility.showWarning ("Port currently owned by Unknown Windows Application");
                } else if (ex.getMessage ().equalsIgnoreCase ("Comm library exception: java.lang.RuntimeException: javax.comm.NoSuchPortException"))
                {
                    MyMessagesUtility.showWarning ("Please check port setting.");
                } else
                {

                }
            } catch (SMSLibException ex)
            {
                ex.printStackTrace ();
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);

            } catch (IOException | InterruptedException ex)
            {
                ex.printStackTrace ();
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);

            } finally
            {
                waitingDialog.dispose ();
                trdWaiting.interrupt ();
            }

        }



    }

    public static void restartServiceWithWaiting ()
    {

//        int minutes = 1 / 2;
//        long milliseconds = minutes * 60000;
//        try
//        {
//            Thread.sleep (milliseconds);
//        } catch (InterruptedException ex)
//        {
//            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
//        }
        if (MySMSServer.getSmsServerService ().getServiceStatus () == Service.ServiceStatus.STARTED)
        {
            try
            {
                MySMSServer.getSmsServerService ().getInstance ().stopService ();
            } catch (SMSLibException ex)
            {
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (IOException ex)
            {
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (InterruptedException ex)
            {
                Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
            }

        }

        try
        {
            MySMSServer.getSmsServerService ().getInstance ().startService ();
        } catch (SMSLibException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (InterruptedException ex)
        {
            Logger.getLogger (MySMSServer.class.getName ()).log (Level.SEVERE, null, ex);
        }

    }

}
