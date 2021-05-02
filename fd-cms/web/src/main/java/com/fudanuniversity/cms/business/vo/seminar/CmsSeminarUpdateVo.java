package com.fudanuniversity.cms.business.vo.seminar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 演讲
 * <p>
 * Created by tidu at 2021-05-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @NotEmpty
    private String stuId;

    /**
     * 演讲主题
     */
    private String theme;

    /**
     * 演讲资源保存链接地址
     */
    private String link;

    /**
     * 演讲时间
     */
    private Date date;
}

