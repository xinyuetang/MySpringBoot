package com.fudanuniversity.cms.combi.service;

import com.fudanuniversity.cms.commons.enums.RoleEnum;
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
     * 获取所有用户
     */
    PagingResult<CmsUser> getAllUsers(Paging paging);

    /**
     * 确认用户权限
     */
    void confirmUserPrivilege(String stuId, RoleEnum privilege);

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

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsUser> queryPagingResultByParam(CmsUserQuery query);

}