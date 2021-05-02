package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsPlanAllocationQuery;

import java.util.List;

/**
 * CmsPlanAllocationDao
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsPlanAllocationDao {

    /**
     * 保存处理
     */
    int insert(CmsPlanAllocation cmsPlanAllocation);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsPlanAllocation> cmsPlanAllocationList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsPlanAllocation cmsPlanAllocation);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsPlanAllocation> selectListByParam(CmsPlanAllocationQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsPlanAllocationQuery query);

}
