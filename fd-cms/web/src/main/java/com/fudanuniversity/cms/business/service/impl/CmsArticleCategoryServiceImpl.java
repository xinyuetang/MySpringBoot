package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.ArticleComponent;
import com.fudanuniversity.cms.business.service.CmsArticleCategoryService;
import com.fudanuniversity.cms.business.vo.article.CmsArticleCategoryAddVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleCategoryQueryVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleCategoryVo;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsArticleCategoryDao;
import com.fudanuniversity.cms.repository.entity.CmsArticleCategory;
import com.fudanuniversity.cms.repository.query.CmsArticleCategoryQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CmsArticleCategoryService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsArticleCategoryServiceImpl implements CmsArticleCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleCategoryServiceImpl.class);

    @Resource
    private CmsArticleCategoryDao cmsArticleCategoryDao;

    @Resource
    private ArticleComponent articleComponent;

    /**
     * 保存处理
     */
    @Override
    public void addArticleCategory(CmsArticleCategoryAddVo categoryAddVo) {
        Integer tag = categoryAddVo.getTag();
        CmsArticleCategory exitsCategory = articleComponent.queryArticleCategory(tag);
        if (exitsCategory != null) {
            throw new BusinessException("文章分类tag[" + tag + "]已经存在");
        }
        CmsArticleCategory category = new CmsArticleCategory();
        category.setTag(tag);
        category.setName(categoryAddVo.getName());
        category.setCreateTime(new Date());
        int affect = cmsArticleCategoryDao.insert(category);
        logger.info("保存CmsArticleCategory affect:{}, categoryAddVo: {}", affect, categoryAddVo);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void deleteArticleCategoryById(Long id) {
        CmsArticleCategory exitsCategory = articleComponent.queryArticleCategory(id);
        if (exitsCategory == null) {
            throw new BusinessException("文章分类不存在");
        }

        int affect = cmsArticleCategoryDao.deleteById(id);
        logger.info("删除CmsArticleCategory affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 查询数据列表
     */
    @Override
    public List<CmsArticleCategoryVo> listArticleCategories(CmsArticleCategoryQueryVo queryVo) {
        CmsArticleCategoryQuery query = new CmsArticleCategoryQuery();
        query.setId(queryVo.getId());
        query.setTag(queryVo.getTag());
        query.setName(queryVo.getName());

        //query.setSorts(new SortColumn("create_at", SortMode.DESC));
        List<CmsArticleCategory> categories = cmsArticleCategoryDao.selectListByParam(query);

        return categories.stream().map(category -> {
            CmsArticleCategoryVo categoryVo = new CmsArticleCategoryVo();
            categoryVo.setId(category.getId());
            categoryVo.setTag(category.getTag());
            categoryVo.setName(category.getName());
            categoryVo.setCreateTime(category.getCreateTime());
            categoryVo.setModifyTime(category.getModifyTime());
            return categoryVo;
        }).collect(Collectors.toList());
    }


}