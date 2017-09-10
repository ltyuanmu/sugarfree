package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_menu_new")
public class TMenuNew {
    /**
     * ����id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "menu_desc")
    private String menuDesc;

    private String url;

    /**
     * �˵�������:1.����,2.��̬����,3����
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
     * �������ĵĻ���
     */
    private Integer point;

    @Column(name = "sub_count")
    private String subCount;

    @Column(name = "menu_sort")
    private Integer menuSort;

    /**
     * ��ȡ����id
     *
     * @return id - ����id
     */
    public Integer getId() {
        return id;
    }

    /**
     * ��������id
     *
     * @param id ����id
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
     * ��ȡ�˵�������:1.����,2.��̬����,3����
     *
     * @return type - �˵�������:1.����,2.��̬����,3����
     */
    public String getType() {
        return type;
    }

    /**
     * ���ò˵�������:1.����,2.��̬����,3����
     *
     * @param type �˵�������:1.����,2.��̬����,3����
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
     * ��ȡ0
     *
     * @return delete_state - 0
     */
    public String getDeleteState() {
        return deleteState;
    }

    /**
     * ����0
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
     * ��ȡ�������ĵĻ���
     *
     * @return point - �������ĵĻ���
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * ���ö������ĵĻ���
     *
     * @param point �������ĵĻ���
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

    /**
     * @return menu_sort
     */
    public Integer getMenuSort() {
        return menuSort;
    }

    /**
     * @param menuSort
     */
    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }
}