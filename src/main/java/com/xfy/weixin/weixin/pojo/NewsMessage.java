package com.xfy.weixin.weixin.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan
 * Create Date 2017-11-18/15:40
 */
public class NewsMessage extends BaseMessage {
    private int ArticleCount;
    private List<news> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<news> getArticles() {
        return Articles;
    }

    public void setArticles(List<news> articles) {
        Articles = articles;
    }
}
