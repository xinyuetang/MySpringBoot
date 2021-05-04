package com.fudanuniversity.cms.business.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 用户
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsUserQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 模糊搜索学号和姓名，学号为精确搜索，姓名模糊搜索
     */
    private String kw;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 权限身份
     */
    private Integer roleId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 手机
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 导师
     */
    private String mentor;

    /**
     * 汇报人
     */
    private String leader;

    /**
     * 就读类型
     */
    private Integer studyType;

    /**
     * 是否科硕
     */
    private Integer keshuo;

    /**
     * 入学时间
     */
    private Date enrollDate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 小于等于创建时间
     */
    private Date eltCreateTime;

    /**
     * 大于等于创建时间
     */
    private Date egtCreateTime;

    /**
     * 小于等于更新时间
     */
    private Date eltModifyTime;

    /**
     * 大于等于更新时间
     */
    private Date egtModifyTime;
}