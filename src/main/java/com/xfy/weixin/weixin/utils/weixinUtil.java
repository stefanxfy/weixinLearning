package com.xfy.weixin.weixin.utils;

import com.xfy.weixin.weixin.menu.Button;
import com.xfy.weixin.weixin.menu.ClickButton;
import com.xfy.weixin.weixin.menu.Menu;
import com.xfy.weixin.weixin.menu.ViewButton;
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
    private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static  final String QUERY_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    private static final String DELETE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
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
    public static JSONObject doPostStr(String url,String outStr)  {
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(url);
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
/*
* 菜单组装
* */
    public static Menu initMenu(){
        Menu menu=new Menu();
        ClickButton clickButton1=new ClickButton();
        clickButton1.setName("主菜单一");
        clickButton1.setType("click");
        clickButton1.setKey("11");

        ViewButton viewButton=new ViewButton();
        viewButton.setName("主菜单二");
        viewButton.setType("view");
        viewButton.setUrl("http://www.baidu.com");

        Button button=new Button();
        button.setName("主菜单三");
        ClickButton  clickButton2=new ClickButton();
        clickButton2.setName("扫码");
        clickButton2.setType("scancode_push");
        clickButton2.setKey("31");

        ClickButton  clickButton3=new ClickButton();
        clickButton3.setName("地理位置");
        clickButton3.setType("location_select");
        clickButton3.setKey("32");

        button.setSub_button(new Button[]{clickButton2,clickButton3});

        menu.setButton(new Button[]{clickButton1,viewButton,button});
        return menu;
    }

    public static int createMenu(String token,String menu){
        int result=0;
        String url=CREATE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject=doPostStr(url,menu);
        if(jsonObject!=null){
            result=jsonObject.getInt("errcode");
        }
        return result;
    }
/*
* 查询菜单
* */
    public static JSONObject queryMenu(String token){
        String url=QUERY_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject=doGetStr(url);
        return jsonObject;
    }
/*
* 删除菜单
* */
    public static int deleteMenu(String token){
        String url=DELETE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject=doGetStr(url);
        int result = 0;
        if (jsonObject!=null){
            result=jsonObject.getInt("errcode");
        }
        return result ;
    }






}
