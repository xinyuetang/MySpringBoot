package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanItemDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanStageDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanWorkDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanItem;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanWork;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanStageQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanWorkQuery;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
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

    @Resource
    private CmsStudyPlanItemDao cmsStudyPlanItemDao;

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
        query.setSorts(SortColumn.create("term", SortMode.ASC), SortColumn.create("index", SortMode.ASC));
        return cmsStudyPlanStageDao.selectListByParam(query);
    }

    public List<CmsStudyPlanStage> queryStudyPlanStages(Long planId, Integer term) {
        AssertUtils.notNull(planId);
        CmsStudyPlanStageQuery query = CmsStudyPlanStageQuery.listQuery();
        query.setPlanId(planId);
        query.setTerm(term);
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

    public List<CmsStudyPlanWork> queryStudyPlanWorks(List<Long> stageIds) {
        AssertUtils.notEmpty(stageIds);
        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.listQuery();
        query.setPlanStageIdList(stageIds);
        return cmsStudyPlanWorkDao.selectListByParam(query);
    }

    public List<CmsStudyPlanWork> queryStudyPlanWorks(Long stageId, Integer workType) {
        AssertUtils.notNull(stageId);
        AssertUtils.notNull(workType);
        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.listQuery();
        query.setPlanStageId(stageId);
        query.setWorkType(workType);
        return cmsStudyPlanWorkDao.selectListByParam(query);
    }

    public List<CmsStudyPlanItem> queryStudyPlanAllocationByUserIds(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.listQuery();
            query.setUserIdList(userIds);
            query.setDeleted(DeletedEnum.Normal.getCode().longValue());
            return cmsStudyPlanItemDao.selectListByParam(query);
        }
        return Collections.emptyList();
    }

    public CmsStudyPlanItem queryStudyPlanAllocationById(Long allocationId) {
        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.singletonQuery();
        query.setId(allocationId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        List<CmsStudyPlanItem> allocations = cmsStudyPlanItemDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(allocations)) {
            return allocations.get(0);
        }
        return null;
    }

    public CmsStudyPlanItem queryUserStudyPlanAllocation(Long userId, Long allocationId) {
        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.singletonQuery();
        query.setId(allocationId);
        query.setUserId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        List<CmsStudyPlanItem> allocations = cmsStudyPlanItemDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(allocations)) {
            return allocations.get(0);
        }
        return null;
    }

    /**
     * Key: workId
     */
    public Map<Long, CmsStudyPlanItem> queryUserStudyPlanAllocationMap(Long userId) {
        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.listQuery();
        query.setUserId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        List<CmsStudyPlanItem> allocations = cmsStudyPlanItemDao.selectListByParam(query);
        return allocations.stream().collect(Collectors.toMap(CmsStudyPlanItem::getPlanWorkId, Function.identity()));
    }

    /**
     * <pre>
     *  将StudyPlan下所有关联的work结构化组织起来
     *
     *   泛型描述：Map<stageId, Map<workType, List<CmsStudyPlanWork>>>
     * </pre>
     */
    public Map<Long, Map<Integer, List<CmsStudyPlanWork>>> convertCmsStudyPlanWorkMap(List<CmsStudyPlanWork> planWorks) {
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
