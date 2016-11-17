package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_subscriber")
public class TSubscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fk_wx_user_id")
    private Integer fkWxUserId;

    /**
     * 订阅的菜单id
     */
    @Column(name = "fk_menu_id")
    private Integer fkMenuId;

    /**
     * 订阅的状态 0:已订阅,1:已退订
     */
    private String status;

    /**
     * 最后课时
     */
    @Column(name = "last_class_time")
    private Integer lastClassTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "op_user")
    private String opUser;

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
     * 获取订阅的菜单id
     *
     * @return fk_menu_id - 订阅的菜单id
     */
    public Integer getFkMenuId() {
        return fkMenuId;
    }

    /**
     * 设置订阅的菜单id
     *
     * @param fkMenuId 订阅的菜单id
     */
    public void setFkMenuId(Integer fkMenuId) {
        this.fkMenuId = fkMenuId;
    }

    /**
     * 获取订阅的状态 0:已订阅,1:已退订
     *
     * @return status - 订阅的状态 0:已订阅,1:已退订
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置订阅的状态 0:已订阅,1:已退订
     *
     * @param status 订阅的状态 0:已订阅,1:已退订
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取最后课时
     *
     * @return last_class_time - 最后课时
     */
    public Integer getLastClassTime() {
        return lastClassTime;
    }

    /**
     * 设置最后课时
     *
     * @param lastClassTime 最后课时
     */
    public void setLastClassTime(Integer lastClassTime) {
        this.lastClassTime = lastClassTime;
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
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return op_user
     */
    public String getOpUser() {
        return opUser;
    }

    /**
     * @param opUser
     */
    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }
}