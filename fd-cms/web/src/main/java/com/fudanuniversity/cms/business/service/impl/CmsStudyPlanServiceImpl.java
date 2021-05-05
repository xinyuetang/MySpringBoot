package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.enums.StudyPlanWorkTypeEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanWork;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Date referenceDate = addVo.getReferenceDate();
        if (referenceDate == null) {
            referenceDate = DateExUtils.eval(enrollYear, 8, 1);//默认每学年9.1日开始
        }
        cmsStudyPlan.setReferenceDate(referenceDate);
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
                studyPlanVo.setReferenceDate(plan.getReferenceDate());
                studyPlanVo.setName(plan.getName());
                studyPlanVo.setCreateTime(plan.getCreateTime());
                studyPlanVo.setModifyTime(plan.getModifyTime());
                return studyPlanVo;
            });
        }

        return pagingResult;
    }

    @Override
    public CmsStudyPlanOverviewVo overviewCmsStudyPlan(Long planId) {
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan, "培养计划[" + planId + "]不存在");

        CmsStudyPlanOverviewVo overviewVo = new CmsStudyPlanOverviewVo();
        overviewVo.setId(studyPlan.getId());
        overviewVo.setName(studyPlan.getName());
        overviewVo.setEnrollYear(studyPlan.getEnrollYear());
        overviewVo.setReferenceDate(studyPlan.getReferenceDate());
        overviewVo.setCreateTime(studyPlan.getCreateTime());
        overviewVo.setModifyTime(studyPlan.getModifyTime());

        List<CmsStudyPlanStage> stages = cmsStudyPlanComponent.queryStudyPlanStageByPlanId(planId);
        List<CmsStudyPlanStageOverviewVo> stageOverviewList = convertStageOverviewVoList(studyPlan, stages);
        overviewVo.setStages(stageOverviewList);

        return overviewVo;
    }

    private List<CmsStudyPlanStageOverviewVo> convertStageOverviewVoList(
            CmsStudyPlan studyPlan, List<CmsStudyPlanStage> stages) {
        if (CollectionUtils.isNotEmpty(stages)) {
            List<CmsStudyPlanStageOverviewVo> retList = Lists.newArrayList();
            List<Long> stageIds = Lists.transform(stages, CmsStudyPlanStage::getId);
            List<CmsStudyPlanWork> planWorks = cmsStudyPlanComponent.queryStudyPlanWorks(stageIds);
            Map<Long, Map<Integer, List<CmsStudyPlanWork>>> planStageWorkMap = convertCmsStudyPlanWorkMap(planWorks);
            Date basicDate = studyPlan.getReferenceDate();
            for (CmsStudyPlanStage stage : stages) {
                CmsStudyPlanStageOverviewVo stageOverviewVo = new CmsStudyPlanStageOverviewVo();
                stageOverviewVo.setId(stage.getId());
                stageOverviewVo.setPlanId(stage.getPlanId());
                stageOverviewVo.setTerm(stage.getTerm());
                stageOverviewVo.setIndex(stage.getIndex());
                Integer workDays = stage.getWorkDays();
                stageOverviewVo.setWorkDays(workDays);
                basicDate = DateUtils.addDays(basicDate, workDays);
                stageOverviewVo.setDeadline(basicDate);
                stageOverviewVo.setCreateTime(stage.getCreateTime());
                stageOverviewVo.setModifyTime(stage.getModifyTime());
                //任务
                Long stageId = stage.getId();
                Map<Integer, List<CmsStudyPlanWork>> workTypeMap = planStageWorkMap.get(stageId);
                List<CmsStudyPlanWork> commonWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Common.getCode());
                List<CmsStudyPlanWorkOverviewVo> commonWorkOverviewVoList = convertWorkOverviewVo(commonWorks);
                stageOverviewVo.setCommonWorks(commonWorkOverviewVoList);

                List<CmsStudyPlanWork> keshuoWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Keshuo.getCode());
                List<CmsStudyPlanWorkOverviewVo> keshuoWorkOverviewVoList = convertWorkOverviewVo(keshuoWorks);
                stageOverviewVo.setKeshuoWorks(keshuoWorkOverviewVoList);

                List<CmsStudyPlanWork> academicWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Academic.getCode());
                List<CmsStudyPlanWorkOverviewVo> academicWorkOverviewVoList = convertWorkOverviewVo(academicWorks);
                stageOverviewVo.setAcademicWorks(academicWorkOverviewVoList);

                List<CmsStudyPlanWork> synthesizingWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Synthesizing.getCode());
                List<CmsStudyPlanWorkOverviewVo> synthesizingWorkOverviewVoList = convertWorkOverviewVo(synthesizingWorks);
                stageOverviewVo.setSynthesizingWorks(synthesizingWorkOverviewVoList);

                List<CmsStudyPlanWork> technologyWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Technology.getCode());
                List<CmsStudyPlanWorkOverviewVo> technologyWorkOverviewVoList = convertWorkOverviewVo(technologyWorks);
                stageOverviewVo.setTechnologyWorks(technologyWorkOverviewVoList);
                retList.add(stageOverviewVo);
            }
            return retList;
        }
        return Collections.emptyList();
    }

    private List<CmsStudyPlanWorkOverviewVo> convertWorkOverviewVo(List<CmsStudyPlanWork> commonWorks) {
        if (CollectionUtils.isNotEmpty(commonWorks)) {
            List<CmsStudyPlanWorkOverviewVo> workOverviewVoList = Lists.newArrayList();
            commonWorks.forEach(commonWork -> {
                CmsStudyPlanWorkOverviewVo workOverviewVo = new CmsStudyPlanWorkOverviewVo();
                workOverviewVo.setId(commonWork.getId());
                workOverviewVo.setPlanId(commonWork.getPlanId());
                workOverviewVo.setPlanStageId(commonWork.getPlanStageId());
                workOverviewVo.setWorkType(commonWork.getWorkType());
                workOverviewVo.setIndex(commonWork.getIndex());
                workOverviewVo.setName(commonWork.getName());
                workOverviewVo.setCreateTime(commonWork.getCreateTime());
                workOverviewVo.setModifyTime(commonWork.getModifyTime());
                workOverviewVoList.add(workOverviewVo);
            });
            return workOverviewVoList;
        }
        return Collections.emptyList();
    }

    /**
     * <pre>
     *   泛型描述：Map<stageId, Map<workType, List<CmsStudyPlanWork>>>
     * </pre>
     */
    private Map<Long, Map<Integer, List<CmsStudyPlanWork>>> convertCmsStudyPlanWorkMap(List<CmsStudyPlanWork> planWorks) {
        Map<Long, Map<Integer, List<CmsStudyPlanWork>>> retMap = Maps.newLinkedHashMap();
        //所有数据按照PlanStageId，WorkType，Index排序
        planWorks.sort((o1, o2) -> {
            int planStageIdComparison = o1.getPlanStageId().compareTo(o2.getPlanStageId());
            if (planStageIdComparison != 0) {
                return planStageIdComparison < 0 ? -1 : 1;
            }
            int workTypeComparison = o1.getWorkType().compareTo(o2.getWorkType());
            if (workTypeComparison != 0) {
                return workTypeComparison < 0 ? -1 : 1;
            }
            return o1.getIndex().compareTo(o2.getIndex());
        });
        for (CmsStudyPlanWork planWork : planWorks) {
            Long planStageId = planWork.getPlanStageId();
            Integer workType = planWork.getWorkType();
            //如果retMap中planStageId(key)对应的Map(value)为null，则初始化workTypeMap
            Map<Integer, List<CmsStudyPlanWork>> workTypeMap =
                    retMap.computeIfAbsent(planStageId, k -> Maps.newLinkedHashMap());
            //如果workTypeMap中workType(key)对应的List(value)为null，则初始化workList
            List<CmsStudyPlanWork> workList = workTypeMap.computeIfAbsent(workType, k -> Lists.newArrayList());
            workList.add(planWork);
        }
        return retMap;
    }
}