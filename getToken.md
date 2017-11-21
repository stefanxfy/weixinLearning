# 获取access_token
#### access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。

---
### 接口调用请求说明
> https请求方式: GET
> https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
#### 从url中我们看到需要两个参数==APPID==，==APPSECRET==，这两个参数可在“微信公众平台-开发-基本配置”页中获得（需要已经成为开发者，且帐号没有异常状态）。调用接口时，请登录“微信公众平台-开发-基本配置”提前将服务器IP地址添加到IP白名单中，点击查看设置方法，否则将无法调用成功。
### 参数说明


参数  | 是否必须 |说明
---|---|---
grant_type | 是|获取access_token填写client_credential
appid | 是|第三方用户唯一凭证
secret|是|第三方用户唯一凭证密钥，即appsecret
### 返回说明
#### 正常情况下，微信会返回下述JSON数据包给公众号：
> {"access_token":"ACCESS_TOKEN","expires_in":7200}

### 参数说明

参数 | 说明
---|---
access_token | 获取到的凭证
expires_in |凭证有效时间，单位：秒

#### 错误时微信会返回错误码等信息，JSON数据包示例如下（该示例为AppID无效错误）:

> {"errcode":40013,"errmsg":"invalid appid"}
### 返回码说明

返回码 | 说明
---|---
-1 | 系统繁忙，此时请开发者稍候再试
0 | 请求成功
40001|AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性
40002|请确保grant_type字段值为client_credential
40164|	调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置

### 代码示意：
#### 第一步：先建一个AccessToken类


```
public class AccessToken {
    private String access_token;
    private int expires_in;
    省略getter、setter
}
```
#### 第二步：建一个weixinUtil


```
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
```
#### 第三步：测试获得token

```
public class weiixinTest {
    public static void main (String[] args){
        AccessToken accessToken= weixinUtil.getAccessToken();
        System.out.println("Access_token:"+accessToken.getAccess_token());
        System.out.println("Expires_in:"+accessToken.getExpires_in());
    }
}
```

##  token的使用和保存建议：

#### 1、建议公众号开发者使用中控服务器统一获取和刷新Access_token，其他业务逻辑服务器所使用的access_token均来自于该中控服务器，不应该各自去刷新，否则容易造成冲突，导致access_token覆盖而影响业务；
#### 2、目前Access_token的有效期通过返回的expire_in来传达，目前是7200秒之内的值。中控服务器需要根据这个有效时间提前去刷新新access_token。在刷新过程中，中控服务器可对外继续输出的老access_token，此时公众平台后台会保证在5分钟内，新老access_token都可用，这保证了第三方业务的平滑过渡；
#### 3、Access_token的有效时间可能会在未来有调整，所以中控服务器不仅需要内部定时主动刷新，还需要提供被动刷新access_token的接口，这样便于业务服务器在API调用获知access_token已超时的情况下，可以触发access_token的刷新流程。
















