package com.fudanuniversity.cms.commons.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tidu at 2021-05-01 22:41:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 登录时间
     */
    private Date loginTime;
}
