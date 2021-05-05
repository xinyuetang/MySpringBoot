package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
@Service
public class CmsStudyPlanServiceImpl implements CmsStudyPlanService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanServiceImpl.class);

    @Resource
    private CmsStudyPlanDao cmsStudyPlanDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsStudyPlan(CmsStudyPlanAddVo addVo) {
        //TODO 校验与赋值映射
        CmsStudyPlan cmsStudyPlan = new CmsStudyPlan();
        int affect = cmsStudyPlanDao.insert(cmsStudyPlan);
        logger.info("保存CmsStudyPlan affect:{}, cmsStudyPlan: {}", affect, cmsStudyPlan);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsStudyPlanById(CmsStudyPlanUpdateVo updateVo) {
        CmsStudyPlan updater = new CmsStudyPlan();
        //TODO 值映射校验与赋值映射

        int affect = cmsStudyPlanDao.updateById(updater);
        logger.info("更新CmsStudyPlan affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsStudyPlanById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsStudyPlanDao.deleteById(id);
        logger.info("删除CmsStudyPlan affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsStudyPlanVo> queryPagingResult(CmsStudyPlanQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanVo> pagingResult = PagingResult.create(paging);

        CmsStudyPlanQuery query = new CmsStudyPlanQuery();
        Long count = cmsStudyPlanDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            //query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsStudyPlan> cmsStudyPlanList = cmsStudyPlanDao.selectListByParam(query);
            pagingResult.setRows(cmsStudyPlanList, plan -> {
                return null;
            });
        }

        return pagingResult;
    }
}