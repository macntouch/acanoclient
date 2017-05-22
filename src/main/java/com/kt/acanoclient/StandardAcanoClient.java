package com.kt.acanoclient;

import com.kt.acanoclient.anno.AcanoType;
import com.kt.acanoclient.exception.AcanoApiException;
import com.kt.acanoclient.obj.AcanoObject;
import com.kt.acanoclient.obj.CoSpace;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/5/19.
 */
public class StandardAcanoClient implements AcanoClient {

    private HttpClient client;

    private String host;
    private int port;
    private String user;
    private String password;

    public StandardAcanoClient(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{truseAllManager}, null);
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(host, port),
                    new UsernamePasswordCredentials(user, password));

            client = HttpClientBuilder.create().
                    setSSLSocketFactory(new SSLSocketFactory(sslcontext, new AllowAllHostnameVerifier())).
                    setDefaultCredentialsProvider(credsProvider).
                    build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

    }

    private static TrustManager truseAllManager = new X509TrustManager() {

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };



    private static final String COSPACES = "<?xml version=\"1.0\"?><coSpaces total=\"16\"><coSpace id=\"31e74029-7983-48a7-8f08-347b5df2d3e3\"><name></name><autoGenerated>false</autoGenerated></coSpace><coSpace id=\"790c71d4-1b1f-4948-aee6-b73ea5fc73bb\"><name></name><autoGenerated>false</autoGenerated></coSpace><coSpace id=\"fc4d0a5c-e92e-45db-8353-6f0840b359e3\"><name>8001</name><autoGenerated>false</autoGenerated><uri>8001</uri><callId>8001</callId></coSpace><coSpace id=\"0cd7ef6d-1161-49b4-9607-52213858175a\"><name>By Program--/ABCä¸\u00ADæ\u0096\u0087</name><autoGenerated>false</autoGenerated><uri>5002</uri><callId>5002</callId></coSpace><coSpace id=\"3e341987-35da-4c74-9493-bfd49204190b\"><name>From JMeter</name><autoGenerated>false</autoGenerated><uri>1013</uri><callId>1027</callId></coSpace><coSpace id=\"419e8c8a-7c0a-4160-90d8-91c1efd3d1cf\"><name>From JMeter</name><autoGenerated>false</autoGenerated><uri>1014</uri><callId>1014</callId></coSpace><coSpace id=\"57b24d29-3535-473b-8486-3a50e7e09633\"><name>Space1</name><autoGenerated>false</autoGenerated><uri>vega2</uri><callId>861363145</callId></coSpace><coSpace id=\"f593ecc9-2e0e-48e2-b219-2ee28c27878a\"><name>Vega æ\u0097 å¯\u0086ç \u0081</name><autoGenerated>false</autoGenerated><uri>10000</uri><callId>344531677</callId></coSpace><coSpace id=\"be083dc5-5f04-4a92-810b-32c4f7813ccd\"><name>new space for testing</name><autoGenerated>false</autoGenerated><uri>new.space.for.testing</uri><callId>158381536</callId></coSpace><coSpace id=\"fa1d7ec6-6cc4-41a7-89e5-1a5ca9df865f\"><name>test1.space</name><autoGenerated>true</autoGenerated></coSpace><coSpace id=\"0fe90e92-c9a9-4a08-bd29-206ed59981a0\"><name>test2.space</name><autoGenerated>true</autoGenerated></coSpace><coSpace id=\"df9eae99-8e4e-4153-a428-e8f34a868dc0\"><name>test3.space</name><autoGenerated>true</autoGenerated></coSpace><coSpace id=\"87efa974-a928-4cd0-97de-b36ee73e99ae\"><name>test4.space</name><autoGenerated>true</autoGenerated></coSpace><coSpace id=\"146ca998-63ed-4de5-8e2c-3e9f582816b6\"><name>test5.space</name><autoGenerated>true</autoGenerated></coSpace><coSpace id=\"edb69c73-c1b9-4233-8242-794d5e010763\"><name>ä½ å¥½</name><autoGenerated>false</autoGenerated><uri>vega</uri><callId>245180399</callId></coSpace><coSpace id=\"5d6ec122-55f0-4c58-9f64-b187444035e5\"><name>æ\u0097 å¯\u0086ç \u0081ä¼\u009Aè®®</name><autoGenerated>false</autoGenerated><callId>973481420</callId></coSpace></coSpaces>";

    public static void main(String args[]) throws AcanoApiException, DocumentException, IllegalAccessException, InstantiationException {
//        String a = "/api/v1/coSpaces/cc3690ea-e17d-40e1-92e2-8fe98dc61ff6";
//        System.out.println(a.lastIndexOf("/"));


        StandardAcanoClient client = new StandardAcanoClient("10.10.10.95", 445, "kty", "kty");

        List<CoSpace> coSpaces = client.listAcanoObjects(CoSpace.class);
//        client.parseXmlAsList(CoSpace.class, COSPACES, "coSpace");
//        System.out.println(client.buildEndPoint());
//
//        CoSpace coSpace = new CoSpace();
//        coSpace.setId("0cd7ef6d-1161-49b4-9607-52213858175a");
//        coSpace.setName("By Program--/ABC中文");
//        coSpace.setCallId("5002");
//        coSpace.setUri("5002");
//        coSpace.setDefaultLayout(ScreenLayout.ALL_EQUAL.getValue());


//        String newObjectId = client.createAcanoObject(coSpace);
//        client.updateAcanoObject(coSpace);
//        client.deleteAcanoObject(coSpace);
        return;
    }

    protected String buildEndPoint() {
        return "https://" + host + ":" + port + "/api/v1";
    }

    @Override
    public void createCoSpace(CoSpace coSpace) {

    }


    public String createAcanoObject(AcanoObject object) throws AcanoApiException {
        HttpPost post = new HttpPost(buildEndPoint() + object.getNewObjectPath());
        post.setConfig(buildDefaultRequestConfig());
        List<BasicNameValuePair> params = object.buildPostParams();
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            post.setEntity(entity);
            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new AcanoApiException(EntityUtils.toString(httpResponse.getEntity()));
            }
            return extractIdFromResponse(httpResponse);
        } catch (UnsupportedEncodingException ignore) {
        } catch (IOException e) {
            e.printStackTrace();
            throw new AcanoApiException(e);
        }
        return null;
    }


    public AcanoObject getAcanoObject(AcanoObject object) throws AcanoApiException {
        HttpGet get = new HttpGet(buildEndPoint() + object.getNewObjectPath() + "/" + object.getId());
        get.setConfig(buildDefaultRequestConfig());
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new AcanoApiException(EntityUtils.toString(response.getEntity()));
            }
            return object;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AcanoApiException(e);
        }
    }


    public void updateAcanoObject(AcanoObject object) throws AcanoApiException {
        HttpPut put = new HttpPut(buildEndPoint() + object.getNewObjectPath() + "/" + object.getId());
        put.setConfig(buildDefaultRequestConfig());
        String putBody = object.buildPutBody();
        put.setEntity(new StringEntity(putBody, ContentType.APPLICATION_FORM_URLENCODED));
        try {
            HttpResponse response = client.execute(put);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new AcanoApiException(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AcanoApiException(e);
        }
    }


    public void deleteAcanoObject(AcanoObject object) throws AcanoApiException {
        HttpDelete delete = new HttpDelete(buildEndPoint() + object.getNewObjectPath() + "/" + object.getId());
        delete.setConfig(buildDefaultRequestConfig());
        try {
            HttpResponse httpResponse = client.execute(delete);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new AcanoApiException(EntityUtils.toString(httpResponse.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AcanoApiException(e);
        }
    }



    public <T extends AcanoObject> List<T> listAcanoObjects(Class<T> clazz) throws AcanoApiException {
        try {
            List<T> result = new ArrayList<>();
            T ao = clazz.newInstance();
            HttpGet get = new HttpGet(buildEndPoint() + ao.getNewObjectPath());
            get.setConfig(buildDefaultRequestConfig());

            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new AcanoApiException(EntityUtils.toString(response.getEntity()));
            }
            AcanoType at = clazz.getAnnotation(AcanoType.class);
            if (at == null) {
                return result;
            } else {
                String xml = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                result = parseXmlAsList(clazz, xml, at.value());
                return result;
            }
        } catch (InstantiationException | IllegalAccessException | IOException | DocumentException e) {
            e.printStackTrace();
            throw new AcanoApiException(e);
        }
    }





    private <T extends AcanoObject> List<T> parseXmlAsList(Class<T> clazz, String xml, String acanoObjectType)
            throws DocumentException, InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<>();
        Document document = DocumentHelper.parseText(xml);
        List<Node> nodes = document.selectNodes("/" + acanoObjectType + "s/" + acanoObjectType);
        for (Node node : nodes) {
            result.add(parseNode(clazz, node));
        }
        return result;
    }

    private <T extends AcanoObject> T parseNode(Class<T> clazz, Node node) throws IllegalAccessException, InstantiationException {
        T ao = clazz.newInstance();
        ao.parseBody(node);
        return ao;
    }




    private RequestConfig buildDefaultRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(10000)   //设置连接超时时间
                .setConnectionRequestTimeout(10000) // 设置请求超时时间
                .setSocketTimeout(10000)
                .setRedirectsEnabled(false)//不允许自动重定向
                .build();
    }


    private String extractIdFromResponse(HttpResponse httpResponse) {
        Header locationHeader = httpResponse.getFirstHeader("Location");
        if (locationHeader == null) {
            return null;
        } else {
            String location = locationHeader.getValue();
            String id = StringUtils.substring(location, StringUtils.lastIndexOf(location, "/" + 1));
            return id;
        }

    }
}
