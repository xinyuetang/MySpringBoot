package com.fudanuniversity.cms.commons.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xinyue.Tang at 2021-05-01 22:41:49
 */
@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 学号
     */
    private String stuId;

    /**
     *
     */
    private Integer roleId;

    /**
     * 登录时间
     */
    private Date loginTime;
}
