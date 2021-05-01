package com.fudanuniversity.cms.combi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * <p>
 * Created by tidu at 2021-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserVo implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     *
     * id
     */
	private Long id;

    /**
     *
     * 学号
     */
	private String stuId;

    /**
     *
     * 权限身份
     */
	private Integer roleId;

    /**
     *
     * 用户名
     */
	private String name;

    /**
     *
     * 手机
     */
	private String telephone;

    /**
     *
     * 邮箱
     */
	private String email;

    /**
     *
     * 导师
     */
	private String mentor;

    /**
     *
     * 汇报人
     */
	private String leader;

    /**
     *
     * 是否科硕
     */
	private String isKeshuo;

    /**
     *
     * 就读类型 0-学术型，1-结合型，2-技术型
     */
	private String type;

    /**
     *
     * 入学时间
     */
	private String enrollDate;

    /**
     *
     * 论文
     */
	private String papers;

    /**
     *
     * 专利
     */
	private String patents;

    /**
     *
     * 服务
     */
	private String services;

    /**
     *
     * 项目
     */
	private String projects;

    /**
     *
     * 状态
     */
	private Integer status;

    /**
     *
     * 删除状态
     */
	private Integer deleted;

    /**
     *
     * 创建时间
     */
	private Date createTime;

    /**
     *
     * 更新时间
     */
	private Date modifyTime;
}

