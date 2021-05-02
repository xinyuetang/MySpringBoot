package com.fudanuniversity.cms.repository.query;

import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 查询对象
 * <p>
 * Created by tidu at 2021-05-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsPlanQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private Long id;

    /**
     * 字段备注:id
     * 数据库字段长度:(19,0)
     * 索引字段:是
     */
    private Long gtId;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private List<Long> idList;

    /**
     * 字段备注:是否公共任务 <p>
     * 数据库字段长度:(3,0) <p>
     * 索引字段:是
     */
    private Integer common;

    /**
     * 字段备注:是否科硕任务 <p>
     * 数据库字段长度:(3,0) <p>
     * 索引字段:不是
     */
    private Integer keshuo;

    /**
     * 字段备注:就读类型 <p>
     * 数据库字段长度:(3,0) <p>
     * 索引字段:不是
     */
    private Integer studyType;

    /**
     * 字段备注:序号 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:不是
     */
    private Integer index;

    /**
     * 字段备注:名称 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String name;

    /**
     * 字段备注:计划天数 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:不是
     */
    private Integer spendDays;

    /**
     * 字段备注:小于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date eltCreateTime;

    /**
     * 字段备注:大于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date egtCreateTime;

    /**
     * 字段备注:小于等于更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date eltModifyTime;

    /**
     * 字段备注:大于等于更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date egtModifyTime;

    public static CmsPlanQuery singletonQuery() {
        CmsPlanQuery query = new CmsPlanQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsPlanQuery listQuery() {
        CmsPlanQuery query = new CmsPlanQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}