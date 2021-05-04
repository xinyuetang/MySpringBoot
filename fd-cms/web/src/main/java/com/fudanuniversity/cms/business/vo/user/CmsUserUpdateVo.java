package com.fudanuniversity.cms.business.vo.user;

import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.StudyTypeEnum;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.enums.UserTypeEnum;
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
public class CmsUserUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 用户类型
     */
    @EnumValue(enumClass = UserTypeEnum.class, property = "code", message = "用户类型参数格式错误")
    private Integer type;

    /**
     * 权限身份
     */
    @NotNull
    //roleId必须为RoleEnum某个的code
    @EnumValue(enumClass = UserRoleEnum.class, property = "code", message = "权限身份参数格式错误")
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
    @Email(message = "邮箱格式不正确")
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
    @EnumValue(enumClass = BooleanEnum.class, property = "code", message = "是否科硕参数格式错误")
    private Integer keshuo;

    /**
     * 就读类型
     */
    @EnumValue(enumClass = StudyTypeEnum.class, property = "code", message = "就读类型参数格式错误")
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

