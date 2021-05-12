package com.fudanuniversity.cms.business.vo.article;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 文章分类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsArticleCategoryAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    @NotNull
    private Integer tag;

    /**
     * 名称
     */
    @NotEmpty
    private String name;
}

