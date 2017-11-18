package com.xfy.weixin.weixin.wxController;
import com.xfy.weixin.weixin.utils.checkUtil;
import com.xfy.weixin.weixin.utils.messageUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Stefan
 * Create Date 2017-11-18/10:44
 */
@RestController
@RequestMapping("/weixin")
public class wxConn {
    @GetMapping("/wxconn")
    public String wxcheck(String signature, String timestamp, String nonce, String echostr) {
        if (checkUtil.checksignature(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return null;
        }
    }
    @PostMapping("/wxconn")
    public String dealMessage(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Map<String, String> map = messageUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String message = null;
            if (messageUtil.MESSAGE_TEXT.equals(msgType)) {
                if ("1".equals(content)) {
                    message = messageUtil.initText(toUserName, fromUserName, messageUtil.firstText());
                } else if ("2".equals(content)) {
                    message = messageUtil.initText(toUserName, fromUserName, messageUtil.secondText());
                }  else if ("3".equals(content)) {
                    message = messageUtil.initNewsMessage(toUserName, fromUserName);
                }
                else if ("?".equals(content) || "？".equals(content)) {
                    message = messageUtil.initText(toUserName, fromUserName, messageUtil.menuText());
                }
            }
            else if (messageUtil.MESSAGE_EVENT.equals(msgType)){
                    String eventType=map.get("Event");
                    if (messageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                        message=messageUtil.initText(toUserName,fromUserName,messageUtil.menuText());
                    }
                }
            System.out.println(message);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



