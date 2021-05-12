package com.fudanuniversity.cms.business.vo.article;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 文章
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsArticleAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    @NotNull
    private Long categoryId;

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

