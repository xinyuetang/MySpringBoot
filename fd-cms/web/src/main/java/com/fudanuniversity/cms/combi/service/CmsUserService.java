package com.fudanuniversity.cms.combi.service;

import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.inner.entity.CmsUser;
import com.fudanuniversity.cms.inner.query.CmsUserQuery;

/**
 * CmsUserService
 * <p>
 * Created by tidu at 2021-05-01
 */
public interface CmsUserService {

    /**
     * 保存处理
     */
    void saveCmsUser(CmsUser cmsUser);

    /**
     * 根据id更新处理
     */
    void updateCmsUserById(CmsUser cmsUser);

    /**
     * 根据id删除处理
     */
    void deleteCmsUserById(Long id);

    PagingResult<CmsUser> getAllUsers(Paging paging);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsUser> queryPagingResultByParam(CmsUserQuery query);

}