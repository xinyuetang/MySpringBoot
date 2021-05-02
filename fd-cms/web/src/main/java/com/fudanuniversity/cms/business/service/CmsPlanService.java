package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsPlan;
import com.fudanuniversity.cms.repository.query.CmsPlanQuery;

/**
 * CmsPlanService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsPlanService {

    /**
     * 保存处理
     */
    void saveCmsPlan(CmsPlan cmsPlan);

    /**
     * 根据id更新处理
     */
    void updateCmsPlanById(CmsPlan cmsPlan);

    /**
     * 根据id删除处理
     */
    void deleteCmsPlanById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsPlan> queryPagingResult(CmsPlanQuery query);

}