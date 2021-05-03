package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsArticleCategory;
import com.fudanuniversity.cms.repository.query.CmsArticleCategoryQuery;

import java.util.List;

/**
 * CmsArticleCategoryDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsArticleCategoryDao {

    /**
     * 保存处理
     */
    int insert(CmsArticleCategory cmsArticleCategory);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsArticleCategory> cmsArticleCategoryList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsArticleCategory cmsArticleCategory);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsArticleCategory> selectListByParam(CmsArticleCategoryQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsArticleCategoryQuery query);

}
