package com.fudanuniversity.cms.repository.entity.deprecated;

import java.io.Serializable;

public class BulletinUser implements Serializable {
    private Integer bulletinId;
    private Integer userId;

    private Integer isRead;

    public Integer getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(Integer bulletinId) {
        this.bulletinId = bulletinId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}