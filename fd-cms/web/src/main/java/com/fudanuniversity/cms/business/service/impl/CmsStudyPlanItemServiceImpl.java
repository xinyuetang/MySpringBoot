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
import com.fudanuniversity.cms.repository.query.CmsStudyPlanInfo;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;
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



    @Override
    public void editStudyPlanItem(CmsStudyPlanItemEditVo editVo) {
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
    public void deleteStudyPlanItemById(Long id) {
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
    public void editUserStudyPlanItem(Long userId, CmsStudyPlanItemUserEditVo userEditVo) {
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
    public CmsStudyPlanItemInfoVo queryUserStudyPlanItemInfo(Long userId, Long planId) {
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
        allocationInfoVo.setUnfinished(planInfo == null ? 0 : planInfo.getUnfinished());
        allocationInfoVo.setRegularUnfinished(planInfo == null ? 0 : planInfo.getRegularUnfinished());
        allocationInfoVo.setDelayUnfinished(planInfo == null ? 0 : planInfo.getDelayUnfinished());
        allocationInfoVo.setOvertimeUnfinished(planInfo == null ? 0 : planInfo.getOvertimeUnfinished());
        allocationInfoVo.setFinished(planInfo == null ? 0 : planInfo.getFinished());
        allocationInfoVo.setRegularFinished(planInfo == null ? 0 : planInfo.getRegularFinished());
        allocationInfoVo.setDelayFinished(planInfo == null ? 0 : planInfo.getDelayFinished());
        allocationInfoVo.setOvertimeUnfinished(planInfo == null ? 0 : planInfo.getOvertimeUnfinished());
        return allocationInfoVo;
    }
}