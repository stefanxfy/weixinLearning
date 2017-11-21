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
