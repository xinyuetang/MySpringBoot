package com.fudanuniversity.cms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 演讲
 * <p>
 * Created by tidu at 2021-05-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsSeminar implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:演讲用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long userId;

    /**
     * 字段备注:演讲主题 <p>
     * 数据库字段长度:(128,0) <p>
     * 是否索引:不是
     */
    private String theme;

    /**
     * 字段备注:演讲资源保存链接地址 <p>
     * 数据库字段长度:(256,0) <p>
     * 是否索引:不是
     */
    private String link;

    /**
     * 字段备注:演讲时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date date;

    /**
     * 字段备注:创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Date createTime;

    /**
     * 字段备注:更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date modifyTime;
}

