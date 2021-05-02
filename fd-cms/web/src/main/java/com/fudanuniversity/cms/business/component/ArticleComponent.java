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
 * Created by tidu at 2021-05-02 22:39:10
 */
@Component
public class ArticleComponent {

    @Resource
    private CmsArticleDao cmsArticleDao;

    @Resource
    private CmsArticleCategoryDao cmsArticleCategoryDao;

    public CmsArticleCategory queryArticleCategory(Long categoryId) {
        CmsArticleCategoryQuery query = new CmsArticleCategoryQuery();
        query.setId(categoryId);
        query.setLimit(1);
        List<CmsArticleCategory> categories = cmsArticleCategoryDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(categories)) {
            return categories.get(0);
        }
        return null;
    }

    public CmsArticleCategory queryArticleCategory(Integer tag) {
        CmsArticleCategoryQuery query = new CmsArticleCategoryQuery();
        query.setTag(tag);
        query.setLimit(1);
        List<CmsArticleCategory> categories = cmsArticleCategoryDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(categories)) {
            return categories.get(0);
        }
        return null;
    }

    public CmsArticle queryArticle(Long articleId) {
        CmsArticleQuery query = new CmsArticleQuery();
        query.setId(articleId);
        query.setLimit(1);
        List<CmsArticle> articles = cmsArticleDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(articles)) {
            return articles.get(0);
        }
        return null;
    }
}
