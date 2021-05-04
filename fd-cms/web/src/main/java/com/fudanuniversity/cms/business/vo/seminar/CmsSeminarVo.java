package com.fudanuniversity.cms.business.vo.seminar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class CmsSeminarVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 演讲用户id
     */
    private Long speakerId;
    private String speakerStuId;
    private String speakerName;

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
    private String link;

    /**
     * 介绍与描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}

