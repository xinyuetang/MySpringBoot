package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsArticleCategoryDao;
import com.fudanuniversity.cms.repository.entity.CmsArticleCategory;
import com.fudanuniversity.cms.repository.query.CmsArticleCategoryQuery;
import com.fudanuniversity.cms.business.service.CmsArticleCategoryService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 保存处理
     */
    @Override
    public void saveCmsArticleCategory(CmsArticleCategory cmsArticleCategory) {
        //TODO 校验与赋值映射

        int affect = cmsArticleCategoryDao.insert(cmsArticleCategory);
        logger.info("保存CmsArticleCategory affect:{}, cmsArticleCategory: {}", affect, cmsArticleCategory);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsArticleCategoryById(CmsArticleCategory cmsArticleCategory) {
        CmsArticleCategory updater = new CmsArticleCategory();
        //TODO 值映射校验与赋值映射

        int affect = cmsArticleCategoryDao.updateById(updater);
        logger.info("更新CmsArticleCategory affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsArticleCategoryById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsArticleCategoryDao.deleteById(id);
        logger.info("删除CmsArticleCategory affect:{}, id: {}", affect, id);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsArticleCategory> queryPagingResultByParam(CmsArticleCategoryQuery query) {
        PagingResult<CmsArticleCategory> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsArticleCategoryDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsArticleCategory> cmsArticleCategoryList = cmsArticleCategoryDao.selectListByParam(query);
            pagingResult.setRows(cmsArticleCategoryList);
        }

        return pagingResult;
    }
}