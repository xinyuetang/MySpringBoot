package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.query.CmsRecorderQuery;

/**
 * CmsRecorderService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsRecorderService {

    /**
     * 保存处理
     */
    void saveCmsRecorder(CmsRecorder cmsRecorder);

    /**
     * 根据id更新处理
     */
    void updateCmsRecorderById(CmsRecorder cmsRecorder);

    /**
     * 根据id删除处理
     */
    void deleteCmsRecorderById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsRecorder> queryPagingResultByParam(CmsRecorderQuery query);

}