package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private CmsStudyPlanComponent cmsStudyPlanComponent;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsStudyPlan(CmsStudyPlanAddVo addVo) {
        Integer enrollYear = addVo.getEnrollYear();
        CmsStudyPlan exitsStudyPlan = cmsStudyPlanComponent.queryStudyPlanByEnrollYear(enrollYear);
        AssertUtils.isNull(exitsStudyPlan, "已经存在[" + enrollYear + "]年培养计划");

        CmsStudyPlan cmsStudyPlan = new CmsStudyPlan();
        cmsStudyPlan.setEnrollYear(enrollYear);
        cmsStudyPlan.setName(addVo.getName());
        cmsStudyPlan.setCreateTime(new Date());
        int affect = cmsStudyPlanDao.insert(cmsStudyPlan);
        logger.info("保存CmsStudyPlan affect:{}, cmsStudyPlan: {}", affect, cmsStudyPlan);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void createFullCmsStudyPlan(CmsStudyPlanFullVo fullVo) {

    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsStudyPlanById(CmsStudyPlanUpdateVo updateVo) {
        Long planId = updateVo.getId();
        CmsStudyPlan exitsStudyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(exitsStudyPlan, "培养计划[" + planId + "]不存在");

        CmsStudyPlan updater = new CmsStudyPlan();
        updater.setId(updateVo.getId());
        updater.setName(updateVo.getName());
        updater.setModifyTime(new Date());
        int affect = cmsStudyPlanDao.updateById(updater);
        logger.info("更新CmsStudyPlan affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCmsStudyPlanById(Long planId) {
        CmsStudyPlan exitsStudyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(exitsStudyPlan, "培养计划[" + planId + "]不存在");

        //TODO 删除CmsStudyPlanStage, CmsStudyPlanWork, CmsStudyPlanAllocation
        int affect = cmsStudyPlanDao.deleteById(planId);
        logger.info("删除CmsStudyPlan affect:{}, id: {}", affect, planId);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsStudyPlanVo> queryPagingResult(CmsStudyPlanQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanVo> pagingResult = PagingResult.create(paging);

        CmsStudyPlanQuery query = new CmsStudyPlanQuery();
        query.setId(queryVo.getId());
        query.setEnrollYear(queryVo.getEnrollYear());
        query.setName(queryVo.getName());
        Long count = cmsStudyPlanDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create("enroll_year", SortMode.DESC));
            List<CmsStudyPlan> cmsStudyPlanList = cmsStudyPlanDao.selectListByParam(query);
            pagingResult.setRows(cmsStudyPlanList, plan -> {
                CmsStudyPlanVo studyPlanVo = new CmsStudyPlanVo();
                studyPlanVo.setId(plan.getId());
                studyPlanVo.setEnrollYear(plan.getEnrollYear());
                studyPlanVo.setName(plan.getName());
                studyPlanVo.setCreateTime(plan.getCreateTime());
                studyPlanVo.setModifyTime(plan.getModifyTime());
                return studyPlanVo;
            });
        }

        return pagingResult;
    }
}