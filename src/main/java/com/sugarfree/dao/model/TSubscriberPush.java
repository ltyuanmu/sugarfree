package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_subscriber_push")
public class TSubscriberPush {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 微信用户id
     */
    @Column(name = "fk_wx_user_id")
    private Integer fkWxUserId;

    /**
     * 文章id
     */
    @Column(name = "fk_article_id")
    private Integer fkArticleId;

    @Column(name = "article_title")
    private String articleTitle;

    /**
     * 状态,0:推送失败,1:推送成功
     */
    private String staus;

    @Column(name = "push_time")
    private Date pushTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取微信用户id
     *
     * @return fk_wx_user_id - 微信用户id
     */
    public Integer getFkWxUserId() {
        return fkWxUserId;
    }

    /**
     * 设置微信用户id
     *
     * @param fkWxUserId 微信用户id
     */
    public void setFkWxUserId(Integer fkWxUserId) {
        this.fkWxUserId = fkWxUserId;
    }

    /**
     * 获取文章id
     *
     * @return fk_article_id - 文章id
     */
    public Integer getFkArticleId() {
        return fkArticleId;
    }

    /**
     * 设置文章id
     *
     * @param fkArticleId 文章id
     */
    public void setFkArticleId(Integer fkArticleId) {
        this.fkArticleId = fkArticleId;
    }

    /**
     * @return article_title
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * @param articleTitle
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 获取状态,0:推送失败,1:推送成功
     *
     * @return staus - 状态,0:推送失败,1:推送成功
     */
    public String getStaus() {
        return staus;
    }

    /**
     * 设置状态,0:推送失败,1:推送成功
     *
     * @param staus 状态,0:推送失败,1:推送成功
     */
    public void setStaus(String staus) {
        this.staus = staus;
    }

    /**
     * @return push_time
     */
    public Date getPushTime() {
        return pushTime;
    }

    /**
     * @param pushTime
     */
    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }
}