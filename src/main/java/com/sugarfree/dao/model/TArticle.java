package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_article")
public class TArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 菜单id
     */
    @Column(name = "fk_menu_id")
    private Integer fkMenuId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 作者名
     */
    private String author;

    /**
     * 摘要
     */
    private String abstarct;

    /**
     * 文章图标
     */
    private String icon;

    /**
     * 类型 0:订阅详请,1:订阅文章内容
     */
    private String type;

    @Column(name = "class_time")
    private Integer classTime;

    /**
     * 状态:0:未提交,1:提交
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

    @Column(name = "op_user")
    private String opUser;

    private String context;

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
     * 获取菜单id
     *
     * @return fk_menu_id - 菜单id
     */
    public Integer getFkMenuId() {
        return fkMenuId;
    }

    /**
     * 设置菜单id
     *
     * @param fkMenuId 菜单id
     */
    public void setFkMenuId(Integer fkMenuId) {
        this.fkMenuId = fkMenuId;
    }

    /**
     * 获取文章标题
     *
     * @return title - 文章标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置文章标题
     *
     * @param title 文章标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取作者名
     *
     * @return author - 作者名
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者名
     *
     * @param author 作者名
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取摘要
     *
     * @return abstarct - 摘要
     */
    public String getAbstarct() {
        return abstarct;
    }

    /**
     * 设置摘要
     *
     * @param abstarct 摘要
     */
    public void setAbstarct(String abstarct) {
        this.abstarct = abstarct;
    }

    /**
     * 获取文章图标
     *
     * @return icon - 文章图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置文章图标
     *
     * @param icon 文章图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取类型 0:订阅详请,1:订阅文章内容
     *
     * @return type - 类型 0:订阅详请,1:订阅文章内容
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型 0:订阅详请,1:订阅文章内容
     *
     * @param type 类型 0:订阅详请,1:订阅文章内容
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return class_time
     */
    public Integer getClassTime() {
        return classTime;
    }

    /**
     * @param classTime
     */
    public void setClassTime(Integer classTime) {
        this.classTime = classTime;
    }

    /**
     * 获取状态:0:未提交,1:提交
     *
     * @return status - 状态:0:未提交,1:提交
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态:0:未提交,1:提交
     *
     * @param status 状态:0:未提交,1:提交
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

    /**
     * @return context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context
     */
    public void setContext(String context) {
        this.context = context;
    }
}