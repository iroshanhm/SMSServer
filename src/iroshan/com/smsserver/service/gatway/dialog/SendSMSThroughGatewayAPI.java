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
import org.smslib.OutboundMessage.MessageStatuses;

/**
 *
 * @author Gim
 */
public class SendSMSThroughGatewayAPI {

    public MessageStatuses send(String phnNoPara, String messagePara) {

        String phnNo = phnNoPara;
        String message = messagePara;

        String returnValue;
        MessageStatuses msgStatus = null;

        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            
//            org.apache.http.ssl.SSLContextBuilder context_b = SSLContextBuilder.create();
//            context_b.loadTrustMaterial(new org.apache.http.conn.ssl.TrustSelfSignedStrategy());
//            SSLContext ssl_context = context_b.build();
//            org.apache.http.conn.ssl.SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ssl_context,
//                    new org.apache.http.conn.ssl.DefaultHostnameVerifier());
//
//            HttpClientBuilder builder = HttpClients.custom()
//                    .setSSLSocketFactory(sslSocketFactory);
//            HttpClient client = builder.build();
//            SSLContext sslcontext = SSLContexts.createSystemDefault();
//            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
//                    sslcontext, new String[]{"TLSv1", "SSLv3"}, null,
//                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//
//            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                    .register("http", PlainConnectionSocketFactory.INSTANCE)
//                    .register("https", sslConnectionSocketFactory)
//                    .build();
//            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//            CloseableHttpClient client = HttpClients.custom()
//                    .setConnectionManager(cm)
//                    .build();

//            client.getParams().setParameter("http.socket.timeout", new Integer(0));
//            client.getParams().setParameter("http.connection.stalecheck", new Boolean(true));
//            HttpClient client = HttpClientBuilder.create().build();
// ORIGINAL ********************************************************************
//            HttpPost post = new HttpPost(Api.getApiURL());
////            HttpGet post = new HttpGet(Api.getApiURL());
////            post.setHeader("User-Agent", USER_AGENT);
//            List<NameValuePair> urlParameters = new ArrayList<>();
//            urlParameters.add(new BasicNameValuePair("destination", phnNo));
//            urlParameters.add(new BasicNameValuePair("q", Api.getApiKey()));
//            urlParameters.add(new BasicNameValuePair("message", message));
//
//            post.setEntity(new UrlEncodedFormEntity(urlParameters));
//            HttpResponse response = client.execute(post);
// END ORIGINAL ****************************************************************            
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost(Api.getApiURL())
                    //        .setPath("/search")
                    .setParameter("destination", phnNo)
                    .setParameter("q", Api.getApiKey())
                    .setParameter("message", message)
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

//            MyMessagesUtility.showInformation("resultJson>>" + resultJson);
//            try {
//                if (resultJson == "0") {
//                    MyMessagesUtility.showInformation("Hurrayyyyyy...");
//                }
//            } catch (Exception e) {
//                MyMessagesUtility.showInformation("Errorrrrr...");
//            }
//            try {
//                if (response.getStatusLine().getStatusCode() == 200) {
//                    MyMessagesUtility.showInformation("Hurrayyyyyy...");
//                }
//            } catch (Exception e) {
//                MyMessagesUtility.showInformation("Errorrrrr...");
//            }
            if (response.getStatusLine().getStatusCode() == 200) {
                msgStatus = MessageStatuses.SENT;
//                MyMessagesUtility.showInformation(" resultJson.equalsIgnoreCase(\"0\") msgStatus>>" + msgStatus);
//                returnValue = "SUCCESS";
            } else {
                msgStatus = MessageStatuses.UNSENT;
//                MyMessagesUtility.showInformation(" else msgStatus>>" + msgStatus);
//                returnValue = "NOT SUCCESS";
            }

            return msgStatus;

        } catch (IOException ex) {
            MyMessagesUtility.showError(ex.getMessage());
            Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
//            Logger.getLogger(SendSMSThroughGatewayAPI.class.getName()).log(Level.SEVERE, null, ex);
            msgStatus = MessageStatuses.FAILED;
            return msgStatus;
        } catch (URISyntaxException ex) {
            MyMessagesUtility.showError(ex.getMessage());
            Logger.getLogger(SendSMSThroughGatewayAPI.class.getName()).log(Level.SEVERE, null, ex);
            return msgStatus;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SendSMSThroughGatewayAPI sendSMSThroughGatewayAPI = new SendSMSThroughGatewayAPI();
        for (int i = 0; i < 2; i++) {
            sendSMSThroughGatewayAPI.send("94765405302", "Hellooo...");
        }
    }

}
