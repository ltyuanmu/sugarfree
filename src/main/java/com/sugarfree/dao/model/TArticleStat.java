package com.sugarfree.dao.model;

import javax.persistence.*;

@Table(name = "t_article_stat")
public class TArticleStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文章id
     */
    @Column(name = "fk_article_id")
    private Integer fkArticleId;

    /**
     * 微信用户id
     */
    @Column(name = "fk_wx_user_id")
    private Integer fkWxUserId;

    /**
     * 文章标题
     */
    @Column(name = "article_title")
    private String articleTitle;

    /**
     * 用户打开次数
     */
    @Column(name = "open_num")
    private Integer openNum;

    /**
     * 是否为分享打开,0:用户正常打开,1分享的用户打开
     */
    private String type;

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
     * 获取文章标题
     *
     * @return article_title - 文章标题
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * 设置文章标题
     *
     * @param articleTitle 文章标题
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 获取用户打开次数
     *
     * @return open_num - 用户打开次数
     */
    public Integer getOpenNum() {
        return openNum;
    }

    /**
     * 设置用户打开次数
     *
     * @param openNum 用户打开次数
     */
    public void setOpenNum(Integer openNum) {
        this.openNum = openNum;
    }

    /**
     * 获取是否为分享打开,0:用户正常打开,1分享的用户打开
     *
     * @return type - 是否为分享打开,0:用户正常打开,1分享的用户打开
     */
    public String getType() {
        return type;
    }

    /**
     * 设置是否为分享打开,0:用户正常打开,1分享的用户打开
     *
     * @param type 是否为分享打开,0:用户正常打开,1分享的用户打开
     */
    public void setType(String type) {
        this.type = type;
    }
}