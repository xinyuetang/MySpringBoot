package com.fudanuniversity.cms.combi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户账户
 * <p>
 * Created by tidu at 2021-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserAccountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @NotEmpty(message = "学号不能为空")
    private String stuId;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不正确")
    private String password;
}

