package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;

import java.util.List;

/**
 * CmsStudyPlanAllocationDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
public interface CmsStudyPlanAllocationDao {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanAllocation cmsStudyPlanAllocation);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanAllocation> cmsStudyPlanAllocationList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsStudyPlanAllocation cmsStudyPlanAllocation);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsStudyPlanAllocation> selectListByParam(CmsStudyPlanAllocationQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsStudyPlanAllocationQuery query);

}
