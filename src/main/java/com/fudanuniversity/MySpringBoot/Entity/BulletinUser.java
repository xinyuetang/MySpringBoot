package com.fudanuniversity.MySpringBoot.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(BulletinUser.class)
public class BulletinUser implements Serializable {
    @Id
    private Integer bulletinId;
    @Id
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

