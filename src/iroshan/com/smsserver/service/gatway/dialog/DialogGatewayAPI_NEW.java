/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service.gatway.dialog;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.gatway.api.dialog.Api;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.smslib.OutboundMessage;

/**
 *
 * @author iroshan
 */
public class DialogGatewayAPI_NEW {

    public OutboundMessage.MessageStatuses send(String phnNoPara, String messagePara) {

        String phnNo = phnNoPara;
        String message = messagePara;

        String returnValue;
        OutboundMessage.MessageStatuses msgStatus = null;

        try {

            HttpClient client = HttpClientBuilder.create().build();
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost(Api.getApiURL())
                    //        .setPath("/search")
                    .setParameter("number", phnNo)
                    .setParameter("mask", Api.getApiKey())
                    .setParameter("text", message)
                    //                    .setParameter("oq", "")
                    .build();

            HttpGet httpget = new HttpGet(uri);
            System.out.println(httpget.getURI());
            HttpResponse response = client.execute(httpget);

            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            String resultJson = result.toString();
            System.out.println("resultJson >> " + resultJson);

            resultJson = resultJson.trim();

            if (response.getStatusLine().getStatusCode() == 200) {
                msgStatus = OutboundMessage.MessageStatuses.SENT;
//                MyMessagesUtility.showInformation(" resultJson.equalsIgnoreCase(\"0\") msgStatus>>" + msgStatus);
//                returnValue = "SUCCESS";
            } else {
                msgStatus = OutboundMessage.MessageStatuses.UNSENT;
//                MyMessagesUtility.showInformation(" else msgStatus>>" + msgStatus);
//                returnValue = "NOT SUCCESS";
            }

            return msgStatus;

        } catch (IOException ex) {
            MyMessagesUtility.showError(ex.getMessage());
            Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
//            Logger.getLogger(SendSMSThroughGatewayAPI.class.getName()).log(Level.SEVERE, null, ex);
            msgStatus = OutboundMessage.MessageStatuses.FAILED;
            return msgStatus;
        } catch (URISyntaxException ex) {
            MyMessagesUtility.showError(ex.getMessage());
            Logger.getLogger(SendSMSThroughGatewayAPI.class.getName()).log(Level.SEVERE, null, ex);
            return msgStatus;
        }
    }

    public static void main(String[] args) {
        DialogGatewayAPI_NEW dialogGatewayAPI_NEW = new DialogGatewayAPI_NEW();
        for (int i = 0; i < 2; i++) {
            dialogGatewayAPI_NEW.send("94776605555", "Hellooo...");
        }

    }
}
