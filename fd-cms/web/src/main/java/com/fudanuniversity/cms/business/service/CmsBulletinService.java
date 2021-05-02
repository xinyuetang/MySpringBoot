package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsBulletin;
import com.fudanuniversity.cms.repository.query.CmsBulletinQuery;

/**
 * CmsBulletinService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsBulletinService {

    /**
     * 保存处理
     */
    void saveCmsBulletin(CmsBulletin cmsBulletin);

    /**
     * 根据id更新处理
     */
    void updateCmsBulletinById(CmsBulletin cmsBulletin);

    /**
     * 根据id删除处理
     */
    void deleteCmsBulletinById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsBulletin> queryPagingResultByParam(CmsBulletinQuery query);

}