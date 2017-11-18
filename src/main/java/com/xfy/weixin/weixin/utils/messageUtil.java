package com.xfy.weixin.weixin.utils;
import com.thoughtworks.xstream.XStream;
import com.xfy.weixin.weixin.pojo.NewsMessage;
import com.xfy.weixin.weixin.pojo.TestMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.xfy.weixin.weixin.pojo.news;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Stefan
 * Create Date 2017-11-18/11:04
 */
public class messageUtil {
    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_NEWS="news";
    public static final String MESSAGE_Image="image";
    public static final String MESSAGE_Voice="voice";
    public static final String MESSAGE_Video="video";
    public static final String MESSAGE_Link="link";
    public static final String MESSAGE_Location="location";
    public static final String MESSAGE_EVENT="event";
    public static final String MESSAGE_SUBSCRIBE="subscribe";
    public static final String MESSAGE_Unsubscribe="unsubscribe";
    public static final String MESSAGE_CLICK="CLICK";
    public static final String MESSAGE_VIEW="VIEW";

    /*
    * 将xml转化为map集合
    * */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map = new HashMap<>();
        SAXReader reader=new SAXReader();
        InputStream ins=request.getInputStream();
        Document doc = reader.read(ins);
        Element root=doc.getRootElement();
        List<Element> list=root.elements();
        for (Element e:list){
            map.put(e.getName(),e.getText());
        }
        ins.close();
        return map;
    }
    /*
    * 将文本对象转为xml
    * */
    public static String textMessageToXml(TestMessage tm){
        XStream xStream=new XStream();
        xStream.alias("xml",tm.getClass());
        return xStream.toXML(tm);
    }
    /*
    * 图文消息转换xml
    *
    * */
    public static String newsMessageToXml(NewsMessage nm){
        XStream xStream=new XStream();
        xStream.alias("xml",nm.getClass());
        xStream.alias("item",new news().getClass());
        return xStream.toXML(nm);
    }
/*
* 图文消息的组装
*
* */
    public  static String initNewsMessage(String toUserName,String fromUserName){
        String message =null;
        List<news> newsList =new ArrayList<>();
        NewsMessage newsMessage = new NewsMessage();

        news n=new news();
        n.setTitle("慕课网介绍");
        n.setDescription("新建素材时，当公众号具备留言功能的权限时，可以指定 media_id 在群发时打开留言功能，并指定该 media_id 的评论范围（所有人都可以留言，或仅公众号粉丝可以留言）。");
        n.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511597544&di=bbbe70f6356bbe257ba15acba3b09680&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.mukewang.com%2F55f93f980001f52125001408-590-330.jpg");
        n.setUrl("www.baidu.com");

        newsList.add(n);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime()+"");
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        message = newsMessageToXml(newsMessage);
        return message;
    }

    public static String initText(String toUserName,String fromUserName,String content){
        TestMessage tm = new TestMessage();
        tm.setFromUserName(toUserName);
        tm.setToUserName(fromUserName);
        tm.setMsgType(messageUtil.MESSAGE_TEXT);
        tm.setCreateTime(new Date().getTime()+"");
        tm.setContent("您发送的信息是：" + content);
        return textMessageToXml(tm);
    }
   /*
   *
   * 主菜单
   * */
    public static String menuText(){
        StringBuffer sb=new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作");
        sb.append("1、课程介绍\n");
        sb.append("2、慕课网介绍\n");
        sb.append("回复？跳出此菜单");
        return sb.toString();
    }

    public static String firstText(){
        StringBuffer sb=new StringBuffer();
        sb.append("本套课程介绍微信初步开发");

        return sb.toString();
    }
    public static String secondText(){
        StringBuffer sb=new StringBuffer();
        sb.append("课程介绍");
        return sb.toString();
    }
}
