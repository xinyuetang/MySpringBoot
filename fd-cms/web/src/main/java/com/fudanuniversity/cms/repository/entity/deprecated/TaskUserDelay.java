package com.fudanuniversity.cms.repository.entity.deprecated;

import java.io.Serializable;

public class TaskUserDelay implements Serializable {
    private Integer bulletinId;
    private Integer userId;

    private Integer delay;//任务期限被延长时间（以天为单位）

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

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
