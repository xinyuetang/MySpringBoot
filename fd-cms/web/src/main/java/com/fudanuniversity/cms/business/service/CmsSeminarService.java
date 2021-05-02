package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsSeminar;
import com.fudanuniversity.cms.repository.query.CmsSeminarQuery;

/**
 * CmsSeminarService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsSeminarService {

    /**
     * 保存处理
     */
    void saveCmsSeminar(CmsSeminar cmsSeminar);

    /**
     * 根据id更新处理
     */
    void updateCmsSeminarById(CmsSeminar cmsSeminar);

    /**
     * 根据id删除处理
     */
    void deleteCmsSeminarById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsSeminar> queryPagingResultByParam(CmsSeminarQuery query);

}