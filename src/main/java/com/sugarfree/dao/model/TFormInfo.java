package com.sugarfree.dao.model;

import javax.persistence.*;

@Table(name = "t_form_info")
public class TFormInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 微信用户id
     */
    @Column(name = "fk_wx_user_id")
    private Integer fkWxUserId;

    /**
     * 表单提交连接
     */
    private String content;

    /**
     * 类型
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
     * 获取表单提交连接
     *
     * @return content - 表单提交连接
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置表单提交连接
     *
     * @param content 表单提交连接
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }
}