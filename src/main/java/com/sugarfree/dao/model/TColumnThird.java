package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_column_third")
public class TColumnThird {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "column_desc")
    private String columnDesc;

    /**
     * 更新期数
     */
    @Column(name = "update_num")
    private Integer updateNum;

    /**
     * 订阅数
     */
    @Column(name = "sub_num")
    private Integer subNum;

    /**
     * 栏目图片
     */
    private String img;

    private String url;

    private Integer sort;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private Date updateUser;

    @Column(name = "update_time")
    private Date updateTime;

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
     * @return column_name
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return column_desc
     */
    public String getColumnDesc() {
        return columnDesc;
    }

    /**
     * @param columnDesc
     */
    public void setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
    }

    /**
     * 获取更新期数
     *
     * @return update_num - 更新期数
     */
    public Integer getUpdateNum() {
        return updateNum;
    }

    /**
     * 设置更新期数
     *
     * @param updateNum 更新期数
     */
    public void setUpdateNum(Integer updateNum) {
        this.updateNum = updateNum;
    }

    /**
     * 获取订阅数
     *
     * @return sub_num - 订阅数
     */
    public Integer getSubNum() {
        return subNum;
    }

    /**
     * 设置订阅数
     *
     * @param subNum 订阅数
     */
    public void setSubNum(Integer subNum) {
        this.subNum = subNum;
    }

    /**
     * 获取栏目图片
     *
     * @return img - 栏目图片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置栏目图片
     *
     * @param img 栏目图片
     */
    public void setImg(String img) {
        this.img = img;
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
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
     * @return update_user
     */
    public Date getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(Date updateUser) {
        this.updateUser = updateUser;
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
     * @return delete_state
     */
    public String getDeleteState() {
        return deleteState;
    }

    /**
     * @param deleteState
     */
    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }
}