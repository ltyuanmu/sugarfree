package com.sugarfree.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_para")
public class TPara {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "para_type")
    private String paraType;

    @Column(name = "para_name")
    private String paraName;

    @Column(name = "para_value")
    private String paraValue;

    @Column(name = "para_desc")
    private String paraDesc;

    @Column(name = "type_desc")
    private String typeDesc;

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
     * @return para_type
     */
    public String getParaType() {
        return paraType;
    }

    /**
     * @param paraType
     */
    public void setParaType(String paraType) {
        this.paraType = paraType;
    }

    /**
     * @return para_name
     */
    public String getParaName() {
        return paraName;
    }

    /**
     * @param paraName
     */
    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    /**
     * @return para_value
     */
    public String getParaValue() {
        return paraValue;
    }

    /**
     * @param paraValue
     */
    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    /**
     * @return para_desc
     */
    public String getParaDesc() {
        return paraDesc;
    }

    /**
     * @param paraDesc
     */
    public void setParaDesc(String paraDesc) {
        this.paraDesc = paraDesc;
    }

    /**
     * @return type_desc
     */
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     * @param typeDesc
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
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