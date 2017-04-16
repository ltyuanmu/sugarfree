package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_menu_new")
public class TMenuNew {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "menu_desc")
    private String menuDesc;

    private String url;

    /**
     * 菜单是类型:1.订阅,2.静态连接,3积分
     */
    private String type;

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
     * 订阅消耗的积分
     */
    private Integer point;

    @Column(name = "sub_count")
    private String subCount;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return menu_desc
     */
    public String getMenuDesc() {
        return menuDesc;
    }

    /**
     * @param menuDesc
     */
    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取菜单是类型:1.订阅,2.静态连接,3积分
     *
     * @return type - 菜单是类型:1.订阅,2.静态连接,3积分
     */
    public String getType() {
        return type;
    }

    /**
     * 设置菜单是类型:1.订阅,2.静态连接,3积分
     *
     * @param type 菜单是类型:1.订阅,2.静态连接,3积分
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取订阅消耗的积分
     *
     * @return point - 订阅消耗的积分
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * 设置订阅消耗的积分
     *
     * @param point 订阅消耗的积分
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return sub_count
     */
    public String getSubCount() {
        return subCount;
    }

    /**
     * @param subCount
     */
    public void setSubCount(String subCount) {
        this.subCount = subCount;
    }
}