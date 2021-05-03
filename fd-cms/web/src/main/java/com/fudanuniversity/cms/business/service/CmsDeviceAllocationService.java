package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationApplyVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationReturnVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsDeviceAllocation;

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

    void applyDeviceAllocation(CmsDeviceAllocationApplyVo allocationApplyVo);

    void returnDeviceAllocation(CmsDeviceAllocationReturnVo allocationReturnVo);

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
    PagingResult<CmsDeviceAllocationVo> queryPagingResult(Paging paging);
}