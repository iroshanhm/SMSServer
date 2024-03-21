package iroshan.com.smsserver.service.gatway.mobitel;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.mobitel.esms.message.SMSManager;
import lk.mobitel.esms.session.NullSessionException;
import lk.mobitel.esms.session.SessionManager;
import lk.mobitel.esms.ws.Alias;
import lk.mobitel.esms.ws.SmsMessage;
import lk.mobitel.esms.ws.User;
import org.smslib.OutboundMessage.MessageStatuses;

/**
 *
 * @author Gim
 */
public class SendSMSThroughMobitelGatewayAPI {

//      int count = 0;

      static User user = null;
      static SessionManager sm = null;
      static Alias alias = null;

      public MessageStatuses send(String phnNoPara, String messagePara) {

//            ++count;
            String phnNo = phnNoPara;
            String message = messagePara;

            if (user == null) {
                  user = new User();
                  user.setUsername("esmsusr_tk1");
                  user.setPassword("f718vr");
            }

            if (sm == null) {
                  sm = SessionManager.getInstance();
                  sm.login(user);
            }

            if (alias == null) {
                  alias = new Alias();
                  alias.setAlias("DNCS Invest");
            }


            SmsMessage msg = new SmsMessage();
            msg.setMessage(message);
            msg.setSender(alias);
//            /**
//             * recipients per SMSMessage is limited to 500 *
//             */
//            msg.getRecipients().add("0094711234567");
            msg.getRecipients().add(phnNo);
            int responceInt = 0;
            try {
                  SMSManager smsMgr = new SMSManager();
                  responceInt = smsMgr.sendMessage(msg);
            } catch (NullSessionException ex) {
                  Logger.getLogger(SendSMSThroughMobitelGatewayAPI.class.getName()).log(Level.SEVERE, null, ex);
            }




            MessageStatuses msgStatus = null;
            if (responceInt == 200) {
                  msgStatus = MessageStatuses.SENT;
            } else {
                  msgStatus = MessageStatuses.UNSENT;
            }

            if (msgStatus == null) {
                  msgStatus = MessageStatuses.UNSENT;
            }
            return msgStatus;
      }

      /**
       * @param args the command line arguments
       */
      public static void main(String[] args) {
            SendSMSThroughMobitelGatewayAPI sendSMSThroughGatewayAPI = new SendSMSThroughMobitelGatewayAPI();
            for (int i = 0; i < 5; i++) {
                  sendSMSThroughGatewayAPI.send("94711967867", "Hellooo...");
            }

            sendSMSThroughGatewayAPI.logoutSessionAndExit();

      }

      private void logoutSessionAndExit() {
            sm.logout();
            sm.exit();
      }

}
