package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_point_history")
public class TPointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fk_wx_user_id")
    private Integer fkWxUserId;

    /**
     * 添加积分状态
     */
    private String status;

    /**
     * 积分的分数
     */
    private String score;

    /**
     * 积分记录时间
     */
    @Column(name = "remark_time")
    private Date remarkTime;

    /**
     * 积分来源:subscribe,share,focus,exchange
     */
    private String source;

    /**
     * 关系用户id
     */
    @Column(name = "relate_wx_user_id")
    private Integer relateWxUserId;

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
     * @return fk_wx_user_id
     */
    public Integer getFkWxUserId() {
        return fkWxUserId;
    }

    /**
     * @param fkWxUserId
     */
    public void setFkWxUserId(Integer fkWxUserId) {
        this.fkWxUserId = fkWxUserId;
    }

    /**
     * 获取添加积分状态
     *
     * @return status - 添加积分状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置添加积分状态
     *
     * @param status 添加积分状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取积分的分数
     *
     * @return score - 积分的分数
     */
    public String getScore() {
        return score;
    }

    /**
     * 设置积分的分数
     *
     * @param score 积分的分数
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 获取积分记录时间
     *
     * @return remark_time - 积分记录时间
     */
    public Date getRemarkTime() {
        return remarkTime;
    }

    /**
     * 设置积分记录时间
     *
     * @param remarkTime 积分记录时间
     */
    public void setRemarkTime(Date remarkTime) {
        this.remarkTime = remarkTime;
    }

    /**
     * 获取积分来源:subscribe,share,focus,exchange
     *
     * @return source - 积分来源:subscribe,share,focus,exchange
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置积分来源:subscribe,share,focus,exchange
     *
     * @param source 积分来源:subscribe,share,focus,exchange
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取关系用户id
     *
     * @return relate_wx_user_id - 关系用户id
     */
    public Integer getRelateWxUserId() {
        return relateWxUserId;
    }

    /**
     * 设置关系用户id
     *
     * @param relateWxUserId 关系用户id
     */
    public void setRelateWxUserId(Integer relateWxUserId) {
        this.relateWxUserId = relateWxUserId;
    }
}