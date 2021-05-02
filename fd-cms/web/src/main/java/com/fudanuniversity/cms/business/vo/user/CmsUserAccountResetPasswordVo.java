package com.fudanuniversity.cms.business.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户帐户
 * <p>
 * Created by tidu at 2021-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsUserAccountResetPasswordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "原密码不能为空")
    @Length(min = 6, message = "原密码长度不正确")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    @Length(min = 6, message = "新密码长度不正确")
    private String newPassword;
}

