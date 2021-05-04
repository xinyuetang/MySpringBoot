package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanDao;
import com.fudanuniversity.cms.repository.mapper.CmsStudyPlanMapper;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Repository
public class CmsStudyPlanDaoImpl implements CmsStudyPlanDao {

    @Resource
    private CmsStudyPlanMapper cmsStudyPlanMapper;

    @Override
    public int insert(CmsStudyPlan cmsStudyPlan) {
        Assert.notNull(cmsStudyPlan, "保存对象不能为空");

        validateEntity(cmsStudyPlan);

        return cmsStudyPlanMapper.insert(cmsStudyPlan);
    }

    @Override
    public int bulkUpsert(List<CmsStudyPlan> cmsStudyPlanList){
        Assert.notEmpty(cmsStudyPlanList, "保存对象列表不能为空");

        for(CmsStudyPlan cmsStudyPlan : cmsStudyPlanList){
            validateEntity(cmsStudyPlan);
        }

        return cmsStudyPlanMapper.bulkUpsert(cmsStudyPlanList);
    }

    private void validateEntity(CmsStudyPlan cmsStudyPlan) {
        Assert.notNull(cmsStudyPlan.getCommon(), "是否公共任务必须有值");
        Assert.notNull(cmsStudyPlan.getKeshuo(), "是否科硕任务必须有值");
        Assert.notNull(cmsStudyPlan.getStudyType(), "就读类型必须有值");
        Assert.notNull(cmsStudyPlan.getIndex(), "序号必须有值");
        Assert.hasText(cmsStudyPlan.getName(), "名称不能为空");
        Assert.notNull(cmsStudyPlan.getSpendDays(), "计划天数必须有值");
        Assert.notNull(cmsStudyPlan.getCreateTime(), "创建时间必须有值");
    }

    @Override
    public int updateById(CmsStudyPlan cmsStudyPlan) {
        Assert.notNull(cmsStudyPlan, "更新对象不能为空");
        Assert.notNull(cmsStudyPlan.getId(), "更新对象id不能为空");
        Assert.notNull(cmsStudyPlan.getModifyTime(), "更新时间必须有值");

        return cmsStudyPlanMapper.updateById(cmsStudyPlan);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsStudyPlanMapper.deleteById(id);
    }

    @Override
    public List<CmsStudyPlan> selectListByParam(CmsStudyPlanQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsStudyPlanQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsStudyPlanQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getCommon() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}