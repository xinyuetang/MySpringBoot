package com.fudanuniversity.cms.business.vo.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xinyue.Tang at 2021-05-03 19:07:15
 */
@Data
@NoArgsConstructor
@ToString
public class CmsLoginUserVo implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 用户名
     */
    private String name;

    /**
     *
     */
    private Integer roleId;

    /**
     * 登录时间
     */
    private Date loginTime;
}
