package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;

/**
 * CmsArticleService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsArticleService {

    /**
     * 保存处理
     */
    void saveCmsArticle(CmsArticle cmsArticle);

    /**
     * 根据id更新处理
     */
    void updateCmsArticleById(CmsArticle cmsArticle);

    /**
     * 根据id删除处理
     */
    void deleteCmsArticleById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsArticle> queryPagingResultByParam(CmsArticleQuery query);

}