package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_music")
public class TMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fk_menu_id")
    private Integer fkMenuId;

    @Column(name = "fk_article_id")
    private Integer fkArticleId;

    private String name;

    private String icon;

    private String singer;

    private String song;

    /**
     * 0,保存，1,提交
     */
    private String status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0
     */
    @Column(name = "delete_state")
    private String deleteState;

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
     * @return fk_menu_id
     */
    public Integer getFkMenuId() {
        return fkMenuId;
    }

    /**
     * @param fkMenuId
     */
    public void setFkMenuId(Integer fkMenuId) {
        this.fkMenuId = fkMenuId;
    }

    /**
     * @return fk_article_id
     */
    public Integer getFkArticleId() {
        return fkArticleId;
    }

    /**
     * @param fkArticleId
     */
    public void setFkArticleId(Integer fkArticleId) {
        this.fkArticleId = fkArticleId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return singer
     */
    public String getSinger() {
        return singer;
    }

    /**
     * @param singer
     */
    public void setSinger(String singer) {
        this.singer = singer;
    }

    /**
     * @return song
     */
    public String getSong() {
        return song;
    }

    /**
     * @param song
     */
    public void setSong(String song) {
        this.song = song;
    }

    /**
     * 获取0,保存，1,提交
     *
     * @return status - 0,保存，1,提交
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置0,保存，1,提交
     *
     * @param status 0,保存，1,提交
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取0
     *
     * @return delete_state - 0
     */
    public String getDeleteState() {
        return deleteState;
    }

    /**
     * 设置0
     *
     * @param deleteState 0
     */
    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }
}