package com.fudanuniversity.cms.business.vo.user;

import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.StudyTypeEnum;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import com.fudanuniversity.cms.commons.validation.group.Update;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsUserMngVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(groups = Update.class)//更新时不能为空
    private Long id;

    /**
     * 学号
     */
    @NotEmpty
    private String stuId;

    /**
     * 权限身份
     */
    @NotNull
    @EnumValue(enumClass = UserRoleEnum.class, property = "code")//roleId必须为RoleEnum某个的code
    private Integer roleId;

    /**
     * 用户名
     */
    @NotEmpty
    private String name;

    /**
     * 手机
     */
    private String telephone;

    /**
     * 邮箱
     */
    @Email
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
     * 是否科硕
     */
    @EnumValue(enumClass = BooleanEnum.class, property = "code")
    private Integer keshuo;

    /**
     * 就读类型
     */
    @EnumValue(enumClass = StudyTypeEnum.class, property = "code")
    private Integer studyType;

    /**
     * 入学时间
     */
    private Date enrollDate;

    /**
     * 论文
     */
    private String papers;

    /**
     * 专利
     */
    private String patents;

    /**
     * 服务
     */
    private String services;

    /**
     * 项目
     */
    private String projects;
}

