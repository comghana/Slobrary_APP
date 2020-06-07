package com.example.autobrary.externalConnecter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;


public class URLConnector extends Thread{

    private final String HOST = "https://www.slobrary.com/app/";

    public HttpResponse getResult() {
        return result;
    }

    private HttpResponse result;
    private String URL;
    private HashMap<String, String> param = new HashMap<String, String>();
    private String longData;
    public URLConnector(String url, HashMap param){
        URL = HOST + url;
        this.param = param;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = HOST + URL;
    }

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String> param) {
        this.param = param;
    }


    public void run() {
        final HttpClient httpclient = new DefaultHttpClient();
        HttpResponse reps = null;
        final HttpPost httppost = new HttpPost(URL);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(param.size());

            // 파라미터 추가
            Set key = param.keySet();
            for (Iterator it = key.iterator(); it.hasNext(); ) {
                String keyName = (String) it.next();
                String valueName = (String) param.get(keyName);
                nameValuePairs.add(new BasicNameValuePair(keyName, valueName));
            }

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request

            reps = httpclient.execute(httppost);
            longData = EntityUtils.toString(reps.getEntity());
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        this.result = reps;
    }
    public String getData(){
        return longData;
    }
}