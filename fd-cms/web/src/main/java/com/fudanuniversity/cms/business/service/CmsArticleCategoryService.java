package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.article.CmsArticleCategoryAddVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleCategoryQueryVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleCategoryVo;

import java.util.List;

/**
 * CmsArticleCategoryService
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsArticleCategoryService {

    /**
     * 分页查询数据列表
     */
    List<CmsArticleCategoryVo> listArticleCategories(CmsArticleCategoryQueryVo queryVo);

    /**
     * 保存处理
     */
    void addArticleCategory(CmsArticleCategoryAddVo categoryAddVo);

    /**
     * 根据id删除处理
     */
    void deleteArticleCategoryById(Long id);
}