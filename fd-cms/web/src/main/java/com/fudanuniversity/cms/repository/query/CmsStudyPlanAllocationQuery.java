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
 * Created by Xinyue.Tang at 2021-05-05 20:47:04
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsStudyPlanAllocationQuery extends BaseQuery {

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
     * 字段备注:用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private Long userId;

    /**
     * 字段备注:培养方案id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private Long planId;

    /**
     * 字段备注:培养方案阶段id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Long planStageId;

    /**
     * 字段备注:培养方案任务id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Long planWorkId;

    /**
     * 字段备注:培养方案任务开始日期 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:不是
     */
    private Date planWorkStart;

    /**
     * 字段备注:培养方案任务结束日期 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:不是
     */
    private Date planWorkEnd;

    /**
     * 字段备注:备注 <p>
     * 数据库字段长度:(16383,0) <p>
     * 索引字段:不是
     */
    private String remark;

    /**
     * 字段备注:删除标记 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Long deleted;

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

    public static CmsStudyPlanAllocationQuery singletonQuery() {
        CmsStudyPlanAllocationQuery query = new CmsStudyPlanAllocationQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsStudyPlanAllocationQuery listQuery() {
        CmsStudyPlanAllocationQuery query = new CmsStudyPlanAllocationQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}