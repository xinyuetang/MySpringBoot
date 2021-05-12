package com.fudanuniversity.cms.business.vo.article;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 文章分类
 *
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsArticleCategoryQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 标签
     */
    private Integer tag;

    /**
     * 名称
     */
    private String name;
}