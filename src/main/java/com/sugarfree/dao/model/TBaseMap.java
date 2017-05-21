package com.sugarfree.dao.model;

import javax.persistence.*;

@Table(name = "t_base_map")
public class TBaseMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    @Column(name = "delete_state")
    private Boolean deleteState;

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
     * @return delete_state
     */
    public Boolean getDeleteState() {
        return deleteState;
    }

    /**
     * @param deleteState
     */
    public void setDeleteState(Boolean deleteState) {
        this.deleteState = deleteState;
    }
}