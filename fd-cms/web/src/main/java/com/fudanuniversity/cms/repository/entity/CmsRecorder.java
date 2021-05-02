package com.fudanuniversity.cms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 演讲记录员
 * <p>
 * Created by tidu at 2021-05-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsRecorder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:演讲时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:是
     */
    private Date date;

    /**
     * 字段备注:辅读人员1用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long recorder1Id;

    /**
     * 字段备注:辅读人员1文件名 <p>
     * 数据库字段长度:(128,0) <p>
     * 是否索引:不是
     */
    private String recorder1File;

    /**
     * 字段备注:辅读人员1记录内容 <p>
     * 数据库字段长度:(2147483647,0) <p>
     * 是否索引:不是
     */
    private byte[] recorder1Content;

    /**
     * 字段备注:辅读人员2用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long recorder2Id;

    /**
     * 字段备注:辅读人员2文件名 <p>
     * 数据库字段长度:(128,0) <p>
     * 是否索引:不是
     */
    private String recorder2File;

    /**
     * 字段备注:辅读人员2记录内容 <p>
     * 数据库字段长度:(2147483647,0) <p>
     * 是否索引:不是
     */
    private byte[] recorder2Content;

    /**
     * 字段备注:记录人员用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long summarizerId;

    /**
     * 字段备注:记录人员文件名 <p>
     * 数据库字段长度:(128,0) <p>
     * 是否索引:不是
     */
    private String summarizerFile;

    /**
     * 字段备注:记录人员记录内容 <p>
     * 数据库字段长度:(2147483647,0) <p>
     * 是否索引:不是
     */
    private byte[] summarizerContent;

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

