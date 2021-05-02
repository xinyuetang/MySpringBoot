package com.fudanuniversity.cms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户通知状态
 * <p>
 * Created by tidu at 2021-05-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsBulletinState implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long userId;

    /**
     * 字段备注:通知id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long bulletinId;

    /**
     * 字段备注:是否已读 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer read;

    /**
     * 字段备注:创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date createTime;

    /**
     * 字段备注:更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date modifyTime;
}

