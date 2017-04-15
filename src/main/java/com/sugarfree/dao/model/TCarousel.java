package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_carousel")
public class TCarousel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 管理员需要跳转的url
     */
    private String url;

    /**
     * 管理员上传的图片
     */
    private String image;

    /**
     * 是否删除0:未删除,1:已删除
     */
    @Column(name = "delete_state")
    private String deleteState;

    /**
     * 操作人
     */
    @Column(name = "op_user")
    private String opUser;

    /**
     * 操作时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取管理员需要跳转的url
     *
     * @return url - 管理员需要跳转的url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置管理员需要跳转的url
     *
     * @param url 管理员需要跳转的url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取管理员上传的图片
     *
     * @return image - 管理员上传的图片
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置管理员上传的图片
     *
     * @param image 管理员上传的图片
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取是否删除0:未删除,1:已删除
     *
     * @return delete_state - 是否删除0:未删除,1:已删除
     */
    public String getDeleteState() {
        return deleteState;
    }

    /**
     * 设置是否删除0:未删除,1:已删除
     *
     * @param deleteState 是否删除0:未删除,1:已删除
     */
    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }

    /**
     * 获取操作人
     *
     * @return op_user - 操作人
     */
    public String getOpUser() {
        return opUser;
    }

    /**
     * 设置操作人
     *
     * @param opUser 操作人
     */
    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    /**
     * 获取操作时间
     *
     * @return update_time - 操作时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置操作时间
     *
     * @param updateTime 操作时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}