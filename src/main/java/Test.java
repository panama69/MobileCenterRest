import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.HTTP;
import org.json.JSONObject;


// reference source
// https://www.javacodegeeks.com/2012/09/simple-rest-client-in-java.html

public class Test {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        HttpClient client = HttpClients.createDefault();

        // Log into MC
        System.out.println ("\r\nLog Login\r\n####################################################################");
        HttpPost post = new HttpPost("http://demo.mobilecenter.io:8080/rest/client/login");
        /*List nameValuePairs = new ArrayList(2);
        nameValuePairs.add(new BasicNameValuePair("name", "david.flynn@hpe.com")); //you can as many name value pair as you want in the list.
        nameValuePairs.add(new BasicNameValuePair("password", "Welc0me")); //you can as many name value pair as you want in the list.
        */
        JSONObject json = new JSONObject();
        json.put("name", "david.flynn@hpe.com");
        json.put("password", "Welc0me");
        StringEntity se = new StringEntity( json.toString());
        post.setEntity(se);
        post.addHeader("Content-Type","application/json");

        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
        HeaderIterator headerIterator =response.headerIterator();
        while( headerIterator.hasNext()){
            System.out.println(headerIterator.next().toString());
        }


        // Get the log
        System.out.println ("\r\nGet log\r\n####################################################################");
        HttpGet request = new HttpGet("http://demo.mobilecenter.io:8080/rest/v2/device/ce12160cf13ad41d05/logs");
        request.addHeader("Accept","text/plain");
        response = client.execute(request);

        rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }

        // Turn logs on
        System.out.println ("\r\nTurn Log On\r\n####################################################################");
        HttpPut myPut = new HttpPut("http://demo.mobilecenter.io:8080/rest/v2/device/ce12160cf13ad41d05/logs/collection");
        myPut.addHeader("Content-Type","application/json");
        json = new JSONObject();
        json.put("enabled", "false");
        se = new StringEntity( json.toString());
        myPut.setEntity(se);
        response = client.execute(myPut);

        rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }

        // Get the log
        System.out.println ("\r\nGet Log\r\n####################################################################");
        request = new HttpGet("http://demo.mobilecenter.io:8080/rest/v2/device/ce12160cf13ad41d05/logs");
        request.addHeader("Accept","text/plain");
        response = client.execute(request);

        rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }

        // Log out
        System.out.println ("\r\nLog out\r\n####################################################################");
        post = new HttpPost("http://demo.mobilecenter.io:8080/rest/client/logout");
        response = client.execute(post);

        rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
        headerIterator =response.headerIterator();
        while( headerIterator.hasNext()){
            System.out.println(headerIterator.next().toString());
        }
    }
}
