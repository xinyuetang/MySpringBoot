package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.user.*;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * CmsUserService
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
public interface CmsUserService {

    /**
     * 确认用户权限
     */
    void confirmUserPrivilege(String stuId, UserRoleEnum privilege);

    /**
     * 保存处理
     */
    void saveCmsUser(CmsUserAddVo userAddVo);

    /**
     * 根据id更新处理
     */
    void updateCmsUserById(CmsUserUpdateVo updateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsUserById(Long userId);

    /**
     * 分页查询数据列表
     */
    List<CmsUserVo> queryUserList(CmsUserQueryVo queryVo, Paging paging);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsUserVo> queryPagingResult(CmsUserQueryVo queryVo, Paging paging);

    /**
     * 查询用户详细信息
     */
    CmsUserDetailVo queryUserDetail(@NotNull String stuId);
}