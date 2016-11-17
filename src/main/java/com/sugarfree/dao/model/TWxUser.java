package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_wx_user")
public class TWxUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "openId")
    private String openid;

    @Column(name = "nick_name")
    private String nickName;

    private Integer sex;

    private String city;

    private String country;

    private String province;

    private String language;

    /**
     * 头像
     */
    private String icon;

    private String unionid;

    /**
     * 备注
     */
    private String remark;

    private Integer groupid;

    @Column(name = "qr_url")
    private String qrUrl;

    @Column(name = "qr_ticket")
    private String qrTicket;

    /**
     * 积分
     */
    private Integer point;

    @Column(name = "recommend_openId")
    private String recommendOpenid;

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
     * @return openId
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * @param openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Integer sex) {
        this.sex = sex;
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
     * 获取头像
     *
     * @return icon - 头像
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置头像
     *
     * @param icon 头像
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return unionid
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * @param unionid
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
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
     * @return groupid
     */
    public Integer getGroupid() {
        return groupid;
    }

    /**
     * @param groupid
     */
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
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
     * @return recommend_openId
     */
    public String getRecommendOpenid() {
        return recommendOpenid;
    }

    /**
     * @param recommendOpenid
     */
    public void setRecommendOpenid(String recommendOpenid) {
        this.recommendOpenid = recommendOpenid;
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