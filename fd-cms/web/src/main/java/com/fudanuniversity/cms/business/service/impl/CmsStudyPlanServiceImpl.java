package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanAllocationStatusEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanWorkTypeEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanDao;
import com.fudanuniversity.cms.repository.entity.*;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private CmsUserComponent cmsUserComponent;

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
    public CmsStudyPlanOverviewVo queryCmsStudyPlanOverview(Long planId) {
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan, "培养计划[" + planId + "]不存在");

        CmsStudyPlanOverviewVo overviewVo = createCmsStudyPlanOverviewVo(studyPlan);

        List<CmsStudyPlanStage> stages = cmsStudyPlanComponent.queryStudyPlanStageByPlanId(planId);
        List<CmsStudyPlanStageOverviewVo> stageOverviewList = convertStageOverviewVoList(stages);
        overviewVo.setStages(stageOverviewList);

        return overviewVo;
    }

    @Override
    public CmsStudyPlanOverviewVo queryUserCmsStudyPlanOverview(Long userId, Long planId) {
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan, "培养计划[" + planId + "]不存在");

        CmsStudyPlanOverviewVo overviewVo = createCmsStudyPlanOverviewVo(studyPlan);
        CmsUser cmsUser = cmsUserComponent.queryUser(userId);
        AssertUtils.notNull(cmsUser);
        List<CmsStudyPlanStage> stages = cmsStudyPlanComponent.queryStudyPlanStageByPlanId(planId);
        List<CmsStudyPlanStageOverviewVo> stageOverviewList = convertStageOverviewVoList(cmsUser, stages);
        overviewVo.setStages(stageOverviewList);
        return overviewVo;
    }

    private CmsStudyPlanOverviewVo createCmsStudyPlanOverviewVo(CmsStudyPlan studyPlan) {
        CmsStudyPlanOverviewVo overviewVo = new CmsStudyPlanOverviewVo();
        overviewVo.setId(studyPlan.getId());
        overviewVo.setName(studyPlan.getName());
        overviewVo.setEnrollYear(studyPlan.getEnrollYear());
        overviewVo.setReferenceDate(studyPlan.getReferenceDate());
        overviewVo.setCreateTime(studyPlan.getCreateTime());
        overviewVo.setModifyTime(studyPlan.getModifyTime());
        return overviewVo;
    }

    private List<CmsStudyPlanStageOverviewVo> convertStageOverviewVoList(List<CmsStudyPlanStage> stages) {
        if (CollectionUtils.isNotEmpty(stages)) {
            List<CmsStudyPlanStageOverviewVo> retList = Lists.newArrayList();
            List<Long> stageIds = Lists.transform(stages, CmsStudyPlanStage::getId);
            List<CmsStudyPlanWork> planWorks = cmsStudyPlanComponent.queryStudyPlanWorks(stageIds);
            Map<Long, Map<Integer, List<CmsStudyPlanWork>>> planStageWorkMap
                    = cmsStudyPlanComponent.convertCmsStudyPlanWorkMap(planWorks);
            for (CmsStudyPlanStage stage : stages) {
                CmsStudyPlanStageOverviewVo stageOverviewVo = new CmsStudyPlanStageOverviewVo();
                stageOverviewVo.setId(stage.getId());
                stageOverviewVo.setPlanId(stage.getPlanId());
                stageOverviewVo.setTerm(stage.getTerm());
                stageOverviewVo.setIndex(stage.getIndex());
                stageOverviewVo.setEndDate(stage.getEndDate());
                stageOverviewVo.setCreateTime(stage.getCreateTime());
                stageOverviewVo.setModifyTime(stage.getModifyTime());
                //任务
                Long stageId = stage.getId();
                Map<Integer, List<CmsStudyPlanWork>> workTypeMap = planStageWorkMap.get(stageId);
                //公共任务
                List<CmsStudyPlanWork> commonWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Common.getCode());
                List<CmsStudyPlanWorkOverviewVo> commonWorkOverviewVoList = convertWorkOverviewVo(commonWorks);
                stageOverviewVo.setCommonWorks(commonWorkOverviewVoList);
                //科硕任务
                List<CmsStudyPlanWork> keshuoWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Keshuo.getCode());
                List<CmsStudyPlanWorkOverviewVo> keshuoWorkOverviewVoList = convertWorkOverviewVo(keshuoWorks);
                stageOverviewVo.setKeshuoWorks(keshuoWorkOverviewVoList);
                //学术型任务
                List<CmsStudyPlanWork> academicWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Academic.getCode());
                List<CmsStudyPlanWorkOverviewVo> academicWorkOverviewVoList = convertWorkOverviewVo(academicWorks);
                stageOverviewVo.setAcademicWorks(academicWorkOverviewVoList);
                //结合型任务
                List<CmsStudyPlanWork> synthesizingWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Synthesizing.getCode());
                List<CmsStudyPlanWorkOverviewVo> synthesizingWorkOverviewVoList = convertWorkOverviewVo(synthesizingWorks);
                stageOverviewVo.setSynthesizingWorks(synthesizingWorkOverviewVoList);
                //技术型公共任务
                List<CmsStudyPlanWork> technologyWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Technology.getCode());
                List<CmsStudyPlanWorkOverviewVo> technologyWorkOverviewVoList = convertWorkOverviewVo(technologyWorks);
                stageOverviewVo.setTechnologyWorks(technologyWorkOverviewVoList);
                retList.add(stageOverviewVo);
            }
            return retList;
        }
        return Collections.emptyList();
    }

    //用户培养计划任务会有任务的进度信息
    private List<CmsStudyPlanStageOverviewVo> convertStageOverviewVoList(CmsUser cmsUser, List<CmsStudyPlanStage> stages) {
        if (CollectionUtils.isNotEmpty(stages)) {
            List<CmsStudyPlanStageOverviewVo> retList = Lists.newArrayList();
            List<Long> stageIds = Lists.transform(stages, CmsStudyPlanStage::getId);
            List<CmsStudyPlanWork> planWorks = cmsStudyPlanComponent.queryStudyPlanWorks(stageIds);
            Map<Long, Map<Integer, List<CmsStudyPlanWork>>> planStageWorkMap
                    = cmsStudyPlanComponent.convertCmsStudyPlanWorkMap(planWorks);
            Map<Long, CmsStudyPlanItem> allocationMap
                    = cmsStudyPlanComponent.queryUserStudyPlanAllocationMap(cmsUser.getId());
            for (CmsStudyPlanStage stage : stages) {
                CmsStudyPlanStageOverviewVo stageOverviewVo = convertCmsStudyPlanStageOverviewVo(stage);
                //任务
                Long stageId = stage.getId();
                Map<Integer, List<CmsStudyPlanWork>> workTypeMap = planStageWorkMap.get(stageId);
                //公共任务
                List<CmsStudyPlanWork> commonWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Common.getCode());
                List<CmsStudyPlanWorkOverviewVo> commonWorkOverviewVoList = convertWorkOverviewVo(commonWorks, allocationMap);
                stageOverviewVo.setCommonWorks(commonWorkOverviewVoList);
                //科硕任务
                List<CmsStudyPlanWorkOverviewVo> keshuoWorkOverviewVoList = Collections.emptyList();
                if (BooleanEnum.isTrue(cmsUser.getKeshuo())) {
                    List<CmsStudyPlanWork> keshuoWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Keshuo.getCode());
                    keshuoWorkOverviewVoList = convertWorkOverviewVo(keshuoWorks, allocationMap);
                }
                stageOverviewVo.setKeshuoWorks(keshuoWorkOverviewVoList);
                StudyPlanWorkTypeEnum workTypeEnum = StudyPlanWorkTypeEnum.studyTypeOf(cmsUser.getStudyType());
                List<CmsStudyPlanWorkOverviewVo> academicWorkOverviewVoList = Collections.emptyList();
                List<CmsStudyPlanWorkOverviewVo> synthesizingWorkOverviewVoList = Collections.emptyList();
                List<CmsStudyPlanWorkOverviewVo> technologyWorkOverviewVoList = Collections.emptyList();
                if (Objects.equals(StudyPlanWorkTypeEnum.Academic, workTypeEnum)) {//学术型任务
                    List<CmsStudyPlanWork> academicWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Academic.getCode());
                    academicWorkOverviewVoList = convertWorkOverviewVo(academicWorks);
                } else if (Objects.equals(StudyPlanWorkTypeEnum.Synthesizing, workTypeEnum)) {//结合型任务
                    List<CmsStudyPlanWork> synthesizingWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Synthesizing.getCode());
                    synthesizingWorkOverviewVoList = convertWorkOverviewVo(synthesizingWorks, allocationMap);
                } else if (Objects.equals(StudyPlanWorkTypeEnum.Technology, workTypeEnum)) {//技术型公共任务
                    List<CmsStudyPlanWork> technologyWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Technology.getCode());
                    technologyWorkOverviewVoList = convertWorkOverviewVo(technologyWorks, allocationMap);
                }
                stageOverviewVo.setAcademicWorks(academicWorkOverviewVoList);
                stageOverviewVo.setSynthesizingWorks(synthesizingWorkOverviewVoList);
                stageOverviewVo.setTechnologyWorks(technologyWorkOverviewVoList);
                retList.add(stageOverviewVo);
            }
            return retList;
        }
        return Collections.emptyList();
    }

    private CmsStudyPlanStageOverviewVo convertCmsStudyPlanStageOverviewVo(CmsStudyPlanStage stage) {
        CmsStudyPlanStageOverviewVo stageOverviewVo = new CmsStudyPlanStageOverviewVo();
        stageOverviewVo.setId(stage.getId());
        stageOverviewVo.setPlanId(stage.getPlanId());
        stageOverviewVo.setTerm(stage.getTerm());
        stageOverviewVo.setIndex(stage.getIndex());
        stageOverviewVo.setEndDate(stage.getEndDate());
        stageOverviewVo.setCreateTime(stage.getCreateTime());
        stageOverviewVo.setModifyTime(stage.getModifyTime());
        return stageOverviewVo;
    }

    private List<CmsStudyPlanWorkOverviewVo> convertWorkOverviewVo(List<CmsStudyPlanWork> stageWorks) {
        return convertWorkOverviewVo(stageWorks, Collections.emptyMap());
    }

    private List<CmsStudyPlanWorkOverviewVo> convertWorkOverviewVo(
            List<CmsStudyPlanWork> stageWorks, Map<Long, CmsStudyPlanItem> allocationMap) {
        if (CollectionUtils.isNotEmpty(stageWorks)) {
            List<CmsStudyPlanWorkOverviewVo> workOverviewVoList = Lists.newArrayList();
            stageWorks.forEach(commonWork -> {
                CmsStudyPlanWorkOverviewVo workOverviewVo = new CmsStudyPlanWorkOverviewVo();
                workOverviewVo.setId(commonWork.getId());
                workOverviewVo.setPlanId(commonWork.getPlanId());
                workOverviewVo.setPlanStageId(commonWork.getPlanStageId());
                workOverviewVo.setWorkType(commonWork.getWorkType());
                workOverviewVo.setIndex(commonWork.getIndex());
                workOverviewVo.setName(commonWork.getName());
                workOverviewVo.setCreateTime(commonWork.getCreateTime());
                workOverviewVo.setModifyTime(commonWork.getModifyTime());
                CmsStudyPlanItem allocation = allocationMap.get(commonWork.getId());
                if (allocation != null) {
                    CmsStudyPlanItemVo allocationVo = convertCmsStudyPlanAllocationVo(allocation);
                    workOverviewVo.setAllocation(allocationVo);
                }
                workOverviewVoList.add(workOverviewVo);
            });
            return workOverviewVoList;
        }
        return Collections.emptyList();
    }

    private CmsStudyPlanItemVo convertCmsStudyPlanAllocationVo(CmsStudyPlanItem allocation) {
        CmsStudyPlanItemVo allocationVo = new CmsStudyPlanItemVo();
        allocationVo.setId(allocation.getId());
        allocationVo.setUserId(allocation.getUserId());
        allocationVo.setPlanId(allocation.getPlanId());
        allocationVo.setPlanStageId(allocation.getPlanStageId());
        allocationVo.setPlanWorkId(allocation.getPlanWorkId());
        Date planWorkStartDate = allocation.getPlanWorkStartDate();
        allocationVo.setPlanWorkStartDate(planWorkStartDate);
        Date planWorkEndDate = allocation.getPlanWorkEndDate();
        allocationVo.setPlanWorkEndDate(planWorkEndDate);
        Integer planWorkDelay = allocation.getPlanWorkDelay();
        allocationVo.setPlanWorkDelay(planWorkDelay);
        Integer finished = allocation.getFinished();
        allocationVo.setFinished(finished);
        Date finishedDate = allocation.getFinishedDate();
        allocationVo.setFinishedDate(finishedDate);
        StudyPlanAllocationStatusEnum statusEnum = StudyPlanAllocationStatusEnum
                .eval(planWorkEndDate, planWorkDelay, finished, finishedDate);
        allocationVo.setStatus(statusEnum.getCode());
        allocationVo.setRemark(allocation.getRemark());
        allocationVo.setCreateTime(allocation.getCreateTime());
        allocationVo.setModifyTime(allocation.getModifyTime());
        return allocationVo;
    }
}