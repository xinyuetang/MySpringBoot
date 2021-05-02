package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsPlanDao;
import com.fudanuniversity.cms.repository.mapper.CmsPlanMapper;
import com.fudanuniversity.cms.repository.entity.CmsPlan;
import com.fudanuniversity.cms.repository.query.CmsPlanQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsPlanDao 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Repository
public class CmsPlanDaoImpl implements CmsPlanDao {

    @Resource
    private CmsPlanMapper cmsPlanMapper;

    @Override
    public int insert(CmsPlan cmsPlan) {
        Assert.notNull(cmsPlan, "保存对象不能为空");

        validateEntity(cmsPlan);

        return cmsPlanMapper.insert(cmsPlan);
    }

    @Override
    public int bulkUpsert(List<CmsPlan> cmsPlanList){
        Assert.notEmpty(cmsPlanList, "保存对象列表不能为空");

        for(CmsPlan cmsPlan : cmsPlanList){
            validateEntity(cmsPlan);
        }

        return cmsPlanMapper.bulkUpsert(cmsPlanList);
    }

    private void validateEntity(CmsPlan cmsPlan) {
        Assert.notNull(cmsPlan.getCommon(), "是否公共任务必须有值");
        Assert.notNull(cmsPlan.getKeshuo(), "是否科硕任务必须有值");
        Assert.notNull(cmsPlan.getStudyType(), "就读类型必须有值");
        Assert.notNull(cmsPlan.getIndex(), "序号必须有值");
        Assert.hasText(cmsPlan.getName(), "名称不能为空");
        Assert.notNull(cmsPlan.getSpendDays(), "计划天数必须有值");
        Assert.notNull(cmsPlan.getCreateTime(), "创建时间必须有值");
    }

    @Override
    public int updateById(CmsPlan cmsPlan) {
        Assert.notNull(cmsPlan, "更新对象不能为空");
        Assert.notNull(cmsPlan.getId(), "更新对象id不能为空");
        Assert.notNull(cmsPlan.getModifyTime(), "更新时间必须有值");

        return cmsPlanMapper.updateById(cmsPlan);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsPlanMapper.deleteById(id);
    }

    @Override
    public List<CmsPlan> selectListByParam(CmsPlanQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsPlanMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsPlanQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsPlanMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsPlanQuery query) {
        query.validateBaseArgument();

        if (query.getId() == null
                && query.getGtId() == null
               && query.getCommon() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }
    }
}