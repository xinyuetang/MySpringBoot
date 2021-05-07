package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanWorkTypeEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.model.wrapper.PairTuple;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.fudanuniversity.cms.commons.validation.group.Delete;
import com.fudanuniversity.cms.repository.dao.*;
import com.fudanuniversity.cms.repository.entity.*;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private CmsStudyPlanStageDao cmsStudyPlanStageDao;

    @Resource
    private CmsStudyPlanWorkDao cmsStudyPlanWorkDao;

    @Resource
    private CmsStudyPlanAllocationDao cmsStudyPlanAllocationDao;

    @Resource
    private CmsStudyPlanItemDao cmsStudyPlanItemDao;

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
        cmsStudyPlan.setVersion(0L);
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

        cmsStudyPlanStageDao.deleteByPlanId(planId);
        cmsStudyPlanWorkDao.deleteByPlanId(planId);
        cmsStudyPlanAllocationDao.deleteByPlanId(planId);
        cmsStudyPlanItemDao.deleteByPlanId(planId);
        cmsStudyPlanDao.deleteById(planId);
        logger.info("删除CmsStudyPlan  id: {}", planId);
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
                studyPlanVo.setVersion(plan.getVersion());
                studyPlanVo.setCreateTime(plan.getCreateTime());
                studyPlanVo.setModifyTime(plan.getModifyTime());
                return studyPlanVo;
            });
        }

        return pagingResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void assignCmsStudyPlan(CmsStudyPlanAssignVo assignVo) {
        Long planId = assignVo.getPlanId();
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan, "培养计划[" + planId + "]不存在");

        List<Long> userIds = assignVo.getUserIds();
        AssertUtils.notEmpty(userIds, "生成培养计划的用户不能为空");
        Map<Long, CmsUser> userMap = cmsUserComponent.queryExitsUserMap(userIds);

        List<CmsStudyPlanStage> planStages = cmsStudyPlanComponent.queryStudyPlanStageByPlanId(planId);
        AssertUtils.notEmpty(planStages, "培养计划[" + planId + "]尚未设置阶段目标");
        List<Long> stageIds = Lists.transform(planStages, CmsStudyPlanStage::getId);
        List<CmsStudyPlanWork> planWorks = cmsStudyPlanComponent.queryStudyPlanWorks(stageIds);
        AssertUtils.notEmpty(planStages, "培养计划[" + planId + "]尚未设置任何任务");
        //1. StudyPlanAllocation
        List<CmsStudyPlanAllocation> allocations = Lists.newArrayListWithCapacity(userIds.size());
        for (Long userId : userIds) {
            CmsStudyPlanAllocation allocation = new CmsStudyPlanAllocation();
            allocation.setUserId(userId);
            allocation.setPlanId(studyPlan.getId());
            allocation.setPlanVersion(studyPlan.getVersion());
            allocation.setDeleted(DeletedEnum.Normal.getCode().longValue());
            Date current = new Date();
            allocation.setCreateTime(current);
            allocation.setModifyTime(current);
            allocations.add(allocation);
        }
        cmsStudyPlanAllocationDao.bulkUpsert(allocations);
        //2. CmsStudyPlanItem
        //idWorkMap Key:CmsStudyPlanWorkId
        Map<Long, CmsStudyPlanWork> idWorkMap = planWorks
                .stream().collect(Collectors.toMap(CmsStudyPlanWork::getId, Function.identity()));
        //stageWorkMap Map<stageId, Map<workType, List<CmsStudyPlanWork>>>
        Map<Long, Map<Integer, List<CmsStudyPlanWork>>> stageWorkMap
                = cmsStudyPlanComponent.convertCmsStudyPlanWorkMap(planWorks);
        //stageDateMap Key:stageId, Value: PairTuple，存储每个阶段开始与结束时间
        Map<Long, PairTuple<Date, Date>> stageDateMap = Maps.newLinkedHashMap();
        Date startDate = studyPlan.getReferenceDate();
        for (CmsStudyPlanStage stage : planStages) {
            Date endDate = stage.getEndDate();
            stageDateMap.put(stage.getId(), PairTuple.create(startDate, endDate));
            startDate = endDate;
        }

        //userIds可能较多分段处理
        List<List<Long>> partitions = Lists.partition(userIds, 10);
        partitions.forEach(partition -> {
            //TODO 最好开启一个新的内置事务操作
            List<CmsStudyPlanItem> userAllocationList = cmsStudyPlanComponent.queryStudyPlanAllocationByUserIds(userIds);
            /*  新的培养计划可能删除了部分任务CmsStudyPlanWork，但是先前已经有部分用户生成了培养计划，
                再次为同一个用户生成任务培养计划，需要删除用户中对应已不存在的任务，
                仍然存在的任务出发更新（参照MySQL语法：ON DUPLICATE KEY UPDATE）*/
            List<Long> userDeletedAllocationIdList = Lists.newArrayList();
            for (CmsStudyPlanItem userExitsAllocation : userAllocationList) {
                if (!idWorkMap.containsKey(userExitsAllocation.getPlanWorkId())) {//用户当前存在培养计划已删除的任务
                    userDeletedAllocationIdList.add(userExitsAllocation.getId());
                }
            }
            cmsStudyPlanItemDao.deleteByIds(userDeletedAllocationIdList);
            //新增/更新生成用户培养计划
            List<CmsStudyPlanItem> planAllocations = Lists.newArrayList();
            for (Long userId : userIds) {
                CmsUser cmsUser = userMap.get(userId);
                for (CmsStudyPlanStage planStage : planStages) {
                    Map<Integer, List<CmsStudyPlanWork>> workTypeMap = stageWorkMap.get(planStage.getId());
                    if (MapUtils.isNotEmpty(workTypeMap)) {
                        List<CmsStudyPlanWork> commonWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Common.getCode());
                        if (CollectionUtils.isNotEmpty(commonWorks)) {
                            for (CmsStudyPlanWork commonWork : commonWorks) {
                                CmsStudyPlanItem allocation = createCmsStudyPlanAllocation(
                                        cmsUser, studyPlan, planStage, commonWork, stageDateMap);
                                planAllocations.add(allocation);
                            }
                        }
                        if (BooleanEnum.isTrue(cmsUser.getKeshuo())) {
                            List<CmsStudyPlanWork> keshuoWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Keshuo.getCode());
                            if (CollectionUtils.isNotEmpty(keshuoWorks)) {
                                for (CmsStudyPlanWork keshuoWork : keshuoWorks) {
                                    CmsStudyPlanItem allocation = createCmsStudyPlanAllocation(
                                            cmsUser, studyPlan, planStage, keshuoWork, stageDateMap);
                                    planAllocations.add(allocation);
                                }
                            }
                        }
                        StudyPlanWorkTypeEnum studyWorkTypeEnum = StudyPlanWorkTypeEnum.studyTypeOf(cmsUser.getStudyType());
                        if (studyWorkTypeEnum != null) {
                            List<CmsStudyPlanWork> studyTypeWorks = workTypeMap.get(studyWorkTypeEnum.getCode());
                            if (CollectionUtils.isNotEmpty(studyTypeWorks)) {
                                for (CmsStudyPlanWork studyTypeWork : studyTypeWorks) {
                                    CmsStudyPlanItem allocation = createCmsStudyPlanAllocation(
                                            cmsUser, studyPlan, planStage, studyTypeWork, stageDateMap);
                                    planAllocations.add(allocation);
                                }
                            }
                        }
                    }
                }
            }
            int affect = cmsStudyPlanItemDao.bulkUpsert(planAllocations);
            logger.info("批量保存CmsStudyPlanAllocation affect:{}, planAllocations: {}", affect, planAllocations);
            AssertUtils.state(affect > 0);
        });
    }

    private CmsStudyPlanItem createCmsStudyPlanAllocation(
            CmsUser cmsUser, CmsStudyPlan studyPlan, CmsStudyPlanStage planStage,
            CmsStudyPlanWork commonWork, Map<Long, PairTuple<Date, Date>> stageDateMap) {
        CmsStudyPlanItem allocation = new CmsStudyPlanItem();
        allocation.setUserId(cmsUser.getId());
        allocation.setPlanId(studyPlan.getId());
        allocation.setPlanStageId(planStage.getId());
        allocation.setPlanWorkId(commonWork.getId());
        @Nonnull PairTuple<Date, Date> tuple = stageDateMap.get(planStage.getId());
        allocation.setPlanWorkStartDate(tuple.getLeft());
        allocation.setPlanWorkEndDate(tuple.getRight());
        allocation.setPlanWorkDelay(0);
        allocation.setFinished(BooleanEnum.False.getCode());
        allocation.setRemark("");
        allocation.setDeleted(DeletedEnum.Normal.getCode().longValue());
        Date current = new Date();
        allocation.setCreateTime(current);
        allocation.setModifyTime(current);
        return allocation;
    }

    @Override
    public CmsStudyPlanOverviewVo queryCmsStudyPlanOverview(Long planId) {
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan, "培养计划[" + planId + "]不存在");

        CmsStudyPlanOverviewVo overviewVo = new CmsStudyPlanOverviewVo();
        overviewVo.setId(studyPlan.getId());
        overviewVo.setName(studyPlan.getName());
        overviewVo.setPlanVersion(studyPlan.getVersion());
        overviewVo.setEnrollYear(studyPlan.getEnrollYear());
        overviewVo.setReferenceDate(studyPlan.getReferenceDate());
        overviewVo.setCreateTime(studyPlan.getCreateTime());
        overviewVo.setModifyTime(studyPlan.getModifyTime());

        List<CmsStudyPlanStage> stages = cmsStudyPlanComponent.queryStudyPlanStageByPlanId(planId);
        List<CmsStudyPlanStageOverviewVo> stageOverviewList = cmsStudyPlanComponent.convertStageOverviewVoList(stages);
        overviewVo.setStages(stageOverviewList);

        return overviewVo;
    }
}