package com.fudanuniversity.cms.business.vo.article;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@ToString
public class CmsArticleEditVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 标签
     */
    @Min(1L)
    private Long categoryId;

    /**
     * 名称
     */
    private String title;

    /**
     * 内容
     */
    private String content;
}

