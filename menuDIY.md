# 自定义菜单的创建、查询、删除

#### 新建接口：
> http请求方式：POST（请使用https协议） https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
#### 自定义菜单能够帮助公众号丰富界面，让用户更好更快地理解公众号的功能。开启自定义菜单后，公众号界面如图所示：
![image](http://mmbiz.qpic.cn/mmbiz_jpg/PiajxSqBRaEIQxibpLbyuSK4TssiajqknJS6dACV5IoTxppdoyfGEYScCFMqV3dgFzf2tGqy2IiciaysTlaZt6zchibA/0?wx_fmt=jpeg)

### 请注意：
> 1.  自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。
> 1.  一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。
> 1.  创建自定义菜单后，菜单的刷新策略是，在用户进入公众号会话页或公众号profile页时，如果发现上一次拉取菜单的请求在5分钟以前，就会拉取一下菜单，如果菜单有更新，就会刷新客户端的菜单。测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果。

### 自定义菜单接口可实现多种类型按钮，如下：
> 1. click：点击推事件用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
> 1. view：跳转URL用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
> 1. scancode_push：扫码推事件用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
> 1. scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
> 1. pic_sysphoto：弹出系统拍照发图用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
> 1. pic_photo_or_album：弹出拍照或者相册发图用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
> 1. pic_weixin：弹出微信相册发图器用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
> 1. location_select：弹出地理位置选择器用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
> 1. media_id：下发消息（除文本消息）用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
> 1. view_limited：跳转图文消息URL用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。

#### 请注意，3到8的所有事件，仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，开发者也不能正常接收到事件推送。9和10，是专门给第三方平台旗下未微信认证（具体而言，是资质认证未通过）的订阅号准备的事件类型，它们是没有事件推送的，能力相对受限，其他类型的公众号不必使用。

#### click和view的请求示例

```
{
     "button":[
     {	
          "type":"click",
          "name":"今日歌曲",
          "key":"V1001_TODAY_MUSIC"
      },
      {
           "name":"菜单",
           "sub_button":[
           {	
               "type":"view",
               "name":"搜索",
               "url":"http://www.soso.com/"
            },
            {
                 "type":"miniprogram",
                 "name":"wxa",
                 "url":"http://mp.weixin.qq.com",
                 "appid":"wx286b93c14bbf93aa",
                 "pagepath":"pages/lunar/index"
             },
            {
               "type":"click",
               "name":"赞一下我们",
               "key":"V1001_GOOD"
            }]
       }]
 }
```
#### 其他新增按钮类型的请求示例


```
{
    "button": [
        {
            "name": "扫码", 
            "sub_button": [
                {
                    "type": "scancode_waitmsg", 
                    "name": "扫码带提示", 
                    "key": "rselfmenu_0_0", 
                    "sub_button": [ ]
                }, 
                {
                    "type": "scancode_push", 
                    "name": "扫码推事件", 
                    "key": "rselfmenu_0_1", 
                    "sub_button": [ ]
                }
            ]
        }, 
        {
            "name": "发图", 
            "sub_button": [
                {
                    "type": "pic_sysphoto", 
                    "name": "系统拍照发图", 
                    "key": "rselfmenu_1_0", 
                   "sub_button": [ ]
                 }, 
                {
                    "type": "pic_photo_or_album", 
                    "name": "拍照或者相册发图", 
                    "key": "rselfmenu_1_1", 
                    "sub_button": [ ]
                }, 
                {
                    "type": "pic_weixin", 
                    "name": "微信相册发图", 
                    "key": "rselfmenu_1_2", 
                    "sub_button": [ ]
                }
            ]
        }, 
        {
            "name": "发送位置", 
            "type": "location_select", 
            "key": "rselfmenu_2_0"
        },
        {
           "type": "media_id", 
           "name": "图片", 
           "media_id": "MEDIA_ID1"
        }, 
        {
           "type": "view_limited", 
           "name": "图文消息", 
           "media_id": "MEDIA_ID2"
        }
    ]
}
```
### 参数说明：
![image](https://github.com/StefanPython/weixinLearning/blob/master/img/r.png)

### 返回结果

#### 正确时的返回JSON数据包如下：
> 
> {"errcode":0,"errmsg":"ok"}
#### 错误时的返回JSON数据包如下（示例为无效菜单名长度）：

> {"errcode":40018,"errmsg":"invalid button name size"}

### 代码示意：

第一步：新建菜单封装类


```
package com.xfy.weixin.weixin.menu;

/**
 * Created by Stefan
 * Create Date 2017-11-21/19:48
 */
public class Menu {
    private Button[] button;
}

```


```
package com.xfy.weixin.weixin.menu;

/**
 * Created by Stefan
 * Create Date 2017-11-21/19:42
 */
public class Button {
    private String type;
    private String name;
    private Button[] sub_button;
}

```


```
package com.xfy.weixin.weixin.menu;

/**
 * Created by Stefan
 * Create Date 2017-11-21/19:45
 */
public class ClickButton extends Button {
    private String key;
}

```


```
package com.xfy.weixin.weixin.menu;

/**
 * Created by Stefan
 * Create Date 2017-11-21/19:46
 */
public class ViewButton extends Button  {
    private String url;
}
```
### 第二步：组装与创建
#### 先在weixinUtil 里添加一个属性，我们用到的接口

```
private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
```
#### 接下来写方法：

```
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
```


```
/*
* 创建菜单
* */
    public static int createMenu(String token,String menu){
        int result=0;
        String url=CREATE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject=doPostStr(url,menu);
        if(jsonObject!=null){
            result=jsonObject.getInt("errcode");
        }
        return result;
    }
```
#### 这里我们对菜单的自定义创建就基本可以了，因为，对菜单的查询与删除很简单，就一个接口，所以就一起写了，但是这仅仅是入门。

### 查询接口：
> http请求方式：GET
> https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN


#### 返回json字符串
```
{"menu":
	{"button":[{"type":"click","name":"主菜单一","key":"11","sub_button":[]},
	{"type":"view","name":"主菜单二","url":"http://www.baidu.com","sub_button":[]},
	{"name":"主菜单三","sub_button":
		[{"type":"scancode_push","name":"扫码","key":"31","sub_button":[]},
		{"type":"location_select","name":"地理位置","key":"32","sub_button":[]}]}]}}
```

### 代码示意：
#### weixinUtil.java

```
private static  final String QUERY_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
```

```
/*
* 查询菜单
* */
    public static JSONObject queryMenu(String token){
        String url=QUERY_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject=doGetStr(url);
        return jsonObject;
    }
```

### 自定义菜单删除接口
> http请求方式：GET
> https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN

#### 使用接口创建自定义菜单后，开发者还可使用接口删除当前使用的自定义菜单。另请注意，在个性化菜单时，调用此接口会删除默认菜单及全部个性化菜单。

#### 返回说明：
> 对应创建接口，正确的Json返回结果:
> {"errcode":0,"errmsg":"ok"}

### 代码示意：
#### weixinUtil.java
```
 private static final String DELETE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
```


```
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

```

### 测试代码：

```
package com.xfy.weixin.weixin;

import com.xfy.weixin.weixin.pojo.AccessToken;
import com.xfy.weixin.weixin.utils.weixinUtil;
import net.sf.json.JSONObject;

/**
 * Created by Stefan
 * Create Date 2017-11-18/20:10
 */
public class weiixinTest {
    public static void main (String[] args){
        AccessToken accessToken= weixinUtil.getAccessToken();
        System.out.println("Access_token:"+accessToken.getAccess_token());
        System.out.println("Expires_in:"+accessToken.getExpires_in());
        //创建菜单
        String menu= JSONObject.fromObject(weixinUtil.initMenu()).toString();
        int result = weixinUtil.createMenu(accessToken.getAccess_token(),menu);
        System.out.println("创建成功"+result);

        //查询菜单
        //JSONObject jsonObject=weixinUtil.queryMenu(accessToken.getAccess_token());
        //System.out.println(jsonObject);

        //删除菜单
        /*int result = weixinUtil.deleteMenu(accessToken.getAccess_token());
        if(result==0){
            System.out.println("菜单删除成功");
        }else {
            System.out.println(result);
        }*/





    }
}

```

###### 未完待续...













