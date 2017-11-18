package com.xfy.weixin.weixin.pojo;

/**
 * Created by Stefan
 * Create Date 2017-11-18/11:16
 */
public class TestMessage extends BaseMessage {
    private String Content;
    private String MsgId;
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
