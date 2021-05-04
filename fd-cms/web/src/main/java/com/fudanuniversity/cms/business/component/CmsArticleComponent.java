package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.repository.dao.CmsArticleCategoryDao;
import com.fudanuniversity.cms.repository.dao.CmsArticleDao;
import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.entity.CmsArticleCategory;
import com.fudanuniversity.cms.repository.query.CmsArticleCategoryQuery;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Xinyue.Tang at 2021-05-02 22:39:10
 */
@Component
public class CmsArticleComponent {

    @Resource
    private CmsArticleDao cmsArticleDao;

    @Resource
    private CmsArticleCategoryDao cmsArticleCategoryDao;

    public CmsArticleCategory queryArticleCategory(Long categoryId) {
        CmsArticleCategoryQuery query = CmsArticleCategoryQuery.singletonQuery();
        query.setId(categoryId);
        List<CmsArticleCategory> categories = cmsArticleCategoryDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(categories)) {
            return categories.get(0);
        }
        return null;
    }

    public List<CmsArticleCategory> queryArticleCategory(Integer tag) {
        CmsArticleCategoryQuery query = CmsArticleCategoryQuery.listQuery();
        query.setTag(tag);
        return cmsArticleCategoryDao.selectListByParam(query);
    }

    public CmsArticle queryArticleInfo(Long articleId) {
        CmsArticleQuery query = CmsArticleQuery.singletonQuery();
        query.setId(articleId);
        List<CmsArticle> articles = cmsArticleDao.selectInfoListByParam(query);
        if (CollectionUtils.isNotEmpty(articles)) {
            return articles.get(0);
        }
        return null;
    }

    public CmsArticle queryArticleDetail(Long articleId) {
        CmsArticleQuery query = CmsArticleQuery.singletonQuery();
        query.setId(articleId);
        List<CmsArticle> articles = cmsArticleDao.selectDetailListByParam(query);
        if (CollectionUtils.isNotEmpty(articles)) {
            return articles.get(0);
        }
        return null;
    }
}
