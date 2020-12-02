package com.mysql.demo.common.model;

import java.util.Date;

public class BsAdminUser {
    private Integer id;

    private String jdUid;

    private String pin;

    private String erp;

    private String jdRole;

    private String userName;

    private Boolean isDel;

    private Date createdTime;

    private Date modifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJdUid() {
        return jdUid;
    }

    public void setJdUid(String jdUid) {
        this.jdUid = jdUid == null ? null : jdUid.trim();
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin == null ? null : pin.trim();
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp == null ? null : erp.trim();
    }

    public String getJdRole() {
        return jdRole;
    }

    public void setJdRole(String jdRole) {
        this.jdRole = jdRole == null ? null : jdRole.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}