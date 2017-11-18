package com.xfy.weixin.weixin.utils;

import com.xfy.weixin.weixin.pojo.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * Created by Stefan
 * Create Date 2017-11-18/16:38
 */
public class weixinUtil {
    private static final String APPID="wxcbb7dc0e98cbae1e";
    private static final String APPSECRET="14e79eb419399a6cbfe3aaed36b87eb5";
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
/*
* GET请求
*
* */
    public static JSONObject doGetStr(String url)  {
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        net.sf.json.JSONObject jsonObject=null;
        HttpResponse response= null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity httpEntity=response.getEntity();
            if (httpEntity!=null){
                String result= EntityUtils.toString(httpEntity,"UTF-8");
                jsonObject= JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
/*
* post请求
* */
    public static JSONObject doPostStr(String outStr)  {
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost();
        JSONObject jsonObject=null;
        HttpResponse response= null;
        try {
            httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
            response = httpClient.execute(httpPost);
            HttpEntity httpEntity=response.getEntity();
            if (httpEntity!=null){
                String result= EntityUtils.toString(httpEntity,"UTF-8");
                jsonObject= JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
/*
* 获取accessToken
* */
    public static AccessToken getAccessToken(){
        AccessToken accessToken = new AccessToken();
        String url=ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject=doGetStr(url);
        if(jsonObject!=null){
            accessToken.setAccess_token(jsonObject.getString("access_token"));
            accessToken.setExpires_in(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }




}
