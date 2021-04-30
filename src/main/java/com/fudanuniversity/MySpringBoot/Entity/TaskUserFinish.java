package com.fudanuniversity.MySpringBoot.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(TaskUserFinish.class)
public class TaskUserFinish implements Serializable {
    @Id
    private Integer taskId;
    @Id
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
