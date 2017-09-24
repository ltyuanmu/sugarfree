package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_menu_new")
public class TMenuNew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "menu_desc")
    private String menuDesc;

    private String url;


    private String type;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "delete_state")
    private String deleteState;

    @Column(name = "op_user")
    private String opUser;


    private Integer point;

    @Column(name = "sub_count")
    private String subCount;

    @Column(name = "menu_sort")
    private Integer menuSort;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getMenuDesc() {
        return menuDesc;
    }


    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getDeleteState() {
        return deleteState;
    }


    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }


    public String getOpUser() {
        return opUser;
    }


    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }


    public Integer getPoint() {
        return point;
    }


    public void setPoint(Integer point) {
        this.point = point;
    }


    public String getSubCount() {
        return subCount;
    }


    public void setSubCount(String subCount) {
        this.subCount = subCount;
    }


    public Integer getMenuSort() {
        return menuSort;
    }


    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }
}