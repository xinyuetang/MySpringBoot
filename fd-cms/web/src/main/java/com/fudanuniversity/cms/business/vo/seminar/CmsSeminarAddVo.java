package com.fudanuniversity.cms.business.vo.seminar;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.Date;


/**
 * 演讲
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsSeminarAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 演讲用户学号
     */
    @NotNull
    private Long speakerId;

    /**
     * 演讲主题
     */
    @NotEmpty
    private String theme;

    /**
     * 演讲资源保存链接地址
     */
    @URL(message = "演讲资源链接地址格式不正确")
    private String link;

    /**
     * 介绍与描述
     */
    private String description;

    /**
     * 演讲时间
     */
    @NotNull
    private Date date;
}

