package com.fudanuniversity.cms.business.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 文章
 * <p>
 * Created by tidu at 2021-05-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsArticleAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    @NotNull
    private Integer categoryTag;

    /**
     * 名称
     */
    @NotEmpty
    private String title;

    /**
     * 内容
     */
    private String content;
}

