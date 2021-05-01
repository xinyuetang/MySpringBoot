package com.fudanuniversity.cms.inner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * <p>
 * Created by tidu at 2021-05-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:学号 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:是
     */
    private String stuId;

    /**
     * 字段备注:权限身份 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer roleId;

    /**
     * 字段备注:用户名 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String name;

    /**
     * 字段备注:手机 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String telephone;

    /**
     * 字段备注:邮箱 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String email;

    /**
     * 字段备注:导师 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String mentor;

    /**
     * 字段备注:汇报人 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String leader;

    /**
     * 字段备注:就读类型 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer type;

    /**
     * 字段备注:论文 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer isKeshuo;

    /**
     * 字段备注:入学时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date enrollDate;

    /**
     * 字段备注:论文 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 是否索引:不是
     */
    private String papers;

    /**
     * 字段备注:专利 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 是否索引:不是
     */
    private String patents;

    /**
     * 字段备注:服务 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 是否索引:不是
     */
    private String services;

    /**
     * 字段备注:项目 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 是否索引:不是
     */
    private String projects;

    /**
     * 字段备注:状态 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer status;

    /**
     * 字段备注:删除状态 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer deleted;

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

