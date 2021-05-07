package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemGenerateVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanWorkTypeEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.model.wrapper.PairTuple;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanAllocationDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanItemDao;
import com.fudanuniversity.cms.repository.entity.*;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;
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
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@Service
public class CmsStudyPlanAllocationServiceImpl implements CmsStudyPlanAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanAllocationServiceImpl.class);

    @Resource
    private CmsStudyPlanAllocationDao cmsStudyPlanAllocationDao;

    @Resource
    private CmsStudyPlanItemDao cmsStudyPlanItemDao;

    @Resource
    private CmsStudyPlanComponent cmsStudyPlanComponent;

    @Resource
    private CmsUserComponent cmsUserComponent;

//        int affect = cmsStudyPlanAllocationDao.insert(cmsStudyPlanAllocation);
//        logger.info("保存CmsStudyPlanAllocation affect:{}, cmsStudyPlanAllocation: {}", affect, cmsStudyPlanAllocation);
//        AssertUtils.state(affect == 1);
//        CmsStudyPlanAllocation updater = new CmsStudyPlanAllocation();
//        int affect = cmsStudyPlanAllocationDao.updateById(updater);
//        logger.info("更新CmsStudyPlanAllocation affect:{}, updater: {}", affect, updater);
//        AssertUtils.state(affect == 1);

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

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsStudyPlanAllocationById(Long id) {
        int affect = cmsStudyPlanAllocationDao.deleteById(id);
        logger.info("删除CmsStudyPlanAllocation affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsStudyPlanAllocationVo> queryPagingResult(CmsStudyPlanAllocationQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanAllocationVo> pagingResult = PagingResult.create(paging);

        CmsStudyPlanAllocationQuery query = new CmsStudyPlanAllocationQuery();
        query.setId(queryVo.getId());
        query.setUserId(queryVo.getUserId());
        query.setPlanId(queryVo.getPlanId());
        query.setEltCreateTime(queryVo.getEltCreateTime());
        query.setEgtCreateTime(queryVo.getEgtCreateTime());
        query.setEltModifyTime(queryVo.getEltModifyTime());
        query.setEgtModifyTime(queryVo.getEgtModifyTime());
        Long count = cmsStudyPlanAllocationDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsStudyPlanAllocation> allocationList = cmsStudyPlanAllocationDao.selectListByParam(query);
            pagingResult.setRows(allocationList, allocation -> {
                CmsStudyPlanAllocationVo allocationVo = new CmsStudyPlanAllocationVo();
                return allocationVo;
            });
        }

        return pagingResult;
    }
}