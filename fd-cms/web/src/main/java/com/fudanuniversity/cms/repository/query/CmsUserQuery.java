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
public class CmsUserQuery extends BaseQuery {

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
     * 字段备注:用户类型 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String type;

    /**
     * 字段备注:学号 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:是
     */
    private String stuId;
    private List<String> stuIds;

    /**
     * 字段备注:权限身份 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:不是
     */
    private Integer roleId;

    /**
     * 字段备注:用户名 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String name;

    /**
     * 字段备注:手机 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String telephone;

    /**
     * 字段备注:邮箱 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String email;

    /**
     * 字段备注:导师 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String mentor;

    /**
     * 字段备注:汇报人 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String leader;

    /**
     * 字段备注:就读类型 <p>
     * 数据库字段长度:(3,0) <p>
     * 索引字段:不是
     */
    private Integer studyType;

    /**
     * 字段备注:是否科硕 <p>
     * 数据库字段长度:(3,0) <p>
     * 索引字段:不是
     */
    private Integer keshuo;

    /**
     * 字段备注:入学时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:不是
     */
    private Date enrollDate;

    /**
     * 字段备注:论文 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 索引字段:不是
     */
    private String papers;

    /**
     * 字段备注:专利 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 索引字段:不是
     */
    private String patents;

    /**
     * 字段备注:服务 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 索引字段:不是
     */
    private String services;

    /**
     * 字段备注:项目 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 索引字段:不是
     */
    private String projects;

    /**
     * 字段备注:状态 <p>
     * 数据库字段长度:(3,0) <p>
     * 索引字段:不是
     */
    private Integer status;

    /**
     * 字段备注:删除状态 <p>
     * 数据库字段长度:(3,0) <p>
     * 索引字段:不是
     */
    private Integer deleted;

    /**
     * 字段备注:小于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private Date eltCreateTime;

    /**
     * 字段备注:大于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
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

    public static CmsUserQuery singletonQuery() {
        CmsUserQuery query = new CmsUserQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsUserQuery listQuery() {
        CmsUserQuery query = new CmsUserQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}