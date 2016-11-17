package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_csol")
public class TCsol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 渠道id
     */
    @Column(name = "fk_gateway_id")
    private Integer fkGatewayId;

    @Column(name = "fk_wx_user_id")
    private Integer fkWxUserId;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 积分兑换码
     */
    private String code;

    /**
     * 兑换状态:0:未兑换.1:已兑换
     */
    private String status;

    /**
     * 兑换时间
     */
    @Column(name = "exchange_time")
    private Date exchangeTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0
     */
    @Column(name = "delete_state")
    private String deleteState;

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
     * 获取渠道id
     *
     * @return fk_gateway_id - 渠道id
     */
    public Integer getFkGatewayId() {
        return fkGatewayId;
    }

    /**
     * 设置渠道id
     *
     * @param fkGatewayId 渠道id
     */
    public void setFkGatewayId(Integer fkGatewayId) {
        this.fkGatewayId = fkGatewayId;
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
     * 获取分数
     *
     * @return score - 分数
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置分数
     *
     * @param score 分数
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取积分兑换码
     *
     * @return code - 积分兑换码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置积分兑换码
     *
     * @param code 积分兑换码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取兑换状态:0:未兑换.1:已兑换
     *
     * @return status - 兑换状态:0:未兑换.1:已兑换
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置兑换状态:0:未兑换.1:已兑换
     *
     * @param status 兑换状态:0:未兑换.1:已兑换
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取兑换时间
     *
     * @return exchange_time - 兑换时间
     */
    public Date getExchangeTime() {
        return exchangeTime;
    }

    /**
     * 设置兑换时间
     *
     * @param exchangeTime 兑换时间
     */
    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
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