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
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsRecorderQuery extends BaseQuery {

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
     * 字段备注:演讲时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:是
     */
    private Date date;

    /**
     * 字段备注:辅读人员1用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Long recorder1Id;

    /**
     * 字段备注:辅读人员1文件名 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String recorder1File;

    /**
     * 字段备注:辅读人员1文件类型 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String recorder1Type;

    /**
     * 字段备注:辅读人员1记录内容 <p>
     * 数据库字段长度:(2147483647,0) <p>
     * 索引字段:不是
     */
    private byte[] recorder1Content;

    /**
     * 字段备注:辅读人员2用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Long recorder2Id;

    /**
     * 字段备注:辅读人员2文件名 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String recorder2File;

    /**
     * 字段备注:辅读人员2文件类型 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String recorder2Type;

    /**
     * 字段备注:辅读人员2记录内容 <p>
     * 数据库字段长度:(2147483647,0) <p>
     * 索引字段:不是
     */
    private byte[] recorder2Content;

    /**
     * 字段备注:记录人员用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Long summarizerId;

    /**
     * 字段备注:记录人员文件名 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String summarizerFile;

    /**
     * 字段备注:记录人员文件类型 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String summarizerType;

    /**
     * 字段备注:记录人员记录内容 <p>
     * 数据库字段长度:(2147483647,0) <p>
     * 索引字段:不是
     */
    private byte[] summarizerContent;

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

    public static CmsRecorderQuery singletonQuery() {
        CmsRecorderQuery query = new CmsRecorderQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsRecorderQuery listQuery() {
        CmsRecorderQuery query = new CmsRecorderQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}