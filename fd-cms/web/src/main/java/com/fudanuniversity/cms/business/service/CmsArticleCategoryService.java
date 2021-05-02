package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsArticleCategory;
import com.fudanuniversity.cms.repository.query.CmsArticleCategoryQuery;

/**
 * CmsArticleCategoryService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsArticleCategoryService {

    /**
     * 保存处理
     */
    void saveCmsArticleCategory(CmsArticleCategory cmsArticleCategory);

    /**
     * 根据id更新处理
     */
    void updateCmsArticleCategoryById(CmsArticleCategory cmsArticleCategory);

    /**
     * 根据id删除处理
     */
    void deleteCmsArticleCategoryById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsArticleCategory> queryPagingResultByParam(CmsArticleCategoryQuery query);

}