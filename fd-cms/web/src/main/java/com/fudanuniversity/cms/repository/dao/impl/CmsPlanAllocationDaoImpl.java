package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsPlanAllocationDao;
import com.fudanuniversity.cms.repository.mapper.CmsPlanAllocationMapper;
import com.fudanuniversity.cms.repository.entity.CmsPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsPlanAllocationQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsPlanAllocationDao 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Repository
public class CmsPlanAllocationDaoImpl implements CmsPlanAllocationDao {

    @Resource
    private CmsPlanAllocationMapper cmsPlanAllocationMapper;

    @Override
    public int insert(CmsPlanAllocation cmsPlanAllocation) {
        Assert.notNull(cmsPlanAllocation, "保存对象不能为空");

        validateEntity(cmsPlanAllocation);

        return cmsPlanAllocationMapper.insert(cmsPlanAllocation);
    }

    @Override
    public int bulkUpsert(List<CmsPlanAllocation> cmsPlanAllocationList){
        Assert.notEmpty(cmsPlanAllocationList, "保存对象列表不能为空");

        for(CmsPlanAllocation cmsPlanAllocation : cmsPlanAllocationList){
            validateEntity(cmsPlanAllocation);
        }

        return cmsPlanAllocationMapper.bulkUpsert(cmsPlanAllocationList);
    }

    private void validateEntity(CmsPlanAllocation cmsPlanAllocation) {
        Assert.notNull(cmsPlanAllocation.getUserId(), "学生id必须有值");
        Assert.notNull(cmsPlanAllocation.getPlanId(), "培养方案id必须有值");
        Assert.notNull(cmsPlanAllocation.getPlanStartTime(), "培养方案开始时间必须有值");
        Assert.notNull(cmsPlanAllocation.getPlanEndTime(), "培养方案到期时间必须有值");
        Assert.notNull(cmsPlanAllocation.getSpendDays(), "计划天数必须有值");
        Assert.notNull(cmsPlanAllocation.getDelayDays(), "延期天数必须有值");
        Assert.notNull(cmsPlanAllocation.getCreateTime(), "创建时间必须有值");
    }

    @Override
    public int updateById(CmsPlanAllocation cmsPlanAllocation) {
        Assert.notNull(cmsPlanAllocation, "更新对象不能为空");
        Assert.notNull(cmsPlanAllocation.getId(), "更新对象id不能为空");
        Assert.notNull(cmsPlanAllocation.getModifyTime(), "更新时间必须有值");

        return cmsPlanAllocationMapper.updateById(cmsPlanAllocation);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsPlanAllocationMapper.deleteById(id);
    }

    @Override
    public List<CmsPlanAllocation> selectListByParam(CmsPlanAllocationQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsPlanAllocationMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsPlanAllocationQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsPlanAllocationMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsPlanAllocationQuery query) {
        query.validateBaseArgument();

        if (query.getId() == null
                && query.getGtId() == null
               && query.getUserId() == null
               && query.getPlanId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }
    }
}