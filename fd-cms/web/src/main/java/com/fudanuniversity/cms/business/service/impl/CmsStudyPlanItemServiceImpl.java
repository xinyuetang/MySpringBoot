package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanItemService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanAllocationStatusEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanWorkTypeEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.wrapper.PairTuple;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanItemDao;
import com.fudanuniversity.cms.repository.entity.*;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CmsStudyPlanAllocationService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
@Service
public class CmsStudyPlanItemServiceImpl implements CmsStudyPlanItemService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanItemServiceImpl.class);

    @Resource
    private CmsStudyPlanItemDao cmsStudyPlanItemDao;

    @Resource
    private CmsStudyPlanComponent cmsStudyPlanComponent;

    @Resource
    private CmsUserComponent cmsUserComponent;

    /**
     * 为学生分配生成培养计划
     */
    @Override
    public void generateUserAllocations(CmsStudyPlanItemGenerateVo generateVo) {
        Long planId = generateVo.getPlanId();
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan, "培养计划[" + planId + "]不存在");

        List<Long> userIds = generateVo.getUserIds();
        AssertUtils.notEmpty(userIds, "生成培养计划的用户不能为空");
        Map<Long, CmsUser> userMap = cmsUserComponent.queryExitsUserMap(userIds);

        List<CmsStudyPlanStage> planStages = cmsStudyPlanComponent.queryStudyPlanStageByPlanId(planId);
        AssertUtils.notEmpty(planStages, "培养计划[" + planId + "]尚未设置阶段目标");
        List<Long> stageIds = Lists.transform(planStages, CmsStudyPlanStage::getId);
        List<CmsStudyPlanWork> planWorks = cmsStudyPlanComponent.queryStudyPlanWorks(stageIds);
        AssertUtils.notEmpty(planStages, "培养计划[" + planId + "]尚未设置任何任务");

        //Key:CmsStudyPlanWorkId
        Map<Long, CmsStudyPlanWork> idWorkMap = planWorks
                .stream().collect(Collectors.toMap(CmsStudyPlanWork::getId, Function.identity()));
        //Map<stageId, Map<workType, List<CmsStudyPlanWork>>>
        Map<Long, Map<Integer, List<CmsStudyPlanWork>>> stageWorkMap
                = cmsStudyPlanComponent.convertCmsStudyPlanWorkMap(planWorks);
        //Key:stageId, Value: PairTuple，存储每个阶段开始与结束时间
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
            cmsStudyPlanItemDao.markAsDeletedByIds(userDeletedAllocationIdList);
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
    public void editAllocation(CmsStudyPlanItemEditVo editVo) {
        Long allocationId = editVo.getId();
        CmsStudyPlanItem allocation = cmsStudyPlanComponent.queryStudyPlanAllocationById(allocationId);
        AssertUtils.notNull(allocation, "培养计划安排[" + allocationId + "]不存在");

        CmsStudyPlanItem updater = new CmsStudyPlanItem();
        updater.setId(allocationId);
        Integer finished = editVo.getFinished();
        Date current = new Date();
        if (finished == null) {
            updater.setFinishedDate(allocation.getFinishedDate());//finished为null不更新完成日期
        } else {
            updater.setFinished(finished);
            if (BooleanEnum.isTrue(finished)) {
                updater.setFinishedDate(current);
            }
        }
        Date delayDate = editVo.getDelayDate();
        if (delayDate != null) {
            Date planWorkEndDate = allocation.getPlanWorkEndDate();
            int delayDays = DateExUtils.evalCrossDays(planWorkEndDate, delayDate);
            updater.setPlanWorkDelay(delayDays);
        }
        updater.setRemark(updater.getRemark());
        updater.setModifyTime(new Date());
        int affect = cmsStudyPlanItemDao.updateById(updater);
        logger.info("更新CmsStudyPlanAllocation affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void deleteAllocationById(Long id) {
        int affect = cmsStudyPlanItemDao.deleteById(id);
        logger.info("删除CmsStudyPlanAllocation affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    @Override
    public PagingResult<CmsStudyPlanItemVo> queryPagingResult(CmsStudyPlanItemQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanItemVo> pagingResult = PagingResult.create(paging);

        CmsStudyPlanItemQuery query = new CmsStudyPlanItemQuery();
        Long count = cmsStudyPlanItemDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            //query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsStudyPlanItem> cmsStudyPlanItemList = cmsStudyPlanItemDao.selectListByParam(query);
            pagingResult.setRows(cmsStudyPlanItemList, allocation -> {
                CmsStudyPlanItemVo allocationVo = new CmsStudyPlanItemVo();
                allocationVo.setId(allocation.getId());
                allocationVo.setUserId(allocation.getUserId());
                allocationVo.setPlanId(allocation.getPlanId());
                allocationVo.setPlanStageId(allocation.getPlanStageId());
                allocationVo.setPlanWorkId(allocation.getPlanWorkId());
                allocationVo.setPlanWorkStartDate(allocation.getPlanWorkStartDate());
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
            });
        }

        return pagingResult;
    }

    @Override
    public void editUserAllocation(Long userId, CmsStudyPlanItemUserEditVo userEditVo) {
        Long allocationId = userEditVo.getId();
        CmsStudyPlanItem allocation = cmsStudyPlanComponent.queryUserStudyPlanAllocation(userId, allocationId);
        AssertUtils.notNull(allocation, "培养计划安排[" + allocationId + "]不存在");

        CmsStudyPlanItem updater = new CmsStudyPlanItem();
        updater.setId(allocationId);
        Integer finished = userEditVo.getFinished();
        Date current = new Date();
        if (finished == null) {
            updater.setFinishedDate(allocation.getFinishedDate());//finished为null不更新完成日期
        } else {
            updater.setFinished(finished);
            if (BooleanEnum.isTrue(finished)) {
                updater.setFinishedDate(current);
            }
        }
        updater.setRemark(updater.getRemark());
        updater.setModifyTime(current);
        int affect = cmsStudyPlanItemDao.updateById(updater);
        logger.info("更新CmsStudyPlanAllocation affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    public CmsStudyPlanItemInfoVo queryUserAllocationInfo(Long userId, Long planId) {
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan, "培养计划[" + planId + "]不存在");

        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.singletonQuery();
        List<CmsStudyPlanInfo> infoList = cmsStudyPlanItemDao.selectInfoByParam(query);
        CmsStudyPlanInfo planInfo = null;
        if (CollectionUtils.isNotEmpty(infoList)) {
            planInfo = infoList.get(0);
        }

        CmsStudyPlanItemInfoVo allocationInfoVo = new CmsStudyPlanItemInfoVo();
        allocationInfoVo.setUserId(userId);
        allocationInfoVo.setPlanId(planId);
        allocationInfoVo.setTotal(planInfo == null ? 0 : planInfo.getTotal());
        allocationInfoVo.setUnderway(planInfo == null ? 0 : planInfo.getUnderway());
        allocationInfoVo.setDelayUnfinished(planInfo == null ? 0 : planInfo.getDelayUnfinished());
        allocationInfoVo.setOvertimeUnfinished(planInfo == null ? 0 : planInfo.getOvertimeUnfinished());
        allocationInfoVo.setNormalFinished(planInfo == null ? 0 : planInfo.getNormalFinished());
        allocationInfoVo.setDelayFinished(planInfo == null ? 0 : planInfo.getDelayFinished());
        allocationInfoVo.setOvertimeUnfinished(planInfo == null ? 0 : planInfo.getOvertimeUnfinished());
        return allocationInfoVo;
    }
}