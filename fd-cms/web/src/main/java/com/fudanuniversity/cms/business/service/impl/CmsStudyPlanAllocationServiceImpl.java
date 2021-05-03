package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanAllocationDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;
import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanAllocationService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Service
public class CmsStudyPlanAllocationServiceImpl implements CmsStudyPlanAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanAllocationServiceImpl.class);

    @Resource
    private CmsStudyPlanAllocationDao cmsStudyPlanAllocationDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsStudyPlanAllocation(CmsStudyPlanAllocation cmsStudyPlanAllocation) {
        //TODO 校验与赋值映射

        int affect = cmsStudyPlanAllocationDao.insert(cmsStudyPlanAllocation);
        logger.info("保存CmsStudyPlanAllocation affect:{}, cmsStudyPlanAllocation: {}", affect, cmsStudyPlanAllocation);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsStudyPlanAllocationById(CmsStudyPlanAllocation cmsStudyPlanAllocation) {
        CmsStudyPlanAllocation updater = new CmsStudyPlanAllocation();
        //TODO 值映射校验与赋值映射

        int affect = cmsStudyPlanAllocationDao.updateById(updater);
        logger.info("更新CmsStudyPlanAllocation affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsStudyPlanAllocationById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsStudyPlanAllocationDao.deleteById(id);
        logger.info("删除CmsStudyPlanAllocation affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsStudyPlanAllocation> queryPagingResult(CmsStudyPlanAllocationQuery query) {
        PagingResult<CmsStudyPlanAllocation> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsStudyPlanAllocationDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(SortColumn.create("create_at", SortMode.DESC));
            List<CmsStudyPlanAllocation> cmsStudyPlanAllocationList = cmsStudyPlanAllocationDao.selectListByParam(query);
            pagingResult.setRows(cmsStudyPlanAllocationList);
        }

        return pagingResult;
    }
}