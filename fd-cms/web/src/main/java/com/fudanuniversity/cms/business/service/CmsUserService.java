package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.user.CmsUserMngVo;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;

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
    void confirmUserPrivilege(String stuId, UserRoleEnum privilege);

    /**
     * 保存处理
     */
    void saveCmsUser(CmsUserMngVo userAddVo);

    /**
     * 根据id更新处理
     */
    void updateCmsUserById(CmsUserMngVo userUpdateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsUserById(Long userId);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsUser> queryPagingResult(CmsUserQuery query);

}