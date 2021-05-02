package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsArticleDao;
import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;
import com.fudanuniversity.cms.business.service.CmsArticleService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsArticleService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsArticleServiceImpl implements CmsArticleService {

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleServiceImpl.class);

    @Resource
    private CmsArticleDao cmsArticleDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsArticle(CmsArticle cmsArticle) {
        //TODO 校验与赋值映射

        int affect = cmsArticleDao.insert(cmsArticle);
        logger.info("保存CmsArticle affect:{}, cmsArticle: {}", affect, cmsArticle);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsArticleById(CmsArticle cmsArticle) {
        CmsArticle updater = new CmsArticle();
        //TODO 值映射校验与赋值映射

        int affect = cmsArticleDao.updateById(updater);
        logger.info("更新CmsArticle affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsArticleById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsArticleDao.deleteById(id);
        logger.info("删除CmsArticle affect:{}, id: {}", affect, id);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsArticle> queryPagingResultByParam(CmsArticleQuery query) {
        PagingResult<CmsArticle> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsArticleDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsArticle> cmsArticleList = cmsArticleDao.selectListByParam(query);
            pagingResult.setRows(cmsArticleList);
        }

        return pagingResult;
    }
}