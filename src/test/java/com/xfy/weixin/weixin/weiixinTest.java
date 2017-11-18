package com.xfy.weixin.weixin;

import com.xfy.weixin.weixin.pojo.AccessToken;
import com.xfy.weixin.weixin.utils.weixinUtil;

/**
 * Created by Stefan
 * Create Date 2017-11-18/20:10
 */
public class weiixinTest {
    public static void main (String[] args){
        AccessToken accessToken= weixinUtil.getAccessToken();
        System.out.println("Access_token:"+accessToken.getAccess_token());
        System.out.println("Expires_in:"+accessToken.getExpires_in());

    }
}
