package com.fudanuniversity.cms.repository.entity.deprecated;

import java.io.Serializable;

public class TaskUserFinish implements Serializable {
    private Integer taskId;
    private Integer userId;

    private Boolean isFinished;//是否完成

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }
}
