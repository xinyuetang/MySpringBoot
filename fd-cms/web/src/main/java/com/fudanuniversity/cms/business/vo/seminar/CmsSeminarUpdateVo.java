package com.fudanuniversity.cms.business.vo.seminar;

import jakarta.validation.constraints.NotNull;
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

@ToString
public class CmsSeminarUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 演讲用户学号
     */
    @NotNull
    private Long speakerId;

    /**
     * 演讲时间
     */
    private Date date;

    /**
     * 演讲主题
     */
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

}

