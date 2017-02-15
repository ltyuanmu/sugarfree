package com.sugarfree.dao.model;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2017/1/25
 */
public class ArticlePushBean {

    private int fkSubscriberId;

    private int fkWxUserId;

    private int fkArticleId;

    private int classTime;

    public int getFkSubscriberId() {
        return fkSubscriberId;
    }

    public void setFkSubscriberId(int fkSubscriberId) {
        this.fkSubscriberId = fkSubscriberId;
    }

    public int getFkWxUserId() {
        return fkWxUserId;
    }

    public void setFkWxUserId(int fkWxUserId) {
        this.fkWxUserId = fkWxUserId;
    }

    public int getFkArticleId() {
        return fkArticleId;
    }

    public void setFkArticleId(int fkArticleId) {
        this.fkArticleId = fkArticleId;
    }

    public int getClassTime() {
        return classTime;
    }

    public void setClassTime(int classTime) {
        this.classTime = classTime;
    }
}
