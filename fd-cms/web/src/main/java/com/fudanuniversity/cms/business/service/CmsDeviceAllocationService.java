package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsDeviceAllocation;
import com.fudanuniversity.cms.repository.query.CmsDeviceAllocationQuery;

/**
 * CmsDeviceAllocationService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsDeviceAllocationService {

    /**
     * 保存处理
     */
    void saveCmsDeviceAllocation(CmsDeviceAllocation cmsDeviceAllocation);

    /**
     * 根据id更新处理
     */
    void updateCmsDeviceAllocationById(CmsDeviceAllocation cmsDeviceAllocation);

    /**
     * 根据id删除处理
     */
    void deleteCmsDeviceAllocationById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsDeviceAllocation> queryPagingResult(CmsDeviceAllocationQuery query);

}