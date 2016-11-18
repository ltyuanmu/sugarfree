package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_wx_user")
public class TWxUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "open_id")
    private String openId;

    private String nickname;

    private String sex;

    private String language;

    private String city;

    private String country;

    private String province;

    @Column(name = "head_img_url")
    private String headImgUrl;

    @Column(name = "subscribe_time")
    private Long subscribeTime;

    @Column(name = "union_id")
    private String unionId;

    @Column(name = "sex_id")
    private Integer sexId;

    /**
     * 备注
     */
    private String remark;

    @Column(name = "group_id")
    private Integer groupId;

    private Boolean subscribe;

    @Column(name = "qr_url")
    private String qrUrl;

    @Column(name = "qr_ticket")
    private String qrTicket;

    /**
     * 积分
     */
    private Integer point;

    @Column(name = "recommend_open_id")
    private String recommendOpenId;

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
     * @return open_id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return head_img_url
     */
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    /**
     * @param headImgUrl
     */
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    /**
     * @return subscribe_time
     */
    public Long getSubscribeTime() {
        return subscribeTime;
    }

    /**
     * @param subscribeTime
     */
    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    /**
     * @return union_id
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * @param unionId
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    /**
     * @return sex_id
     */
    public Integer getSexId() {
        return sexId;
    }

    /**
     * @param sexId
     */
    public void setSexId(Integer sexId) {
        this.sexId = sexId;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return group_id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * @return subscribe
     */
    public Boolean getSubscribe() {
        return subscribe;
    }

    /**
     * @param subscribe
     */
    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    /**
     * @return qr_url
     */
    public String getQrUrl() {
        return qrUrl;
    }

    /**
     * @param qrUrl
     */
    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    /**
     * @return qr_ticket
     */
    public String getQrTicket() {
        return qrTicket;
    }

    /**
     * @param qrTicket
     */
    public void setQrTicket(String qrTicket) {
        this.qrTicket = qrTicket;
    }

    /**
     * 获取积分
     *
     * @return point - 积分
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * 设置积分
     *
     * @param point 积分
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return recommend_open_id
     */
    public String getRecommendOpenId() {
        return recommendOpenId;
    }

    /**
     * @param recommendOpenId
     */
    public void setRecommendOpenId(String recommendOpenId) {
        this.recommendOpenId = recommendOpenId;
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