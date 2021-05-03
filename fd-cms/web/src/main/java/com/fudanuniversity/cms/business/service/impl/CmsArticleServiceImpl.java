package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsArticleComponent;
import com.fudanuniversity.cms.business.service.CmsArticleService;
import com.fudanuniversity.cms.business.vo.article.CmsArticleAddVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleEditVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleQueryVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsArticleDao;
import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.entity.CmsArticleCategory;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * CmsArticleService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Service
public class CmsArticleServiceImpl implements CmsArticleService {

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleServiceImpl.class);

    @Resource
    private CmsArticleDao cmsArticleDao;

    @Resource
    private CmsArticleComponent cmsArticleComponent;

    @Override
    public CmsArticleVo getArticle(Long id) {
        CmsArticle article = cmsArticleComponent.queryArticle(id);
        if (article != null) {
            return convertCmsArticleVo(article);
        }
        return null;
    }

    private CmsArticleVo convertCmsArticleVo(CmsArticle article) {
        CmsArticleVo articleVo = new CmsArticleVo();
        articleVo.setId(article.getId());
        articleVo.setCategoryTag(article.getCategoryTag());
        articleVo.setTitle(article.getTitle());
        articleVo.setContent(article.getContent());
        articleVo.setCreateTime(article.getCreateTime());
        articleVo.setModifyTime(article.getModifyTime());
        return articleVo;
    }

    /**
     * 保存处理
     */
    @Override
    public void saveCmsArticle(CmsArticleAddVo articleAddVo) {
        Integer categoryTag = articleAddVo.getCategoryTag();
        CmsArticleCategory category = cmsArticleComponent.queryArticleCategory(categoryTag);
        AssertUtils.notNull(category, "文章分类标签不存在");
        CmsArticle article = new CmsArticle();
        article.setCategoryTag(articleAddVo.getCategoryTag());
        article.setTitle(articleAddVo.getTitle());
        article.setContent(articleAddVo.getContent());
        article.setCreateTime(new Date());
        int affect = cmsArticleDao.insert(article);
        logger.info("保存CmsArticle affect:{}, cmsArticle: {}", affect, articleAddVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void editCmsArticleBy(CmsArticleEditVo editVo) {
        Long articleId = editVo.getId();
        CmsArticle article = cmsArticleComponent.queryArticle(articleId);
        if (article == null) {
            throw new BusinessException("当前文章已不存在");
        }

        Integer categoryTag = editVo.getCategoryTag();
        if (categoryTag != null) {
            CmsArticleCategory category = cmsArticleComponent.queryArticleCategory(categoryTag);
            AssertUtils.notNull(category, "文章分类标签不存在");
        }
        CmsArticle updater = new CmsArticle();
        updater.setCategoryTag(editVo.getCategoryTag());
        updater.setTitle(editVo.getTitle());
        updater.setContent(editVo.getContent());
        updater.setModifyTime(new Date());
        int affect = cmsArticleDao.updateById(updater);
        logger.info("更新CmsArticle affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsArticleById(Long id) {
        CmsArticle article = cmsArticleComponent.queryArticle(id);
        if (article == null) {
            throw new BusinessException("当前文章已不存在");
        }

        int affect = cmsArticleDao.deleteById(id);
        logger.info("删除CmsArticle affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsArticleVo> queryPagingResult(CmsArticleQueryVo queryVo, Paging paging) {
        PagingResult<CmsArticleVo> pagingResult = PagingResult.create(paging);

        CmsArticleQuery query = new CmsArticleQuery();
        queryVo.setCategoryTag(queryVo.getCategoryTag());
        queryVo.setTitle(queryVo.getTitle());
        queryVo.setEltCreateTime(queryVo.getEltCreateTime());
        queryVo.setEgtCreateTime(queryVo.getEgtCreateTime());
        queryVo.setEltModifyTime(queryVo.getEltModifyTime());
        queryVo.setEgtModifyTime(queryVo.getEgtModifyTime());
        Long count = cmsArticleDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsArticle> cmsArticleList = cmsArticleDao.selectListByParam(query);
            pagingResult.setRows(cmsArticleList, this::convertCmsArticleVo);
        }

        return pagingResult;
    }
}