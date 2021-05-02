package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsBulletinState;
import com.fudanuniversity.cms.repository.query.CmsBulletinStateQuery;

/**
 * CmsBulletinStateService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsBulletinStateService {

    /**
     * 保存处理
     */
    void saveCmsBulletinState(CmsBulletinState cmsBulletinState);

    /**
     * 根据id更新处理
     */
    void updateCmsBulletinStateById(CmsBulletinState cmsBulletinState);

    /**
     * 根据id删除处理
     */
    void deleteCmsBulletinStateById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsBulletinState> queryPagingResult(CmsBulletinStateQuery query);

}