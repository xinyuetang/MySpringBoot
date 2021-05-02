package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsPlanAllocationQuery;

/**
 * CmsPlanAllocationService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsPlanAllocationService {

    /**
     * 保存处理
     */
    void saveCmsPlanAllocation(CmsPlanAllocation cmsPlanAllocation);

    /**
     * 根据id更新处理
     */
    void updateCmsPlanAllocationById(CmsPlanAllocation cmsPlanAllocation);

    /**
     * 根据id删除处理
     */
    void deleteCmsPlanAllocationById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsPlanAllocation> queryPagingResult(CmsPlanAllocationQuery query);

}