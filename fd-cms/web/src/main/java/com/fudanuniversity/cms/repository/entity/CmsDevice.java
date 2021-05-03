package com.fudanuniversity.cms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备
 * <p>
 * Created by tidu at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:设备类型 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long type;

    /**
     * 字段备注:设备型号 <p>
     * 数据库字段长度:(128,0) <p>
     * 是否索引:不是
     */
    private String model;

    /**
     * 字段备注:负责人姓名 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String principal;

    /**
     * 字段备注:设备名称 <p>
     * 数据库字段长度:(256,0) <p>
     * 是否索引:不是
     */
    private String name;

    /**
     * 字段备注:库存 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer inventory;

    /**
     * 字段备注:库存单位 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String inventoryUnit;

    /**
     * 字段备注:删除状态 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer deleted;

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

