package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanStageDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanWorkDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanWork;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanStageQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanWorkQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Xinyue.Tang at 2021-05-05 19:23:04
 */
@Component
public class CmsStudyPlanComponent {

    @Resource
    private CmsStudyPlanDao cmsStudyPlanDao;

    @Resource
    private CmsStudyPlanStageDao cmsStudyPlanStageDao;

    @Resource
    private CmsStudyPlanWorkDao cmsStudyPlanWorkDao;

    public CmsStudyPlan queryStudyPlanById(Long planId) {
        AssertUtils.notNull(planId);
        CmsStudyPlanQuery query = CmsStudyPlanQuery.singletonQuery();
        query.setId(planId);
        List<CmsStudyPlan> studyPlans = cmsStudyPlanDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(studyPlans)) {
            return studyPlans.get(0);
        }
        return null;
    }

    public CmsStudyPlan queryStudyPlanByEnrollYear(Integer enrollYear) {
        AssertUtils.notNull(enrollYear);
        CmsStudyPlanQuery query = CmsStudyPlanQuery.singletonQuery();
        query.setEnrollYear(enrollYear);
        List<CmsStudyPlan> studyPlans = cmsStudyPlanDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(studyPlans)) {
            return studyPlans.get(0);
        }
        return null;
    }

    public CmsStudyPlanStage queryStudyPlanStageById(Long stageId) {
        AssertUtils.notNull(stageId);
        CmsStudyPlanStageQuery query = CmsStudyPlanStageQuery.singletonQuery();
        query.setId(stageId);
        List<CmsStudyPlanStage> stages = cmsStudyPlanStageDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(stages)) {
            return stages.get(0);
        }
        return null;
    }

    public List<CmsStudyPlanStage> queryStudyPlanStageByPlanId(Long planId) {
        AssertUtils.notNull(planId);
        CmsStudyPlanStageQuery query = CmsStudyPlanStageQuery.listQuery();
        query.setPlanId(planId);
        return cmsStudyPlanStageDao.selectListByParam(query);
    }

    public Map<Long, CmsStudyPlanStage> queryStudyPlanStageMapByPlanId(List<Long> stageIds) {
        AssertUtils.notEmpty(stageIds);
        CmsStudyPlanStageQuery query = CmsStudyPlanStageQuery.listQuery();
        query.setIdList(stageIds);
        List<CmsStudyPlanStage> stages = cmsStudyPlanStageDao.selectListByParam(query);
        return stages.stream().collect(Collectors.toMap(CmsStudyPlanStage::getId, Function.identity()));
    }

    public CmsStudyPlanWork queryStudyPlanWorkById(Long workId) {
        AssertUtils.notNull(workId);
        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.singletonQuery();
        query.setId(workId);
        List<CmsStudyPlanWork> works = cmsStudyPlanWorkDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(works)) {
            return works.get(0);
        }
        return null;
    }

    public List<CmsStudyPlanWork> queryStudyPlanWorks(Long stageId, Integer workType) {
        AssertUtils.notNull(stageId);
        AssertUtils.notNull(workType);
        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.listQuery();
        query.setPlanStageId(stageId);
        query.setWorkType(workType);
        return cmsStudyPlanWorkDao.selectListByParam(query);
    }
}
