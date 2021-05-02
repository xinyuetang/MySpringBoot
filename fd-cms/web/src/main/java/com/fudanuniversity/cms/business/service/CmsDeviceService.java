package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsDevice;
import com.fudanuniversity.cms.repository.query.CmsDeviceQuery;

/**
 * CmsDeviceService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsDeviceService {

    /**
     * 保存处理
     */
    void saveCmsDevice(CmsDevice cmsDevice);

    /**
     * 根据id更新处理
     */
    void updateCmsDeviceById(CmsDevice cmsDevice);

    /**
     * 根据id删除处理
     */
    void deleteCmsDeviceById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsDevice> queryPagingResultByParam(CmsDeviceQuery query);

}