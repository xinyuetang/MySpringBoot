package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanAllocationDao;
import com.fudanuniversity.cms.repository.mapper.CmsStudyPlanAllocationMapper;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanAllocationDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Repository
public class CmsStudyPlanAllocationDaoImpl implements CmsStudyPlanAllocationDao {

    @Resource
    private CmsStudyPlanAllocationMapper cmsStudyPlanAllocationMapper;

    @Override
    public int insert(CmsStudyPlanAllocation cmsStudyPlanAllocation) {
        Assert.notNull(cmsStudyPlanAllocation, "保存对象不能为空");

        validateEntity(cmsStudyPlanAllocation);

        return cmsStudyPlanAllocationMapper.insert(cmsStudyPlanAllocation);
    }

    @Override
    public int bulkUpsert(List<CmsStudyPlanAllocation> cmsStudyPlanAllocationList){
        Assert.notEmpty(cmsStudyPlanAllocationList, "保存对象列表不能为空");

        for(CmsStudyPlanAllocation cmsStudyPlanAllocation : cmsStudyPlanAllocationList){
            validateEntity(cmsStudyPlanAllocation);
        }

        return cmsStudyPlanAllocationMapper.bulkUpsert(cmsStudyPlanAllocationList);
    }

    private void validateEntity(CmsStudyPlanAllocation cmsStudyPlanAllocation) {
        Assert.notNull(cmsStudyPlanAllocation.getUserId(), "学生id必须有值");
        Assert.notNull(cmsStudyPlanAllocation.getPlanId(), "培养方案id必须有值");
        Assert.notNull(cmsStudyPlanAllocation.getPlanStartTime(), "培养方案开始时间必须有值");
        Assert.notNull(cmsStudyPlanAllocation.getPlanEndTime(), "培养方案到期时间必须有值");
        Assert.notNull(cmsStudyPlanAllocation.getSpendDays(), "计划天数必须有值");
        Assert.notNull(cmsStudyPlanAllocation.getDelayDays(), "延期天数必须有值");
        Assert.notNull(cmsStudyPlanAllocation.getCreateTime(), "创建时间必须有值");
    }

    @Override
    public int updateById(CmsStudyPlanAllocation cmsStudyPlanAllocation) {
        Assert.notNull(cmsStudyPlanAllocation, "更新对象不能为空");
        Assert.notNull(cmsStudyPlanAllocation.getId(), "更新对象id不能为空");
        Assert.notNull(cmsStudyPlanAllocation.getModifyTime(), "更新时间必须有值");

        return cmsStudyPlanAllocationMapper.updateById(cmsStudyPlanAllocation);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsStudyPlanAllocationMapper.deleteById(id);
    }

    @Override
    public List<CmsStudyPlanAllocation> selectListByParam(CmsStudyPlanAllocationQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanAllocationMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsStudyPlanAllocationQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanAllocationMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsStudyPlanAllocationQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getUserId() == null
               && query.getPlanId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}